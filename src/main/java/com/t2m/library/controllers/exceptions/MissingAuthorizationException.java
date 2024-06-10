package com.t2m.library.controllers.exceptions;

import java.security.InvalidParameterException;

public class MissingAuthorizationException extends InvalidParameterException {
    private static final long serialVersionUID = 1L;

	public MissingAuthorizationException(String message) {
        super(message);
    }
}
