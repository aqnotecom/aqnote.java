/*
 * Copyright madding.me.
 */
package com.madding.shared.lang;

import org.apache.commons.lang.StringUtils;

/**
 * 类StringUtils.java的实现描述： 有关字符串处理的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author madding.lip May 7, 2012 5:26:08 PM
 */
public class StringUtil {

    /** 空字符串。 */
    public static final String EMPTY_STRING = "";

    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = false
     * StringUtils.isEmpty("")        = false
     * StringUtils.isEmpty(" ")       = true
     * StringUtils.isEmpty("bob")     = true
     * StringUtils.isEmpty("  bob  ") = true
     * </pre>
     * 
     * @param str 要检查的字符串
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     * 
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * 
     * <pre>
     * StringUtils.isBlank(null)      = false
     * StringUtils.isBlank("")        = false
     * StringUtils.isBlank(" ")       = false
     * StringUtils.isBlank("bob")     = true
     * StringUtils.isBlank("  bob  ") = true
     * </pre>
     * 
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /* ============================================================================ */
    /* 默认值函数。 */
    /*                                                                              */
    /* 当字符串为null、empty或blank时，将字符串转换成指定的默认字符串。 */
    /* ============================================================================ */

    /**
     * 如果字符串是<code>null</code>，则返回空字符串<code>""</code>，否则返回字符串本身。
     * 
     * <pre>
     * StringUtils.defaultIfNull(null)  = ""
     * StringUtils.defaultIfNull("")    = ""
     * StringUtils.defaultIfNull("  ")  = "  "
     * StringUtils.defaultIfNull("bat") = "bat"
     * </pre>
     * 
     * @param str 要转换的字符串
     * @return 字符串本身或空字符串<code>""</code>
     */
    public static String defaultIfNull(String str) {
        return defaultIfNull(str, EMPTY_STRING);
    }

    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     * 
     * <pre>
     * StringUtils.defaultIfNull(null, "default")  = "default"
     * StringUtils.defaultIfNull("", "default")    = ""
     * StringUtils.defaultIfNull("  ", "default")  = "  "
     * StringUtils.defaultIfNull("bat", "default") = "bat"
     * </pre>
     * 
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String defaultIfNull(String str, String defaultStr) {
        return (str == null) ? defaultStr : str;
    }

    /**
     * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回空字符串<code>""</code>，否则返回字符串本身。
     * <p>
     * 此方法实际上和<code>defaultIfNull(String)</code>等效。
     * 
     * <pre>
     * StringUtils.defaultIfEmpty(null)  = ""
     * StringUtils.defaultIfEmpty("")    = ""
     * StringUtils.defaultIfEmpty("  ")  = "  "
     * StringUtils.defaultIfEmpty("bat") = "bat"
     * </pre>
     * </p>
     * 
     * @param str 要转换的字符串
     * @return 字符串本身或空字符串<code>""</code>
     */
    public static String defaultIfEmpty(String str) {
        return defaultIfEmpty(str, EMPTY_STRING);
    }

    /**
     * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回指定默认字符串，否则返回字符串本身。
     * 
     * <pre>
     * StringUtils.defaultIfEmpty(null, "default")  = "default"
     * StringUtils.defaultIfEmpty("", "default")    = "default"
     * StringUtils.defaultIfEmpty("  ", "default")  = "  "
     * StringUtils.defaultIfEmpty("bat", "default") = "bat"
     * </pre>
     * 
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String defaultIfEmpty(String str, String defaultStr) {
        return ((str == null) || (str.length() == 0)) ? defaultStr : str;
    }

    /**
     * 如果字符串是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符，则返回空字符串<code>""</code>，否则返回字符串本身。
     * 
     * <pre>
     * StringUtils.defaultIfBlank(null)  = ""
     * StringUtils.defaultIfBlank("")    = ""
     * StringUtils.defaultIfBlank("  ")  = ""
     * StringUtils.defaultIfBlank("bat") = "bat"
     * </pre>
     * 
     * @param str 要转换的字符串
     * @return 字符串本身或空字符串<code>""</code>
     */
    public static String defaultIfBlank(String str) {
        return defaultIfBlank(str, EMPTY_STRING);
    }

