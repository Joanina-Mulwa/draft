package com.chess.draftpesa.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotActiveException extends AuthenticationException {

    public UserNotActiveException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotActiveException(String msg) {
        super(msg);
    }
}
