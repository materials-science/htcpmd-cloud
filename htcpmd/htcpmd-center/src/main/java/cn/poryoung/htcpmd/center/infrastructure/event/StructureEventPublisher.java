package cn.poryoung.htcpmd.center.infrastructure.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdBizJobConstant;
import cn.poryoung.htcpmd.center.constant.HtcpmdEventMqEnum;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import cn.poryoung.htcpmd.common.util.EventPublisher;
import com.ruoyi.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StructureEventPublisher extends EventPublisher {

    private static final Log log = LogFactory.get();

    @Autowired
    private RedisService redisService;

    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;

    public void publishUploadStructureEvent(Map<String, Object> event) throws BusinessException, SystemException {
        publishImportStructureEvent(HtcpmdEventMqEnum.STRUCTURE_UPLOAD_QUEUE, event, "Upload Structure Job", "Upload structure files.");
    }

    public void publishImportStructureEvent(String type, Map<String, Object> event) throws BusinessException {
        event.put("type", type);
        publishImportStructureEvent(HtcpmdEventMqEnum.STRUCTURE_IMPORT_QUEUE, event, "Import Structure Job", "Import structure files.");
    }

    private void publishImportStructureEvent(HtcpmdEventMqEnum queue, Map<String, Object> event, String jobName, String jobDesc) throws BusinessException {
        List<String> logArr = new ArrayList<>();
        String jobType = StrUtil.format("event.{}", queue.getRouteKey());
        logArr.add(StrUtil.format("[INFO] Preparing to publish `{}` event.", jobType));
        // create a structure upload job
        HtcpmdBizJob bizJob = newBizJob(jobName, jobType, jobDesc);
        logArr.add("[INFO] Submit to Task Queue.");
        redisService.hSetAll(HtcpmdBizJobConstant.getRedisObjectKey(bizJob.getId()), BeanUtil.beanToMap(bizJob));
        redisService.setCacheList(HtcpmdBizJobConstant.getRedisLogsKey(bizJob.getId()), logArr);

        event.put("bizJobId", bizJob.getId());
        publishEvent(queue.getExchange(), queue.getRouteKey(), event);

        log.debug("Sent Message to Queue {}.", queue.getName());
    }

    private HtcpmdBizJob newBizJob(String name, String type, String desc) throws BusinessException {
        HtcpmdBizJob bizJob = new HtcpmdBizJob(name, type);
        bizJob.setDescription(desc);
        bizJob.setStatus(HtcpmdBizJobConstant.StatusEnum.SUBMITTED.getStatus());
        bizJob.setStatusMsg(HtcpmdBizJobConstant.StatusEnum.SUBMITTED.getStatus_msg());
        bizJob.setGroupId(CustRequestHelper.getGroupId());

        htcpmdBizJobDomainService.insertHtcpmdBizJob(bizJob);
        BusinessException.throwExceptionIfTrue(StrUtil.isBlank(bizJob.getId()), BusinessErrorStatusEnum.FAILED_TO_CREATE_A_BIZ_JOB, "创建BizJob失败");

        return bizJob;
    }
}
