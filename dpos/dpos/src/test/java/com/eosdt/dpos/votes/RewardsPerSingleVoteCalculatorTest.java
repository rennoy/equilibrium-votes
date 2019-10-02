package com.eosdt.dpos.votes;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidatesInit;
import com.eosdt.dpos.service.votes.RewardsPerSingleVoteCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardsPerSingleVoteCalculatorTest {

    @Autowired
    private CandidatesInit candidatesInit;

    @Autowired
    private RewardsPerSingleVoteCalculator rewardsPerSingleVoteCalculator;

    @Test
    public void calculateTest() {

        Candidate candidate = candidatesInit.getCandidates().get(25);

        List<Candidate> candidates = new ArrayList<>();
        candidates.addAll(candidatesInit.getCandidates());
        candidates.remove(candidates.get(25));
        Integer newStakes = candidates.get(25).getStakes() + 100000000;
        candidates.add(candidates.get(25).withStakes(newStakes));


        Double extraReward = this.rewardsPerSingleVoteCalculator
                .calculate(candidate, 100000000, candidates);

        Assert.assertNotNull(extraReward);
        Assert.assertTrue(extraReward > 0);
        Assert.assertTrue(extraReward > 100000000 * 0.0000022737);

    }
}
