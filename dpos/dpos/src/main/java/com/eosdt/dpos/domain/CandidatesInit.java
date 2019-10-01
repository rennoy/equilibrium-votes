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
}

