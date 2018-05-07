package com.guysfromusa.carsgame.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = "com.guysfromusa.carsgame")
public class SpringContextConfiguration {

    @Bean
    public ConversionServiceFactoryBean conversionService(Set<Converter> converters) {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(converters);
        return factoryBean;
    }

    @Bean
    public TaskExecutor threadPoolExecutor(@Value("${threads.pool.capacity}") int capacity,
                                           @Value("${threads.pool.max}") int maxPoolSize,
                                           @Value("${threads.pool.coreSize}") int corePoolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(capacity);
        return threadPoolTaskExecutor;
    }

    @Bean
    public ScheduledExecutorService scheduledPollExecutor(){
        return  newSingleThreadScheduledExecutor();
    }
}
