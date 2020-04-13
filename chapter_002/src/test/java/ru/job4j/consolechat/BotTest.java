package ru.job4j.consolechat;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BotTest {
    @Test
    public void anyMessageFromBot() {
        Bot bot = new Bot("BOT — ");
        bot.words = new String[]{"Всё может быть", "Наверное", "Надо подумать", "Это разумно",
                "Как выясню сообщу", "Наверное да, а может быть и нет:)",
                "Давай уже в следующий раз...", "Я бухой !"};
        String mesage = "anything";
        String actual = bot.action(mesage);

        long a = Arrays.stream(bot.words).filter(n -> n == actual).count();
        assertTrue(a == 1);
    }

    @Test
    public void continueMustReturnAnyMessageFromBot() {
        Bot bot = new Bot("BOT — ");
        bot.words = new String[]{"Всё может быть", "Наверное", "Надо подумать", "Это разумно",
                "Как выясню сообщу", "Наверное да, а может быть и нет:)",
                "Давай уже в следующий раз...", "Я бухой !"};
        String mesage = "продолжить";
        String actual = bot.action(mesage);

        long a = Arrays.stream(bot.words).filter(n -> n == actual).count();
        assertEquals(a, 1);
    }

    @Test
    public void stopMustReturnEmptyString() {
        Bot bot = new Bot("BOT — ");
        bot.words = new String[]{"Всё может быть", "Наверное", "Надо подумать", "Это разумно",
                "Как выясню сообщу", "Наверное да, а может быть и нет:)",
                "Давай уже в следующий раз...", "Я бухой !"};
        String mesage = "стоп";
        String actual = bot.action(mesage);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void endMustReturnEndchat() {
        Bot bot = new Bot("BOT — ");
        bot.words = new String[]{"Всё может быть", "Наверное", "Надо подумать", "Это разумно",
                "Как выясню сообщу", "Наверное да, а может быть и нет:)",
                "Давай уже в следующий раз...", "Я бухой !"};
        String mesage = "закончить";
        String actual = bot.action(mesage);

        assertTrue(actual.equals("@endchat"));
    }
}