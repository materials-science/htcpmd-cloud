package cn.poryoung.htcpmd.common.constant;

public enum BusinessErrorStatusEnum implements ErrorStatusEnum {
    BUSINESS_ERROR(ErrorStatusPrefixEnum.BUSINESS.addPrefix("1"), "Failed to handle this business!"),

    USER_NOT_FOUND(ErrorStatusPrefixEnum.BUSINESS.addPrefix("2"), "Failed to get the user Info!"),

    FILE_UPLOAD_ERROR(ErrorStatusPrefixEnum.BUSINESS.addPrefix("3"), "Failed to upload file!"),

    INVALID_REQUEST_PARAMETERS(ErrorStatusPrefixEnum.BUSINESS.addPrefix("4"), "无效的请求参数!"),

    FAILED_TO_CREATE_A_BIZ_JOB(ErrorStatusPrefixEnum.BUSINESS.addPrefix("5"), "创建业务任务失败"),

    STRUCTURE_NOT_FOUND(ErrorStatusPrefixEnum.BUSINESS.addPrefix("6"), "Failed to get the structure Info!"),

    DUPLICATE_STRUCTURE_TAG(ErrorStatusPrefixEnum.BUSINESS.addPrefix("6"), "Structure tag name is duplicate."),
    ;

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
