package com.commercesystem.image.exception;

import com.commercesystem.image.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ImageNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleImageNotFound(Exception ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiResponse<>(
                            false,
                            ex.getMessage(),
                            null
                        )
                );
    }
}
