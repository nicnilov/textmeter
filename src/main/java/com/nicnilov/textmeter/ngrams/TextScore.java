package com.nicnilov.textmeter.ngrams;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created as part of jmc project
 * by Nic Nilov on 27.10.13 at 23:03
 */
public class TextScore {

    private EnumMap<NgramType, Double> ngramScores = new EnumMap<>(NgramType.class);

    public EnumMap<NgramType, Double> getNgramScores() {
        return ngramScores;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<NgramType, Double> entry : ngramScores.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(String.format("%s: %.5f ", entry.getKey(), entry.getValue()));
            }
        }
        return sb.toString();
    }

}
