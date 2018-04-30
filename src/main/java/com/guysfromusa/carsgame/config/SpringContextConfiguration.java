package com.guysfromusa.carsgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@Configuration
@ComponentScan(basePackages = "com.guysfromusa.carsgame")
public class SpringContextConfiguration {

    @Bean
    public ConversionServiceFactoryBean conversionService(Set<Converter> converters) {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(converters);
        return factoryBean;
    }

}
