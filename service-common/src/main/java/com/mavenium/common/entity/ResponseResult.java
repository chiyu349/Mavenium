package com.mavenium.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mavenium.common.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应体
 * <p>
 * 所有微服务接口统一返回该结构，前端只需判断 {@code success / code}。
 * 约定：HTTP 状态码固定 200，业务结果通过 {@code code} 表达（见 {@link ResultCode}）。
 *
 * @author Mavenium
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 业务状态码：200 成功，其余为失败（见 ResultCode） */
    private Integer code;

    /** 提示信息 */
    private String message;

    /** 是否成功，前端可直接判断 */
    private Boolean success;

    /** 业务数据 */
    private T data;

    /** 服务端时间戳 */
    private Long timestamp;

    private ResponseResult(Integer code, String message, Boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ---------------- 成功 ----------------

    public static <T> ResponseResult<T> ok() {
        return build(ResultCode.SUCCESS, null);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return build(ResultCode.SUCCESS, data);
    }

    public static <T> ResponseResult<T> ok(String message, T data) {
        return build(ResultCode.SUCCESS.getCode(), message, true, data);
    }

    // ---------------- 失败 ----------------

    public static <T> ResponseResult<T> fail() {
        return build(ResultCode.ERROR, null);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return build(ResultCode.ERROR.getCode(), message, false, null);
    }

    public static <T> ResponseResult<T> fail(ResultCode resultCode) {
        return build(resultCode.getCode(), resultCode.getMessage(), false, null);
    }

    public static <T> ResponseResult<T> fail(ResultCode resultCode, String message) {
        return build(resultCode.getCode(), message, false, null);
    }

    public static <T> ResponseResult<T> fail(Integer code, String message) {
        return build(code, message, false, null);
    }

    // ---------------- 内部构造 ----------------

    private static <T> ResponseResult<T> build(ResultCode resultCode, T data) {
        boolean success = resultCode == ResultCode.SUCCESS;
        return build(resultCode.getCode(), resultCode.getMessage(), success, data);
    }

    private static <T> ResponseResult<T> build(Integer code, String message, Boolean success, T data) {
        return new ResponseResult<>(code, message, success, data);
    }
}
