package cn.poryoung.htcpmd.common.exception;

import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemException extends CommonException {

    public SystemException(SystemErrorStatusEnum statusEnum) {
        super(statusEnum);
    }

    public SystemException(SystemErrorStatusEnum statusEnum, String logMsg) {
        super(statusEnum, logMsg);

    }

    public SystemException(String code, String msg) {
        super(code, msg);

    }

    public SystemException(String code, String msg, String logMsg) {
        super(code, msg, logMsg);
    }

    public static void throwExceptionIfTrue(Boolean flag) throws SystemException {
        if (flag != null && flag) {
            throw new SystemException(SystemErrorStatusEnum.FAILED.getCode(), SystemErrorStatusEnum.FAILED.getMsg());
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String logMsg) throws SystemException {
        if (flag != null && flag) {
            throw new SystemException(SystemErrorStatusEnum.FAILED.getCode(), SystemErrorStatusEnum.FAILED.getMsg(), logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg) throws SystemException {
        if (flag != null && flag) {
            throw new SystemException(code, msg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg, String logMsg) throws SystemException {
        if (flag != null && flag) {
            throw new SystemException(code, msg, logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, SystemErrorStatusEnum statusEnum, String logMsg) throws SystemException {
        if (flag != null && flag) {
            throw new SystemException(statusEnum, logMsg);
        }
    }
}
