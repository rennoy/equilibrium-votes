package com.eosdt.dpos.service.random;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidatesInit;
import org.springframework.stereotype.Service;

@Service
public class UniqueRandomCandidates {

    private final UniqueRandomGenerator uniqueRandomGenerator;

    private final CandidatesInit candidatesInit;

    public UniqueRandomCandidates(UniqueRandomGenerator uniqueRandomGenerator,
                                  CandidatesInit candidatesInit) {
        this.uniqueRandomGenerator = uniqueRandomGenerator;
        this.candidatesInit = candidatesInit;
    }

    /**
     * Generate a random vector of k candidates
     * @param k size of the vector
     * @return the vector of candidates
     */
    public Candidate[] generate(Integer k) {

        return uniqueRandomGenerator.pickRandom(k, candidatesInit.getCandidates().size() - 1)
                .stream()
                .map(r -> candidatesInit.getCandidates().get(r))
                .toArray(Candidate[]::new);

    }

}
