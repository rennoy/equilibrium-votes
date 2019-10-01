package com.eosdt.dpos.service.converter;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class ElectionToWinningCandidatesConverter implements Converter<Election, List<Candidate>> {

    @Override
    public List<Candidate> convert(Election election) {

        // TODO: select the 10 candidates with the higher NUT votes

        return null;
    }
}
