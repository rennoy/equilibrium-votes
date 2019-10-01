package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.domain.VotesInit;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FitNUTStakesPerElectorGammaDensity implements FitNUTStakesPerElectorDensity {

    private final VotesInit votesInit;

    public FitNUTStakesPerElectorGammaDensity(VotesInit votesInit) {
        this.votesInit = votesInit;
    }

    @Override
    public AbstractRealDistribution fit() {

        List<Integer> NUTHoldings =
                this.votesInit
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

