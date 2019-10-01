package com.eosdt.dpos.service.votes;

import com.eosdt.dpos.domain.Candidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsPerSingleVoteCalculator {

    /**
     * Calculate the extra reward accrued to the candidate, given a classification (EOS result)
     * @param candidate whose vote is for
     * @param stake e.g. Equilibrium EOS stake
     * @param rankedCandidates EOS Round candidates classified
     * @return
     */
    public Double calculate(Candidate candidate, Integer stake, List<Candidate> rankedCandidates) {

        Double candidateExtraRewards = 0.0;

        if (candidate.getRank() >= 21) {

            // SCENARIO 1: the candidate is already in the top 21

            candidateExtraRewards = ((double) stake / (double) candidate.getStakes())
                    * candidate.getRewards();

        } else if (candidate.getStakes() >= 100 &&
                rankedCandidates.indexOf(candidate) > 21) {

            // SCENARIO 2: the candidate is already in in standby, and remains in standby

            candidateExtraRewards = ((double) stake / (double) candidate.getStakes())
                    * candidate.getRewards();

        } else if (candidate.getRewards() >= 100 &&
                rankedCandidates.indexOf(candidate) <= 21) {

            // SCENARIO 3:  a standby BP if the Y votes can boost the BP to the 21st place.
            // Then rewards of the BP are multiplied by a factor of 1.58

            Double extraRewardsStandby = ((double) stake / (double) candidate.getStakes()) * candidate.getRewards();
            candidateExtraRewards = extraRewardsStandby + 0.58 * (extraRewardsStandby + candidate.getRewards());

        } else if (candidate.getRewards() == 0 &&
                (candidate.getStakes() + stake) * 0.0000022737 >= 100) {

            // SCENARIO 4: a standby BP if the Y votes can boost the BP to a standby position.
            // Then the reward is 0.0000022737 of the votes per day

            candidateExtraRewards = (candidate.getStakes() + stake) * 0.0000022737;

        } else if (candidate.getRewards() == 0 &&
                rankedCandidates.indexOf(candidate) <= 21) {

            // SCENARIO 5: a top 21 BP if the Y votes can boost the BP from a no-reward to a top 21 position.
            // Then the reward is 0.0000022737 of the votes per day

            // This scenario will never happen as we would need votes above 200 mio.

            candidateExtraRewards = (candidate.getStakes() + stake) * 0.000003590;
        }

        return candidateExtraRewards;

    }
}
