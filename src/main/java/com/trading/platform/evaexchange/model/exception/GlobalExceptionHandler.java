package com.trading.platform.evaexchange.model.exception;

import com.trading.platform.evaexchange.model.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.trading.platform.evaexchange")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePortfolioNotFound(
            PortfolioNotFoundException ex, HttpServletRequest request) {

        log.error("Portfolio not found: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Portfolio Not Found")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ShareNotRegisteredException.class)
    public ResponseEntity<ErrorResponseDto> handleShareNotRegistered(
            ShareNotRegisteredException ex, HttpServletRequest request) {

        log.error("Share not registered: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Share Not Registered")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InsufficientSharesException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientShares(
            InsufficientSharesException ex, HttpServletRequest request) {

        log.error("Insufficient shares: {}", ex.getMessage());

        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Insufficient Shares")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Validation Failed")
                .message(errors.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex, HttpServletRequest request) throws Exception {

        String path = request.getRequestURI();

        // Extra safety check for SpringDoc paths
        if (path.startsWith("/v3/") || path.startsWith("/swagger")) {
            throw ex;
        }

        log.error("Unexpected error occurred: ", ex);

        ErrorResponseDto error = ErrorResponseDto.builder()
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
