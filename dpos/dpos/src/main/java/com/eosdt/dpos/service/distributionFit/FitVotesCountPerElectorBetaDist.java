package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.configuration.ElectionConfiguration;
import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.EquilibriumElectionFromCsv;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FitVotesCountPerElectorBetaDist implements FitVotesCountPerElectorDist {

    private final ElectionConfiguration electionConfiguration;

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    public FitVotesCountPerElectorBetaDist(ElectionConfiguration electionConfiguration,
                                           EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.electionConfiguration = electionConfiguration;
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public Map<Integer, Double> fit() {

        final Map<Integer, Double> dist = new HashMap<>();

        Integer maxVotesPerElector = electionConfiguration.equilibriumElection().getNMaxVotesPerElector();

        Flux<Candidate[]> candidates =
                this.equilibriumElectionFromCsv.getVotes()
                        .map(Vote::getCandidates)
                        .filter(cs -> cs.length > 0);

        Double mu =
                candidates
                .toStream()
                .mapToInt(Array::getLength)
                .mapToDouble(i -> ((double) i) / ((double) maxVotesPerElector))
                .average()
                .orElse(Double.NaN);

        double sigma2 =
                candidates
                .map(Array::getLength)
                .toStream()
                .mapToDouble(s ->
                        ( ((double) s) / ((double) maxVotesPerElector) - mu ) *
                                ( ((double) s)  / ((double) maxVotesPerElector) - mu ))
                .average()
                .orElse(Double.NaN);

        double alpha = mu*mu/sigma2 - mu*mu*mu/sigma2 - mu;
        double beta = alpha*(1/mu-1);

        BetaDistribution betaDist = new BetaDistribution(alpha, beta);

        return Flux.range(1, maxVotesPerElector)
                .toStream()
                .collect(Collectors.toMap(
                        i -> i,
                        i -> betaDist.cumulativeProbability(((double)i) / ((double)maxVotesPerElector)) -
                                betaDist.cumulativeProbability(((double)i-1) / ((double)maxVotesPerElector))));


    }
}
