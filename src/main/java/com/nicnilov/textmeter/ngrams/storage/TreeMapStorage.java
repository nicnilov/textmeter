package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.ngrams.NgramType;

import java.util.TreeMap;

/**
 * Created as part of jmc project
 * by Nic Nilov on 27.10.13 at 1:22
 */
public class TreeMapStorage extends NgramStorage {

    public TreeMapStorage(NgramType ngramType) {
        super(ngramType);
        this.storage = new TreeMap<>();
    }

}
