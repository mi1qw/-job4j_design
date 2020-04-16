package ru.job4j.consolechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Human extends HumanBot implements HumanBotInt {
    public Human(String str) {
        super(str);
    }

    @Override
    public String action(String mesage) {
        String answer = null;
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
