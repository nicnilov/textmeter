package com.nicnilov.textmeter;

import com.nicnilov.textmeter.ngrams.NgramType;
import com.nicnilov.textmeter.ngrams.storage.LineFormatException;
import com.nicnilov.textmeter.ngrams.storage.NgramStorage;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageFactory;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 14.11.13 at 22:32
 */
public class NgramStorageTest extends TestCase {

    public static Test suite() {
        return new TestSuite(NgramStorageTest.class);
    }

    public void testStorageFactory() throws Exception {
        for (NgramType ngramType: NgramType.values()) {
            NgramStorage ngramStorage = NgramStorageFactory.get(ngramType, NgramStorageStrategy.TREEMAP, 10);
            assert (ngramStorage.getNgramType() == ngramType);
            assert (ngramStorage.getStorageStrategy() == NgramStorageStrategy.TREEMAP);
        }
        for (NgramType ngramType: NgramType.values()) {
            NgramStorage ngramStorage = NgramStorageFactory.get(ngramType, NgramStorageStrategy.HASHMAP, 0);
            assert (ngramStorage.getNgramType() == ngramType);
            assert (ngramStorage.getStorageStrategy() == NgramStorageStrategy.HASHMAP);
        }
        for (NgramType ngramType: NgramType.values()) {
            try {
                NgramStorageFactory.get(ngramType, NgramStorageStrategy.ARRAY, 0);
            } catch (NotImplementedException nie) {
                continue;
            }
            throw new Exception("Using non-implemented ARRAY storage strategy should throw an exception");
        }
    }

    public void loadNgramsByAllStrategies(NgramType ngramType, String resourcePath, int expectedCount) throws IOException, LineFormatException {
        NgramStorage ngramStorage = NgramStorageFactory.get(ngramType, NgramStorageStrategy.TREEMAP, expectedCount);
        assert (ngramStorage.count() == 0);
        ngramStorage.load(TestUtils.loadResource(this.getClass(), resourcePath));
        assert (ngramStorage.count() == expectedCount);

        ngramStorage = NgramStorageFactory.get(ngramType, NgramStorageStrategy.HASHMAP, expectedCount);
        assert (ngramStorage.count() == 0);
        ngramStorage.load(TestUtils.loadResource(this.getClass(), resourcePath));
        assert (ngramStorage.count() == expectedCount);

    }

    public void testUnigramEnTreeMapLoading() throws IOException, LineFormatException {
        loadNgramsByAllStrategies(NgramType.UNIGRAM, TestUtils.EN_UNIGRAMS, TestUtils.EN_UNIGRAMS_EXCNT);
    }

    public void testBigramEnTreeMapLoading() throws IOException, LineFormatException {
        loadNgramsByAllStrategies(NgramType.BIGRAM, TestUtils.EN_BIGRAMS, TestUtils.EN_BIGRAMS_EXCNT);
    }

    public void testTrigramEnTreeMapLoading() throws IOException, LineFormatException {
        loadNgramsByAllStrategies(NgramType.TRIGRAM, TestUtils.EN_TRIGRAMS, TestUtils.EN_TRIGRAMS_EXCNT);
    }

    public void testQuadgramEnTreeMapLoading() throws IOException, LineFormatException {
        loadNgramsByAllStrategies(NgramType.QUADGRAM, TestUtils.EN_QUADGRAMS, TestUtils.EN_QUADGRAMS_EXCNT);
    }

    public void testQuintgramEnTreeMapLoading() throws IOException, LineFormatException {
        loadNgramsByAllStrategies(NgramType.QUINTGRAM, TestUtils.EN_QUINTGRAMS, TestUtils.EN_QUINTGRAMS_EXCNT);
    }

}
