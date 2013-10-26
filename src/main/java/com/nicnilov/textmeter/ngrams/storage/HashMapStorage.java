package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.ngrams.NgramType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created as part of jmc project
 * by Nic Nilov on 26.10.13 at 0:03
 */
public class HashMapStorage extends NgramStorage {

    public HashMapStorage(NgramType ngramType) {
        super(ngramType);
        this.storage = new HashMap<>();
    }

}
