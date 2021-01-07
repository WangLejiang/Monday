package com.wanglejiang.common.exception;

import java.text.MessageFormat;

public class AuthRequiredException extends RuntimeException {

    private static final long serialVersionUID = 1558778506589427470L;

    public AuthRequiredException() {
        super();
    }

    public AuthRequiredException(String message) {
        super(message);
    }

    public AuthRequiredException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

}
