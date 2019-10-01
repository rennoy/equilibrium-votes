package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.domain.VotesInit;
import com.eosdt.dpos.election.ElectionConfiguration;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Profile("fit_votes_count_per_elector_beta_dist")
public class FitVotesCountPerElectorBetaDensity implements FitVotesCountPerElectorDensity {

    private final ElectionConfiguration electionConfiguration;

    private final VotesInit votesInit;

    public FitVotesCountPerElectorBetaDensity(ElectionConfiguration electionConfiguration, VotesInit votesInit) {
        this.electionConfiguration = electionConfiguration;
        this.votesInit = votesInit;
    }

    @Override
    public Map<Integer, Double> fit() {

        Integer maxVotesPerElector = electionConfiguration.equilibriumElection().getNMaxVotesPerElector();

        List<Candidate[]> candidates =
                this.votesInit
                        .getVotes()
                        .stream()
                        .map(Vote::getCandidates)
                        .filter(cs -> cs.length > 0)
                        .collect(Collectors.toList());

        Double mu =
                candidates
                .stream()
                .mapToInt(Array::getLength)
                .mapToDouble(i -> ((double) i) / ((double) maxVotesPerElector))
                .average()
                .orElse(Double.NaN);

        double sigma2 =
                candidates
                .stream()
                .map(Array::getLength)
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
