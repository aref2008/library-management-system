package com.library.demo.exception;

import com.library.demo.model.ErrorLog;
import com.library.demo.repository.ErrorLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private final ErrorLogRepository errorLogRepository;

    public GlobalExceptionHandler(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    // Handle generic exceptions (500 errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        // Extract Patreon ID from the session or request context
        Integer patreonId = (Integer) request.getSession().getAttribute("patreonId");

        // Create and save the error log
        ErrorLog errorLog = new ErrorLog();
        errorLog.setExceptionMessage(ex.getMessage());
        errorLog.setStackTrace(getStackTraceAsString(ex)); // Convert stack trace to string
        errorLog.setPatreonId(patreonId); // Set the Patreon ID
        errorLog.setGuid(UUID.randomUUID().toString()); // Generate a random GUID for the error log
        errorLogRepository.save(errorLog);

        // Prepare response message with GUID
        Map<String, Object> response = new HashMap<>();
        response.put("message", "An internal server error occurred. Please contact the admin with this GUID: " + errorLog.getGuid());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle validation exceptions (400 errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation errors");

        // Collect validation errors
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        response.put("errors", errors);

        // Create and save the error log for validation errors
        ErrorLog errorLog = new ErrorLog();
        errorLog.setExceptionMessage("Validation errors occurred");
        errorLog.setStackTrace(getStackTraceAsString(ex)); // Optionally include stack trace
        errorLog.setPatreonId((Integer) request.getSession().getAttribute("patreonId")); // Set the Patreon ID
        errorLog.setGuid(UUID.randomUUID().toString()); // Generate a random GUID for the error log
        errorLogRepository.save(errorLog);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String getStackTraceAsString(Exception ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
