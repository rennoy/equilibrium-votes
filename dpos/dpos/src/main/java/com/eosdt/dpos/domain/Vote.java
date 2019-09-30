package com.eosdt.dpos.domain;

import lombok.Data;

@Data
public class Vote {

    private final Elector elector;
    private final Candidate[] candidates;
    private final Integer stake;

}
