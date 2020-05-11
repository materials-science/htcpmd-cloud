package cn.poryoung.htcpmd.common.exception;

import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BussinessException extends CommonException {
    public BussinessException(BusinessErrorStatusEnum statusEnum) {
        super(statusEnum);
    }

    public BussinessException(BusinessErrorStatusEnum statusEnum, String logMsg) {
        super(statusEnum, logMsg);
    }

    public BussinessException(String code, String msg) {
        super(code, msg);
    }

    public BussinessException(String code, String msg, String logMsg) {
        super(code, msg, logMsg);
    }

    public static void throwExceptionIfTrue(Boolean flag) throws BussinessException {
        if (flag != null && flag) {
            throw new BussinessException(BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), BusinessErrorStatusEnum.BUSINESS_ERROR.getMsg());
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String logMsg) throws BussinessException {
        if (flag != null && flag) {
            throw new BussinessException(BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), BusinessErrorStatusEnum.BUSINESS_ERROR.getMsg(), logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg) throws BussinessException {
        if (flag != null && flag) {
            throw new BussinessException(code, msg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg, String logMsg) throws BussinessException {
        if (flag != null && flag) {
            throw new BussinessException(code, msg, logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, BusinessErrorStatusEnum statusEnum, String logMsg) throws BussinessException {
        if (flag != null && flag) {
            throw new BussinessException(statusEnum, logMsg);
        }
    }
}
