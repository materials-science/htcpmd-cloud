package cn.poryoung.htcpmd.common.constant;

public enum SystemErrorStatusEnum implements ErrorStatusEnum {

    SUCCESS("0", "Success!"),

    USERNAME_OR_PASSWORD_ERROR("1", "Wrong username or password!"),

    ACCOUNT_NOT_ACTIVATED("2", "Account is not activated!"),

    ACCOUNT_DISABLED("3", "Account is disabled!"),

    ACCOUNT_EXPIRED("4", "Account is expired!"),

    ACCOUNT_CREDENTIAL_EXPIRED("5", "Account credentials are expired!"),

    FAILED("500", "操作失败"),

    VALIDATE_FAILED("404", "参数检验失败"),

    UNAUTHORIZED("401", "暂未登录或token已经过期"),

    FORBIDDEN("403", "没有相关权限");

    private String code;
    private String msg;

    SystemErrorStatusEnum(String code, String msg) {
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
