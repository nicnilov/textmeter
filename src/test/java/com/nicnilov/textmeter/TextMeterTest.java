package com.nicnilov.textmeter;

import com.nicnilov.textmeter.ngrams.NgramType;
import com.nicnilov.textmeter.ngrams.TextScore;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;

/**
 * Created as part of jmc project
 * by Nic Nilov on 27.10.13 at 18:58
 */
public class TextMeterTest extends TestCase {

    private static final String EN_UNIGRAMS = "en/english_unigrams.txt";
    private static final String EN_BIGRAMS = "en/english_bigrams.txt";
    private static final String EN_TRIGRAMS = "en/english_trigrams.txt";
    private static final String EN_QUADGRAMS = "en/english_quadgrams.txt";
    private static final String EN_QUINTGRAMS = "en/english_quintgrams.txt";

    private static final String RU_UNIGRAMS = "ru/russian_unigrams.txt";
    private static final String RU_BIGRAMS = "ru/russian_bigrams.txt";
    private static final String RU_TRIGRAMS = "ru/russian_trigrams.txt";
    private static final String RU_QUADGRAMS = "ru/russian_quadgrams.txt";

    private static final String EN_TEXT = "In a departure from the style of traditional encyclopedias, Wikipedia is open to outside editing. This means that, with the exception of particularly sensitive and/or vandalism-prone pages that are \"protected\" to some degree,[25] the reader of an article can edit the text without needing approval, doing so with a registered account or even anonymously. Different language editions modify this policy to some extent; for example, only registered users may create a new article in the English edition.[26] No article is considered to be owned by its creator or any other editor, nor is it vetted by any recognized authority. Instead, editors are supposed to agree on the content and structure of articles by consensus.[27]\n" +
            ("By default, an edit to an article becomes available immediately, prior to any review. As such, an article may contain inaccuracies, ideological biases, or even patent nonsense, until or unless another editor corrects the problem. Different language editions, each under separate administrative control, are free to modify this policy. For example, the German Wikipedia maintains \"stable versions\" of articles,[28] which have passed certain reviews. Following the protracted trials and community discussion, the \"pending changes\" system was introduced to English Wikipedia in December 2012.[29] Under this system, new users' edits to certain controversial or vandalism-prone articles would be \"subject to review from an established Wikipedia editor before " +
                    "publication\".");

    private static final String RU_TEXT = "Участники Википедии образуют сообщество участников Википедии. Структура этого сообщества — иерархическая, то есть, это своего рода некая структура власти[58][59]. Участники Википедии с хорошей репутацией в сообществе могут баллотироваться на один из многих уровней добровольного руководства; это начинается с «администратора»[60], самой большой группы привилегированных пользователей (1,594 Wikipedians for the English edition on September 30, 2008), которые имеют возможность удаления страниц, блокировки статей от изменений в случае вандализма или редакторских споров и блокировки участников. Несмотря на название, администраторы не имеют никаких особенных привилегий в процессе принятия решений, и им запрещено использовать свои полномочия для урегулирования споров. Роли администраторов часто описываются как «уборка» и в основном ограничиваются внесением правок, имеющих эффект в масштабах всего проекта (и поэтому запрещённых для обычных редакторов, чтобы минимизировать нарушения), а также блокировкой пользователей для предотвращения разрушительных правок, таких как вандализм.\n" +
            "Поскольку Википедия развивается на основе нетрадиционной модели составления энциклопедии, вопрос «Кто пишет Википедию?» стал одним из наиболее часто задаваемых вопросов по проекту, часто со ссылкой на другие проекты Веб 2.0, такие как Digg[61] или, например, News2 и Хабрахабр. Джимми Уэйлс однажды утверждал, что только «сообщество… преданная группа нескольких сотен добровольцев» делает основной вклад в Википедию и что этот проект является поэтому «очень похожим на любую традиционную организацию». Это было позже оспорено Аароном Шварцем, который отметил, что ряд просмотренных им статей имели крупные части содержания, внесёнными участниками с малым количеством " +
            "правок[62].\n";

    private TextMeter textMeter;

    public static Test suite() {
        return new TestSuite(TextMeterTest.class);
    }

