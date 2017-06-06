package com.aqnote.shared.test.locale;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

import com.aqnote.shared.lang.StringUtil;

/**
 * Created by madding on 5/11/17.
 */
public class LocaleSensitiveTest {

    public static void main(String[] args) {
        LocaleSensitiveTest test = new LocaleSensitiveTest();
        test.test_getCurrencyCode();
        test.test_getAvailableCurrencies();
        test.test_getAvailableLocales();
    }

    private void test_getCurrencyCode() {
        Locale locale = Locale.forLanguageTag("en-US");
        Currency currency = Currency.getInstance(locale);
        System.out.printf("%3s %4d %s\n", currency.getCurrencyCode(), currency.getNumericCode(), currency.getDisplayName());
        System.out.println(StringUtil.repeat("=", 32));
    }

    private void test_getAvailableCurrencies() {
        Set<Currency> currencySet = Currency.getAvailableCurrencies();
        for(Currency currency : currencySet) {
            System.out.printf("%3s %4d %s\n", currency.getCurrencyCode(), currency.getNumericCode(), currency.getDisplayName());
        }

        System.out.println(StringUtil.repeat("=", 32));
    }

    private void test_getAvailableLocales() {
        Locale[] avaliableLocales = DateFormat.getAvailableLocales();
        for(Locale locale : avaliableLocales) {
            System.out.printf("%-10.5s : %s\n", locale.toString(), locale.getDisplayName());
            // System.out.printf("%-10s : %s\n", locale.toString(), locale.getDisplayName());
        }
        System.out.println(StringUtil.repeat("=", 32));
    }

    private void test_() {

        Calendar.getInstance();
    }
}
