/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.prototype.A1;

public class FactoryPrototype {

    interface IBase {

        IBase cloan();
    }

    static class Tom implements IBase {

        public IBase cloan() {
            return new Tom();
        }

        public String toString() {
            return "ttt";
        }
    }

    static class Dick implements IBase {

        public IBase cloan() {
            return new Dick();
        }

        public String toString() {
            return "ddd";
        }
    }

    static class Harry implements IBase {

        public IBase cloan() {
            return new Harry();
        }

        public String toString() {
            return "hhh";
        }
    }

    static class Factory {

        private static java.util.Map<String, IBase> prototypes = new java.util.HashMap<String, IBase>();
        static {
            prototypes.put("tom", new Tom());
            prototypes.put("dick", new Dick());
            prototypes.put("harry", new Harry());
        }

        public static IBase makeObject(String s) {
            return ((IBase) prototypes.get(s)).cloan();
        }
    }

}
