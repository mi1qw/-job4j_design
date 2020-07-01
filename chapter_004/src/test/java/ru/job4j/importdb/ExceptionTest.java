package ru.job4j.importdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PowerMockIgnore({"javax.net.ssl.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportDB.class})
public class ExceptionTest {
    @Test
    public void a4ExceptionToString() throws Exception {
        whenNew(ImportDB.class).withAnyArguments().thenThrow(new Exception());
        ImportDB.main(new String[0]);
        assertTrue(true);
    }
}
