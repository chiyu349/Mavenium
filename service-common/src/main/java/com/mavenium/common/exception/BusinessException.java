package com.mavenium.common.exception;

import com.mavenium.common.enums.ResultCode;
import lombok.Getter;

/**
 * 业务异常：业务校验不通过时抛出，由全局异常处理器转换为统一响应体
 *
 * @author Mavenium
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 业务状态码 */
    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
