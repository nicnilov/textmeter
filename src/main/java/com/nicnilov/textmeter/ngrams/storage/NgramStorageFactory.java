package com.nicnilov.textmeter.ngrams.storage;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created as part of jmc project
 * by Nic Nilov on 26.10.13 at 0:22
 */
public class NgramStorageFactory {

    public static NgramStorage get(NgramStorageStrategy ngramStorageStrategy) {
        NgramStorage ngramStorage;
        switch (ngramStorageStrategy) {
            case HASHMAP: {
                ngramStorage = new HashMapStorage();
                break;
            }
//            case TREEMAP: {
//                break;
//            }
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
