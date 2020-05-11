package cn.poryoung.htcpmd.common.pojo;

import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import lombok.Data;

@Data
public class CommonResp<T> {
    private String code = SystemErrorStatusEnum.SUCCESS.getCode();

    private String msg = SystemErrorStatusEnum.SUCCESS.getMsg();

    private T data;

    public CommonResp(T data) {
        this.data = data;
    }

    public CommonResp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResp() {
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResp<T> success(T data) {
        return new CommonResp<T>(SystemErrorStatusEnum.SUCCESS.getCode(), SystemErrorStatusEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResp<T> success(T data, String message) {
        return new CommonResp<T>(SystemErrorStatusEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResp<T> failed(SystemErrorStatusEnum errorCode) {
        return new CommonResp<T>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> CommonResp<T> failed(String errorCode, String message) {
        return new CommonResp<T>(errorCode, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResp<T> failed(String message) {
        return new CommonResp<T>(SystemErrorStatusEnum.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResp<T> failed() {
        return failed(SystemErrorStatusEnum.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResp<T> validateFailed() {
        return failed(SystemErrorStatusEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResp<T> validateFailed(String message) {
        return new CommonResp<T>(SystemErrorStatusEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResp<T> unauthorized(T data) {
        return new CommonResp<T>(SystemErrorStatusEnum.UNAUTHORIZED.getCode(), SystemErrorStatusEnum.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResp<T> forbidden(T data) {
        return new CommonResp<T>(SystemErrorStatusEnum.FORBIDDEN.getCode(), SystemErrorStatusEnum.FORBIDDEN.getMsg(), data);
    }
}
