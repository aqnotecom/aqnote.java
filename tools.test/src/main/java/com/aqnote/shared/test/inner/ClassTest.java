package com.aqnote.shared.test.inner;

/**
 * Created by aqnote on 6/6/17.
 */
public class ClassTest {

    public static void main(String[] args) {
        OutterClass clazz = new OutterClass();
        clazz.run();

        // OutterClass.InnerClass clazz = new OutterClass.InnerClass();
    }
}


class OutterClass {
    int mA;

    public void run() {
        System.out.println("invoke OutterClass.run()");

        OutterClass.InnerClass clazz = new OutterClass.InnerClass();
        clazz.onClick();
    }


    private static final class InnerClass {

        public void onClick() {
            System.out.println("invoke InnerClass.onClick()");
        }
    }
}