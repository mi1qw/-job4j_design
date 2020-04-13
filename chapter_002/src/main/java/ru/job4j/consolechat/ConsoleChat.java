package ru.job4j.consolechat;

import java.io.*;
import java.util.List;
import java.util.Map;

interface HumanBotInt {
    String action(String mesage);
}


interface Answer {
    String getAnswer(String mesage);
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

    private String play(String mesage) {
        int q = (int) (Math.random() * words.length);
        String answer = words[q];
        out.println(this.str + answer);
        //System.out.println(this.str + answer);
        return answer;
    }

    private String stop(String mesage) {
        //System.out.println("stop");
        return "";
    }

    private String end(String mesage) {
        return "@endchat";
    }

    @Override
    public String action(String mesage) {
        if (map.containsKey(mesage)) {
            l = (Answer) map.get(mesage);
        }
        answer = l.getAnswer(mesage);
        return answer;
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


class Human extends HumanBot implements HumanBotInt {
    public Human(String str) {
        super(str);
    }

    @Override
    public String action(String mesage) {

        try {
            answer = getString();
            out.println(this.str + answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    String getString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}


class HumanBot {
    PrintStream out;
    String str = "";
    String answer;

    public HumanBot(String str) {
        this.out = System.out;
        this.str = str;
    }
}

