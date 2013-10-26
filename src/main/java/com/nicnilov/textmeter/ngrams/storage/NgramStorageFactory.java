package com.nicnilov.textmeter.ngrams.storage;

import com.nicnilov.textmeter.NotImplementedException;
import com.nicnilov.textmeter.ngrams.NgramType;

/**
 * Created as part of jmc project
 * by Nic Nilov on 26.10.13 at 0:22
 */
public class NgramStorageFactory {

    public static NgramStorage get(NgramType ngramType, NgramStorageStrategy ngramStorageStrategy) {
        NgramStorage ngramStorage;
        switch (ngramStorageStrategy) {
            case HASHMAP: {
                ngramStorage = new HashMapStorage(ngramType);
                break;
            }
            case TREEMAP: {
                ngramStorage = new TreeMapStorage(ngramType);
                break;
            }
//            case ARRAY: {
//                break;
//            }
            default: {
                throw new NotImplementedException();
            }
        }
        return ngramStorage;
    }
}
