package ru.job4j.consolechat;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BotTest implements Constant {
    @Test
    public void anyMessageFromBot() {
        final HumanBotInt bot = new Bot(new Human());

        /**
         * Чтобы добиться полного сходства, оставляем только одну фразу.
         */
        ((Bot) bot).words = new String[]{"Всё может быть"};

        final StringBuilder expected = new StringBuilder()
                .append("Human - 1").append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - 2").append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - стоп").append(LN)
                .append("Human - 3").append(LN)
                .append("Human - 4").append(LN)
                .append("Human - продолжить").append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - 5").append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - закончить").append(LN);

        final String[] list = {"1", "2", STOP, "3", "4", CONT, "5", END};
        final StringBuilder actual = new StringBuilder();

        for (int n = 0; n != list.length; ++n) {
            System.setIn(new ByteArrayInputStream(list[n].getBytes()));
            actual.append(bot.action()).append(LN);
        }
        System.out.println(actual);
        assertThat(actual.toString(), is(expected.toString()));
    }
}


