package com.wanglejiang.common.exception;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1558778506589427470L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

    public static void checkIsEmpty(Object o, String message, Object... params) {
        boolean isBlankStr = o instanceof String && StrUtil.isBlank((String) o);
        if (isBlankStr || ObjectUtil.isEmpty(o)) {
            throw new BusinessException(message, params);
        }
    }

    public static void checkNotEmpty(Object o, String message, Object... params) {
        boolean isNotBlankStr = o instanceof String && StrUtil.isNotBlank((String) o);
        if (isNotBlankStr || ObjectUtil.isNotEmpty(o)) {
            throw new BusinessException(message, params);
        }
    }

}
