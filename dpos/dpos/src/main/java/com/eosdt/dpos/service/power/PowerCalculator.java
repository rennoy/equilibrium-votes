package com.eosdt.dpos.service.power;

import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.config.EOSElectionFromCsv;
import org.springframework.stereotype.Service;

@Service
public class PowerCalculator {

    private final EOSElectionFromCsv eosElectionFromCsv;

    public PowerCalculator(EOSElectionFromCsv eosElectionFromCsv) {
        this.eosElectionFromCsv = eosElectionFromCsv;
    }

    public Double calculateElectorsAveragePower(Election election) {

        Double avgPower = 0.0;

        for (Vote vote : election.getVotes()) {

            // TODO: use the voteToReward converter

        }

        return avgPower;

    }

}
