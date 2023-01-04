package cn.poryoung.htcpmd.center.constant;

import lombok.Getter;

import static cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant.DLQ_DEFAULT_QUEUE_NAME;
import static cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant.DLQ_DIRECT_EXCHANGE_NAME;

@Getter
public enum HtcpmdDeadLetterMqEnum {
    /**
     * htcpmd center task queue
     */
    DLQ_DEFAULT_QUEUE(DLQ_DIRECT_EXCHANGE_NAME, DLQ_DEFAULT_QUEUE_NAME, DLQ_DEFAULT_QUEUE_NAME);
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

    HtcpmdDeadLetterMqEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
