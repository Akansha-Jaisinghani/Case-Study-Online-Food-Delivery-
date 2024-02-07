package com.foodapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This exception is thrown when an empty field is encountered in the
 * application. It extends RuntimeException and provides an error code and error
 * message to be used in the response.
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class EmptyFieldException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(EmptyFieldException.class);
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;

	/**
	 * Constructs an EmptyFieldException with the specified error code and error
	 * message.
	 *
	 * @param errorCode    The error code associated with the exception.
	 * @param errorMessage The error message associated with the exception.
	 */
	public EmptyFieldException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

		logger.error("An empty field exception occurred - ErrorCode: {}, ErrorMessage: {}", errorCode, errorMessage);
	}
}
