package com.eosdt.dpos.service.random;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class UniqueRandomGenerator {

    private final Random random;

    public UniqueRandomGenerator(Random random) {
        this.random = random;
    }

    /**
     * n unique random numbers below and excluding k
     * @param n random numbers
     * @param k range (k excluded)
     * @return a Set of integers
     */
    public Set<Integer> pickRandom(int n, int k) {

        if (k <= 0) {
            return null;
        }

        if (k < n) {
            return null;
        }

        final Set<Integer> picked = new HashSet<>();
        while (picked.size() < n) {
            picked.add(random.nextInt(k));
        }
        return picked;
        
    }

}
