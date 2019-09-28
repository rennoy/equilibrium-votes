package com.eosdt.dpos.domain;

import lombok.Data;

@Data
public class Election {

    private final ElectionParams electionParams;
    private final Vote[] votes;
}
