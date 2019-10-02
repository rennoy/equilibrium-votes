package com.eosdt.dpos.stakes;


import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.election.ElectionConfigurationFiveTen;
import com.eosdt.dpos.service.converter.ElectionToWinningCandidatesConverter;
import com.eosdt.dpos.service.stakes.ProxyStakesReassign;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyStakesReassignTest {

    @Autowired
    private ElectionConfigurationFiveTen electionConfigurationFiveTen;

    @Autowired
    private ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter;

    @Autowired
    private ProxyStakesReassign proxyStakesReassign;

    @Test
    public void convertTest() {

        List<Candidate> prevWinnersList =
                electionToWinningCandidatesConverter.convert(electionConfigurationFiveTen.EOSElection());

        Vote[] votes = new Vote[electionConfigurationFiveTen.EOSElection().getVotes().length + 1];

        for (int v = 0; v < electionConfigurationFiveTen.EOSElection().getVotes().length; v++) {
            votes[v] = electionConfigurationFiveTen.EOSElection().getVotes()[v];
        }

        // Add a new vote for a random candidate (50th) that should bump him in the top 10.

        votes[electionConfigurationFiveTen.EOSElection().getVotes().length]
                = new Vote(null,
                new Candidate[] {electionConfigurationFiveTen.EOSElection().getCandidates()[50]},
                500000000
        );

        List<Candidate> winnersList =
                electionToWinningCandidatesConverter.convert(electionConfigurationFiveTen.EOSElection()
                        .withVotes(votes));

        Map<Candidate, Integer> voteCount = proxyStakesReassign.adjustStakes(prevWinnersList, winnersList, 500000000);

        Assert.assertTrue(!voteCount.isEmpty());
        Assert.assertTrue(voteCount.get(prevWinnersList.get(9)) == prevWinnersList.get(9).getStakes() - 500000000);
        Assert.assertTrue(voteCount.get(electionConfigurationFiveTen.EOSElection().getCandidates()[50])
                == electionConfigurationFiveTen.EOSElection().getCandidates()[50].getStakes() + 500000000);

    }

}
