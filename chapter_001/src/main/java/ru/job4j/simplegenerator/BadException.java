package ru.job4j.simplegenerator;

import java.util.Set;

abstract class BadException extends RuntimeException {
}


class NoKeys extends BadException {
    public void noKeys(String qq) {
        System.out.print("  Erorr - No keys found in List -> ");
        System.out.println(qq);
    }
}


class UnwantedKeys extends BadException {
    public void unwantedKeys(Set<String> keyList) {
        System.out.print("Error - There is no such keys in string ");
        System.out.println(keyList);
    }
}


class BlankString extends BadException {
    public void blankString() {
        System.out.println("blank String \n It`s useless");
    }
}
