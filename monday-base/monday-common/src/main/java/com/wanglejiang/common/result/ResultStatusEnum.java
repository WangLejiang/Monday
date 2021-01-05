package com.wanglejiang.common.result;

import lombok.Getter;

public enum ResultStatusEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    OBJECT_ALREADY_EXISTS(305, "对象已存在"),
    CALCULATE_EXCEPTION(400, "计算式异常"),
    BUSINESS_EXCEPTION(500, "业务异常"),
    SYSTEM_EXCEPTION(600, "系统异常"),
    REPEAT_REQUEST_EXCEPTION(700, "重复请求异常"),
    INVALID_PARAM(300, "无效参数"),
    FEIGN_EXCEPTION(1000, "远程调用异常"),
    AUTHENTICATION_EXCEPTION(401, "认证异常"),
    AUTH_REQUIRED_EXCEPTION(403, "需要重新登陆"),
    FILE_EXEEDS_SIZE_EXCEPTION(308, "文件过大异常"),
    UNKNOWN_EXCEPTION(800, "未知异常"),
    SAVE_FAIL(801, "保存失败"),
    DELETE_FAIL(802, "删除失败"),
    UPDATE_FAIL(803, "修改失败");

    @Getter
    private final int code;
    @Getter
    private final String message;

    ResultStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
