package ru.job4j.consolechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Human implements HumanBotInt {
    @Override
    public String action() {
        String answer = null;
        try {
            answer = getString();
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
