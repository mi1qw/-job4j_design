package ru.job4j.analizy;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Analizy {
    public void unavailable(String source, String target) {

    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



     // 1. Реализуйте метод unavailable.
     // source - имя файла лога
     // target - имя файла после анализа.
     //
     // 2. Метод anavailable должен находить диапазоны, когда сервер не работал.
     // Сервер не работал. если status = 400 или 500.
     // Диапазон считается последовательность статусов 400 и 500
     // Например:
     // 200 10:56:01
     // 500 10:57:01
     // 400 10:58:01
     // 200 10:59:01
     // 500 11:01:02
     // 200 11:02:02
     // тут два периода - 10:57:01 до 10:59:01 и 11:01:02 до 11:02:02
     // Начальное время - это время когда статус 400 или 500. конечное время это когда статус меняется с 400 или 500 на 200 300.
     //
     // 3. Результат анализа нужно записать в файл target.
     // Формат файла
     // начала периода;конец периода;
     //
     // 4. Записать тесты.