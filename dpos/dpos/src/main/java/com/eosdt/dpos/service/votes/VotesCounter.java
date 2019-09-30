package com.eosdt.dpos.service.votes;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidateDesc;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.election.ElectionConfiguration;
import com.eosdt.dpos.service.EOSElectionFromCsv;
import com.eosdt.dpos.service.converter.ElectionToWinningCandidatesConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotesCounter {

    private final ElectionConfiguration electionConfiguration;

    private final EOSElectionFromCsv eosElectionFromCsv;

    private final ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter;

    public VotesCounter(ElectionConfiguration electionConfiguration,
                        EOSElectionFromCsv eosElectionFromCsv,
                        ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter) {
        this.electionConfiguration = electionConfiguration;
        this.eosElectionFromCsv = eosElectionFromCsv;
        this.electionToWinningCandidatesConverter = electionToWinningCandidatesConverter;
    }

    /**
     * Summarize new votes to fit the EOS election round format.
     * Reminder: the EOS election object is assumed to be:
     * 1 candidate = 1 voter for the BP total stake
     * @param election Equilibrium election
     * @return
     */
    public Election addedRewards(Election election) {

        List<Candidate> equilibriumWinners = electionToWinningCandidatesConverter.convert(election);

        Integer equilibriumStake = electionConfiguration.equilibriumElection().getStake();

        Map<CandidateDesc, Integer> stakes = eosElectionFromCsv.getCandidates()
                                                .toStream()
                                                .collect(Collectors.toMap(c -> c.getCandidateDesc(),
                                                                          c -> {
                                                    if (equilibriumWinners.contains(c)) {
                                                        return c.getStakes() + equilibriumStake;
                                                    } else {
                                                        return c.getStakes();
                                                    }
                                                }));

        // TODO: create the new election using the new stakes -

        return null;
    }

}
