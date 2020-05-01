package ru.job4j.consolechat;

import java.io.*;

public class ConsoleChat implements Constant {
    private String fileLog = "chapter_002/data/logchat.txt";

    public static void main(final String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        new ConsoleChat().go();
    }

    final void go() throws FileNotFoundException, UnsupportedEncodingException {
        String mesage = "";
        System.out.println("Введите предложение:");

        final HumanBotInt human = new Human();
        final HumanBotInt bot = new Bot(human);

        while (!mesage.endsWith(END)) {
            mesage = bot.action();
            System.out.println(mesage);
            log(mesage);
        }
    }

    final void log(final String mesage) {
        if (!mesage.isEmpty()) {
            String log = mesage + System.lineSeparator();
            savefile(fileLog, log);
        }
    }

    final void savefile(final String path, final String text) {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


