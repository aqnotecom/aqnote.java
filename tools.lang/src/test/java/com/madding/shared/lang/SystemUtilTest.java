package com.madding.shared.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * SystemUtil测试类
 * 
 * @author madding.lip
 */
public class SystemUtilTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        SystemUtil.dumpSystemInfo();

        List list = new ArrayList(System.getProperties().keySet());

        Collections.sort(list);

        for (Iterator i = list.iterator(); i.hasNext();) {
            String key = (String) i.next();
            String value = System.getProperty(key);

            System.out.println(key + " = " + StringUtil.defaultIfNull(StringEscapeUtil.escapeJava(value), "[n/a]"));
        }
    }
}
