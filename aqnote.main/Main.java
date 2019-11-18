package com.aqnote.main;

public class Main {

    public static void main(String[] args) {
        int[] ｘ = { 1, 2, 3 };
        int[] x = { 1, 2, 3 };
        int[] ｙ = { 4, 5, 6 };
        int[] y = { 4, 5, 6 };
        int[] ｚ = new int[ｘ.length];
        int[] z = new int[ｘ.length];
        for (int i = 0; i < ｚ.length; i++) {
            ｚ[i] = ｘ[i] + ｙ[i];
            z[i] = x[i] + y[i];
        }
        System.out.println(ｚ);
        System.out.println(z);
    }
}