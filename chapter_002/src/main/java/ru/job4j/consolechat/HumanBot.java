package ru.job4j.consolechat;

import java.io.PrintStream;

class HumanBot {
    PrintStream out;
    String str = "";

    public HumanBot(String str) {
        this.out = System.out;
        this.str = str;
    }
}
