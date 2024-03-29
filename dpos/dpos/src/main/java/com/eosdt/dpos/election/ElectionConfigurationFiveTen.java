package com.eosdt.dpos.election;

import com.eosdt.dpos.domain.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("election_configuration_five_ten")
public class ElectionConfigurationFiveTen implements ElectionConfiguration {

    private final CandidatesInit candidatesInit;

    public ElectionConfigurationFiveTen(CandidatesInit candidatesInit) {
        this.candidatesInit = candidatesInit;
    }

    /**
     * We are assuming as a start, for the exercise,
     * that the EOS election is set and every candidate received 1 vote
     * and the 1 vote represents all the EOS staked for the candidate in the election.
     *
     * 1 candidate = 1 vote = total EOS staked for the candidate
     * @return Election object
     */
    public Election EOSElection() {

        List<Vote> votesList = new ArrayList<>();

        Integer totalStake = 0;

        for (Candidate candidate : candidatesInit.getCandidates()) {

            votesList.add(new Vote(
                    new Elector(candidate.getCandidateDesc().getName(),
                            candidate.getStakes().doubleValue()),
                    new Candidate[]{candidate},
                    candidate.getStakes()));

            totalStake = totalStake + candidate.getStakes();
        }


        Vote[] votes = votesList.toArray(new Vote[] {});
        Candidate[] candidates =  candidatesInit
                                        .getCandidates().toArray(new Candidate[] {});

        ElectionParams electionParams =  
                new ElectionParams(
                        100,
                        100,
                        1,
                        0,
                        21,
                        totalStake,
                        100.0
                );

        return new Election(
                electionParams,
                candidates,
                votes
        );
    }

    public ElectionParams equilibriumElection() {

        return new ElectionParams(
                100,
                61,
                5,
                2,
                10,
                5000000,
                null
        );

    }
}
