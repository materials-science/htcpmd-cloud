package cn.poryoung.htcpmd.common.exception;

import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends CommonException {
    public BusinessException(BusinessErrorStatusEnum statusEnum) {
        super(statusEnum);
    }

    public BusinessException(BusinessErrorStatusEnum statusEnum, String logMsg) {
        super(statusEnum, logMsg);
    }

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(String code, String msg, String logMsg) {
        super(code, msg, logMsg);
    }

    public static void throwExceptionIfTrue(Boolean flag) throws BusinessException {
        if (flag != null && flag) {
            throw new BusinessException(BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), BusinessErrorStatusEnum.BUSINESS_ERROR.getMsg());
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String logMsg) throws BusinessException {
        if (flag != null && flag) {
            throw new BusinessException(BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), BusinessErrorStatusEnum.BUSINESS_ERROR.getMsg(), logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg) throws BusinessException {
        if (flag != null && flag) {
            throw new BusinessException(code, msg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, String code, String msg, String logMsg) throws BusinessException {
        if (flag != null && flag) {
            throw new BusinessException(code, msg, logMsg);
        }
    }

    public static void throwExceptionIfTrue(Boolean flag, BusinessErrorStatusEnum statusEnum, String logMsg) throws BusinessException {
        if (flag != null && flag) {
            throw new BusinessException(statusEnum, logMsg);
        }
    }
}
