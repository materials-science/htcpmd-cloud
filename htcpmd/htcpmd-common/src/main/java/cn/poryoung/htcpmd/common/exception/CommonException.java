package cn.poryoung.htcpmd.common.exception;

import cn.poryoung.htcpmd.common.constant.ErrorStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommonException extends Exception {

    protected String code;
    protected String msg;

    protected String logMsg;

    public CommonException() {
    }

    public CommonException(ErrorStatusEnum statusEnum) {
        super();
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public CommonException(ErrorStatusEnum statusEnum, String logMsg) {
        super();
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
        this.logMsg = logMsg;
    }

    public CommonException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public CommonException(String code, String msg, String logMsg) {
        super();
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }
}
