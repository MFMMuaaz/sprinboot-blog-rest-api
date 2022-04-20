package com.springboot.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.springboot.blog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	// specific
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
		ErrorDetails error = new ErrorDetails();
		error.setStatus(404);
		error.setMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setDetails(webRequest.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorDetails> handleBlogAppException(BlogAppException exception, WebRequest webRequest) {
		ErrorDetails error = new ErrorDetails();
		error.setStatus(exception.getCode());
		error.setMessage(exception.getMessage());
		error.setTimestamp(new Date());
		error.setDetails(webRequest.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	// global
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> GlobalExceptionHandler(Exception exception, WebRequest webRequest) {
		ErrorDetails error = new ErrorDetails();
		error.setStatus(500);
		error.setMessage("Internal Server Error.");
		error.setTimestamp(new Date());
		error.setDetails(webRequest.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
