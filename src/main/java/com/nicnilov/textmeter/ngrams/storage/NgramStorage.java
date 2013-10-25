package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.ngrams.NgramType;

import java.io.InputStream;

/**
 * Created as part of jmc project
 * by Nic Nilov on 25.10.13 at 23:30
 */
public interface NgramStorage {

    public int load(NgramType ngramType, InputStream inputStream);

}
