package com.wanglejiang.common.exception;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.text.MessageFormat;

/**
 * 参数异常
 */
public class InvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String parameterName, String message) {
        super(parameterName + message);
    }

    public InvalidParameterException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

    public static void checkIsEmpty(Object o, String message, Object... params) {
        boolean isBlankStr = o instanceof String && StrUtil.isBlank((String) o);
        if (isBlankStr || ObjectUtil.isEmpty(o)) {
            throw new InvalidParameterException(message, params);
        }
    }
    
}
