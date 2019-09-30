package com.eosdt.dpos.service;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Elector;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.service.converter.JsonArrayToCandidatesArrayConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.BaseStream;

@Slf4j
@Setter
@Component
@ConfigurationProperties("equilibriumelection")
public class EquilibriumElectionFromCsv {

    private String name;

    private final JsonArrayToCandidatesArrayConverter jsonArrayToCandidatesArrayConverter;

    public EquilibriumElectionFromCsv(JsonArrayToCandidatesArrayConverter jsonArrayToCandidatesArrayConverter) {
        this.jsonArrayToCandidatesArrayConverter = jsonArrayToCandidatesArrayConverter;
    }

    /**
     * Parsing a structured text file with columns
     * elector, nuts, vote_json
     * @return Votes
     */
    public Flux<Vote> getVotes() {

        Path path = Paths.get(name);

        return Flux.using(
                () ->
                    Files.lines(path)
                        .skip(1)
                        .map(line -> {
                                try {

                                    String[] splitter = line.split(";");

                                    Elector elector = new Elector(
                                            splitter[0],
                                            Double.parseDouble(splitter[1])
                                    );

                                    JSONObject jsonObject = new JSONObject(splitter[2].replace("\"\"", "\""));
                                    JSONArray jsonArray = jsonObject.getJSONArray("eosdtbpproxy.producers");

                                    return new Vote(
                                            elector,
                                            jsonArrayToCandidatesArrayConverter.convert(jsonArray),
                                            (int) Math.round(Double.parseDouble(splitter[1]))
                                    );
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        )
                ,
                Flux::fromStream,
                BaseStream::close
        ).share();

    }
}
