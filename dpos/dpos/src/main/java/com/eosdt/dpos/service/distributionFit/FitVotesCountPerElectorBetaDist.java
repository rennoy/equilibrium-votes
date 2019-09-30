package com.eosdt.dpos.service.distributionFit;

import com.eosdt.dpos.configuration.ElectionConfiguration;
import com.eosdt.dpos.service.EquilibriumElectionFromCsv;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FitVotesCountPerElectorBetaDist implements FitVotesCountPerElectorDist {

    private final ElectionConfiguration electionConfiguration;

    private final EquilibriumElectionFromCsv equilibriumElectionFromCsv;

    public FitVotesCountPerElectorBetaDist(ElectionConfiguration electionConfiguration,
                                           EquilibriumElectionFromCsv equilibriumElectionFromCsv) {
        this.electionConfiguration = electionConfiguration;
        this.equilibriumElectionFromCsv = equilibriumElectionFromCsv;
    }

    @Override
    public Map<Integer, Double> fit() {
        return null;
    }
}
