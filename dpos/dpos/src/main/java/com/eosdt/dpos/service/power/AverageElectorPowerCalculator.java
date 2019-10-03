package com.eosdt.dpos.service.power;

import com.eosdt.dpos.config.SimulationsConfig;
import com.eosdt.dpos.domain.CandidatesInit;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Elector;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.random.UniqueRandomCandidates;
import com.eosdt.dpos.service.votes.RewardsPerVoterCalculator;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.stream.IntStream;

@Service
public class AverageElectorPowerCalculator {

    private final CandidatesInit candidatesInit;

    private final UniqueRandomCandidates uniqueRandomCandidates;

    private final RewardsPerVoterCalculator rewardsPerVoterCalculator;

    public AverageElectorPowerCalculator(CandidatesInit candidatesInit,
                                         UniqueRandomCandidates uniqueRandomCandidates,
                                         RewardsPerVoterCalculator rewardsPerVoterCalculator) {
        this.candidatesInit = candidatesInit;
        this.uniqueRandomCandidates = uniqueRandomCandidates;
        this.rewardsPerVoterCalculator = rewardsPerVoterCalculator;
    }

    /**
     * calculates summary statistics for the power of an elector with specific NUT holdings
     * @param election Equilibrium election
     * @param holdings e.g. elector's NUT holdings
     * @return the summary statistics
     */
    public DoubleSummaryStatistics calculate(Election election, Double holdings) {

        Integer nSimulations = SimulationsConfig.N_SIMULATIONS;

        Elector elector = new Elector(
                SimulationsConfig.VIRTUAL_ELECTOR_NAME,
                holdings
        );

        return IntStream.range(0, nSimulations)
                .parallel()
                .mapToDouble(sim -> {
                    Vote vote = new Vote(elector,
                            uniqueRandomCandidates.generate(
                                    election.getElectionParams().getNMaxVotesPerElector()),
                            (int) Math.round(holdings)
                    );

                    return this.rewardsPerVoterCalculator.calculate(election, vote);
                })
                .summaryStatistics();


    }

}
