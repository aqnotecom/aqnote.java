/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.singleton;

public class Main {

    public static void main(String[] args) {

        Singleton singleton = Singleton.getInstance();
        singleton.execute();
    }
}
