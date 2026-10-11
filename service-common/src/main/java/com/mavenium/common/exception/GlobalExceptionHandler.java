package com.mavenium.common.exception;

import com.mavenium.common.entity.ResponseResult;
import com.mavenium.common.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>
 * 将各类异常统一转换为 {@link ResponseResult}，保证异常响应与正常响应格式一致。
 * 约定：HTTP 状态码固定 200，业务结果通过 body.code 表达。
 *
 * @author Mavenium
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 业务异常 */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseResult<Void>> handleBusinessException(BusinessException e) {
        log.warn("业务异常：code={}, message={}", e.getCode(), e.getMessage());
        return ResponseEntity.ok(ResponseResult.fail(e.getCode(), e.getMessage()));
    }

    /** 参数校验异常（@Valid + @RequestBody，或表单绑定） */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ResponseResult<Void>> handleValidationException(Exception e) {
        String message = ResultCode.BAD_REQUEST.getMessage();
        FieldError fieldError = null;
        if (e instanceof MethodArgumentNotValidException ex) {
            fieldError = ex.getBindingResult().getFieldError();
        } else if (e instanceof BindException ex) {
            fieldError = ex.getBindingResult().getFieldError();
        }
        if (fieldError != null) {
            message = fieldError.getField() + " " + fieldError.getDefaultMessage();
        }
        log.warn("参数校验失败：{}", message);
        return ResponseEntity.ok(ResponseResult.fail(ResultCode.BAD_REQUEST, message));
    }

    /** 其他未知异常 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<Void>> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.ok(ResponseResult.fail(ResultCode.ERROR));
    }
}
