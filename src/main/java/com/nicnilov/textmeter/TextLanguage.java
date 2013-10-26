package com.nicnilov.textmeter;

import com.nicnilov.textmeter.ngrams.Ngram;
import com.nicnilov.textmeter.ngrams.NgramBuilder;
import com.nicnilov.textmeter.ngrams.NgramType;
import com.nicnilov.textmeter.ngrams.storage.LineFormatException;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Locale;

/**
 * Created as part of jmc project
 * by Nic Nilov on 25.10.13 at 23:19
 */
public class TextLanguage {

    private EnumMap<NgramType, Ngram> ngrams = new EnumMap<>(NgramType.class);
    private Locale locale;

    public TextLanguage(Locale locale) {
        this.locale = locale;
    }

    protected Ngram getNgram(NgramType ngramType) {
        if (ngrams.containsKey(ngramType)) {
            return ngrams.get(ngramType);
        }
        throw new NotInitializedException(String.format("Ngrams of type %s have not been loaded", ngramType));
    }

    public Ngram getNgram(NgramType ngramType, InputStream inputStream, NgramStorageStrategy ngramStorageStrategy) throws IOException, LineFormatException {
        Ngram ngram = NgramBuilder.build(ngramType, inputStream, ngramStorageStrategy);
        ngrams.put(ngramType, ngram);
        return ngram;
    }

    public void releaseNgram(NgramType ngramType) {
        ngrams.remove(ngramType);
    }

    public void releaseAllNgrams() {
        ngrams.clear();
    }

}
