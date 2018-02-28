package com.guysfromusa.carsgame.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.guysfromusa.carsgame.repositories")
@EntityScan(basePackages = "com.guysfromusa.carsgame.entities")
public class DbConfiguration {

}
