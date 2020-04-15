package ru.job4j.argzip;

import java.nio.file.Path;

/**
 * Интерфейс методов сравнения {@code matchM}.
 */
interface MatchextIn {
    /**
     * Интерфейс методов сравнения {@code matchM}.
     *
     * @param file    файл который будет сравниваться
     * @param pattern маска искомого файла
     * @return the boolean True -  совпадение
     */
    boolean matchM(Path file, String pattern);

    boolean matchF();

    boolean matchR();
}
