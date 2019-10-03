package com.eosdt.dpos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomConfig {

    @Bean
    Random getRandom() {
        return new Random();
    }

}
