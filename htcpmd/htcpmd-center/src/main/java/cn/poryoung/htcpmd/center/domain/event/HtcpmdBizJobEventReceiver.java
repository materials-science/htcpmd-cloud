package cn.poryoung.htcpmd.center.domain.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Htcpmd BizJob消息的处理者
 * Created by PorYoung
 */
@Component
public class HtcpmdBizJobEventReceiver {

    private final Log log = LogFactory.get();
    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;
    @Autowired
    private FileStorageService fileStorageService;

    @RabbitListener(queues = {HtcpmdCenterMqConstant.BIZ_JOB_STATUS_UPDATE_QUEUE_NAME})
    public void jobStatusUpdate(JSONObject jsonObject) throws Exception {
        // todo: dead letter queue and error handler
        log.debug("received msg:", jsonObject.toString());

        HtcpmdBizJob bizJob = BeanUtil.copyProperties(jsonObject.get("bizJob"), HtcpmdBizJob.class);
        List<FileInfo> fileInfoList = jsonObject.getBeanList("fileList", FileInfo.class);
        if (!Objects.isNull(fileInfoList)) {
            fileInfoList.forEach(fileInfo -> {
                fileStorageService.delete(fileInfo);
            });
        }
        if (!Objects.isNull(bizJob)) {
            htcpmdBizJobDomainService.updateHtcpmdBizJob(bizJob);
        }
    }
}
