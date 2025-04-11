package com.usuarios.NTTData.Infrastructure.configuration;

import com.usuarios.NTTData.Infrastructure.dto.ApiResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponseDto<?> response = new ApiResponseDto<>(null, ex.getMessage());
        return ResponseEntity.badRequest().body(response); // Retorna 400
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiResponseDto<?> response = new ApiResponseDto<>(null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
