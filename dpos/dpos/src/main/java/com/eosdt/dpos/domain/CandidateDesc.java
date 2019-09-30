package com.eosdt.dpos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@Wither
@Getter
@AllArgsConstructor
public class CandidateDesc {

    /**
     * candidate's name
     */
    private final String name;

    /**
     * candidate's country
     */
    private final String country;

}
