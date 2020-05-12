package ru.job4j.srp;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

enum Format {
    HTML, XML, JSON, CONSOLE
}


class Report implements Nrn {
    private static Map<String, String> html = new HashMap<>();
    private static Map<String, String> xml = new HashMap<>();
    private static Map<String, String> json = new HashMap<>();
    private static Map<String, String> console = new HashMap<>();
    private static Map<Format, Map<String, String>> formatMapMap = new EnumMap<>(Format.class);

    protected Report() {
        setHtml();
        setXml();
        setJson();
        setConsole();
    }

    public void setHtml() {
        this.html.put("head", "<!DOCTYPE HTML>" + N
                + "<html>" + N
                + " <head>" + N
                + "  <meta charset=\"utf-8\">" + N
                + "  <title>Ответы в виде html</title>" + N
                + " </head>" + N
                + " <body>" + N);
        html.put("/head", " </body>" + N
                + "</html>" + N);
        html.put("p", "  <p>");
        html.put("/p", "</p>" + N);
        html.put("Employees", "  <p>" + N);
        html.put("/Employees", "  </p>" + N);
        html.put("name", "    ");
        html.put("/name", "");
        html.put("hired", "");
        html.put("/hired", "");
        html.put("fired", "");
        html.put("/fired", "");
        html.put("salary", "");
        html.put("/salary", "<br/>" + N);
        formatMapMap.put(Format.HTML, html);
    }

    public void setXml() {
        this.xml.put("head", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + N
                + "<department>" + N
                + " <name>job4j</name>" + N
                + ""
                + ""
                + ""
                + "");
        this.xml.put("/head", ""
                + "</department>" + N);
        this.xml.put("p", " <name>");
        this.xml.put("/p", "</name>" + N);
        this.xml.put("Employees", "<Employees>" + N);
        this.xml.put("/Employees", "</Employees>" + N);
        this.xml.put("name", " <employee>" + N + "   <name>");
        this.xml.put("/name", "</name>" + N);
        this.xml.put("hired", "   <hired>");
        this.xml.put("/hired", "</hired>" + N);
        this.xml.put("fired", "   <fired>");
        this.xml.put("/fired", "</fired>" + N);
        this.xml.put("salary", "   <salary>");
        this.xml.put("/salary", "</salary>" + N + " </employee>" + N);
        formatMapMap.put(Format.XML, xml);
    }

    public void setJson() {
        this.json.put("head", "{" + N);
        this.json.put("/head", "}" + N);
        this.json.put("p", " \"name\":\"");
        this.json.put("/p", "\"," + N);
        this.json.put("Employees", "");
        this.json.put("/Employees", "");
        this.json.put("name", "{" + N + " \"name\":\"");
        this.json.put("/name", "\"," + N);
        this.json.put("hired", " \"hired\":\"");
        this.json.put("/hired", "\"," + N);
        this.json.put("fired", " \"fired\":\"");
        this.json.put("/fired", "\"," + N);
        this.json.put("salary", " \"salary\":\"");
        this.json.put("/salary", "\"" + N + "}" + N);
        formatMapMap.put(Format.JSON, json);
    }

    public void setConsole() {
        this.console.put("head", "");
        this.console.put("/head", "");
        this.console.put("p", "");
        this.console.put("/p", N);
        this.console.put("Employees", "");
        this.console.put("/Employees", "");
        this.console.put("name", "");
        this.console.put("/name", "");
        this.console.put("hired", "");
        this.console.put("/hired", "");
        this.console.put("fired", "");
        this.console.put("/fired", "");
        this.console.put("salary", "");
        this.console.put("/salary", N);
        formatMapMap.put(Format.CONSOLE, console);
    }

    public Map<String, String> getformat(final Format format) {
        return formatMapMap.get(format);
    }
}


