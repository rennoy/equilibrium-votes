package com.eosdt.dpos.converter;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.election.ElectionConfigurationFiveTen;
import com.eosdt.dpos.service.converter.ElectionToWinningCandidatesConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectionToWinningCandidatesConverterTest {

    @Autowired
    private ElectionConfigurationFiveTen electionConfigurationFiveTen;

    @Autowired
    private ElectionToWinningCandidatesConverter electionToWinningCandidatesConverter;

    @Test
    public void convertTest() {

       List<Candidate> candidateList =
               electionToWinningCandidatesConverter.convert(electionConfigurationFiveTen.EOSElection());

        Assert.assertTrue(candidateList.size() == 10);
        Assert.assertTrue(candidateList.get(0).getRank() == 1);
        Assert.assertTrue(candidateList.get(1).getRank() == 2);
        Assert.assertTrue(IntStream.range(0, candidateList.size())
                .allMatch(i -> candidateList.get(i).getRank() == i + 1));

    }
}
