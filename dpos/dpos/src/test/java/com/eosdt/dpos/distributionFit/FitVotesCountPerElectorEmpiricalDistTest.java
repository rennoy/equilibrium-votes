package com.eosdt.dpos.distributionFit;


import com.eosdt.dpos.service.distributionFit.FitVotesCountPerElectorEmpiricalDensity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FitVotesCountPerElectorEmpiricalDistTest {

    @Autowired
    private FitVotesCountPerElectorEmpiricalDensity fitVotesCountPerElectorEmpiricalDist;

    @Test
    public void FitVotesCountPerElectorDistTest() {

        Map<Integer, Double> fit = this.fitVotesCountPerElectorEmpiricalDist.fit();
        Assert.assertTrue(fit.values().size() > 0);

    }
}
