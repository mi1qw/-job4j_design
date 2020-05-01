package ru.job4j.consolechat;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BotTest implements Constant {
    @Test
    public void anyMessageFromBot() throws UnsupportedEncodingException, FileNotFoundException {
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
                .append("Human - ").append(STOP).append(LN)
                .append("Human - 3").append(LN)
                .append("Human - 4").append(LN)
                .append("Human - ").append(CONT).append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - 5").append(LN)
                .append("Bot - Всё может быть").append(LN)
                .append("Human - ").append(END).append(LN);

        final String[] list = {"1", "2", STOP, "3", "4", CONT, "5", END};
        final StringBuilder actual = new StringBuilder();

        //System.out.println(System.getProperty("file.encoding"));
        for (int n = 0; n != list.length; ++n) {
            System.setIn(new ByteArrayInputStream(list[n].getBytes("UTF8")));
            actual.append(bot.action()).append(LN);
        }
        System.out.println(actual);
        assertThat(actual.toString(), is(expected.toString()));
    }
}


