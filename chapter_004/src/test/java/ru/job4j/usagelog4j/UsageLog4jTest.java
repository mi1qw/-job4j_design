package ru.job4j.usagelog4j;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UsageLog4jTest {
    @Test
    public void log() {
        UsageLog4j.main(new String[0]);
        assertTrue(true);
    }
}