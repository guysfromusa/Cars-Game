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

import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static com.guysfromusa.carsgame.model.Direction.WEST;

@SpringBootApplication
public class CarsGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsGameApplication.class, args);
	}

	@Bean
	public CommandLineRunner populate(MapRepository mapRepository, CarRepository carRepository, GameRepository gameRepository) {
		return args -> {
			MapEntity map = new MapEntity("map1", "1,1,1");
			mapRepository.save(map);

			GameEntity game = new GameEntity();
			game.setName("game1");
			game.setMap(map);
			gameRepository.save(game);

			CarEntity car = new CarEntity();
			car.setName("car1");
			car.setGame(game);
			car.setDirection(NORTH);
			carRepository.save(car);

			CarEntity car2 = new CarEntity();
			car2.setName("car2");
			car2.setDirection(WEST);
			carRepository.save(car2);

			CarEntity car3 = new CarEntity();
			car3.setName("car3");
			car3.setDirection(WEST);
			car3.setCrashed(true);
			carRepository.save(car3);

		};
	}


}
