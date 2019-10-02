package com.eosdt.dpos.random;

import com.eosdt.dpos.service.random.UniqueRandomGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniqueRandomGeneratorTest {

    @Autowired
    private UniqueRandomGenerator uniqueRandomGenerator;

    @Test
    public void pickRandomTest() {

        Set<Integer> set1 = uniqueRandomGenerator.pickRandom(1, 1); // will return 0
        Set<Integer> set2 = uniqueRandomGenerator.pickRandom(2, 1); // will return null
        Set<Integer> set3 = uniqueRandomGenerator.pickRandom(10, 100);

        Assert.assertNotNull(set3);
        Assert.assertNull(set2);
        Assert.assertTrue(set3.size() == 10);
        Assert.assertTrue(Arrays.stream(set3.toArray(new Integer[] {})).allMatch(e -> e < 100));

    }

}
