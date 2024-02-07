package com.foodapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MandatoryFieldException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a NotFoundException with the specified error message.
	 *
	 * @param message The error message associated with the exception.
	 */
	public MandatoryFieldException(String message) {
		super(message);
		logger.error("Mandatory Field exception occurred - Message: {}", message);
	}
}