package com.eosdt.dpos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@Wither
@Getter
@AllArgsConstructor
public class Election {

    private final ElectionParams electionParams;
    private final Candidate[] candidates;
    private final Vote[] votes;
}
