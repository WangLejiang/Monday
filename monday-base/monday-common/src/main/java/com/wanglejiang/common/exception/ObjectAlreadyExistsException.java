package com.wanglejiang.common.exception;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.text.MessageFormat;


public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException() {
        super();
    }

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }

    public ObjectAlreadyExistsException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

    public static void checkNotEmpty(Object o, String message, Object... params) {
        boolean isNotBlankStr = o instanceof String && StrUtil.isNotBlank((String) o);
        if (isNotBlankStr || ObjectUtil.isNotEmpty(o)) {
            throw new ObjectAlreadyExistsException(message, params);
        }
    }

}
