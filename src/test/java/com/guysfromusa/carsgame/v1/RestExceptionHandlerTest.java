package com.guysfromusa.carsgame.v1;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian Mikucki, 28.02.18
 */
public class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    public void shouldReturnApiErrorWhenEntityNotFoundException() {
        //given
        EntityNotFoundException exception = new EntityNotFoundException("not found");
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleEntityNotFound(exception);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("not found", "NOT_FOUND");

    }

    @Test
    public void shouldReturnBadRequestApiError() {
        //given
        IllegalArgumentException exception = new IllegalArgumentException("Illegal argument");
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleBadRequest(exception);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("Illegal argument", "BAD_REQUEST");
    }

    @Test
    public void shouldReturnIntegrityApiError() {
        //given
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Non unique data");
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleDataIntegrityViolation(exception);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("Non unique data", "CONFLICT");
    }

    @Test
    public void shouldReturnServerApiError() {
        //given
        Exception exception = new Exception("sth bad");
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleException(exception);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("sth bad", "INTERNAL_SERVER_ERROR");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowException() {
        //given
        Error exception = new Error("sth bad");
        //when
        restExceptionHandler.handleExecutionException(new UncheckedExecutionException(exception));
    }

    @Test
    public void shouldReturnApiError() {
        //given
        IllegalArgumentException exception = new IllegalArgumentException("sth bad");
        UncheckedExecutionException uncheckedExecutionException = new UncheckedExecutionException(exception);
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleExecutionException(uncheckedExecutionException);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("sth bad", "BAD_REQUEST");
    }

    @Test
    public void shouldReturnServerApiErrorWhenNoMatch() {
        //given
        IllegalStateException exception = new IllegalStateException("Cannot parse");
        UncheckedExecutionException uncheckedExecutionException = new UncheckedExecutionException(exception);
        //when
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleExecutionException(uncheckedExecutionException);
        //then
        assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("Cannot parse", "INTERNAL_SERVER_ERROR");
    }

}