package org.poo.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import org.poo.fileio.ExchangeInput;

/**
 * Aceasta clasa este utilizata pentru a obtine rate de schimb intermediare
 * pe baza unui set de rate initiale. Ratele de schimb sunt salvate intr-un
 * map si pot fi folosite ulterior pentru conversii.
 */
public final class SimpleRateMapConverter {
    @Getter
    private static Map<String, Double> ratesMap = new HashMap<>();

    private SimpleRateMapConverter() { }

    /**
     * Precalculeaza ratele pentru fiecare pereche, inclusiv ratele intermediare
     * obtinute pe baza ratelor deja existente.
     *
     * @param exchangeInputs lista de rate de schimb de la input
     */
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
                            double rate = ratesMap.get(fromIntermediateKey)
                                    * ratesMap.get(intermediateToKey);
                            ratesMap.put(fromToKey, rate);
                            break;
                    }
                }
            }
        }
    }
}
