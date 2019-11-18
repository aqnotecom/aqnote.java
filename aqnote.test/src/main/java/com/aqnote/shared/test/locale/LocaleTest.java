package com.aqnote.shared.test.locale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

import com.aqnote.shared.lang.StringUtil;

/**
 * Created by madding on 5/2/17.
 */
public class LocaleTest {

    public static void main(String[] args) {
        LocaleTest test = new LocaleTest();
        test.test_getExtensionKeys();
        test.test_getUnicodeLocaleKeys();
        test.test_toLanguageTag();
        test.test_toString();
        test.test_toString2();
        test.test_LanguageRange();
        test.test_filter();
    }

    private void test_getExtensionKeys() {
        System.out.println(Locale.CHINA.getExtensionKeys());
    }

    private void test_getUnicodeLocaleKeys() {
        System.out.println(Locale.CHINA.getUnicodeLocaleKeys());
    }

    private void test_toLanguageTag() {
        System.out.println(Locale.CHINA.toLanguageTag());
    }

    private void test_toString() {
        System.out.println(Locale.forLanguageTag("zh-hans-CN").toString());
    }

    private void test_toString2() {
        Locale locale = new Locale.Builder().setLanguage("zh").setScript("Hant").setRegion("TW").setVariant("xjava").build();
        System.out.println(locale.toString());
        System.out.println(StringUtil.repeat("=", 32));

        System.out.println(locale.getLanguage());
        System.out.println(locale.getDisplayLanguage());
        System.out.println(locale.getISO3Language());
        System.out.println(StringUtil.repeat("=", 32));

        System.out.println(locale.getCountry());
        System.out.println(locale.getDisplayCountry());
        System.out.println(locale.getISO3Country());
        System.out.println(StringUtil.repeat("=", 32));

        System.out.println(locale.getVariant());
        System.out.println(locale.getDisplayVariant());
        System.out.println(StringUtil.repeat("=", 32));

        System.out.println(locale.getScript());
        System.out.println(locale.getDisplayScript());
        System.out.println(StringUtil.repeat("=", 32));
    }

    private void test_LanguageRange() {
        Locale locale = Locale.forLanguageTag("en-US");
        String range = "en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0";
        List<LanguageRange> languageRangeList = Locale.LanguageRange.parse(range);
        System.out.printf("%s\n", languageRangeList);
    }

    private void test_filter() {
        Collection<Locale> locales = new ArrayList<>();
        locales.add(Locale.forLanguageTag("en-GB"));
        locales.add(Locale.forLanguageTag("ja"));
        locales.add(Locale.forLanguageTag("zh-cmn-Hans-CN"));
        locales.add(Locale.forLanguageTag("en-US"));
        locales.add(Locale.forLanguageTag("fr-FR"));
        locales.add(Locale.forLanguageTag("fr"));

        String ranges = "en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.1,fr;q=0.0";
        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(ranges);

        List<Locale> results = Locale.filter(languageRanges, locales);

        for (Locale l : results) {
            System.out.println(l.toString());
        }
    }


}
