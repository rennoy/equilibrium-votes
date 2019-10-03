package com.eosdt.dpos.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DensityToDistributionConverter implements Converter<Map<Integer, Double>, Map<Integer, Double>> {

    @Override
    public Map<Integer, Double> convert(Map<Integer, Double> integerDoubleMap) {

        Map<Integer, Double> dist = new HashMap<>();
        dist.putAll(integerDoubleMap);

        dist.forEach((kk, vv) ->
            integerDoubleMap.forEach((k, v) -> {
                if (k < kk) {
                    dist.put(kk, dist.get(kk) + v);
                }
            })
        );

        return dist;

    }
}
