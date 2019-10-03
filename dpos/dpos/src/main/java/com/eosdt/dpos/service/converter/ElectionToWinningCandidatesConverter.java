package com.eosdt.dpos.service.converter;

import com.eosdt.dpos.domain.Candidate;
import com.eosdt.dpos.domain.Election;
import com.eosdt.dpos.election.ElectionConfiguration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectionToWinningCandidatesConverter implements Converter<Election, List<Candidate>> {

    private final ElectionConfiguration electionConfiguration;

    public ElectionToWinningCandidatesConverter(ElectionConfiguration electionConfiguration) {
        this.electionConfiguration = electionConfiguration;
    }

    public List<Candidate> convert(Election election) {

        Map<Candidate, Integer> electionCount = new HashMap<>();

        Arrays.stream(election.getVotes())
                .forEach(vote -> {
                    for (Candidate candidate : vote.getCandidates()) {

                        // Increment with the candidate's number of stakes (NUTs)

                        if (electionCount.keySet().contains(candidate)) {
                            electionCount.put(candidate, electionCount.get(candidate) + vote.getStake());
                        } else {
                            electionCount.put(candidate, vote.getStake());
                        }
                    }
                });

        return electionCount.keySet()
                .stream()
                .sorted(Comparator.comparing(k -> -electionCount.get(k)))
                .limit(electionConfiguration.equilibriumElection().getNElected())
                .collect(Collectors.toList());

    }
}