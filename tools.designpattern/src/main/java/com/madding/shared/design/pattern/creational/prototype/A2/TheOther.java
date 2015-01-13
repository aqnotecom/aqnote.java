package com.madding.shared.design.pattern.creational.prototype.A2;

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