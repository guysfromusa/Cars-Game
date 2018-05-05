package com.guysfromusa.carsgame.v1.validator;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.v1.model.Map;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@Component
public class MapValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Map map = (Map) target;

        // TODO: add validation: is square

        Integer[][] board = GameMapUtils.getMapMatrixFromContent(map.getContent());

        if (!GameMapUtils.isReachable(board)) {
            errors.rejectValue("content", "map.reachable",
                    "The map is not reachable.");
        }
    }
}