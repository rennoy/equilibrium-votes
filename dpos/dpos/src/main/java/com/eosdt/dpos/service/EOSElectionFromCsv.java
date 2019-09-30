package com.eosdt.dpos.service;

import com.eosdt.dpos.domain.Candidate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.BaseStream;

@Slf4j
@Setter
@Service
@ConfigurationProperties("eoselection")
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

                                                String[] splitter = line.split(";");

                                                return new Candidate(
                                                        splitter[1].replace("\"", ""),
                                                        splitter[3].replace("\"", ""),
                                                        Integer.parseInt(splitter[0].replace("\"", "")),
                                                        Integer.parseInt(splitter[7].replace("\"", "")),
                                                        Double.parseDouble(splitter[6].replace("\"", "")),
                                                        Integer.parseInt(splitter[8].replace("\"", "")),
                                                        Integer.parseInt(splitter[9].replace("\"", ""))
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
