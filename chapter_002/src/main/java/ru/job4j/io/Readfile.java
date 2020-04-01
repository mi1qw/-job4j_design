package ru.job4j.io;

import java.io.FileInputStream;

public class Readfile {
    public static void main(String[] args) {
        StringBuilder text = new StringBuilder();
        String path = "chapter_002/data/input.txt";

        try (FileInputStream in = new FileInputStream(path)) {
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] lines = text.toString().split(System.lineSeparator());
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
