package ru.job4j.search;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

class PrintFiles implements FileVisitor<Path> {
    List<String> list = new ArrayList<>();
    String typeFile;

    public List<String> getFiles() {
        return list;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String name = file.getFileName().toString();
        if (name.endsWith(typeFile)) {
            list.add(name);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return CONTINUE;
    }
}


public class Search {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalStateException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        //Path start = Paths.get("chapter_002");
        //Path start = Paths.get("chapter_002/src/main/java/ru/job4j/search");
        Path start = Paths.get(args[0]);
        if (start.toFile().isFile()) {
            throw new IllegalStateException("1st argument must be a folder. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        //String ext = "java";
        String ext = args[1];
        System.out.println(search(start, ext));
    }

    public static List<String> search(Path root, String ext) throws IOException {
        PrintFiles visitor = new PrintFiles();
        visitor.setTypeFile(ext);
        Files.walkFileTree(root, visitor);
        return visitor.getFiles();
    }
}
