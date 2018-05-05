package com.guysfromusa.carsgame.v1.validator;

import com.guysfromusa.carsgame.v1.model.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.verify;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@RunWith(MockitoJUnitRunner.class)
public class MapValidatorTest {

    @Mock
    private Errors errors;

    @InjectMocks
    private MapValidator validator;

    @Test
    public void shouldReportErrorWhenNotReachable() {
        validator.validate(new Map("foo", "0,1\n1,0"), errors);

        verify(errors).rejectValue("content", "map.reachable", "The map is not reachable.");
    }

    @Test
    public void shouldReportErrorWhenNotIsSquare() {
        validator.validate(new Map("foo", "1,1,1,1\n0,0"), errors);

        verify(errors).rejectValue("content", "map.square", "The map is not square.");
    }
}