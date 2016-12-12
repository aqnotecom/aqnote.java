/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.code.unicode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 类NCRTools.java的实现描述：NCR表示字符解析成unicode
 * 
 * @author madding.lip Jul 13, 2012 1:46:50 PM
 */
public class NCRTools {

    /**
     * 将NCR编码的字符串转成GBK编码的汉字
     * 
     * @param ncrStr:NCR编码字符
     * @return: String GBK编码字符
     */
    private static String singChangeNCR2GB(String ncrStr) {
        if (ncrStr.indexOf("&") == 0) {
            String ls = ncrStr.substring(2);
            int lnum = Integer.parseInt(ls);

            String res = Integer.toHexString(lnum);
            System.out.println("res==" + res);
            byte[] neByte = new byte[2];
            if (res.length() > 3) {
                neByte[0] = (byte) Integer.parseInt(res.substring(0, 2), 16);
                neByte[1] = (byte) Integer.parseInt(res.substring(2), 16);
            } else if (res.length() == 3) {
                return "";
            } else {
                if (lnum > 128) {
                    return "";
                } else {
                    neByte[0] = 0;
                    neByte[1] = (byte) Integer.parseInt(res, 16);
                }
            }
            String lss = "";
            try {
                lss = new String(neByte, "utf-16be");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return lss;

        } else if (ncrStr.indexOf("&") > 0) {
            int bgs = ncrStr.indexOf("&");
            String festr = ncrStr.substring(0, bgs);

            String chstr = ncrStr.substring(bgs + 2);
            int lnum = Integer.parseInt(chstr);
            String res = Integer.toHexString(lnum);
            byte neByte[] = new byte[2];

            if (res.length() > 3) {
                neByte[0] = (byte) Integer.parseInt(res.substring(0, 2), 16);
                neByte[1] = (byte) Integer.parseInt(res.substring(2), 16);
            } else if (res.length() == 3) {
                return festr + " ";
            } else {
                if (lnum > 128) {
                    return festr + " ";
                } else {
                    neByte[0] = 0;
                    neByte[1] = (byte) Integer.parseInt(res, 16);
                }
            }

            String lss = "";
            try {
                lss = new String(neByte, "utf-16be");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return festr + lss;
        } else {
            return ncrStr;
        }
    }

    /**
     * 将多个NCR编码字符转为GBK编码
     * 
     * @param ncrStr:NCR字符串
     * @return:String GBK编码字符
     */
    public static String mumChangeNCR2GB(String ncrStr) {
        if (!ncrStr.contains(";")) {
            return singChangeNCR2GB(ncrStr);
        } else {
            String[] strScr = toArray(ncrStr, ";");
            String[] strRstr = new String[strScr.length];
            for (int i = 0; i < strRstr.length; i++) {
                strRstr[i] = singChangeNCR2GB(strScr[i]);
            }
            String res = arrayToString(strRstr, "");
            return res;
        }
    }

    /**
     * 将GBK编码字符串转为汉字
     * 
     * @param str:GBK编码字符串
     * @return:NCR编码字符串
     */
    public static String singChangeGB2NCR(String str) {
        String res = "";
        try {
            res = new String(str.getBytes("GB2312"), "utf-16");
            byte[] utf_16E = str.getBytes("utf-16be");
            String Str16k = byteTo16String(utf_16E);
            System.out.println("utf_16E==" + Str16k);
            int ncrNum = Integer.parseInt(Str16k, 16);
            // System.out.println("int number==="+ncrNum);
            res = "&#" + ncrNum;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 將byte数组转化为utf-16be的String字符
     * 
     * @param bt:byte类型数组
     * @return:String 以utf-16be编码字符
     */
    public static String byteTo16String(byte[] bt) {
        String res = "";
        for (int i = 0; i < bt.length; i++) {
            int hex = (int) bt[i] & 0xff;
            System.out.print(Integer.toHexString(hex) + " ");
            res = res + Integer.toHexString(hex);
        }
        return res;
    }

    /**
     * 將一个字符串按照分隔符分解成一个符串數組
     * 
     * @param: s 需要分解的字符串
     * @param: delimiter 字符串分隔符
     * @return:String[] 分解後的字符串數組
     */
    public static String[] toArray(String s, String delimiter) {
        if (s == null) {
            return null;
        }

        if (s.equals("")) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(s, delimiter);

        ArrayList<String> vec = new ArrayList<String>();

        for (; st.hasMoreTokens();) {
            String t = st.nextToken();

            if ((t != null) && (t.length() > 0)) {
                vec.add(t.trim());
            }
        }

        // no value selected
        if (vec.size() == 0) {
            return null;
        }
        String[] kw = (String[]) vec.toArray(new String[vec.size()]);
        return kw;
    }

    /**
     * 將一個字符串數組按照指定的分隔組合成一個字符串
     * 
     * @param s:需要轉換的字符串數組
     * @param delimiter:指定的分隔符
     * @return String:組合後的字符串
     */
    public static String arrayToString(String[] s, String delimiter) {
        String res = "";

        for (int i = 0; i < s.length; i++) {
            res = res + s[i];

            if (i < (s.length - 1)) {
                res = res + delimiter;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println("run this is 1==" + mumChangeNCR2GB("&#37038;&#20214;&#27169;&#29256;&#27979;&#35797;&#39033;&#30446;1833"));
        System.out.println("run this is 1==" + mumChangeNCR2GB("濟南吉宇&#183;勛業代理銷售有限公司"));
        System.out.println("change ncr==" + singChangeGB2NCR("'"));
        System.out.println("change ncr==" + singChangeGB2NCR("mm"));
    }

}