    private InputStream loadResource(String resourceName) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new MissingResourceException("Could not load example resource", this.getClass().getName(), resourceName);
        }
        return is;
    }

    public void setUp() throws Exception {
        textMeter = new TextMeter();
        textMeter.createTextLanguage("en");
        textMeter.createTextLanguage("ru");

        TextLanguage en = textMeter.get("en");
        long mark = System.currentTimeMillis();
        en.getNgram(NgramType.UNIGRAM, loadResource(EN_UNIGRAMS), NgramStorageStrategy.TREEMAP, 26);
        en.getNgram(NgramType.BIGRAM, loadResource(EN_BIGRAMS), NgramStorageStrategy.TREEMAP, 676);
        en.getNgram(NgramType.TRIGRAM, loadResource(EN_TRIGRAMS), NgramStorageStrategy.TREEMAP, 17556);
        en.getNgram(NgramType.QUADGRAM, loadResource(EN_QUADGRAMS), NgramStorageStrategy.TREEMAP, 389373);
        en.getNgram(NgramType.QUINTGRAM, loadResource(EN_QUINTGRAMS), NgramStorageStrategy.TREEMAP, 4354915);
        System.out.println(String.format("en finished: %d msec", System.currentTimeMillis() - mark));

        TextLanguage ru = textMeter.get("ru");
        mark = System.currentTimeMillis();
        ru.getNgram(NgramType.UNIGRAM, loadResource(RU_UNIGRAMS), NgramStorageStrategy.TREEMAP, 33);
        ru.getNgram(NgramType.BIGRAM, loadResource(RU_BIGRAMS), NgramStorageStrategy.TREEMAP, 1085);
        ru.getNgram(NgramType.TRIGRAM, loadResource(RU_TRIGRAMS), NgramStorageStrategy.TREEMAP, 29913);
        ru.getNgram(NgramType.QUADGRAM, loadResource(RU_QUADGRAMS), NgramStorageStrategy.TREEMAP, 440609);
        System.out.println(String.format("ru finished: %d msec", System.currentTimeMillis() - mark));
    }

    public void tearDown() {
        textMeter.releaseAll();
        textMeter = null;
    }

    public String getRandomBinaryText(int length) {
        byte[] result = new byte[length];
        Random rg = new Random();
        rg.nextBytes(result);
        return new String(result);
    }

    public String getRandomCharacterText(final String chars, int length) {
        char[] result = new char[length];
        Random rg = new Random();
        for (int i = 0; i < result.length; i++) {
            result[i] = chars.charAt(rg.nextInt(chars.length() - 1));
        }
        return new String(result);
    }

    public void testTextScore() throws Exception {
        long mark = System.currentTimeMillis();

        TextScore textScore = textMeter.get("en").score(EN_TEXT.toUpperCase(Locale.ENGLISH));
        System.out.println("en-based score for english text:\n" + textScore);

        textScore = textMeter.get("en").score(RU_TEXT.toUpperCase());
        System.out.println("en-based score for russian text:\n" + textScore);

        char[] testArray = new char[2048];
        Arrays.fill(testArray, 'A');

        String testString = new String(testArray);
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for non-natural text:\n" + textScore);

        testString = new String(getRandomBinaryText(2048)).toUpperCase();
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for random binary text:\n" + textScore);

        testString = new String(getRandomCharacterText("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 2048));
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for random character text:\n" + textScore);

        System.out.println(String.format("%s floor: %.7f number: %.0f\n", NgramType.QUADGRAM, textMeter.get("en").getNgram(NgramType.QUADGRAM).floor(), textMeter.get("en").getNgram(NgramType.QUADGRAM).totalFreq()));


        textScore = textMeter.get("ru").score(EN_TEXT.toUpperCase(Locale.ENGLISH));
        System.out.println("ru-based score for english text:\n" + textScore);

        textScore = textMeter.get("ru").score(RU_TEXT.toUpperCase());
        System.out.println("ru-based score for russian text:\n" + textScore);

        testArray = new char[2048];
        Arrays.fill(testArray, 'А');

        testString = new String(testArray);
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for non-natural text:\n" + textScore);

        testString = new String(getRandomBinaryText(2048)).toUpperCase();
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for random binary text:\n" + textScore);

        testString = new String(getRandomCharacterText("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЭЬЭЮЯ", 2048));
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for random character text:\n" + textScore);

        System.out.println(String.format("calc finished: %d msec", System.currentTimeMillis() - mark));

    }

}
