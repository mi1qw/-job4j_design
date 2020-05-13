package ru.job4j.analizy;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Analizy {
    protected List<String> listin = new ArrayList<>();
    protected List<String> listout = new ArrayList<>();
    private boolean unavailable = false;

    /**
     * @param source
     * @param target
     */
    public void unavailable(final String source, final String target) {
        System.out.println();
        String begin = "";
        String end;
        String line;
        try (BufferedReader read = new BufferedReader(new FileReader(source))) {
            while ((line = read.readLine()) != null) {
                String[] l = line.split(" ");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        listout.forEach(System.out::println);
        savefile(target);
    }

    public final void readfile(final String path) {
        try (BufferedReader read = new BufferedReader(new FileReader(path))) {
            read.lines().forEach(n -> this.listin.add(n));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void savefile(final String target) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
            listout.stream().forEach(n -> out.println(n));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
