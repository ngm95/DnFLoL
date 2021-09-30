package com.project.dnflol.Exception;

import org.springframework.security.core.AuthenticationException;

public class BadCredentailsException extends AuthenticationException {
	public BadCredentailsException(String msg) {
		super(msg);
	}
}
