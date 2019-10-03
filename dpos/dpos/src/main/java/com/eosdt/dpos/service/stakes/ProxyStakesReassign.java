package com.eosdt.dpos.service.stakes;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidatesInit;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProxyStakesReassign {

    private final CandidatesInit candidatesInit;

    public ProxyStakesReassign(CandidatesInit candidatesInit) {
        this.candidatesInit = candidatesInit;
    }

    public Map<Candidate, Integer> adjustStakes(List<Candidate> previousWinners, List<Candidate> newWinners, Integer stakeAdjustment) {

        Map<Candidate, Integer> newStakes = new HashMap<>();

        for (Candidate candidate : candidatesInit.getCandidates()) {

            Integer candidateStakes = candidatesInit
                    .getCandidate(candidate.getCandidateDesc().getName())
                    .getStakes();

            if (!previousWinners.contains(candidate) && newWinners.contains(candidate)) {

                // SCENARIO A: the only scenario where the voter has an impact

                candidateStakes = candidateStakes + stakeAdjustment;

            } else if (previousWinners.contains(candidate) && !newWinners.contains(candidate)) {

                // The new stake is added to the new winners, but also removed from the losers
                // Take into account losers for the candidates ranks' new configuration

                candidateStakes = candidateStakes - stakeAdjustment;

            }

            newStakes.put(candidate, candidateStakes);

        }

        return newStakes;
    }
}