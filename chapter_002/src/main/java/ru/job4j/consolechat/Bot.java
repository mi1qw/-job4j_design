package ru.job4j.consolechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

class Bot implements HumanBotInt, Constant {
    private String filephrase = "chapter_002/data/phrase.txt";
    String[] words;
    private Map<String, Answer> map = Map.of(
            CONT, this::play,
            STOP, this::stop,
            END, this::end
    );
    private Answer l = this::play;
    private HumanBotInt answer1;

    public Bot(final HumanBotInt answer1) {
        this.words = load(new File(filephrase));
        this.answer1 = answer1;
    }

    @Override
    public String action() {
        String mesage = this.answer1.action();
        if (map.containsKey(mesage)) {
            l = map.get(mesage);
        }
        return "Human - " + mesage + l.getAnswer();
    }

    private String play() {
        int q = (int) (Math.random() * words.length);
        return System.lineSeparator() + "Bot - " + words[q];
    }

    private String stop() {
        return "";
    }

    private String end() {
        return "";
    }

    public String[] load(final File path) {
        String[] words = new String[0];
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            words = read.lines().filter(n -> !n.isEmpty()).toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
