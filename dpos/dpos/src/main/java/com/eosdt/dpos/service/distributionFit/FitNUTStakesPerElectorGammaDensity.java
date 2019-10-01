package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.config.EquilibriumElectionFromCsv;
import com.eosdt.dpos.domain.Vote;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FitNUTStakesPerElectorGammaDensity implements FitNUTStakesPerElectorDensity {

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    public FitNUTStakesPerElectorGammaDensity(EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public AbstractRealDistribution fit() {

        List<Integer> NUTHoldings =
                this.equilibriumElectionFromCsv.getVotesInit()
                        .getVotes()
                        .stream()
                        .map(Vote::getStake)
                        .collect(Collectors.toList());

        double mu = NUTHoldings.stream()
                .mapToInt(h -> h)
                .average()
                .orElse(Double.NaN);

        double sigma2 =
                NUTHoldings.stream()
                        .mapToInt(h -> h)
                        .mapToDouble(s ->
                                ( ((double) s) - mu ) *
                                        ( ((double) s) - mu ))
                        .average()
                        .orElse(Double.NaN);

        double alpha = mu * mu / sigma2;
        double beta = sigma2 / mu;

        return new GammaDistribution(alpha, beta);
    }
}

