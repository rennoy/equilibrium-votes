package com.eosdt.dpos.config;

import com.eosdt.dpos.domain.*;
import com.eosdt.dpos.service.EOSElectionFromCsv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.BinaryOperator;

@Configuration
public class ElectionConfiguration {

    private final EOSElectionFromCsv eosElectionFromCsv;

    public ElectionConfiguration(EOSElectionFromCsv eosElectionFromCsv) {
        this.eosElectionFromCsv = eosElectionFromCsv;
    }

    /**
     * We are assuming as a start, for the exercise,
     * that the EOS election is set and every candidate received 1 vote
     * and the 1 vote represents all the EOS staked for the candidate in the election.
     *
     * 1 candidate = 1 vote = total EOS staked for the candidate
     * @return Election object
     */
    @Bean
    public Election EOSElection() {

        Flux<Vote> votesFlux =
                eosElectionFromCsv.getCandidates().map(candidate ->
                        new Vote(
                                new Elector(candidate.getName()),
                                new Candidate[] {candidate},
                                candidate.getStakes()));


        Vote[] votes = votesFlux.toStream().toArray(Vote[]::new);
        Integer totalStake = votesFlux.toStream()
                .mapToInt(Vote::getStake)
                .sum();


        ElectionParams electionParams =  
                new ElectionParams(
                        100,
                        100,
                        1,
                        0,
                        21,
                        totalStake
                );

        return new Election(
                electionParams,
                votes
        );
    }

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
