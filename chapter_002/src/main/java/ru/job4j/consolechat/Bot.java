package ru.job4j.consolechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

class Bot extends HumanBot implements HumanBotInt {
    String filephrase = "chapter_002/data/phrase.txt";
    //String[] words = {"Всё может быть", "Наверное", "Надо подумать", "Это разумно",
    //        "Как выясню сообщу", "Наверное да, а может быть и нет:)",
    //        "Давай уже в следующий раз...", "Я бухой !"};
    String[] words;
    Map<String, Answer> map = Map.of(
            "продолжить", this::play,
            "стоп", this::stop,
            "закончить", this::end
    );
    Answer l = this::play;

    public Bot(String str) {
        super(str);
        this.words = load(new File(filephrase));
    }

    @Override
    public String action(String mesage) {
        if (map.containsKey(mesage)) {
            l = (Answer) map.get(mesage);
        }
        answer = l.getAnswer();
        return answer;
    }

    private String play() {
        int q = (int) (Math.random() * words.length);
        String answer = words[q];
        out.println(this.str + answer);
        //System.out.println(this.str + answer);
        return answer;
    }

    private String stop() {
        //System.out.println("stop");
        return "";
    }

    private String end() {
        return "@endchat";
    }

    public String[] load(File path) {
        String[] words = new String[0];
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            words = read.lines().filter(n -> !n.isEmpty()).toArray(String[]::new);
        } catch (Exception e) {
        }
        return words;
    }
}
