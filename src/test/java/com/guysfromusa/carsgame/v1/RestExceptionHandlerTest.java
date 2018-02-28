package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import org.junit.Test;
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
}