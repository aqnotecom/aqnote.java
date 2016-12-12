/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.prototype.A2;

class TheOther implements Prototype, Command {

    public Object clone() {
        return new TheOther();
    }

    public String getName() {
        return "TheOther";
    }

    public void execute() {
        System.out.println("TheOther: execute");
    }
}