package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.ngrams.NgramType;

import java.util.TreeMap;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 27.10.13 at 1:22
 */
class TreeMapStorage extends NgramStorage {

    public TreeMapStorage(NgramType ngramType) {
        super(ngramType);
        this.storage = new TreeMap<>();
    }

}
