package ru.job4j.consolechat;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BotTest {
    @Test
    public void anyMessageFromBot() {
        final HumanBotInt bot = new Bot(new Human());

        /**
         * Чтобы добиться полного сходства, оставляем только одну фразу.
         */
        ((Bot) bot).words = new String[]{"Всё может быть"};

        final StringBuilder expected = new StringBuilder()
                .append("Human - 1").append(System.lineSeparator())
                .append("Bot - Всё может быть").append(System.lineSeparator())
                .append("Human - 2").append(System.lineSeparator())
                .append("Bot - Всё может быть").append(System.lineSeparator())
                .append("Human - стоп").append(System.lineSeparator())
                .append("Human - 3").append(System.lineSeparator())
                .append("Human - 4").append(System.lineSeparator())
                .append("Human - продолжить").append(System.lineSeparator())
                .append("Bot - Всё может быть").append(System.lineSeparator())
                .append("Human - 5").append(System.lineSeparator())
                .append("Bot - Всё может быть").append(System.lineSeparator())
                .append("Human - закончить").append(System.lineSeparator());

        final String[] list = {"1", "2", "стоп", "3", "4", "продолжить", "5", "закончить"};
        final StringBuilder actual = new StringBuilder();

        for (int n = 0; n != list.length; ++n) {
            System.setIn(new ByteArrayInputStream(list[n].getBytes()));
            actual.append(bot.action()).append(System.lineSeparator());
        }
        System.out.println(actual);
        assertThat(actual.toString(), is(expected.toString()));
    }
}


