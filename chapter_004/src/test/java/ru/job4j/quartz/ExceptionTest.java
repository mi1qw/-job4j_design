package ru.job4j.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AlertRabbit.class})
public class ExceptionTest {
    @Test
    public void a1ExceptionInit() throws Exception {
        whenNew(Properties.class).withNoArguments().thenThrow(new IOException());
        AlertRabbit.main(new String[0]);
        assertTrue(true);
    }
}
