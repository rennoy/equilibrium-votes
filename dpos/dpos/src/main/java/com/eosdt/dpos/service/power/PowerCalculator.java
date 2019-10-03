package com.eosdt.dpos.service.power;

import com.eosdt.dpos.domain.CandidatesInit;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Vote;
import org.springframework.stereotype.Service;

@Service
public class PowerCalculator {

    private final CandidatesInit candidatesInit;

    public PowerCalculator(CandidatesInit candidatesInit) {
        this.candidatesInit = candidatesInit;
    }

    public Double calculateElectorsAveragePower(Election election) {

        Double avgPower = 0.0;

        for (Vote vote : election.getVotes()) {

            // TODO: use the voteToReward converter

        }

        return avgPower;

    }

}
