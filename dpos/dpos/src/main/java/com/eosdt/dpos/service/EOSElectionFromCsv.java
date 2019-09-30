package com.eosdt.dpos.service;

import com.eosdt.dpos.domain.Candidate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@ConfigurationProperties("eos_election_from_csv")
public class EOSElectionFromCsv {

    private String name;

    /**
     * Parsing a structured text file with columns
     * Rank, Account, Status, Country, Staked pct, EOS Staked Jump, Reward, Votes, Votes diff. with contender, Jump in votes
     * @return Candidates
     */
    public Flux<Candidate> getCandidates() {

        Path path = Paths.get(name);

        return Flux.using(
                () ->
                        Files.lines(path)
                                .skip(1)
                                .map(line -> {
                                            try {
                                                return new Candidate(
                                                        line.split(",")[1],
                                                        line.split(",")[3],
                                                        Integer.parseInt(line.split(",")[0]),
                                                        Integer.parseInt(line.split(",")[7]),
                                                        Double.parseDouble(line.split(",")[6]),
                                                        Integer.parseInt(line.split(",")[8]),
                                                        Integer.parseInt(line.split(",")[9])
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
        );

    }

}
