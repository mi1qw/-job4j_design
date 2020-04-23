package ru.job4j.consolechat;

interface HumanBotInt {
    String action();
}


interface Answer {
    String getAnswer();
}


interface Constant {
    static final String END = "закончить";
    static final String CONT = "продолжить";
    static final String STOP = "стоп";
    static final String LN = System.lineSeparator();

    default String ln() {
        return System.lineSeparator();
    }
}