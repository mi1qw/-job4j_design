package ru.job4j.quartz;

import org.junit.Test;
import org.quartz.JobExecutionContext;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AlertRabbitTest {
    @Test
    public void run() {
        AlertRabbit.main(new String[0]);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionUtility() {
        new AlertRabbit();
    }

    @Test
    public void rabbitExecute() {
        AlertRabbit.Rabbit rabbit = new AlertRabbit.Rabbit();
        rabbit.execute(mock(JobExecutionContext.class));
        assertTrue(true);
    }
}

