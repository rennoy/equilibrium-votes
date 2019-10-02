package com.eosdt.dpos.power;

import com.eosdt.dpos.domain.*;
import com.eosdt.dpos.election.ElectionConfigurationFiveTen;
import com.eosdt.dpos.service.power.AverageElectorPowerCalculator;
import com.eosdt.dpos.service.simulator.ElectionRoundSimulator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.DoubleSummaryStatistics;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AverageElectorPowerCalculatorTest {

    @Autowired
    private ElectionConfigurationFiveTen electionConfigurationFiveTen;

    @Autowired
    private CandidatesInit candidatesInit;

    @Autowired
    private VotesInit votesInit;

    @Autowired
    private AverageElectorPowerCalculator averageElectorPowerCalculator;

    @Autowired
    private ElectionRoundSimulator electionRoundSimulator;


    @Test
    public void calculateTest() {

        Election election = new Election(
                electionConfigurationFiveTen.equilibriumElection(),
                candidatesInit.getCandidates().toArray(new Candidate[] {}),
                votesInit.getVotes().toArray(new Vote[] {})
                );

        for (int i = 99; i < 100; i++) {
            DoubleSummaryStatistics power = averageElectorPowerCalculator.calculate(election,  760.0);
            System.out.println((10 * i) + ";" + power.getAverage() + ";" + power.getMax());
        }
    }
}
