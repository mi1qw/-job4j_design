package ru.job4j.memoryusage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MemoryUsag {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryUsag.class);
    private static final String LN = System.lineSeparator();
    private static List<Object> objects = new LinkedList<>();
    public static final int SLEEP = 1000;
    private static Runtime runtime = Runtime.getRuntime();
    public static final int MB = 1024 * 1024;

    protected MemoryUsag() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(final String[] args) throws InterruptedException {
        MemoryUtil.startGCMonitor();

        addRemObjects(4, objects::add, User::new);

        LOG.info("Remowing some data{}", LN);
        addRemObjects(4, n -> objects.remove((int) n), () -> 0);
        runtime.gc();

        addRemObjects(2, objects::add, User::new);

        LOG.info("Adding Empty class{}", LN);
        addRemObjects(3, objects::add, Empty::new);

        LOG.info("Adding until an Error occurs{}", LN);
        addRemObjects(5, objects::add, User::new);
    }

    private static <T> void addRemObjects(final int megabyte, final Consumer<T> operat,
                                          final Supplier<T> ob) throws InterruptedException {
        for (int n = 0; n < megabyte; ++n) {
            operat.accept(ob.get());
            dispMemomry();
            Thread.sleep(SLEEP);
        }
    }

    private static void dispMemomry() {
        if (LOG.isInfoEnabled()) {
            LOG.info("{}{}Used Memory: {}{}Free Memory: {}{}Total Memory: {}{}Maximum Memory: {}{}",
                    objects.size(), LN,
                    String.format("%d.2", (runtime.totalMemory() - runtime.freeMemory()) / MB), LN,
                    String.format("%d.2", runtime.freeMemory() / MB), LN,
                    String.format("%d.2", runtime.totalMemory() / MB), LN,
                    String.format("%d.2", runtime.maxMemory() / MB), LN);
        }
        MemoryUtil.printUsage(true);
    }

    private static class User {
        private byte[] anbyte;

        User() {
            this.anbyte = new byte[MB];
        }

        /**
         * Called by the garbage collector on an object when garbage collection.
         */
        @Override
        //@Deprecated
        protected void finalize() throws Throwable {
            super.finalize();
            LOG.info("finalize User !!!{}", LN);
        }
    }


    private static final class Empty {
        private String empty;

        private Empty() {
        }
    }
}
