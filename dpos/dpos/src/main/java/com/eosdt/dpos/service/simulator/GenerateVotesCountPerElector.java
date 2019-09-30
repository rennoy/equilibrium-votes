package com.eosdt.dpos.service.simulator;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GenerateVotesCountPerElector implements Generate<Flux<Integer>> {

    @Override
    public Flux<Integer> generate() {
        return null;
    }
}
