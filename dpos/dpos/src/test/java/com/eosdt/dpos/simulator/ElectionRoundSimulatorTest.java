package com.eosdt.dpos.simulator;

import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.service.simulator.ElectionRoundSimulator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectionRoundSimulatorTest {

    @Autowired
    private ElectionRoundSimulator electionRoundSimulator;

    @Test
    public void setElectionRoundSimulatorTest() {

        Election election = electionRoundSimulator.simulate();
        Assert.assertTrue(election.getVotes().length > 0);

    }

}
