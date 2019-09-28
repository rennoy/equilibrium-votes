package com.eosdt.dpos.config;

import com.eosdt.dpos.domain.ElectionParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElectionConfiguration {

    @Bean
    public ElectionParams fiveTenElection() {

        return new ElectionParams(
                100,
                61,
                5,
                2,
                10,
                5000000
        );
    }
}
