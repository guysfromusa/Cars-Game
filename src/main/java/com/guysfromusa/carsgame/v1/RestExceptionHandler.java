package com.guysfromusa.carsgame.v1;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import io.vavr.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * Created by Sebastian Mikucki, 28.02.18
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        // TODO: make error more human readable???
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getBindingResult().toString())
                        .status("VALIDATION_FAILED")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Got exception", ex);
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
        log.error("Got exception", ex);
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
        log.error("Got exception", ex);
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status("CONFLICT")
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleThrowable(Throwable ex) {
        log.error("Got exception", ex);
        return new ResponseEntity<>(
                ApiError.builder()
                        .date(LocalDateTime.now())
                        .message(ex.getMessage())
                        .status("INTERNAL_SERVER_ERROR")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(UncheckedExecutionException.class)
    public ResponseEntity<ApiError> handleExecutionException(UncheckedExecutionException ex){
        return Match(ex.getCause()).of(
                Case($(Predicates.instanceOf(IllegalArgumentException.class)), this::handleBadRequest),
                Case($(), this::handleThrowable)
        );
    }

}
