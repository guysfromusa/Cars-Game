package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * Created by Sebastian Mikucki, 28.02.18
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status("NOT_FOUND")
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status("BAD_REQUEST")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status("CONFLICT")
                        .build(),
                HttpStatus.CONFLICT
        );
    }

}