    /**
     * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回指定默认字符串，否则返回字符串本身。
     * 
     * <pre>
     * StringUtils.defaultIfBlank(null, "default")  = "default"
     * StringUtils.defaultIfBlank("", "default")    = "default"
     * StringUtils.defaultIfBlank("  ", "default")  = "default"
     * StringUtils.defaultIfBlank("bat", "default") = "bat"
     * </pre>
     * 
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String defaultIfBlank(String str, String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.trim(null, *)          = null
     * StringUtils.trim("", *)            = ""
     * StringUtils.trim("abc", null)      = "abc"
     * StringUtils.trim("  abc", null)    = "abc"
     * StringUtils.trim("abc  ", null)    = "abc"
     * StringUtils.trim(" abc ", null)    = "abc"
     * StringUtils.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str 要处理的字符串
     * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(String str, String stripChars) {
        return trim(str, stripChars, 0);
    }

    /**
     * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * 
     * <pre>
     * StringUtils.trimStart(null)         = null
     * StringUtils.trimStart("")           = ""
     * StringUtils.trimStart("abc")        = "abc"
     * StringUtils.trimStart("  abc")      = "abc"
     * StringUtils.trimStart("abc  ")      = "abc  "
     * StringUtils.trimStart(" abc ")      = "abc "
     * </pre>
     * </p>
     * 
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回<code>null</code>
     */
    public static String trimStart(String str) {
        return trim(str, null, -1);
    }

    /**
     * 除去字符串头部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.trimStart(null, *)          = null
     * StringUtils.trimStart("", *)            = ""
     * StringUtils.trimStart("abc", "")        = "abc"
     * StringUtils.trimStart("abc", null)      = "abc"
     * StringUtils.trimStart("  abc", null)    = "abc"
     * StringUtils.trimStart("abc  ", null)    = "abc  "
     * StringUtils.trimStart(" abc ", null)    = "abc "
     * StringUtils.trimStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     * 
     * @param str 要处理的字符串
     * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trimStart(String str, String stripChars) {
        return trim(str, stripChars, -1);
    }

    /**
     * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * 
     * <pre>
     * StringUtils.trimEnd(null)       = null
     * StringUtils.trimEnd("")         = ""
     * StringUtils.trimEnd("abc")      = "abc"
     * StringUtils.trimEnd("  abc")    = "  abc"
     * StringUtils.trimEnd("abc  ")    = "abc"
     * StringUtils.trimEnd(" abc ")    = " abc"
     * </pre>
     * </p>
     * 
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回<code>null</code>
     */
    public static String trimEnd(String str) {
        return trim(str, null, 1);
    }

