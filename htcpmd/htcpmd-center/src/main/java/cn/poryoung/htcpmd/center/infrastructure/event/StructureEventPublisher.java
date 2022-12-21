package cn.poryoung.htcpmd.center.infrastructure.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdBizJobConstant;
import cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqEnum;
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
        List<String> logArr = new ArrayList<>();
        logArr.add("[INFO] Preparing to publish `event.htcpmd.structure.upload` event.");
        // create a structure upload job
        HtcpmdBizJob bizJob = new HtcpmdBizJob("Upload Structure Job", "event.htcpmd.structure.upload");
        bizJob.setDescription("Upload structure files.");
        bizJob.setStatus(HtcpmdBizJobConstant.StatusEnum.SUBMITTED.getStatus());
        bizJob.setStatusMsg(HtcpmdBizJobConstant.StatusEnum.SUBMITTED.getStatus_msg());
        bizJob.setGroupId(Long.valueOf(CustRequestHelper.getGroupId()));

        htcpmdBizJobDomainService.insertHtcpmdBizJob(bizJob);
        BusinessException.throwExceptionIfTrue(StrUtil.isBlank(bizJob.getId()), BusinessErrorStatusEnum.FAILED_TO_CREATE_A_BIZ_JOB, "创建BizJob失败");
        logArr.add("[INFO] Submit to Task Queue.");
        redisService.hSetAll(HtcpmdBizJobConstant.getRedisObjectKey(bizJob.getId()), BeanUtil.beanToMap(bizJob));
        redisService.setCacheList(HtcpmdBizJobConstant.getRedisLogsKey(bizJob.getId()), logArr);

        event.put("bizJobId", bizJob.getId());
        publishEvent(HtcpmdCenterMqEnum.STRUCTURE_UPLOAD_QUEUE.getExchange(), HtcpmdCenterMqEnum.STRUCTURE_UPLOAD_QUEUE.getRouteKey(), event);

        log.debug("Sent Message to Queue {}.", HtcpmdCenterMqEnum.STRUCTURE_UPLOAD_QUEUE.getName());
    }

    public void publishImportStructureEvent() {

    }
}
