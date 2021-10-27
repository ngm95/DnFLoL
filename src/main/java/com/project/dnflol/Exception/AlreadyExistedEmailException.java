package com.project.dnflol.Exception;

public class AlreadyExistedEmailException extends RuntimeException {
	public AlreadyExistedEmailException(String msg) {
		super(msg);
	}
}
