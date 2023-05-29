package com.chess.draftpesa.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotLoggedInException extends AuthenticationException {

    public UserNotLoggedInException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotLoggedInException(String msg) {
        super(msg);
    }
}
