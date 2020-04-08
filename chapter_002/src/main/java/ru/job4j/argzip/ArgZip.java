package ru.job4j.argzip;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ArgZip {
    private final String[] args;
    private List<String> directory = new ArrayList<>();
    private List<String> exclude = new ArrayList<>();
    private List<String> output = new ArrayList<>();
    boolean valid = false;
    Map<String, List<String>> param = new HashMap<>();

    public ArgZip(String[] args) {
        this.args = args;
        param.put("-d", directory);
        param.put("-e", exclude);
        param.put("-o", output);
    }

    public boolean valid() throws Wrongkey, UseKeyDEO {
        if (!this.valid) {
            try {
                List<String> data = new ArrayList<>();
                for (int n = 0; n != this.args.length; ++n) {
                    if (args[n].startsWith("-")) {
                        if (param.containsKey(args[n])) {
                            data = param.get(args[n]);
                            param.remove(args[n]);
                            continue;
                        } else {
                            throw new Wrongkey(args[n]);        // неправильный ключ в ком.строке
                        }
                    }
                    data.add(args[n]);
                }
                if (param.size() > 0) {
                    throw new UseKeyDEO(param);                // не все ключи использованы -d -e -o
                } else {
                    this.valid = true;
                }
            } catch (UseKeyDEO e) {
                System.out.print(" Error, use this Key ");
                e.param.forEach((n, n1) -> System.out.print(n + " "));
                System.out.println();
                e.printStackTrace();
            } catch (Wrongkey e) {
                System.out.println(e.arg + " Wrong key, No such key");
                e.printStackTrace();
            }
        }
        return this.valid;
    }

    public Path directory() {
        return Paths.get(this.directory.get(0));
    }

    public List<String> exclude() {
        return this.exclude;
    }

    public Path output() {
        return Paths.get(this.output.get(0));
    }
}
