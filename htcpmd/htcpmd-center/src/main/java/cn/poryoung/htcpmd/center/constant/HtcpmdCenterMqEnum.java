package cn.poryoung.htcpmd.center.constant;

import lombok.Getter;

import static cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant.*;

@Getter
public enum HtcpmdCenterMqEnum {
    /**
     * htcpmd center task queue
     */
    EVENT_DEFAULT_QUEUE(EVENT_DIRECT_EXCHANGE_NAME, EVENT_DEFAULT_QUEUE_NAME, EVENT_DEFAULT_QUEUE_NAME),
    BIZ_JOB_STATUS_UPDATE_QUEUE(EVENT_DIRECT_EXCHANGE_NAME, BIZ_JOB_STATUS_UPDATE_QUEUE_NAME, BIZ_JOB_STATUS_UPDATE_QUEUE_NAME),
    STRUCTURE_UPLOAD_QUEUE(EVENT_DIRECT_EXCHANGE_NAME, STRUCTURE_UPLOAD_QUEUE_NAME, STRUCTURE_UPLOAD_QUEUE_NAME),
    STRUCTURE_UPLOAD_CALLBACK_QUEUE(EVENT_DIRECT_EXCHANGE_NAME, STRUCTURE_UPLOAD_CALLBACK_QUEUE_NAME, STRUCTURE_UPLOAD_CALLBACK_QUEUE_NAME),
    STRUCTURE_IMPORT_QUEUE(EVENT_DIRECT_EXCHANGE_NAME, STRUCTURE_IMPORT_QUEUE_NAME, STRUCTURE_IMPORT_QUEUE_NAME);
    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    HtcpmdCenterMqEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
