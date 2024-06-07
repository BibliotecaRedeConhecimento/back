package com.t2m.library.controllers.exceptions;

import java.security.InvalidParameterException;

public class MissingAuthorizationException extends InvalidParameterException {
    public MissingAuthorizationException(String message) {
        super(message);
    }
}
