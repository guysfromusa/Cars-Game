package com.guysfromusa.carsgame.config;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.guysfromusa.carsgame.model.Direction.EAST;
import static com.guysfromusa.carsgame.model.Direction.NORTH;

@SpringBootApplication
public class CarsGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsGameApplication.class, args);
	}

	@Bean
	public CommandLineRunner populate(MapRepository mapRepository, CarRepository carRepository, GameRepository gameRepository) {
		return args -> {
			MapEntity map = new MapEntity("map1", "1");
			mapRepository.save(map);

			MapEntity map2 = new MapEntity("map2", "1,1,1\n0,0,1");
			mapRepository.save(map2);

			GameEntity game = new GameEntity();
			game.setName("game1");
			game.setMap(map);
			gameRepository.save(game);

			CarEntity car = new CarEntity();
			car.setName("car1");
			car.setGame(game);
			car.setDirection(NORTH);
			car.setPositionY(0);
			car.setPositionX(0);
			carRepository.save(car);

			CarEntity car2 = new CarEntity();
			car2.setName("car2");
			car2.setDirection(EAST);
			carRepository.save(car2);

		};
	}


}
