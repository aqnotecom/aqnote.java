/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.prototype.A2;

public class PrototypesModule {

    // 2. "registry" of prototypical objs
    private static Prototype[] prototypes = new Prototype[9];
    private static int         total      = 0;

    // Adds a feature to the Prototype attribute of the PrototypesModule class
    // obj The feature to be added to the Prototype attribute
    public static void addPrototype(Prototype obj) {
        prototypes[total++] = obj;
    }

    public static Object findAndClone(String name) {
        // 4. The "virtual ctor"
        for (int i = 0; i < total; i++) {
            if (prototypes[i].getName().equals(name)) {
                return prototypes[i].clone();
            }
        }
        System.out.println(name + " not found");
        return null;
    }
}