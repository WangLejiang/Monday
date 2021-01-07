package com.wanglejiang.common.exception;

import java.text.MessageFormat;


public class RepeatRequestException extends RuntimeException {

    private static final long serialVersionUID = 1558778506589427470L;

    public RepeatRequestException() {
        super();
    }

    public RepeatRequestException(String message) {
        super(message);
    }

    public RepeatRequestException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

}
