package ru.job4j.consolechat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

interface HumanBotInt {
    String action(String mesage);
}


interface Answer {
    String getAnswer();
}


public class ConsoleChat {
    String fileLog = "chapter_002/data/logchat.txt";
    static char[] buffer = new char[16];
    String mesage = "";

    public static void main(String[] args) {
        new ConsoleChat().go();
    }

    void go() {
        HumanBot bot = new Bot("BOT — ");
        HumanBot human = new Human("Human — ");
        List<HumanBot> humanBots = List.of(human, bot);
        System.out.println("Введите предложение:");
        int n = 0;
        while (!mesage.equals("@endchat")) {
            mesage = ((HumanBotInt) (humanBots.get(n))).action(mesage);
            log(mesage, humanBots.get(n));
            n = ++n % humanBots.size();
        }
    }

    void log(String mesage, HumanBot h) {
        if (!mesage.isEmpty()) {
            String log = h.str + mesage + System.lineSeparator();
            //System.out.print("печать  " + log);
            savefile(fileLog, log);
        }
    }

    void savefile(String path, String text) {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


