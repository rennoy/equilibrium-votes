package com.eosdt.dpos.config;

import com.eosdt.dpos.domain.Elector;
import com.eosdt.dpos.domain.Vote;
import com.eosdt.dpos.domain.VotesInit;
import com.eosdt.dpos.service.converter.JsonArrayToCandidatesArrayConverter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

@Slf4j
@Setter
@Configuration
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
    @Bean
    public VotesInit getVotesInit() {

        Path path = Paths.get(name);

        List<Vote> votes = Flux.using(
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
        ).toStream()
         .collect(Collectors.toList());

        return new VotesInit(votes);


    }
}
