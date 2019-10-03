package com.eosdt.dpos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CandidatesInit {

    private final List<Candidate> candidates;

    public CandidatesInit() {
        this.candidates = new ArrayList<>();
    }

    /**
     * returns a single candidate - search in the db
     * @param name name of the candidate (account name)
     * @return the Candidate or a new Candidate if not found in the initial list
     */
    public Candidate getCandidate(String name) {
        return candidates
                .stream()
                .filter(c -> c.getCandidateDesc().getName().equals(name))
                .findFirst()
                .orElse(Candidate.builder()
                        .candidateDesc(CandidateDesc.builder().name(name).build())
                        .build());

    }
}

