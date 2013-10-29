package com.nicnilov.textmeter.ngrams;

import com.nicnilov.textmeter.ngrams.storage.LineFormatException;
import com.nicnilov.textmeter.ngrams.storage.NgramStorage;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageFactory;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created as part of jmc project
 * by Nic Nilov on 25.10.13 at 23:20
 */
public class Ngram {

    private NgramType ngramType;
    private NgramStorage ngramStorage;

    protected Ngram(NgramType ngramType, NgramStorageStrategy ngramStorageStrategy, int sizeHint) {
        this.ngramType = ngramType;
        this.ngramStorage = NgramStorageFactory.get(ngramType, ngramStorageStrategy, sizeHint);
    }

    protected Ngram load(InputStream inputStream) throws IOException, LineFormatException {
        this.ngramStorage.load(inputStream);
        return this;
    }

    public double score(final String text) {
        return this.ngramStorage.score(text);
    }

    public long size() {
        return this.ngramStorage.ngramsCount();
    }

    public double totalFreq() {
        return this.ngramStorage.totalNgrams();
    }

    public double floor() {
        return this.ngramStorage.floor();
    }

}
