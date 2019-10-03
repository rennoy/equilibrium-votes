package com.eosdt.dpos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class VotesInit {

    private final List<Vote> votes;

    public VotesInit() {
        this.votes = new ArrayList<>();
    }

}
