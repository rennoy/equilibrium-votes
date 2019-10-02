package com.eosdt.dpos.votes;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.simulator.ElectionRoundSimulator;
import com.eosdt.dpos.service.votes.RewardsPerVoterCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardsPerVoterCalculatorTest {

    @Autowired
    private RewardsPerVoterCalculator rewardsPerVoterCalculator;

    @Autowired
    private ElectionRoundSimulator electionRoundSimulator;

    @Test
    public void calculateTest() {

        Election election = electionRoundSimulator.simulate();
        Vote vote = new Vote(null, new Candidate[] {
                                    election.getCandidates()[50] },
                             100000000);

        Double extraReward = rewardsPerVoterCalculator.calculate(election, vote);

        Assert.assertNotNull(extraReward);
        Assert.assertTrue(extraReward > 0);
    }
}
