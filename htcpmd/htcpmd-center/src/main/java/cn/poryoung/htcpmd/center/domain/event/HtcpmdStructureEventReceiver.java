package cn.poryoung.htcpmd.center.domain.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdBizJobConstant;
import cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant;
import cn.poryoung.htcpmd.center.domain.entity.EsStructure;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructure;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureAttr;
import cn.poryoung.htcpmd.center.domain.repository.EsStructureRepository;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureAttrDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureDomainService;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.redis.service.RedisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class HtcpmdStructureEventReceiver {
    private final Log log = LogFactory.get();

    @Value("${spring.application.name}")
    private String APP_NAME;

    @Autowired
    private RedisService redisService;

    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;

    @Autowired
    private HtcpmdStructureDomainService htcpmdStructureDomainService;

    @Autowired
    private HtcpmdStructureAttrDomainService htcpmdStructureAttrDomainService;

    @Autowired
    private EsStructureRepository esStructureRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private void pushJobLog(String key, String text) {
        pushJobLog(key, text, "INFO");
    }

    private void pushJobErrorLog(String key, String text) {
        pushJobLog(key, text, "ERROR");
    }

    private void pushJobLog(String key, String text, String type) {
        redisService.lPush(
                key, StrUtil.format(
                        "[{}] [{}] {}", type, APP_NAME, text
                )
        );
    }

    @RabbitListener(queues = {HtcpmdCenterMqConstant.STRUCTURE_UPLOAD_CALLBACK_QUEUE_NAME})
    public void jobStatusUpdate(JSONObject jsonObject) throws Exception {
        log.debug("received msg: {}", JSONUtil.toJsonStr(jsonObject));

        Map headers = jsonObject.getBean("headers", Map.class);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        if (Objects.isNull(dataObj)) {
            log.error("received Data is null.");
            return;
        }

        String bizJobId = dataObj.getStr("bizJobId");
        if (StrUtil.isBlank(bizJobId)) {
            log.error("received BizJobId is null, cannot update job status.");
            return;
        }

        String jobKey = HtcpmdBizJobConstant.getRedisObjectKey(bizJobId);
        String jobLogKey = HtcpmdBizJobConstant.getRedisLogsKey(bizJobId);

        HtcpmdBizJob bizJob = null;
        if (!redisService.hasKey(jobKey)) {
            bizJob = htcpmdBizJobDomainService.selectHtcpmdBizJobById(bizJobId);
            if (Objects.isNull(bizJob)) {
                log.error("BizJob (id=`{}`) is null, cannot update job status.", bizJobId);
                redisService.deleteObject(jobLogKey);
                return;
            }
            redisService.hSetAll(jobKey, BeanUtil.beanToMap(bizJob));
        }

        pushJobLog(jobLogKey, "Received parsed structures, start to store.");
        redisService.hSet(jobKey, "status", HtcpmdBizJobConstant.StatusEnum.RUNNING.getStatus());
        redisService.hSet(jobKey, "statusMsg", HtcpmdBizJobConstant.StatusEnum.RUNNING.getStatus_msg());

        JSONArray jsonArray = dataObj.getJSONArray("structureList");
        if (Objects.nonNull(jsonArray)) {
            Set<HtcpmdStructure> structureList = new HashSet<>(jsonArray.size());
            Set<HtcpmdStructureAttr> structureAttrList = new HashSet<>(jsonArray.size());
            Set<EsStructure> esStructureList = new HashSet<>(jsonArray.size());
            jsonArray.toList(JSONObject.class).forEach(o -> {
                String uuid = IdUtil.simpleUUID();
                // get meta info
                HtcpmdStructure structure = o.toBean(HtcpmdStructure.class);
                structure.setUuid(uuid);
                structure.setGroupId(CustRequestHelper.getGroupId(headers));
                structure.setCreateBy(CustRequestHelper.getValue(SecurityConstants.DETAILS_USER_ID, headers));
//                htcpmdStructureDomainService.insertHtcpmdStructure(structure);
                structureList.add(structure);
                // get attr
                HtcpmdStructureAttr structureAttr = o.getBean("attributes", HtcpmdStructureAttr.class);
                structureAttr.setUuid(uuid);
                structureAttrList.add(structureAttr);
                // set es index
                EsStructure esStructure = BeanUtil.copyProperties(structure, EsStructure.class);
                BeanUtil.copyProperties(structureAttr, esStructure);
                esStructureList.add(esStructure);
            });
            // todo: Manual Transaction
            htcpmdStructureDomainService.insertBatch(structureList);
            htcpmdStructureAttrDomainService.saveAll(structureAttrList);
            esStructureRepository.saveAll(esStructureList);
        } else {
            pushJobLog(jobLogKey, "Received parsed structure list is empty, nothing to do.");
        }

        redisService.hSet(jobKey, "status", HtcpmdBizJobConstant.StatusEnum.COMPLETED.getStatus());
        redisService.hSet(jobKey, "statusMsg", HtcpmdBizJobConstant.StatusEnum.COMPLETED.getStatus_msg());

        try {
            bizJob = BeanUtil.toBean(redisService.hGetAll(jobKey), HtcpmdBizJob.class);
        } catch (Exception e) {
            if (Objects.isNull(bizJob)) {
                bizJob = new HtcpmdBizJob();
                bizJob.setId(bizJobId);
            }
            pushJobLog(jobLogKey, "Failed to get the latest BizJob (id=``) .", bizJobId);
            bizJob.setStatus(HtcpmdBizJobConstant.StatusEnum.COMPLETED.getStatus());
            bizJob.setStatusMsg(HtcpmdBizJobConstant.StatusEnum.COMPLETED.getStatus_msg());
        }
        bizJob.setLogs(ArrayUtil.join(redisService.getCacheList(jobLogKey).toArray(), "\n"));
        redisService.hSet(jobKey, "logs", bizJob.getLogs());

        // todo: 校验是否入库成功
        htcpmdBizJobDomainService.updateHtcpmdBizJob(bizJob);
    }
}
