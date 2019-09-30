package com.eosdt.dpos.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;

@Builder
@Wither
@Getter
@AllArgsConstructor
public class Candidate {

    /**
     * candidate's name
     */
    private final String name;

    /**
     * candidate's country
     */
    private final String country;

    /**
     * candidate's rank
     */
    private final Integer rank;

    /**
     * candidate's total votes (or stakes)
     */
    private final Integer stakes;

    /**
     * candidate's rewards
     */
    private final Double rewards;

    /**
     * candidate's votes difference with the next lower ranked candidate
     */
    private final Integer votesDiffWContender;

    /**
     * Jump in votes since the last election
     */
    private final Integer jumpInVotes;


}
