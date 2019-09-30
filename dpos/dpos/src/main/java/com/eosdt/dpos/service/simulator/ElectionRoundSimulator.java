package com.eosdt.dpos.service.simulator;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Elector;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.election.ElectionConfiguration;
import com.eosdt.dpos.service.distributionFit.FitNUTStakesPerElectorDensity;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ElectionRoundSimulator {

    private final Random random;

    private final ElectionConfiguration electionConfiguration;

    private final VotesCountPerElectorGenerator votesCountPerElectorGenerator;

    private final FitNUTStakesPerElectorDensity fitNUTStakesPerElectorDensity;

    public ElectionRoundSimulator(Random random,
                                  ElectionConfiguration electionConfiguration,
                                  VotesCountPerElectorGenerator votesCountPerElectorGenerator,
                                  FitNUTStakesPerElectorDensity fitNUTStakesPerElectorDensity) {
        this.random = random;
        this.electionConfiguration = electionConfiguration;
        this.votesCountPerElectorGenerator = votesCountPerElectorGenerator;
        this.fitNUTStakesPerElectorDensity = fitNUTStakesPerElectorDensity;
    }

    /**
     * Simulates Equilibrium voting round
     * @return the election object
     */
    public Election simulate() {

        // Get the number of electors

        Integer nbreElectors = electionConfiguration.equilibriumElection().getNElectors();

        // Get the candidates

        Candidate[] candidates = electionConfiguration.EOSElection().getCandidates();

        // Simulate the votes

        AbstractRealDistribution NUTholdingsDist = fitNUTStakesPerElectorDensity.fit();

        Vote[] votes = this.votesCountPerElectorGenerator.generate(nbreElectors)

            .map(votesCount -> {

                // Simulate the elector's holdings assuming a "normal distribution" - distribution TBD

                double NUTholdings = NUTholdingsDist.sample();


                // The elector's name is random

                Elector elector = new Elector(
                        UUID.randomUUID().toString(),
                        NUTholdings
                );

                // An elector who has belo-0 NUT holdings can't vote.

                if (NUTholdings < 2.0) {
                    return new Vote(elector,
                            new Candidate[] {},
                            0);
                }

                // The elector's votes are random - TODO: can't vote for the same candidate twice: quick fix: distinct()

                Candidate[] votedFor = IntStream.range(1, 2*votesCount)
                        .mapToObj(i -> random.nextInt(candidates.length-1))
                        .distinct()
                        .limit(votesCount)
                        .map(r -> candidates[r])
                        .toArray(Candidate[]::new);

                return new Vote(
                        elector,
                        votedFor,
                        (int) Math.round(NUTholdings));
            })
            .onErrorContinue((t, e) -> t.printStackTrace())
            .filter(Objects::nonNull)
            .toStream()
            .toArray(Vote[]::new);

        // Return the election object

        return new Election(electionConfiguration.equilibriumElection(),
                candidates,
                votes);
    }
}
