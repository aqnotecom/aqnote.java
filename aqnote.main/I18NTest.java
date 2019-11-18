package com.aqnote.main;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by madding on 5/3/17.
 *
 * @author "Peng Li"<aqnote@aqnote.com>
 */
public class I18NTest {

    public static void main(String[] args) {
        I18NTest test = new I18NTest();
        test.test_en_US();
        test.test_zh_CN();
        test.test_de_DE();
    }

    public void test_en_US() {
        String language = "en";
        String country = "US";
        doI18n(language, country);
    }

    public void test_zh_CN() {
        String language = "zh";
        String country = "CN";
        doI18n(language, country);
    }

    public void test_de_DE() {
        String language = "de";
        String country = "DE";
        doI18n(language, country);
    }

    public void doI18n(String lang, String country) {
        Locale locale = new Locale(lang, country);

        ResourceBundle bundle = ResourceBundle.getBundle("key", locale);

        for (int i = 1; i <= 3; i++) {
            String value = bundle.getString("key" + i);
            System.out.format("key=%s, value=%s\n", "key" + i, value);
        }
    }

}
