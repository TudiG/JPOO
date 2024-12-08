package org.poo.utils;

import java.util.*;

import org.poo.fileio.ExchangeInput;

public final class SimpleRateMapConverter {
    public static final Map<String, Double> ratesMap = new HashMap<>();

    public static void precomputeRates(final List<ExchangeInput> exchangeInputs) {
        Set<String> currencies = new HashSet<>();
        
        for (ExchangeInput input : exchangeInputs) {
            String fromToKey = input.getFrom() + "-" + input.getTo();
            String toFromKey = input.getTo() + "-" + input.getFrom();
            ratesMap.put(fromToKey, input.getRate());
            ratesMap.put(toFromKey, 1 / input.getRate());

            currencies.add(input.getFrom());
            currencies.add(input.getTo());
        }


        for (String from : currencies) {
            for (String to : currencies) {
                if (from.equals(to)) {
                    ratesMap.putIfAbsent(from + "-" + to, 1.0);
                    continue;
                }
        
                for (String intermediate : currencies) {
                    String fromToKey = from + "-" + to;
                    String fromIntermediateKey = from + "-" + intermediate;
                    String intermediateToKey = intermediate + "-" + to;
        
                    if (!ratesMap.containsKey(fromToKey) 
                        && ratesMap.containsKey(fromIntermediateKey) 
                        && ratesMap.containsKey(intermediateToKey)) {
                            double rate = ratesMap.get(fromIntermediateKey) * ratesMap.get(intermediateToKey);
                            ratesMap.put(fromToKey, rate);
                            break;
                    }
                }
            }
        }
    }
}
