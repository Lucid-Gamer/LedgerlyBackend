package com.apptrove.ledgerlyBackend.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apptrove.ledgerlyBackend.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
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
	
	public ResponseEntity<ApiResponse<String>> exceptionHandler(Exception ex) {
		ApiResponse<String> apiResponse = new ApiResponse<String>();
		apiResponse.setRespObject(env.getProperty("server.internal.error.message"));
		apiResponse.setErrorMsg(ex.getMessage());
		apiResponse.setErrorCd(env.getProperty("internal.server.error.code"));
		
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
