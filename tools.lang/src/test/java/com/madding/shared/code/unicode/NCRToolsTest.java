/*
 * Programmer-tools -- A develop code for dever to quickly analyse Copyright (C) 2013-2016 madding.lip
 * <madding.lip@gmail.com>. This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.madding.shared.code.unicode;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

/**
 * NCRToolsTest.java desc：TODO
 * 
 * @author madding.lip Dec 25, 2015 1:36:26 PM
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NCRToolsTest extends TestCase {

    public void test01() {
        System.out.println("run this is 1=="
                           + NCRTools.mumChangeNCR2GB("&#37038;&#20214;&#27169;&#29256;&#27979;&#35797;&#39033;&#30446;1833"));
        System.out.println("run this is 1==" + NCRTools.mumChangeNCR2GB("濟南吉宇&#183;勛業代理銷售有限公司"));
        System.out.println("change ncr==" + NCRTools.singChangeGB2NCR("'"));
        System.out.println("change ncr==" + NCRTools.singChangeGB2NCR("mm"));
    }

}
