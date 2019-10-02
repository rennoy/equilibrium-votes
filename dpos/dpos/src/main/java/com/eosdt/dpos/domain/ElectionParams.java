package com.eosdt.dpos.domain;

import lombok.Data;

@Data
public class ElectionParams {

    /**
     * e.g 100 BP candidates
     */
    private final Integer nCandidates;
    /**
     * e.g 61 electors
     */
    private final Integer nElectors;
    /**
     * e.g 5 votes per elector
     */
    private final Integer nMaxVotesPerElector;
    /**
     * e.g. minimum NUT a voter must hold to vote
     */
    private final Integer minTokenHeldForVote;
    /**
     * e.g 10 elected by the EOSDT proxy
     */
    private final Integer nElected;
    /**
     * e.g 5mio EOS staked
     */
    private final Integer stake;

    /**
    * e.g. 100 EOS
    **/
    private final Double standByRewardThreshold;
}
