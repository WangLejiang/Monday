package com.wanglejiang.common.exception;

import java.text.MessageFormat;


public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1558778506589427470L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }


}
