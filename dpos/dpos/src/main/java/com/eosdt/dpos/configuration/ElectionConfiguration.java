package com.eosdt.dpos.configuration;

import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.ElectionParams;

public interface ElectionConfiguration {

    Election EOSElection();

    ElectionParams equilibriumElection();
}
