package com.guysfromusa.carsgame.v1.integration;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.v1.CarApiAware;
import com.guysfromusa.carsgame.v1.GameApiAware;
import com.guysfromusa.carsgame.v1.MapApiAware;
import com.guysfromusa.carsgame.v1.UndoMovementApiAware;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Point;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = SpringContextConfiguration.class)
public class UndoMovementIntegrationTest implements CarApiAware, MapApiAware, GameApiAware, UndoMovementApiAware {

    private static final String gameName = "game1";
    private static final String carName = "opel";
    private static final String mapName = "map";

    @Inject
    private TestRestTemplate template;

    @Inject
    private AsyncTaskExecutor taskExecutor;

    @Inject
    private CarRepository carRepository;

    @Test
    @Sql("/sql/clean.sql")
    public void shouldStartGameAndDoTwoSetpBack() {
        //given
        final String mapContent = "0,1,1,0,0\n" +
                "0,0,1,0,0\n" +
                "0,0,1,0,0\n" +
                "0,0,1,1,0\n" +
                "0,0,0,1,0\n";


        //when
        addNewMap(template, new Map(mapName, mapContent));
        addNewCar(template, carName, NORMAL);
        startNewGame(template, gameName, mapName);
        //todo do some move

        List<Future<Car>> addedCar = List.<Callable<Car>>of(
                () -> addCarToGame(template, carName, gameName, new Point(2, 0)))
                .map(taskExecutor::submit);



        //then todo : finish it after all
//        Optional<CarEntity> carAfterBack = carRepository.findByName(carName);
//        assertThat(carAfterBack.isPresent());
//        assertThat(carAfterBack.get()).extracting(CarEntity::getPositionX, CarEntity::getPositionY, CarEntity::getDirection).containsExactly(1, 2, Direction.SOUTH);
    }
}
