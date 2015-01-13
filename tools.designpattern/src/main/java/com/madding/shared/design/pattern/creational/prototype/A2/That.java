package com.madding.shared.design.pattern.creational.prototype.A2;

class That implements Prototype, Command {

    public Object clone() {
        return new That();
    }

    public String getName() {
        return "That";
    }

    public void execute() {
        System.out.println("That: execute");
    }
}