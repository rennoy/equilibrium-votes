package com.eosdt.dpos.service.converter;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidatesInit;
import org.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class JsonArrayToCandidatesArrayConverter implements Converter<JSONArray, Candidate[]> {

    private final CandidatesInit candidatesInit;

    public JsonArrayToCandidatesArrayConverter(CandidatesInit candidatesInit) {
        this.candidatesInit = candidatesInit;
    }

    @Override
    public Candidate[] convert(JSONArray objects) {

        return  Flux.range(0, objects.length())
                .map(objects::get)
                .map(Object::toString)
                .map(candidatesInit::getCandidate)
                .toStream()
                .toArray(Candidate[]::new);


    }
}
