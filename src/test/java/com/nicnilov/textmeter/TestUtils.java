package com.nicnilov.textmeter;

import java.io.InputStream;
import java.util.MissingResourceException;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 14.11.13 at 23:19
 */
public class TestUtils {

    public static final String EN_UNIGRAMS = "en/english_unigrams.txt";
    public static final int EN_UNIGRAMS_EXCNT = 26;
    public static final String EN_BIGRAMS = "en/english_bigrams.txt";
    public static final int EN_BIGRAMS_EXCNT = 676;
    public static final String EN_TRIGRAMS = "en/english_trigrams.txt";
    public static final int EN_TRIGRAMS_EXCNT = 17556;
    public static final String EN_QUADGRAMS = "en/english_quadgrams.txt";
    public static final int EN_QUADGRAMS_EXCNT = 389373;
    public static final String EN_QUINTGRAMS = "en/english_quintgrams.txt";
    public static final int EN_QUINTGRAMS_EXCNT = 4354914;

    public static final String RU_UNIGRAMS = "ru/russian_unigrams.txt";
    public static final int RU_UNIGRAMS_EXCNT = 33;
    public static final String RU_BIGRAMS = "ru/russian_bigrams.txt";
    public static final int RU_BIGRAMS_EXCNT = 1085;
    public static final String RU_TRIGRAMS = "ru/russian_trigrams.txt";
    public static final int RU_TRIGRAMS_EXCNT = 29913;
    public static final String RU_QUADGRAMS = "ru/russian_quadgrams.txt";
    public static final int RU_QUADGRAMS_EXCNT = 440609;

    public static InputStream loadResource(Class clazz, String resourceName) {
        InputStream is = clazz.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new MissingResourceException("Could not load example resource", clazz.getName(), resourceName);
        }
        return is;
    }

}
