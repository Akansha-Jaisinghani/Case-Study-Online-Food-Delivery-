package com.foodapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This exception is thrown when a resource is not found in the application. It
 * extends RuntimeException and provides an error message to be used in the
 * response.
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class NotFoundException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a NotFoundException with the specified error message.
	 *
	 * @param message The error message associated with the exception.
	 */
	public NotFoundException(String message) {
		super(message);
		logger.error("A not found exception occurred - Message: {}", message);
	}

}
