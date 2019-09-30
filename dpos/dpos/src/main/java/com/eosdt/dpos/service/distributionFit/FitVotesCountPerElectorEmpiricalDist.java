package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.configuration.ElectionConfiguration;
import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.EquilibriumElectionFromCsv;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FitVotesCountPerElectorEmpiricalDist implements FitVotesCountPerElectorDist {

    private final ElectionConfiguration electionConfiguration;

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    /**
     * Empirical distribution based on an Equilibrium vote
     * @param electionConfiguration
     * @param equilibriumElectionFromCsv
     */
    public FitVotesCountPerElectorEmpiricalDist(ElectionConfiguration electionConfiguration,
                                                EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.electionConfiguration = electionConfiguration;
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public Map<Integer, Double> fit() {

        final Map<Integer, Double> dist = new HashMap<>();

        Flux<Candidate[]> candidates =
                this.equilibriumElectionFromCsv.getVotes()
                        .map(Vote::getCandidates)
                        .filter(cs -> cs.length > 0);

        Long tot =
                candidates.toStream()
                .count();

        return candidates
                    .map(Array::getLength)
                    .groupBy(v -> v)
                    .flatMap(Flux::collectList)
                    .toStream()
                    .collect(Collectors.toMap(
                            l -> l.get(0), l ->((Integer) l.size()).doubleValue() / tot.doubleValue()));

    }

}
