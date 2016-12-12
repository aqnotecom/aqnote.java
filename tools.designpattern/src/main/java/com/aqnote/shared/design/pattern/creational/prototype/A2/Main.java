/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.prototype.A2;

public class Main {

    // 3. Populate the "registry"
    public static void initializePrototypes() {
        PrototypesModule.addPrototype(new This());
        PrototypesModule.addPrototype(new That());
        PrototypesModule.addPrototype(new TheOther());
    }

    public static void main(String[] args) {
        initializePrototypes();
        Object[] objects = new Object[9];
        int total = 0;

        // 6. Client does not use "new"
        for (int i = 0; i < args.length; i++) {
            objects[total] = PrototypesModule.findAndClone(args[i]);
            if (objects[total] != null) total++;
        }
        for (int i = 0; i < total; i++) {
            ((Command) objects[i]).execute();
        }
    }
}
