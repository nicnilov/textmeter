package com.nicnilov.textmeter;

import java.util.HashMap;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 25.10.13 at 20:46
 */

public class TextMeter
{
    private HashMap<String, TextLanguage> textLanguages = new HashMap<>();

    public TextMeter() {
    }

    public TextLanguage createTextLanguage(final String language) {
        if ((language == null) || (language.length() == 0)) throw new IllegalArgumentException();

        TextLanguage tl = new TextLanguage(language);
        textLanguages.put(language, tl);
        return tl;
    }

    public TextLanguage get(final String language) {
        return textLanguages.get(language);
    }

    public void release(final String language) {
        textLanguages.remove(language);
    }

    public void releaseAll() {
        textLanguages.clear();
    }

}
