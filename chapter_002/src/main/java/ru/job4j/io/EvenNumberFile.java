package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        StringBuilder text = new StringBuilder();
        String path = "chapter_002/data/even.txt";

        try (FileInputStream in = new FileInputStream(path)) {
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] lines = text.toString().split(System.lineSeparator());
        int num;
        for (String line : lines) {
            if (line.equals("")) {
                continue;
            }
            num = Integer.parseInt(line);
            if (num % 2 == 0) {
                System.out.println(num);
            }
        }
    }
}
