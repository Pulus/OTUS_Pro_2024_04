package ru.otus.pro.hw1;

import com.google.common.base.Joiner;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String[] args) {
        Joiner joiner = Joiner.on(", ").skipNulls();
        System.out.println(joiner.join("Hello", null, "Otus!"));
    }
}
