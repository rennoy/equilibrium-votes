package com.eosdt.dpos.service.votes;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.election.ElectionConfiguration;
import com.eosdt.dpos.service.converter.ElectionToWinningCandidatesConverter;
import com.eosdt.dpos.service.stakes.ProxyStakesReassign;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsPerVoterCalculator {

    private final ElectionConfiguration electionConfiguration;

    private final ProxyStakesReassign proxyStakesReassign;

    private final ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter;

    private final RewardsPerSingleVoteCalculator rewardsPerSingleVoteCalculator;

    public RewardsPerVoterCalculator(ElectionConfiguration electionConfiguration,
                                     ProxyStakesReassign proxyStakesReassign,
                                     ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter,
                                     RewardsPerSingleVoteCalculator rewardsPerSingleVoteCalculator) {

        this.electionConfiguration = electionConfiguration;
        this.proxyStakesReassign = proxyStakesReassign;
        this.electionToWinningCandidatesConverter = electionToWinningCandidatesConverter;
        this.rewardsPerSingleVoteCalculator = rewardsPerSingleVoteCalculator;
    }

    /**
     * Calculate rewards for one extra vote
     *
     * @param election Equilibrium election
     * @param vote     extra vote
     * @return total rewards
     */
    public Double calculate(Election election, Vote vote) {

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

        // Reassign stakes

        Integer equilibriumStake = electionConfiguration.equilibriumElection().getStake();

        Map<Candidate, Integer> newStakes = proxyStakesReassign.adjustStakes(
                equilibriumWinners, equilibriumNewWinners, equilibriumStake);

        // New ranking

        List<Candidate> sortedBPsEOSRound = newStakes.keySet()
                .stream()
                .sorted(Comparator.comparing(c -> -newStakes.get(c)))
                .collect(Collectors.toList());

        for (Candidate candidate : vote.getCandidates()) {

            Double candidateExtraRewards; // Daily reward

            if (!equilibriumWinners.contains(candidate) && equilibriumNewWinners.contains(candidate)) {

                candidateExtraRewards =
                        rewardsPerSingleVoteCalculator.calculate(candidate, equilibriumStake, sortedBPsEOSRound);

                extraRewards = extraRewards + candidateExtraRewards;

            }

        }
        return extraRewards;
    }
}
