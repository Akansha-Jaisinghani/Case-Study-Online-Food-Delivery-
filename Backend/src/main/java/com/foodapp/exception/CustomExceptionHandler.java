package com.foodapp.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	@ExceptionHandler(AlreadyPresentException.class)
	public ResponseEntity<String> handleAlreadyPresent(AlreadyPresentException ape, WebRequest request){
		logger.error("HTTP request method not supported: {}", ape.getMessage());
		return new ResponseEntity<String>("Item already exists", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(MandatoryFieldException.class)
	public ResponseEntity<String> handleAlreadyPresent(MandatoryFieldException ape, WebRequest request){
		logger.error("HTTP request method not supported: {}", ape.getMessage());
		return new ResponseEntity<String>("Input field is mandatory", HttpStatus.BAD_REQUEST);
	}
	/**
	 * Handles the EmptyFieldException by returning an appropriate error response.
	 *
	 * @param emptyFieldException The exception to be handled.
	 * @return ResponseEntity containing the error message and HTTP status code.
	 */
	@ExceptionHandler(EmptyFieldException.class)
	public ResponseEntity<String> handleEmptyInputException(EmptyFieldException emptyFieldException) {
		logger.error("An empty field exception occurred: {}", emptyFieldException.getMessage());
		return new ResponseEntity<String>("Input field is empty,Please look into it", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles the NoSuchElementException by returning an appropriate error
	 * response.
	 *
	 * @param noSuchElementException The exception to be handled.
	 * @return ResponseEntity containing the error message and HTTP status code.
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
		logger.error("A no such element exception occurred: {}", noSuchElementException.getMessage());
		return new ResponseEntity<String>("No value is present in DB,Please change your request", HttpStatus.NOT_FOUND);
	}
	

	/**
	 * Handles the HttpRequestMethodNotSupportedException by returning an
	 * appropriate error response.
	 *
	 * @param ex      The exception to be handled.
	 * @param headers The HTTP headers.
	 * @param status  The HTTP status code.
	 * @param request The WebRequest.
	 * @return ResponseEntity containing the error message and HTTP status code.F
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCodeException status, WebRequest request) {
		logger.error("HTTP request method not supported: {}", ex.getMessage());
		return new ResponseEntity<Object>("Please,change your HTTP method type", HttpStatus.NOT_FOUND);
	}
	

}
