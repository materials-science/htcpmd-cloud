package cn.poryoung.htcpmd.common.constant;

import cn.hutool.core.util.StrUtil;

public enum ErrorStatusPrefixEnum {

    SYSTEM(0, ""),
    BUSINESS(1, "BUSINESS"),
    GATEWAY(2, "GATEWAY");

    private Integer code;
    private String prefix;

    ErrorStatusPrefixEnum(Integer code, String prefix) {
        this.code = code;
        this.prefix = prefix;
    }

    public String addPrefix(Object val) {
        return StrUtil.format("{}_{}", this.getPrefix(), String.valueOf(val));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
