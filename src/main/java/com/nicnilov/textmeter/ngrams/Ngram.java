package com.nicnilov.textmeter.ngrams;

import com.nicnilov.textmeter.NotInitializedException;
import com.nicnilov.textmeter.ngrams.storage.LineFormatException;
import com.nicnilov.textmeter.ngrams.storage.NgramStorage;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageFactory;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 25.10.13 at 23:20
 */
public class Ngram {

    private NgramType ngramType;
    private NgramStorage ngramStorage;
    private long volume;
    private double floor;

    protected Ngram(NgramType ngramType, NgramStorageStrategy ngramStorageStrategy, int sizeHint) {
        this.ngramType = ngramType;
        this.ngramStorage = NgramStorageFactory.get(ngramType, ngramStorageStrategy, sizeHint);
    }

    protected Ngram load(InputStream inputStream) throws IOException, LineFormatException {
        if (ngramStorage == null) { throw new NotInitializedException(); }

        this.volume = this.ngramStorage.load(inputStream);

        if (volume != 0) {
            floor = Math.log10(0.01 / volume);
        }

        calculateLogFrequences();
        return this;
    }

    protected void calculateLogFrequences() {
        for (Map.Entry<String, Float> entry : ngramStorage) {
            entry.setValue(new Float(Math.log10(entry.getValue() / volume)));
        }
    }

    public ScoreStats score(final String text) {
        if ((text == null) || (text.length() < ngramType.length())) throw new IllegalArgumentException();
        if (ngramStorage == null) { throw new NotInitializedException(); }

        ScoreStats scoreStats = new ScoreStats();
        Float ngramScore;

        int cnt = text.length() - ngramType.length();
        scoreStats.ngramsTotal = cnt + 1;

        for (int i = 0; i <= cnt; i++) {
            ngramScore = ngramStorage.get(text.substring(i, ngramType.length() + i));
            if (ngramScore != null) {
                scoreStats.ngramsFound++;
                scoreStats.score += ngramScore;
            }
        }

        scoreStats.minScore = floor * scoreStats.ngramsTotal;

        scoreStats.score = scoreStats.ngramsFound == 0 ? scoreStats.minScore : scoreStats.ngramsTotal * (scoreStats.score / scoreStats.ngramsFound);
        return scoreStats;
    }

    public long count() {
        return this.ngramStorage.count();
    }

    public double volume() {
        return this.volume;
    }

    public double floor() {
        return floor;
    }


//    public void dumpNgrams() throws FileNotFoundException, UnsupportedEncodingException {
//        PrintWriter pw = new PrintWriter("d:/dump.txt", "UTF-8");
//
//        for (Map.Entry<String, Float> entry : storage.entrySet()) {
//            pw.println(String.format("%s: %.5f", entry.getKey(), entry.getValue()));
//        }
//
//        pw.close();
//    }

    /**
     * Created as part of textmeter project
     * by Nic Nilov on 30.10.13 at 23:41
     */
    public static class ScoreStats {
        private double score;
        private double minScore;
        private double ngramsTotal;
        private double ngramsFound;

        public double getScore() {
            return score;
        }

        public double getMinScore() {
            return minScore;
        }

        public double getNgramsTotal() {
            return ngramsTotal;
        }

        public double getNgramsFound() {
            return ngramsFound;
        }
    }

}
