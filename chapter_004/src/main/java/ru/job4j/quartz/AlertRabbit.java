package ru.job4j.quartz;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    private static final Logger LOG = LogManager.getLogger(AlertRabbit.class);
    private static String fileRabbit = Objects.requireNonNull(AlertRabbit.class.getClassLoader().
            getResource("rabbit.properties")).getFile();

    protected AlertRabbit() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(final String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            LOG.error(se.getMessage(), se);
        }
    }

    private static int getInterval() {
        String time = "0";
        try (FileInputStream in = new FileInputStream(fileRabbit)) {
            Properties cfg = new Properties();
            cfg.load(in);
            time = cfg.getProperty("rabbit.interval");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return Integer.parseInt(time);
    }

    public static class Rabbit implements Job {
        /**
         * execute.
         *
         * @param context context
         */
        @Override
        public void execute(final JobExecutionContext context) {
            LOG.info("Rabbit runs here ...");
        }
    }
}





















