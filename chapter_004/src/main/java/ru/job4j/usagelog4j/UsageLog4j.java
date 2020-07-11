package ru.job4j.usagelog4j;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UsageLog4j {
    private static final Logger LOG = LogManager.getLogger(UsageLog4j.class.getName());

    public static void main(final String[] args) {
        new UsageLog4j().go();
    }

    protected final void go() {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
    }
}
