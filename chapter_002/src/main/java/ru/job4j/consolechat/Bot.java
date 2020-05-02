package ru.job4j.consolechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Map;

class Bot implements HumanBotInt, Constant {
    private File filephrase = Path.of("data", "phrase.txt").toFile();
    String[] words;
    private Map<String, Answer> map = Map.of(
            CONT, this::play,
            STOP, this::stop,
            END, this::end
    );
    private Answer l = this::play;
    private HumanBotInt answer1;

    public Bot(final HumanBotInt answer1) throws FileNotFoundException {
        this.words = load(new File(String.valueOf(filephrase)));
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

    public String[] load(final File path) throws FileNotFoundException {
        System.out.println(path);
        String[] words = new String[10];
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            words = read.lines().filter(n -> !n.isEmpty()).toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
