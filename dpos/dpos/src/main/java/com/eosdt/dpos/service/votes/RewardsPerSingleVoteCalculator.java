package com.eosdt.dpos.service.votes;

import com.eosdt.dpos.config.RewardsConfig;
import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.election.ElectionConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsPerSingleVoteCalculator {

    private final ElectionConfiguration electionConfiguration;

    public RewardsPerSingleVoteCalculator(ElectionConfiguration electionConfiguration) {
        this.electionConfiguration = electionConfiguration;
    }

    /**
     * Calculate the extra reward accrued to the candidate, given a classification (EOS result)
     * @param candidate whose vote is for
     * @param stake e.g. Equilibrium EOS proxy stake
     * @param rankedCandidates EOS Round candidates classified after assignment of the proxy stake
     * @return the total rewards impact of an extra proxy stake assignment
     */
    public Double calculate(Candidate candidate, Integer stake, List<Candidate> rankedCandidates) {

        Double candidateExtraRewards = 0.0;

        Integer EOSElectedCount = electionConfiguration.EOSElection().getElectionParams().getNElected();

        Double standByThreshold = electionConfiguration.equilibriumElection().getStandByRewardThreshold();

        if (candidate.getRank() <= EOSElectedCount) {

            // SCENARIO 1: the candidate is already in the top 21

            candidateExtraRewards = ((double) stake / (double) candidate.getStakes())
                    * candidate.getRewards();

        } else if (candidate.getStakes() >= standByThreshold &&
                rankedCandidates.indexOf(candidate) >= EOSElectedCount) {

            // SCENARIO 2: the candidate is already in in standby, and remains in standby

            candidateExtraRewards = ((double) stake / (double) candidate.getStakes())
                    * candidate.getRewards();

        } else if (candidate.getRewards() >= standByThreshold &&
                rankedCandidates.indexOf(candidate) < EOSElectedCount) {

            // SCENARIO 3:  a standby BP if the Y votes can boost the BP to the 21st place.
            // Then rewards of the BP are multiplied by a factor of 1.58

            Double extraRewardsStandby = ((double) stake / (double) candidate.getStakes()) * candidate.getRewards();
            candidateExtraRewards = extraRewardsStandby + (RewardsConfig.PRODUCER_DAILY_REWARD_PER_EOS/RewardsConfig.STANDBY_DAILY_REWARD_PER_EOS - 1.0)
                    * (extraRewardsStandby + candidate.getRewards());

        } else if (candidate.getRewards() == 0.0 &&
                (candidate.getStakes() + stake) * RewardsConfig.STANDBY_DAILY_REWARD_PER_EOS >= standByThreshold) {

            // SCENARIO 4: a standby BP if the Y votes can boost the BP to a standby position.
            // Then the reward is 0.0000022737 of the votes per day

            candidateExtraRewards = (candidate.getStakes() + stake) * RewardsConfig.STANDBY_DAILY_REWARD_PER_EOS;

        } else if (candidate.getRewards() == 0.0 &&
                rankedCandidates.indexOf(candidate) < EOSElectedCount) {

            // SCENARIO 5: a top 21 BP if the Y votes can boost the BP from a no-reward to a top 21 position.
            // Then the reward is 0.0000022737 of the votes per day

            // This scenario will never happen as we would need votes above 200 mio.

            candidateExtraRewards = (candidate.getStakes() + stake) * RewardsConfig.PRODUCER_DAILY_REWARD_PER_EOS;
        }

        return candidateExtraRewards;

    }
}