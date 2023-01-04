package cn.poryoung.htcpmd.center.domain.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
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
import cn.poryoung.htcpmd.center.constant.HtcpmdStructureConstant;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.entity.es.EsStructure;
import cn.poryoung.htcpmd.center.domain.entity.mongo.HtcpmdStructureDoc;
import cn.poryoung.htcpmd.center.domain.entity.mongo.StructureDiagramDoc;
import cn.poryoung.htcpmd.center.domain.entity.mongo.StructureTagDoc;
import cn.poryoung.htcpmd.center.domain.repository.EsStructureRepository;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureDocRepository;
import cn.poryoung.htcpmd.center.domain.repository.StructureDiagramDocRepository;
import cn.poryoung.htcpmd.center.domain.repository.StructureTagDocRepository;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagRelDomainService;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.redis.service.RedisService;
import lombok.var;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private HtcpmdStructureTagDomainService htcpmdStructureTagDomainService;

    @Autowired
    private HtcpmdStructureTagRelDomainService htcpmdStructureTagRelDomainService;

    @Autowired
    private StructureTagDocRepository structureTagDocRepository;

    @Autowired
    private HtcpmdStructureDocRepository htcpmdStructureDocRepository;

    @Autowired
    private StructureDiagramDocRepository structureDiagramDocRepository;

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

    @RabbitListener(queues = {HtcpmdCenterMqConstant.STRUCTURE_UPLOAD_CALLBACK_QUEUE_NAME, HtcpmdCenterMqConstant.STRUCTURE_IMPORT_CALLBACK_QUEUE_NAME})
    @Transactional
    public void jobStatusUpdate(JSONObject jsonObject) throws Exception {
        log.debug("received msg: {}", JSONUtil.toJsonStr(jsonObject));
        Map headers = jsonObject.getBean("headers", Map.class);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        if (Objects.isNull(dataObj)) {
            log.error("received Data is null.");
            return;
        }

        final String bizJobId = dataObj.getStr("bizJobId");
        if (StrUtil.isBlank(bizJobId)) {
            log.error("received BizJobId is null, cannot update job status.");
            return;
        }

        final String jobKey = HtcpmdBizJobConstant.getRedisObjectKey(bizJobId);
        final String jobLogKey = HtcpmdBizJobConstant.getRedisLogsKey(bizJobId);
        final String groupId = CustRequestHelper.getGroupId(headers);
        final String userId = CustRequestHelper.getValue(SecurityConstants.DETAILS_USER_ID, headers);

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

        // set tags if exist
        List<StructureTagDoc> structureTagList = null;
        List<String> tagNameList = dataObj.getBeanList("tags", String.class);
        if (CollectionUtil.isNotEmpty(tagNameList)) {
            structureTagList = tagNameList.stream().map(name -> {
                return structureTagDocRepository.findStructureTagDocByNameAndGroupId(name, groupId);
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }

        JSONArray jsonArray = dataObj.getJSONArray("structureList");
        if (Objects.nonNull(jsonArray) && jsonArray.size() > 0) {
            List<HtcpmdStructureDoc> structureList = new ArrayList<>(jsonArray.size());
            List<EsStructure> esStructureList = new ArrayList<>(jsonArray.size());
            for (var o : jsonArray.toList(JSONObject.class)) {
                String uuid = IdUtil.simpleUUID();
                // get meta info
                HtcpmdStructureDoc structure = o.toBean(HtcpmdStructureDoc.class);
                structure.setUuid(uuid);
                structure.setGroupId(groupId);
                structure.setCreateBy(userId);
                structure.setCreateTime(DateTime.now());
                structure.setVerNo(1L);
                if (CollectionUtil.isNotEmpty(structureTagList)) {
                    structure.setTags(structureTagList);
                }
                structureList.add(structure);
                // set es index
                EsStructure esStructure = BeanUtil.copyProperties(structure, EsStructure.class);
                esStructureList.add(esStructure);
            }
            // todo: Manual Transaction
            htcpmdStructureDocRepository.saveAll(structureList);
            esStructureRepository.saveAll(esStructureList);
            // save diagram data
            List<StructureDiagramDoc> diagramDocList = new ArrayList<>();
            for (var structure : structureList) {
                if (CollectionUtil.isNotEmpty(structure.getBandList())) {
                    structure.getBandList().forEach(o -> {
                        o.setStructureId(structure.getUuid());
                        if (StrUtil.isBlank(o.getField())) {
                            o.setField(HtcpmdStructureConstant.DiagramFiled.band.getName());
                            o.setDescription(HtcpmdStructureConstant.DiagramFiled.band.getDesc());
                        }
                    });
                    CollectionUtil.addAll(diagramDocList, structure.getBandList());
                }
                if (CollectionUtil.isNotEmpty(structure.getPhononList())) {
                    structure.getPhononList().forEach(o -> {
                        o.setStructureId(structure.getUuid());
                        if (StrUtil.isBlank(o.getField())) {
                            o.setField(HtcpmdStructureConstant.DiagramFiled.phonon.getName());
                            o.setDescription(HtcpmdStructureConstant.DiagramFiled.phonon.getDesc());
                        }
                    });
                    CollectionUtil.addAll(diagramDocList, structure.getPhononList());
                }
            }
            if (CollectionUtil.isNotEmpty(diagramDocList)) {
                structureDiagramDocRepository.saveAll(diagramDocList);
            }
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

        redisService.expire(jobKey, HtcpmdBizJobConstant.EXPIRE_TIME);
        redisService.expire(jobLogKey, HtcpmdBizJobConstant.EXPIRE_TIME);

        // todo: 消息推送
    }
}