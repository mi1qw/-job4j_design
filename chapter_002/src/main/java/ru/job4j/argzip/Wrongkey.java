package ru.job4j.argzip;

import java.util.List;
import java.util.Map;

class Wrongkey extends Exception {
    String arg;

    public Wrongkey(String arg) {
        this.arg = arg;
    }
}


class UseKeyDEO extends Exception {
    Map<String, List<String>> param;

    public UseKeyDEO(Map<String, List<String>> param) {
        this.param = param;
    }
}
