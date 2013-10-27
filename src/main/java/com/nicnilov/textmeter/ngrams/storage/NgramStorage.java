package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.NotInitializedException;
import com.nicnilov.textmeter.ngrams.NgramType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created as part of jmc project
 * by Nic Nilov on 25.10.13 at 23:30
 */
public abstract class NgramStorage {

    protected static int DEFAULT_SIZE_HINT = 16;

    private NgramType ngramType;
    private long ngramsCount = 0;
    private double totalFreq = 0;
    private double floor = 0;

    protected AbstractMap<String, Float> storage;

    private NgramStorage() {}

    protected NgramStorage(NgramType ngramType) {
        this.ngramType = ngramType;
    }

    public void load(InputStream inputStream) throws LineFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        internalLoad(br);
        calculateLogFrequences();
    }

    protected void calculateLogFrequences() {
        for (Map.Entry<String, Float> entry : storage.entrySet()) {
            entry.setValue(new Float(Math.log10(entry.getValue() / totalFreq)));
        }
    }

    protected void internalLoad(BufferedReader br) throws LineFormatException, IOException {
        if (br == null) throw new IllegalArgumentException();

        ngramsCount = 0;
        totalFreq = 0;
        floor = 0;

        storage.clear();

        final String lineRegex = String.format("^[a-zA-Z]{%d}\\s\\d+$", this.getNgramType().length());
        int lineNo = 0;

        String line;
        int freqStart = this.getNgramType().length() + 1;
        float freq;
        while ((line = br.readLine()) != null) {
            lineNo++;
            if (!line.matches(lineRegex)) {
                throw new LineFormatException(String.format("Ngram resource line %d doesn't match pattern \"%s\"", lineNo, lineRegex));
            }
            freq = Float.parseFloat(line.substring(freqStart, line.length()));
            storage.put(line.substring(0, this.getNgramType().length()), freq);
            totalFreq += freq;
        }
        if (totalFreq != 0) {
            floor = Math.log10(1/totalFreq);
        }
        ngramsCount = lineNo;
    }

    public double score(final String text) {
        if ((text == null) || (text.length() < ngramType.length())) throw new IllegalArgumentException();
        if (storage == null) { throw new NotInitializedException(); }

        double result = 0;
        Float score;

        int cnt = text.length() - ngramType.length();
        for (int i = 0; i <= cnt; i++) {
            score = storage.get(text.substring(i, ngramType.length() + i).toUpperCase(Locale.ENGLISH));
            if (score == null) {
                result += floor;
            } else {
                result += score;
            }
        }
        return result;
    }


    public NgramType getNgramType() {
        return ngramType;
    }

    public long ngramsCount() {
        return ngramsCount;
    }

    public double totalFreq() {
        return totalFreq;
    }

    public double floor() {
        return floor;
    }
}
