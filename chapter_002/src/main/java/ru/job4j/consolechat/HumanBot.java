package ru.job4j.consolechat;

import java.io.PrintStream;

class HumanBot {
    PrintStream out;
    String str = "";
    String answer;

    public HumanBot(String str) {
        this.out = System.out;
        this.str = str;
    }
}
