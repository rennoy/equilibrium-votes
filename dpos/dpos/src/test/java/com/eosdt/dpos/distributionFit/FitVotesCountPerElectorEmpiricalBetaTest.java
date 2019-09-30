package com.eosdt.dpos.distributionFit;


import com.eosdt.dpos.service.distributionFit.FitVotesCountPerElectorBetaDist;
import com.eosdt.dpos.service.distributionFit.FitVotesCountPerElectorEmpiricalDist;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FitVotesCountPerElectorEmpiricalBetaTest {

    @Autowired
    private FitVotesCountPerElectorBetaDist fitVotesCountPerElectorBetaDist;

    @Test
    public void FitVotesCountPerElectorDistTest() {

        Map<Integer, Double> fit = this.fitVotesCountPerElectorBetaDist.fit();
        Assert.assertTrue(fit.values().size() > 0);

    }
}
