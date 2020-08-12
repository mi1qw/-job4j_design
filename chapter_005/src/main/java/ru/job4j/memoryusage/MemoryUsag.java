package ru.job4j.memoryusage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class MemoryUsag {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryUsag.class);
    private static final String LN = System.lineSeparator();
    private static List<Object> objects = new LinkedList<>();
    private static final int SLEEP = 1;
    private static int series = 100;
    private static Runtime runtime = Runtime.getRuntime();
    private static final int MB = 1024 * 1024;
    private static LongSummaryStatistics minor;
    private static LongSummaryStatistics major;
    private static long dt0;
    private static long dt;

    protected MemoryUsag() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(final String[] args) throws InterruptedException {

        var gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        gcBeans.stream().forEach(gc -> {

            System.out.println(format("GC Name : %s", gc.getName()));
            var poolNames = gc.getMemoryPoolNames();
            if (poolNames != null) {
                List.of(poolNames).forEach(pool ->
                        System.out.println(format("Pool name %s", pool)));
            } else {
                System.out.println("No memory pools for " + gc.getName());
            }
        });

        MemoryUtil.startGCMonitor();
        dt0 = System.currentTimeMillis();
        while (series-- > 0) {
            addRemObjects(4 * 4, objects::add, User::new);

            LOG.info("Remowing some data{}", LN);
            addRemObjects(4 * 4, n -> objects.remove((int) n), () -> 0);

            addRemObjects(2 * 4, objects::add, User::new);

            LOG.info("Adding Empty class{}", LN);
            addRemObjects(2 * 4, objects::add, Empty::new);

            addRemObjects(objects.size(), n -> objects.remove((int) n), () -> 0);
        }
        dt = System.currentTimeMillis() - dt0;
        System.out.println();
        System.out.println(MemoryUtil.getMsMinor());
        System.out.println(MemoryUtil.getMsMajor());

        minor = MemoryUtil.getMsMinor().stream().collect(Collectors.
                summarizingLong(Long::longValue));
        major = MemoryUtil.getMsMajor().stream().collect(Collectors.
                summarizingLong(Long::longValue));
        if (minor.getCount() > 1) {
            LOG.info(
                    "minor GC time/count {}/{}   min {}  avg {}  max {}  {}"
                            + "major GC time/count {}/{}   min {}  avg {}  max {}  {}"
                            + "GC time/count {}/{}   avg {}ms {}"
                            + "Code Time {}  GC% {}{}",
                    minor.getSum(), minor.getCount(), minor.getMin(), roundTo(minor.getAverage(),
                            1),
                    minor.getMax(), LN,
                    major.getSum(), major.getCount(), major.getMin(), roundTo(major.getAverage(),
                            1),
                    major.getMax(), LN,
                    minor.getSum() + major.getSum(), minor.getCount() + major.getCount(),
                    roundTo((double) (minor.getSum() + major.getSum()) / (minor.getCount() + major.getCount()), 1),
                    LN,
                    dt, Math.round((double) (minor.getSum() + major.getSum()) / dt * 100), LN
            );
        }
    }

    private static double roundTo(final double val, final int r) {
        double scale = Math.pow(10, r);
        return Math.round(val * scale) / scale;
    }

    private static <T> void addRemObjects(final int megabyte, final Consumer<T> operat,
                                          final Supplier<T> ob) throws InterruptedException {
        for (int n = 0; n < megabyte; ++n) {
            operat.accept(ob.get());
            dispMemomry();
            if (n % 2 == 0) {
                Thread.sleep(SLEEP);
            }
        }
    }

    private static void dispMemomry() {
        if (LOG.isInfoEnabled()) {
            LOG.info("{}{}Used Memory: {}{}Free Memory: {}{}Total Memory: {}{}Maximum Memory: {}{}",
                    objects.size(), LN,
                    format("%d.2", (runtime.totalMemory() - runtime.freeMemory()) / MB), LN,
                    format("%d.2", runtime.freeMemory() / MB), LN,
                    format("%d.2", runtime.totalMemory() / MB), LN,
                    format("%d.2", runtime.maxMemory() / MB), LN);
        }
        MemoryUtil.printUsage(true);
    }

    private static class User {
        private byte[] anbyte;              //256kb

        User() {
            this.anbyte = new byte[MB / 4];
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
