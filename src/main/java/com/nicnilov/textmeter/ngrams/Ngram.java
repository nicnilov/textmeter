package com.nicnilov.textmeter.ngrams;

import com.nicnilov.textmeter.ngrams.storage.NgramStorage;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageFactory;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;

import java.io.InputStream;

/**
 * Created as part of jmc project
 * by Nic Nilov on 25.10.13 at 23:20
 */
public class Ngram {

    private NgramType ngramType;
    private NgramStorage ngramStorage;

    protected Ngram(NgramType ngramType, NgramStorageStrategy ngramStorageStrategy) {
        this.ngramType = ngramType;
        this.ngramStorage = NgramStorageFactory.get(ngramStorageStrategy);
    }

    protected Ngram load(InputStream inputStream) {
        this.ngramStorage.load(this.ngramType, inputStream);
        return this;
    }

}
