package ru.job4j.analizy;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Analizy {
    List<String> listin = new ArrayList<>();
    List<String> listout = new ArrayList<>();
    boolean unavailable = false;

    public void unavailable(String source, String target) {
        System.out.println();

        readfile(source);

        String begin = "";
        String end;
        for (String str : listin) {
            String[] l = str.split(" ");
            if (l[0].equals("500") || l[0].equals("400")) {
                if (!unavailable) {
                    begin = l[1];
                    unavailable = true;
                }
            } else if (unavailable) {
                end = l[1];
                unavailable = false;
                listout.add(begin + ";" + end);
            }
        }
        listout.forEach(System.out::println);

        savefile(target);
    }

    public static void main(String[] args) {
        new Analizy().unavailable("chapter_002/data/server.log", "chapter_002/data/unavailable.csv");
    }

    void readfile(String path) {
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            read.lines().forEach(n -> this.listin.add(n));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void savefile(String target) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
            listout.stream().forEach(n -> out.println(n));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
