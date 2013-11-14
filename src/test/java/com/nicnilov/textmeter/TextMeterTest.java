package com.nicnilov.textmeter;

import com.nicnilov.textmeter.ngrams.NgramType;
import com.nicnilov.textmeter.ngrams.TextScore;
import com.nicnilov.textmeter.ngrams.storage.NgramStorageStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/**
 * Created as part of textmeter project
 * by Nic Nilov on 27.10.13 at 18:58
 */
public class TextMeterTest extends TestCase {

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

    public void setUp() throws Exception {
        textMeter = new TextMeter();
        textMeter.createTextLanguage("en");
        textMeter.createTextLanguage("ru");

        TextLanguage en = textMeter.get("en");
        long mark = System.currentTimeMillis();
        en.getNgram(NgramType.UNIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.EN_UNIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.EN_UNIGRAMS_EXCNT);
        en.getNgram(NgramType.BIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.EN_BIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.EN_BIGRAMS_EXCNT);
        en.getNgram(NgramType.TRIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.EN_TRIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.EN_TRIGRAMS_EXCNT);
        en.getNgram(NgramType.QUADGRAM, TestUtils.loadResource(this.getClass(), TestUtils.EN_QUADGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.EN_QUADGRAMS_EXCNT);
        en.getNgram(NgramType.QUINTGRAM, TestUtils.loadResource(this.getClass(), TestUtils.EN_QUINTGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.EN_QUINTGRAMS_EXCNT);
        System.out.println(String.format("en ngrams loaded: %d msec", System.currentTimeMillis() - mark));

        TextLanguage ru = textMeter.get("ru");
        mark = System.currentTimeMillis();
        ru.getNgram(NgramType.UNIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.RU_UNIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.RU_UNIGRAMS_EXCNT);
        ru.getNgram(NgramType.BIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.RU_BIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.RU_BIGRAMS_EXCNT);
        ru.getNgram(NgramType.TRIGRAM, TestUtils.loadResource(this.getClass(), TestUtils.RU_TRIGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.RU_TRIGRAMS_EXCNT);
        ru.getNgram(NgramType.QUADGRAM, TestUtils.loadResource(this.getClass(), TestUtils.RU_QUADGRAMS), NgramStorageStrategy.TREEMAP, TestUtils.RU_QUADGRAMS_EXCNT);
        System.out.println(String.format("ru ngrams loaded: %d msec", System.currentTimeMillis() - mark));
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
            result[i] = chars.charAt(rg.nextInt(chars.length()));
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

        testString = new String(new char[512]).replace("\0", "TION");
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for max ngram:\n" + textScore);

        testString = getRandomBinaryText(2048).toUpperCase();
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for random binary text:\n" + textScore);

        testString = getRandomCharacterText("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 2048);
        textScore = textMeter.get("en").score(testString);
        System.out.println("en-based score for random character text:\n" + textScore);

        System.out.println(String.format("%s floor: %.7f volume: %.0f\n", NgramType.QUADGRAM,
                textMeter.get("en").getNgram(NgramType.QUADGRAM).floor(),
                textMeter.get("en").getNgram(NgramType.QUADGRAM).volume()));


        textScore = textMeter.get("ru").score(EN_TEXT.toUpperCase(Locale.ENGLISH));
        System.out.println("ru-based score for english text:\n" + textScore);

        textScore = textMeter.get("ru").score(RU_TEXT.toUpperCase());
        System.out.println("ru-based score for russian text:\n" + textScore);

        testArray = new char[2048];
        Arrays.fill(testArray, 'А');

        testString = new String(testArray);
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for non-natural text:\n" + textScore);

        testString = getRandomBinaryText(2048).toUpperCase();
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for random binary text:\n" + textScore);

        testString = getRandomCharacterText("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЭЬЭЮЯ", 2048);
        textScore = textMeter.get("ru").score(testString);
        System.out.println("ru-based score for random character text:\n" + textScore);

        System.out.println(String.format("calc finished: %d msec", System.currentTimeMillis() - mark));

    }

}
