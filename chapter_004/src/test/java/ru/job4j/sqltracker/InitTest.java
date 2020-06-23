package ru.job4j.sqltracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Properties;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SqlTracker.class})

public class InitTest {
    @Test(expected = IllegalStateException.class)
    //@Test
    public void qqq() throws Exception {
        //Работает
        Properties mock = mock(Properties.class);
        whenNew(Properties.class).withNoArguments().thenThrow(new Exception());

        SqlTracker sp = new SqlTracker();
        sp.init();
    }
}
