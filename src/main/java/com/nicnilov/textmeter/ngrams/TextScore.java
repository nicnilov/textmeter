package com.nicnilov.textmeter.ngrams;

import com.nicnilov.textmeter.ngrams.storage.NgramStorage;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created as part of jmc project
 * by Nic Nilov on 27.10.13 at 23:03
 */
public class TextScore {

    private EnumMap<NgramType, NgramStorage.ScoreStats> ngramScores = new EnumMap<>(NgramType.class);

    public EnumMap<NgramType, NgramStorage.ScoreStats> getNgramScores() {
        return ngramScores;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<NgramType, NgramStorage.ScoreStats> entry : ngramScores.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(String.format("%s: %.5f (min: %.5f total: %.0f found: %.0f)\n", entry.getKey(),
                        entry.getValue().getScore(),
                        entry.getValue().getMinScore(),
                        //entry.getValue().getCalcScore(),
                        entry.getValue().getNgramsTotal(),
                        entry.getValue().getNgramsFound()));
            }
        }
        return sb.toString();
    }

}
