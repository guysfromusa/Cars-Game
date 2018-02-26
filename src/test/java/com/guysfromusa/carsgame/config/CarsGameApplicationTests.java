package com.guysfromusa.carsgame.config;

import com.guysfromusa.carsgame.v1.GamesResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarsGameApplicationTests {

	@Inject
	private GamesResource gamesResource;

	@Test
	public void contextLoads() {
		assertThat(gamesResource).isNotNull();
	}

}
