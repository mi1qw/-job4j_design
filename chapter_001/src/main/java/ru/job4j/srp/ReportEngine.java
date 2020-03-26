package ru.job4j.srp;

import java.util.*;
import java.util.function.Predicate;

import ru.job4j.simplegenerator.*;

enum Format {
    HTML, XML, JSON, CONSOLE
}


class Report {
    static Map<String, String> html = new HashMap<>();
    static Map<String, String> xml = new HashMap<>();
    static Map<String, String> json = new HashMap<>();
    static Map<String, String> console = new HashMap<>();
    static Map<Format, Map<String, String>> formatMapMap = new HashMap<>();

    public Report() {
        setHtml();
        setXml();
        setJson();
        setConsole();
    }

    public void setHtml() {
        this.html.put("head", "<!DOCTYPE HTML>\n"
                + "<html>\n"
                + " <head>\n"
                + "  <meta charset=\"utf-8\">\n"
                + "  <title>Ответы в виде html</title>\n"
                + " </head>\n"
                + " <body>\n");
        html.put("/head", " </body>\n"
                + "</html>\n");
        html.put("p", "  <p>");
        html.put("/p", "</p>\n");
        html.put("Employees", "  <p>\n");
        html.put("/Employees", "  </p>\n");
        html.put("name", "    ");
        html.put("/name", "");
        html.put("hired", "");
        html.put("/hired", "");
        html.put("fired", "");
        html.put("/fired", "");
        html.put("salary", "");
        html.put("/salary", "<br/>\n");
        formatMapMap.put(Format.HTML, html);
    }

    public void setXml() {
        this.xml.put("head", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<department>\n"
                + " <name>job4j</name>\n"
                + ""
                + ""
                + ""
                + "");
        this.xml.put("/head", ""
                + "</department>\n");
        this.xml.put("p", " <name>");
        this.xml.put("/p", "</name>\n");
        this.xml.put("Employees", "<Employees>\n");
        this.xml.put("/Employees", "</Employees>\n");
        this.xml.put("name", " <employee>\n   <name>");
        this.xml.put("/name", "</name>\n");
        this.xml.put("hired", "   <hired>");
        this.xml.put("/hired", "</hired>\n");
        this.xml.put("fired", "   <fired>");
        this.xml.put("/fired", "</fired>\n");
        this.xml.put("salary", "   <salary>");
        this.xml.put("/salary", "</salary>\n </employee>\n");
        formatMapMap.put(Format.XML, xml);
    }

    public void setJson() {
        this.json.put("head", "{\n");
        this.json.put("/head", "}\n");
        this.json.put("p", " \"name\":\"");
        this.json.put("/p", "\",\n");
        this.json.put("Employees", "");
        this.json.put("/Employees", "");
        this.json.put("name", "{\n \"name\":\"");
        this.json.put("/name", "\",\n");
        this.json.put("hired", " \"hired\":\"");
        this.json.put("/hired", "\",\n");
        this.json.put("fired", " \"fired\":\"");
        this.json.put("/fired", "\",\n");
        this.json.put("salary", " \"salary\":\"");
        this.json.put("/salary", "\"\n}\n");
        formatMapMap.put(Format.JSON, json);
    }

    public void setConsole() {
        this.console.put("head", "");
        this.console.put("/head", "");
        this.console.put("p", "");
        this.console.put("/p", "\n");
        this.console.put("Employees", "");
        this.console.put("/Employees", "");
        this.console.put("name", "");
        this.console.put("/name", "");
        this.console.put("hired", "");
        this.console.put("/hired", "");
        this.console.put("fired", "");
        this.console.put("/fired", "");
        this.console.put("salary", "");
        this.console.put("/salary", "\n");
        formatMapMap.put(Format.CONSOLE, console);
    }

    public Map<String, String> getformat(Format format) {
        return formatMapMap.get(format);
    }
}


