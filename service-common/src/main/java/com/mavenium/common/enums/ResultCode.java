package com.mavenium.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一业务状态码
 *
 * @author Mavenium
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /** 成功 */
    SUCCESS(200, "操作成功"),

    /** 请求参数错误 */
    BAD_REQUEST(400, "请求参数错误"),

    /** 未登录或登录已过期 */
    UNAUTHORIZED(401, "未登录或登录已过期"),

    /** 无访问权限 */
    FORBIDDEN(403, "无访问权限"),

    /** 请求资源不存在 */
    NOT_FOUND(404, "请求资源不存在"),

    /** 系统内部错误 */
    ERROR(500, "系统内部错误");

    private final Integer code;

    private final String message;
}
