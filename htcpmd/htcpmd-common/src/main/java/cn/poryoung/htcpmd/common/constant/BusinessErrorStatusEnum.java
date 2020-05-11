package cn.poryoung.htcpmd.common.constant;

public enum BusinessErrorStatusEnum implements ErrorStatusEnum {
    BUSINESS_ERROR(ErrorStatusPrefixEnum.BUSINESS.addPrefix("1"), "Failed to handle this business!"),

    USER_NOT_FOUND(ErrorStatusPrefixEnum.BUSINESS.addPrefix("2"), "Failed to get the user Info!");

    private String code;
    private String msg;

    BusinessErrorStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
