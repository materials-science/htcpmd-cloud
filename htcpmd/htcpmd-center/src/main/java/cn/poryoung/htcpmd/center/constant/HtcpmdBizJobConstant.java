package cn.poryoung.htcpmd.center.constant;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

public class HtcpmdBizJobConstant {
    public static final String REDIS_KEY = "biz_job";

    /* redis cache expire time in second */
    public static final long EXPIRE_TIME = 3600L;

    public static String getRedisObjectKey(String id) {
        return StrUtil.format("{}:{}", REDIS_KEY, id);
    }

    public static String getRedisLogsKey(String id) {
        return StrUtil.format("{}:logs", getRedisObjectKey(id));
    }

    @Getter
    public enum StatusEnum {
        COMPLETED(0, "已完成"),
        DELETED(1, "已删除"),
        FAILED(2, "任务失败"),
        RUNNING(3, "运行中"),
        SUBMITTING(4, "提交中"),
        SUBMITTED(5, "已提交"),
        CREATING(6, "创建中"),
        CREATED(7, "已创建"),
        WAITING(8, "等待中"),
        IDLE(9, "闲置"),
        PAUSED(10, "暂停"),
        SUSPEND(11, "挂起"),
        ;


        private Integer status;

        private String status_msg;

        StatusEnum(Integer status, String status_msg) {
            this.status = status;
            this.status_msg = status_msg;
        }
    }
}
