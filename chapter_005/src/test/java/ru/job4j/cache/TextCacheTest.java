package ru.job4j.cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class TextCacheTest {
    private TxtCache txt;

    @Before
    public void setUp() {
        txt = new TextCache();
    }

    @Test
    public void exception() throws NoSuchFieldException {
        FieldSetter.setField(txt, txt.getClass().getDeclaredField("directory"), null);
        txt.getTxt("Names.txt");
        assertTrue(true);
    }

    @Test
    public void softRefgetEqualNull() throws NoSuchFieldException, IllegalAccessException {
        SoftReference<?> softRef = Mockito.mock(SoftReference.class);
        Mockito.when(softRef.get()).thenReturn(null);
        HashMap<String, SoftReference<?>> map = new HashMap<>();
        map.put("Names.txt", softRef);
        Field field = txt.getClass().getSuperclass().getDeclaredField("cacheT");
        field.setAccessible(true);
        field.set(txt, map);
        txt.getTxt("Names.txt");
        assertTrue(true);
    }

    @Test
    public void getTxt() {
        int n = 10;
        while (n-- > 0) {
            txt.getTxt("Names.txt");
            txt.getTxt("Address.txt");
            txt.getTxt("log4j2.xml");
            txt.getTxt("big.txt");
            txt.getTxt("big2.txt");
        }
        assertTrue(true);
    }
}