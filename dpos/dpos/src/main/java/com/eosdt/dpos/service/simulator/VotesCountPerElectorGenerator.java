package com.eosdt.dpos.service.simulator;

import com.eosdt.dpos.service.converter.DensityToDistributionConverter;
import com.eosdt.dpos.service.distributionFit.FitVotesCountPerElectorDensity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Random;

@Service
public class VotesCountPerElectorGenerator implements Generator<Flux<Integer>> {

    private final Random random;

    private final FitVotesCountPerElectorDensity fitVotesCountPerElectorDensity;

    private final DensityToDistributionConverter densityToDistributionConverter;

    public VotesCountPerElectorGenerator(Random random,
                                         FitVotesCountPerElectorDensity fitVotesCountPerElectorDensity,
                                         DensityToDistributionConverter densityToDistributionConverter) {
        this.random = random;
        this.fitVotesCountPerElectorDensity = fitVotesCountPerElectorDensity;
        this.densityToDistributionConverter = densityToDistributionConverter;
    }

    @Override
    public Flux<Integer> generate(Integer draws) {

        Map<Integer, Double> dist =
                densityToDistributionConverter.convert(fitVotesCountPerElectorDensity.fit());

        return Flux.range(1, draws)
                .map(d -> random.nextDouble())
                .map(r -> {

                    if (dist.isEmpty()) {
                        return 0;
                    }

                    int b = dist.size();

                    for (int k : dist.keySet()) {
                        if (r < dist.get(k)) {
                            b = Math.min(b, k);
                        }
                    }

                    return b;
                });
    }
}
