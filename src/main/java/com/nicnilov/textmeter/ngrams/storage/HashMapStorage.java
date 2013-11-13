package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.ngrams.NgramType;

import java.util.HashMap;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 26.10.13 at 0:03
 */
class HashMapStorage extends NgramStorage {

    public HashMapStorage(NgramType ngramType, int sizeHint) {
        super(ngramType);
        this.storage = new HashMap<>(sizeHint < DEFAULT_SIZE_HINT ? DEFAULT_SIZE_HINT : sizeHint);
    }

}
