package ru.job4j.argzip;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Zip {
    public void packFiles(List<File> sources, File target) {

    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile1(File source, File target, ZipOutputStream zip) throws IOException {
        try {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile2(File source, File target, ZipOutputStream zip) throws IOException {
        try {
            zip.putNextEntry(new ZipEntry(source.getPath() + "\\"));
            //+ "/"
            //try (FileInputStream out = new FileInputStream(source)) {
            //    zip.write(out.readAllBytes());
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //byte[] buf = new byte[1024];
    //ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
    //
    //private void run(String prefix, File root) throws IOException {
    //    File[] children = root.listFiles();
    //
    //    for (int i = 0; i < children.length; ++i) {
    //
    //        System.out.println(children[i].getAbsolutePath());
    //        if (children[i].isDirectory()) {
    //            //out.closeEntry();
    //            out.putNextEntry(new ZipEntry(prefix + "\\" + children[i].getName() + "\\"));
    //            run(prefix + "/" + children[i].getName(), children[i]);
    //            out.closeEntry();
    //        }
    //        if (children[i].isFile()) {
    //            out.putNextEntry(new ZipEntry(prefix + "/" + children[i].getName()));
    //            FileInputStream in = new FileInputStream(children[i]);
    //            // Add ZIP entry to output stream.
    //            // Transfer bytes from the file to the ZIP file
    //            int len;
    //            while ((len = in.read(buf)) > 0) {
    //                out.write(buf, 0, len);
    //            }
    //            in.close();
    //            out.closeEntry();
    //        }
    //    }
    //}

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalStateException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        new Zip().go(args);
    }

    void go(String[] args) throws Wrongkey, UseKeyDEO, IOException {
        ArgZip a = new ArgZip(args);
        a.valid();
        //new Zip().zip1("chapter_002", "zip.zip");
        //Properties.class.getClassLoader().getResourceAsStream(filename))

        try (ZipOutputStream zip =
                     new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(a.output().toFile())))) {
            PrintFiles visitor = new PrintFiles(this, a, zip);
            visitor.setTypeFile(a.exclude());

            Files.walkFileTree(a.directory(), visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //search(a.directory(), a.exclude());

        //new Zip().packSingleFile(
        //        new File("./chapter_002/data/pom.xml"),
        //        new File("./chapter_002/data/pom.zip")
        //);
    }

    //public List<String> search(Path root, List<String> ext) throws IOException {
    //    PrintFiles visitor = new PrintFiles(this, a);
    //    visitor.setTypeFile(ext);
    //    Files.walkFileTree(root, visitor);
    //    return visitor.getFiles();
    //}

    private void zip1(String sourceDir, String zipFile) throws Exception {
        // Cоздание объекта ZipOutputStream из FileOutputStream
        FileOutputStream fout = new FileOutputStream(zipFile);
        ZipOutputStream zout = new ZipOutputStream(fout);
        // Определение кодировки
        //zout.setEncoding("CP866");

        // Создание объекта File object архивируемой директории
        File fileSource = new File(sourceDir);

        addDirectory(zout, fileSource);

        // Закрываем ZipOutputStream
        zout.close();

        System.out.println("Zip файл создан!");
    }

    private void addDirectory(ZipOutputStream zout, File fileSource) throws Exception {
        File[] files = fileSource.listFiles();
        System.out.println("Добавление директории <" + fileSource.getName() + ">");
        for (int i = 0; i < files.length; i++) {
            // Если file является директорией, то рекурсивно вызываем
            // метод addDirectory
            //if (files[i].isDirectory()) {
            //    addDirectory(zout, files[i]);
            //    //continue;
            //}
            System.out.println("Добавление файла <" + files[i].getName() + ">");
            if (files[i].getPath().endsWith("java")) {

                //files[i].renameTo(new Path(files[i].getParent()));
                //files[i].renameTo((Path(files[i].getParent())));

                //= Path(files[i].getParent() + "\\").toFile();
                continue;
            }

            FileInputStream fis = new FileInputStream(files[i]);

            zout.putNextEntry(new ZipEntry(files[i].getPath()));

            byte[] buffer = new byte[4048];
            int length;
            while ((length = fis.read(buffer)) > 0)
                zout.write(buffer, 0, length);
            // Закрываем ZipOutputStream и InputStream
            zout.closeEntry();
            fis.close();
        }
    }
}

// -d c:\project\job4j\ -e *.java -o project.zip
// -d - directory - которую мы хотим архивировать
//         -e - exclude - исключить файлы *.xml
//         -o - output - во что мы архивируем.


class PrintFiles implements FileVisitor<Path> {
    public PrintFiles(Zip zip, ArgZip a, ZipOutputStream zout) {
        this.zip = zip;
        this.a = a;
        this.zout = zout;
    }

    List<String> list = new ArrayList<>();
    List<String> typeFile;
    Zip zip;
    ArgZip a;
    ZipOutputStream zout;

    public List<String> getFiles() {
        return list;
    }

    public void setTypeFile(List<String> typeFile) {
        this.typeFile = typeFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //zip.packSingleFile(dir.toFile(), a.output().toFile());
        //zip.packSingleFile1(dir.toFile(), a.output().toFile(), zout);
        //zip.packSingleFile2(dir.toFile(), a.output().toFile(), zout);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String name = file.getFileName().toString();
        //if (name.endsWith(typeFile)) {
        list.add(name);
        //zip.packSingleFile(file.toFile(), a.output().toFile());
        //}
        //file.getFileName().;
        if (name.endsWith("java")) {
            return CONTINUE;
        }
        zip.packSingleFile1(file.toFile(), a.output().toFile(), zout);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        zip.packSingleFile2(dir.toFile(), a.output().toFile(), zout);
        return CONTINUE;
    }
}
