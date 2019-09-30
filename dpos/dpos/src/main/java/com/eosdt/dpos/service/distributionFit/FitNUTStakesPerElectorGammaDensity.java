package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.EquilibriumElectionFromCsv;
import org.apache.commons.math3.distribution.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FitNUTStakesPerElectorGammaDensity implements FitNUTStakesPerElectorDensity {

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    public FitNUTStakesPerElectorGammaDensity(EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public AbstractRealDistribution fit() {

        Flux<Integer> NUTHoldings =
                this.equilibriumElectionFromCsv.getVotes()
                        .map(Vote::getStake);

        double mu = this.equilibriumElectionFromCsv.getVotes().toStream()
                .mapToInt(Vote::getStake)
                .average()
                .orElse(Double.NaN);

        double sigma2 =
                this.equilibriumElectionFromCsv.getVotes().toStream()
                        .mapToInt(Vote::getStake)
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