    /**
     * 除去字符串尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.trimEnd(null, *)          = null
     * StringUtils.trimEnd("", *)            = ""
     * StringUtils.trimEnd("abc", "")        = "abc"
     * StringUtils.trimEnd("abc", null)      = "abc"
     * StringUtils.trimEnd("  abc", null)    = "  abc"
     * StringUtils.trimEnd("abc  ", null)    = "abc"
     * StringUtils.trimEnd(" abc ", null)    = " abc"
     * StringUtils.trimEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str 要处理的字符串
     * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trimEnd(String str, String stripChars) {
        return trim(str, stripChars, 1);
    }

    /**
     * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.trim(null, *)          = null
     * StringUtils.trim("", *)            = ""
     * StringUtils.trim("abc", null)      = "abc"
     * StringUtils.trim("  abc", null)    = "abc"
     * StringUtils.trim("abc  ", null)    = "abc"
     * StringUtils.trim(" abc ", null)    = "abc"
     * StringUtils.trim("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str 要处理的字符串
     * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
     * @param mode <code>-1</code>表示trimStart，<code>0</code>表示trim全部，<code>1</code>表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    private static String trim(String str, String stripChars, int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = length;

        // 扫描字符串头部
        if (mode <= 0) {
            if (stripChars == null) {
                while ((start < end) && (Character.isWhitespace(str.charAt(start)))) {
                    start++;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                    start++;
                }
            }
        }

        // 扫描字符串尾部
        if (mode >= 0) {
            if (stripChars == null) {
                while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
                    end--;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
                while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                    end--;
                }
            }
        }

        if ((start > 0) || (end < length)) {
            return str.substring(start, end);
        }

        return str;
    }

    /**
     * 将字符串转换成camel case。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.toCamelCase(null)  = null
     * StringUtils.toCamelCase("")    = ""
     * StringUtils.toCamelCase("aBc") = "aBc"
     * StringUtils.toCamelCase("aBc def") = "aBcDef"
     * StringUtils.toCamelCase("aBc def_ghi") = "aBcDefGhi"
     * StringUtils.toCamelCase("aBc def_ghi 123") = "aBcDefGhi123"
     * </pre>
     * </p>
     * <p>
     * 此方法会保留除了下划线和空白以外的所有分隔符。
     * </p>
     * 
     * @param str 要转换的字符串
     * @return camel case字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toCamelCase(String str) {
        return CAMEL_CASE_TOKENIZER.parse(str);
    }

    /**
     * 将字符串转换成pascal case。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.toPascalCase(null)  = null
     * StringUtils.toPascalCase("")    = ""
     * StringUtils.toPascalCase("aBc") = "ABc"
     * StringUtils.toPascalCase("aBc def") = "ABcDef"
     * StringUtils.toPascalCase("aBc def_ghi") = "ABcDefGhi"
     * StringUtils.toPascalCase("aBc def_ghi 123") = "aBcDefGhi123"
     * </pre>
     * </p>
     * <p>
     * 此方法会保留除了下划线和空白以外的所有分隔符。
     * </p>
     * 
     * @param str 要转换的字符串
     * @return pascal case字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toPascalCase(String str) {
        return PASCAL_CASE_TOKENIZER.parse(str);
    }

    /**
     * 将字符串转换成下划线分隔的大写字符串。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.toUpperCaseWithUnderscores(null)  = null
     * StringUtils.toUpperCaseWithUnderscores("")    = ""
     * StringUtils.toUpperCaseWithUnderscores("aBc") = "A_BC"
     * StringUtils.toUpperCaseWithUnderscores("aBc def") = "A_BC_DEF"
     * StringUtils.toUpperCaseWithUnderscores("aBc def_ghi") = "A_BC_DEF_GHI"
     * StringUtils.toUpperCaseWithUnderscores("aBc def_ghi 123") = "A_BC_DEF_GHI_123"
     * StringUtils.toUpperCaseWithUnderscores("__a__Bc__") = "__A__BC__"
     * </pre>
     * </p>
     * <p>
     * 此方法会保留除了空白以外的所有分隔符。
     * </p>
     * 
     * @param str 要转换的字符串
     * @return 下划线分隔的大写字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toUpperCaseWithUnderscores(String str) {
        return UPPER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
    }

    /**
     * 将字符串转换成下划线分隔的小写字符串。
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * 
     * <pre>
     * StringUtils.toLowerCaseWithUnderscores(null)  = null
     * StringUtils.toLowerCaseWithUnderscores("")    = ""
     * StringUtils.toLowerCaseWithUnderscores("aBc") = "a_bc"
     * StringUtils.toLowerCaseWithUnderscores("aBc def") = "a_bc_def"
     * StringUtils.toLowerCaseWithUnderscores("aBc def_ghi") = "a_bc_def_ghi"
     * StringUtils.toLowerCaseWithUnderscores("aBc def_ghi 123") = "a_bc_def_ghi_123"
     * StringUtils.toLowerCaseWithUnderscores("__a__Bc__") = "__a__bc__"
     * </pre>
     * </p>
     * <p>
     * 此方法会保留除了空白以外的所有分隔符。
     * </p>
     * 
     * @param str 要转换的字符串
     * @return 下划线分隔的小写字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toLowerCaseWithUnderscores(String str) {
        return LOWER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
    }

    /** 解析单词的解析器。 */
    private static final WordTokenizer CAMEL_CASE_TOKENIZER                  = new WordTokenizer() {

                                                                                 protected void startSentence(StringBuffer buffer,
                                                                                                              char ch) {
                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void startWord(StringBuffer buffer,
                                                                                                          char ch) {
                                                                                     if (!isDelimiter(buffer.charAt(buffer.length()
                                                                                                                    - 1))) {
                                                                                         buffer.append(Character.toUpperCase(ch));
                                                                                     } else {
                                                                                         buffer.append(Character.toLowerCase(ch));
                                                                                     }
                                                                                 }

                                                                                 protected void inWord(StringBuffer buffer,
                                                                                                       char ch) {
                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void startDigitSentence(StringBuffer buffer,
                                                                                                                   char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void startDigitWord(StringBuffer buffer,
                                                                                                               char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDigitWord(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDelimiter(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     if (ch != UNDERSCORE) {
                                                                                         buffer.append(ch);
                                                                                     }
                                                                                 }
                                                                             };

    private static final WordTokenizer PASCAL_CASE_TOKENIZER                 = new WordTokenizer() {

                                                                                 protected void startSentence(StringBuffer buffer,
                                                                                                              char ch) {
                                                                                     buffer.append(Character.toUpperCase(ch));
                                                                                 }

                                                                                 protected void startWord(StringBuffer buffer,
                                                                                                          char ch) {
                                                                                     buffer.append(Character.toUpperCase(ch));
                                                                                 }

                                                                                 protected void inWord(StringBuffer buffer,
                                                                                                       char ch) {
                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void startDigitSentence(StringBuffer buffer,
                                                                                                                   char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void startDigitWord(StringBuffer buffer,
                                                                                                               char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDigitWord(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDelimiter(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     if (ch != UNDERSCORE) {
                                                                                         buffer.append(ch);
                                                                                     }
                                                                                 }
                                                                             };

    private static final WordTokenizer UPPER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {

                                                                                 protected void startSentence(StringBuffer buffer,
                                                                                                              char ch) {
                                                                                     buffer.append(Character.toUpperCase(ch));
                                                                                 }

                                                                                 protected void startWord(StringBuffer buffer,
                                                                                                          char ch) {
                                                                                     if (!isDelimiter(buffer.charAt(buffer.length()
                                                                                                                    - 1))) {
                                                                                         buffer.append(UNDERSCORE);
                                                                                     }

                                                                                     buffer.append(Character.toUpperCase(ch));
                                                                                 }

                                                                                 protected void inWord(StringBuffer buffer,
                                                                                                       char ch) {
                                                                                     buffer.append(Character.toUpperCase(ch));
                                                                                 }

                                                                                 protected void startDigitSentence(StringBuffer buffer,
                                                                                                                   char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void startDigitWord(StringBuffer buffer,
                                                                                                               char ch) {
                                                                                     if (!isDelimiter(buffer.charAt(buffer.length()
                                                                                                                    - 1))) {
                                                                                         buffer.append(UNDERSCORE);
                                                                                     }

                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDigitWord(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDelimiter(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }
                                                                             };

    private static final WordTokenizer LOWER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {

                                                                                 protected void startSentence(StringBuffer buffer,
                                                                                                              char ch) {
                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void startWord(StringBuffer buffer,
                                                                                                          char ch) {
                                                                                     if (!isDelimiter(buffer.charAt(buffer.length()
                                                                                                                    - 1))) {
                                                                                         buffer.append(UNDERSCORE);
                                                                                     }

                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void inWord(StringBuffer buffer,
                                                                                                       char ch) {
                                                                                     buffer.append(Character.toLowerCase(ch));
                                                                                 }

                                                                                 protected void startDigitSentence(StringBuffer buffer,
                                                                                                                   char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void startDigitWord(StringBuffer buffer,
                                                                                                               char ch) {
                                                                                     if (!isDelimiter(buffer.charAt(buffer.length()
                                                                                                                    - 1))) {
                                                                                         buffer.append(UNDERSCORE);
                                                                                     }

                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDigitWord(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }

                                                                                 protected void inDelimiter(StringBuffer buffer,
                                                                                                            char ch) {
                                                                                     buffer.append(ch);
                                                                                 }
                                                                             };

    /**
     * 解析出下列语法所构成的<code>SENTENCE</code>。
     * 
     * <pre>
     *  SENTENCE = WORD (DELIMITER* WORD)*
     * 
     *  WORD = UPPER_CASE_WORD | LOWER_CASE_WORD | TITLE_CASE_WORD | DIGIT_WORD
     * 
     *  UPPER_CASE_WORD = UPPER_CASE_LETTER+
     *  LOWER_CASE_WORD = LOWER_CASE_LETTER+
     *  TITLE_CASE_WORD = UPPER_CASE_LETTER LOWER_CASE_LETTER+
     *  DIGIT_WORD      = DIGIT+
     * 
     *  UPPER_CASE_LETTER = Character.isUpperCase()
     *  LOWER_CASE_LETTER = Character.isLowerCase()
     *  DIGIT             = Character.isDigit()
     *  NON_LETTER_DIGIT  = !Character.isUpperCase() && !Character.isLowerCase() && !Character.isDigit()
     * 
     *  DELIMITER = WHITESPACE | NON_LETTER_DIGIT
     * </pre>
     */
    private abstract static class WordTokenizer {

        protected static final char UNDERSCORE = '_';

        /**
         * Parse sentence。
         */
        public String parse(String str) {
            if (StringUtils.isEmpty(str)) {
                return str;
            }

            int length = str.length();
            StringBuffer buffer = new StringBuffer(length);

            for (int index = 0; index < length; index++) {
                char ch = str.charAt(index);

                // 忽略空白。
                if (Character.isWhitespace(ch)) {
                    continue;
                }

                // 大写字母开始：UpperCaseWord或是TitleCaseWord。
                if (Character.isUpperCase(ch)) {
                    int wordIndex = index + 1;

                    while (wordIndex < length) {
                        char wordChar = str.charAt(wordIndex);
                        if (Character.isUpperCase(wordChar)) {
                            wordIndex++;
                        } else if (Character.isLowerCase(wordChar)) {
                            wordIndex--;
                            break;
                        } else {
                            break;
                        }
                    }

                    // 1. wordIndex == length，说明最后一个字母为大写，以upperCaseWord处理之。
                    // 2. wordIndex == index，说明index处为一个titleCaseWord。
                    // 3. wordIndex > index，说明index到wordIndex - 1处全部是大写，以upperCaseWord处理。
                    if ((wordIndex == length) || (wordIndex > index)) {
                        index = parseUpperCaseWord(buffer, str, index, wordIndex);
                    } else {
                        index = parseTitleCaseWord(buffer, str, index);
                    }

                    continue;
                }

                // 小写字母开始：LowerCaseWord。
                if (Character.isLowerCase(ch)) {
                    index = parseLowerCaseWord(buffer, str, index);
                    continue;
                }

                // 数字开始：DigitWord。
                if (Character.isDigit(ch)) {
                    index = parseDigitWord(buffer, str, index);
                    continue;
                }

                // 非字母数字开始：Delimiter。
                inDelimiter(buffer, ch);
            }

            return buffer.toString();
        }

        private int parseUpperCaseWord(StringBuffer buffer, String str, int index, int length) {
            char ch = str.charAt(index++);

            // 首字母，必然存在且为大写。
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // 后续字母，必为小写。
            for (; index < length; index++) {
                ch = str.charAt(index);
                inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuffer buffer, String str, int index) {
            char ch = str.charAt(index++);

            // 首字母，必然存在且为小写。
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // 后续字母，必为小写。
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuffer buffer, String str, int index) {
            char ch = str.charAt(index++);

            // 首字母，必然存在且为大写。
            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            // 后续字母，必为小写。
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuffer buffer, String str, int index) {
            char ch = str.charAt(index++);

            // 首字符，必然存在且为数字。
            if (buffer.length() == 0) {
                startDigitSentence(buffer, ch);
            } else {
                startDigitWord(buffer, ch);
            }

            // 后续字符，必为数字。
            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isDigit(ch)) {
                    inDigitWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        protected boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuffer buffer, char ch);

        protected abstract void startWord(StringBuffer buffer, char ch);

        protected abstract void inWord(StringBuffer buffer, char ch);

        protected abstract void startDigitSentence(StringBuffer buffer, char ch);

        protected abstract void startDigitWord(StringBuffer buffer, char ch);

        protected abstract void inDigitWord(StringBuffer buffer, char ch);

        protected abstract void inDelimiter(StringBuffer buffer, char ch);
    }

}
