package ru.job4j.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*", "org.apache.http.conn.ssl.*", "com.amazonaws.*", "javax.net.ssl.*", "com.sun.*", "org.w3c.*"})
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
