package com.mose.taskmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
     // 1. Not Found (404)
     @ExceptionHandler(TaskNotFoundException.class)
     public ResponseEntity<ApiErrorResponse> handleNotFound(TaskNotFoundException ex, HttpServletRequest request) {
          ApiErrorResponse error = new ApiErrorResponse(
               Instant.now(),
               HttpStatus.NOT_FOUND.value(),
               HttpStatus.NOT_FOUND.getReasonPhrase(),
               ex.getMessage(),
               request.getRequestURI()
          );
          return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
     }

     // 2. Bad Requests
     @ExceptionHandler(BusinessRuleException.class)
     public ResponseEntity<ApiErrorResponse> handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
          ApiErrorResponse error = new ApiErrorResponse(
               Instant.now(),
               HttpStatus.BAD_REQUEST.value(),
               HttpStatus.BAD_REQUEST.getReasonPhrase(),
               ex.getMessage(),
               request.getRequestURI()
          );
          return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
     }

     // 3. Catch all unexpected System failures (HTTP 500)
     @ExceptionHandler(Exception.class)
     public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
          ApiErrorResponse error = new ApiErrorResponse(
          Instant.now(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "An unexpected server error occured. ",
          request.getRequestURI()
          );
          return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
     }
}
