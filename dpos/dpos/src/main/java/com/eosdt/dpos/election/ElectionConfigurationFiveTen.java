package com.eosdt.dpos.election;

import com.eosdt.dpos.domain.*;
import com.eosdt.dpos.service.EOSElectionFromCsv;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Profile("election_configuration_five_ten")
public class ElectionConfigurationFiveTen implements ElectionConfiguration {

    private final EOSElectionFromCsv eosElectionFromCsv;

    public ElectionConfigurationFiveTen(EOSElectionFromCsv eosElectionFromCsv) {
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
    public Election EOSElection() {

        Flux<Vote> votesFlux =
                eosElectionFromCsv.getCandidates().map(candidate ->
                        new Vote(
                                new Elector(candidate.getCandidateDesc().getName(),
                                            candidate.getStakes().doubleValue()),
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
                eosElectionFromCsv.getCandidates().toStream().toArray(Candidate[]::new),
                votes
        );
    }

    public ElectionParams equilibriumElection() {

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
