package com.foodapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This exception is thrown when a null field is encountered in the application.
 * It extends RuntimeException and provides an error message and code to be used
 * in the response.
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class NullFieldException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(NullFieldException.class);
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;

	/**
	 * Constructs a NullFieldException with the specified error code and message.
	 *
	 * @param errorCode    The error code associated with the exception.
	 * @param errorMessage The error message associated with the exception.
	 */
	public NullFieldException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

		logger.error("A null field exception occurred - ErrorCode: {}, ErrorMessage: {}", errorCode, errorMessage);
	}
}
