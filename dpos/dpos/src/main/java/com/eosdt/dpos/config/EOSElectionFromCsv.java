package com.eosdt.dpos.config;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.CandidateDesc;
import com.eosdt.dpos.domain.CandidatesInit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@ConfigurationProperties("eoselection")
public class EOSElectionFromCsv {

    private String name;

    /**
     * Parsing a structured text file with columns
     * Rank, Account, Status, Country, Staked pct, EOS Staked Jump, Reward, Votes, Votes diff. with contender, Jump in votes
     * @return CandidatesInit
     */
    @Bean
    public CandidatesInit getCandidatesInit() {

        Path path = Paths.get(name);

        List<Candidate> candidates = Flux.using(
                () ->
                        Files.lines(path)
                                .skip(1)
                                .map(line -> {
                                            try {

                                                String[] splitter = line.split(";");

                                                return new Candidate(
                                                        CandidateDesc.builder()
                                                                .name(splitter[1].replace("\"", ""))
                                                                .country(splitter[3].replace("\"", ""))
                                                                .build(),
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

        ).toStream().collect(Collectors.toList());

        return new CandidatesInit(candidates);
    }

}
