package com.madding.shared.lang;

import java.util.Locale;


/**
 * 类LocaleUtil.java的实现描述：用来处理地域和字符编码的工具类
 * 
 * @author madding.lip May 7, 2012 5:20:10 PM
 */
public class LocaleUtil {

    private static final Locale              systemLocale;
    private static Locale                    defaultLocal;

    private static final ThreadLocal<Locale> contextLocaleHolder = new ThreadLocal<Locale>();

    static {
        systemLocale = Locale.getDefault();
        defaultLocal = systemLocale;
    }

    public static Locale getDefault() {
        return (defaultLocal == null) ? systemLocale : defaultLocal;
    }

    public static Locale getContextLocale() {
        Locale contextLocale = contextLocaleHolder.get();
        return (contextLocale == null) ? getDefault() : contextLocale;
    }

    public static Locale setContextLocale(Locale locale) {
        Locale old = getContextLocale();
        contextLocaleHolder.set(locale);
        return old;
    }

    public static void resetContext() {
        contextLocaleHolder.set(null);
    }

}
