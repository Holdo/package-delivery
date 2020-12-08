package com.test.exception;

/**
 * Basic exception class for validation exceptions.
 */
public class ValidationException extends Exception {

	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

}
