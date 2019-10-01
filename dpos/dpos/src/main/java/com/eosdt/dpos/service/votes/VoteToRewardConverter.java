package com.eosdt.dpos.service.votes;

import com.eosdt.dpos.config.EOSElectionFromCsv;
import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.election.ElectionConfiguration;
import com.eosdt.dpos.service.converter.ElectionToWinningCandidatesConverter;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteToRewardConverter {

    private final ElectionConfiguration electionConfiguration;

    private final EOSElectionFromCsv eosElectionFromCsv;

    private final ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter;

    public VoteToRewardConverter(ElectionConfiguration electionConfiguration,
                                 EOSElectionFromCsv eosElectionFromCsv,
                                 ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter) {
        this.electionConfiguration = electionConfiguration;
        this.eosElectionFromCsv = eosElectionFromCsv;
        this.electionToWinningCandidatesConverter = electionToWinningCandidatesConverter;
    }

    /**
     * Calculate rewards for one extra vote
     *
     * @param election Equilibrium election
     * @param vote     extra vote
     * @return total rewards
     */
    public Double addedRewards(Election election, Vote vote) {

        // TODO test this method - add services

        Double extraRewards = 0.0;

        Vote[] newVotes = new Vote[election.getVotes().length + 1];

        for (int i = 0; i < election.getVotes().length; i++) {
            newVotes[i] = election.getVotes()[i];
        }
        newVotes[election.getVotes().length] = vote;

        // Get winners of the proxy election with and without the vote

        List<Candidate> equilibriumWinners = electionToWinningCandidatesConverter.convert(election);

        List<Candidate> equilibriumNewWinners = electionToWinningCandidatesConverter.convert(election.withVotes(newVotes));

        Integer equilibriumStake = electionConfiguration.equilibriumElection().getStake();

        Map<Candidate, Integer> newStakes = new HashMap<>();

        for (Candidate candidate : eosElectionFromCsv.getCandidatesInit().getCandidates()) {

            Integer candidateStakes = eosElectionFromCsv
                    .getCandidate(candidate.getCandidateDesc().getName())
                    .getStakes();

            if (!equilibriumWinners.contains(candidate) && equilibriumNewWinners.contains(candidate)) {

                // SCENARIO A: the only scenario where the voter has an impact

                candidateStakes = candidateStakes + equilibriumStake;

            } else if (equilibriumWinners.contains(candidate) && !equilibriumNewWinners.contains(candidate)) {

                // The new stake is added to the new winners, but also removed from the losers
                // Take into account losers for the candidates ranks' new configuration

                candidateStakes = candidateStakes - equilibriumStake;

            }

            newStakes.put(candidate, candidateStakes);

        }

        // New ranking

        List<Candidate> sortedBPsEOSRound = newStakes.keySet()
                .stream()
                .sorted(Comparator.comparing(c -> -newStakes.get(c)))
                .collect(Collectors.toList());

        for (Candidate candidate : vote.getCandidates()) {

            Double candidateExtraRewards = 0.0; // Daily reward

            if (!equilibriumWinners.contains(candidate) && equilibriumNewWinners.contains(candidate)) {
                if (candidate.getRank() >= 21) {

                    // SCENARIO 1: the candidate is already in the top 21

                    candidateExtraRewards = ((double) equilibriumStake / (double) candidate.getStakes())
                            * candidate.getRewards();

                } else if (candidate.getStakes() >= 100 &&
                        sortedBPsEOSRound.indexOf(candidate) > 21) {

                    // SCENARIO 2: the candidate is already in in standby, and remains in standby

                    candidateExtraRewards = ((double) equilibriumStake / (double) candidate.getStakes())
                            * candidate.getRewards();

                } else if (candidate.getRewards() >= 100 &&
                        sortedBPsEOSRound.indexOf(candidate) <= 21) {

                    // SCENARIO 3:  a standby BP if the Y votes can boost the BP to the 21st place.
                    // Then rewards of the BP are multiplied by a factor of 1.58

                    Double extraRewardsStandby = ((double) equilibriumStake / (double) candidate.getStakes()) * candidate.getRewards();
                    candidateExtraRewards = extraRewardsStandby + 0.58 * (extraRewardsStandby + candidate.getRewards());

                } else if (candidate.getRewards() == 0 &&
                        (candidate.getStakes() + equilibriumStake) * 0.0000022737 >= 100) {

                    // SCENARIO 4:  a standby BP if the Y votes can boost the BP to a standby position.
                    // Then the reward is 0.0000022737 of the votes per day

                    candidateExtraRewards = (candidate.getStakes() + equilibriumStake) * 0.0000022737;

                } else if (candidate.getRewards() == 0 &&
                        sortedBPsEOSRound.indexOf(candidate) <= 21) {

                    // SCENARIO 4:  a standby BP if the Y votes can boost the BP to a standby position.
                    // Then the reward is 0.0000022737 of the votes per day

                    candidateExtraRewards = (candidate.getStakes() + equilibriumStake) * 0.000003590;
                }

                extraRewards = extraRewards + candidateExtraRewards;

            }

        }
        return extraRewards;
    }
}
