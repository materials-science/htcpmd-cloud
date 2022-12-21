package cn.poryoung.htcpmd.common.exception;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.common.pojo.CommonResp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Log log = LogFactory.get();

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResp businessException(BusinessException e) {
        log.error("Thrown business exception, errCode is: {}, errMsg is: {}. LogMsg: {}", e.getCode(), e.getMsg(), e.getLogMsg());
        return BeanUtil.copyProperties(e, CommonResp.class);
    }

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResp systemException(SystemException e) {
        log.error("Thrown system exception, errCode is: {}, errMsg is: {}. LogMsg: {}", e.getCode(), e.getMsg(), e.getLogMsg());
        return BeanUtil.copyProperties(e, CommonResp.class);
    }
}
