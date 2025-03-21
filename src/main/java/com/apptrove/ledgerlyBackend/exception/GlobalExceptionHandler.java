package com.apptrove.ledgerlyBackend.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apptrove.ledgerlyBackend.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity< ApiResponse<String>> usernameNotFoundExceptionHandler(UsernameNotFoundException ex) {
		ApiResponse<String> apiResponse = new ApiResponse<String>();
		apiResponse.setRespObject(env.getProperty("login.fail.message"));
		apiResponse.setErrorMsg(ex.getMessage());
		apiResponse.setErrorCd(env.getProperty("login.user.username.not.found.code"));
		
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> exceptionHandler(Exception ex) {
		ApiResponse<String> apiResponse = new ApiResponse<String>();
		apiResponse.setRespObject(env.getProperty("server.internal.error.message"));
		apiResponse.setErrorMsg(ex.getMessage());
		apiResponse.setErrorCd(env.getProperty("internal.server.error.code"));
		
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		ApiResponse<String> apiResponse = new ApiResponse<String>();
		apiResponse.setRespObject(ex.getMessage());
		apiResponse.setErrorMsg(ex.getMessage());
		apiResponse.setErrorCd(env.getProperty("common.request.failed.code"));
		return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.NO_CONTENT);
	}
	
}
