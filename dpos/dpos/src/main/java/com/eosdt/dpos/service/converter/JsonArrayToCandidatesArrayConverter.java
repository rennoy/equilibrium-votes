package com.eosdt.dpos.service.converter;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.service.EOSElectionFromCsv;
import org.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class JsonArrayToCandidatesArrayConverter implements Converter<JSONArray, Candidate[]> {

    private final EOSElectionFromCsv eosElectionFromCsv;

    public JsonArrayToCandidatesArrayConverter(EOSElectionFromCsv eosElectionFromCsv) {
        this.eosElectionFromCsv = eosElectionFromCsv;
    }

    @Override
    public Candidate[] convert(JSONArray objects) {

        return  Flux.range(0, objects.length())
                .flatMap(i -> eosElectionFromCsv.getCandidates()
                        .filter(c -> c.getCandidateDesc().getName().equals(objects.get(i).toString()))
                        .take(1))
                .toStream()
                .toArray(Candidate[]::new);

    }
}
