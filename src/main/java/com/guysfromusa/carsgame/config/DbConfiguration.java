package com.guysfromusa.carsgame.config;

import com.guysfromusa.carsgame.entities.Map;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.guysfromusa.carsgame.repositories")
@EntityScan(basePackages = "com.guysfromusa.carsgame.entities")
public class DbConfiguration {

    @Bean
    public CommandLineRunner populate(MapRepository mapRepository) {
        return args -> {
            Map map = new Map("map1");
            mapRepository.save(map);
        };
    }

}