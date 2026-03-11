package com.example.auth.exception;

  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.security.access.AccessDeniedException;
  import org.springframework.web.bind.annotation.ControllerAdvice;
  import org.springframework.web.bind.annotation.ExceptionHandler;

  import java.util.HashMap;
  import java.util.Map;

  @ControllerAdvice
  public class GlobalExceptionHandler {

      @ExceptionHandler(AccessDeniedException.class)
      public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
          Map<String, String> response = new HashMap<>();
          response.put("error", "Access Denied");
          response.put("message", "You do not have permission to access this resource");
          return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
      }

      @ExceptionHandler(Exception.class)
      public ResponseEntity<?> handleGlobalException(Exception ex) {
          Map<String, String> response = new HashMap<>();
          response.put("error", "Internal Server Error");
          response.put("message", ex.getMessage());
          return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }\n