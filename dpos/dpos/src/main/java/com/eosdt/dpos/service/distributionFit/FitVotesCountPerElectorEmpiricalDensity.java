package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.config.EquilibriumElectionFromCsv;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Profile("fit_votes_count_per_elector_empirical_dist")
@Primary
public class FitVotesCountPerElectorEmpiricalDensity implements FitVotesCountPerElectorDensity {

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    /**
     * Empirical distribution based on an Equilibrium vote
     * @param equilibriumElectionFromCsv service to get a static view of the Equilibrium election -
     *                                   used to fit distributions
     */
    public FitVotesCountPerElectorEmpiricalDensity(EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public Map<Integer, Double> fit() {

        List<Candidate[]> candidates =
                this.equilibriumElectionFromCsv.getVotesInit()
                        .getVotes()
                        .stream()
                        .map(Vote::getCandidates)
                        .filter(cs -> cs.length > 0)
                        .collect(Collectors.toList());

        return Flux.fromIterable(candidates)
                    .map(Array::getLength)
                    .groupBy(v -> v)
                    .flatMap(Flux::collectList)
                    .toStream()
                    .collect(Collectors.toMap(
                            l -> l.get(0), l ->((Integer) l.size()).doubleValue() / ((double) candidates.size())));

    }

}
