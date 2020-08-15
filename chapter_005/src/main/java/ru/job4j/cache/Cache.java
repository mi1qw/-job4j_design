package ru.job4j.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cache<E, T> implements CacheSoftReference<E, T> {
    protected Map<E, SoftReference<T>> cache = new HashMap<>();

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public Map<E, SoftReference<T>> getCache() {
        return cache;
    }

    /**
     * get from cache.
     *
     * @param key key
     * @return T
     */
    @Override
    public T get(final E key) {
        SoftReference<T> softRef = cache.get(key);
        if (softRef == null || softRef.get() == null) {
            System.out.println(String.format("Not exist %s = %s", key,
                    softRef == null ? "new" : "null"));
            return put(key);
        }
        return softRef.get();
    }
}


class TextCache extends Cache<String, List<String>> implements TxtCache {
    public static final Logger LOG = LoggerFactory.getLogger(TextCache.class);
    private Path directory = Path.of("chapter_005", "src", "main", "resources");

    public static void main(final String[] args) {
        new TextCache().go();
    }

    public void go() {
        int n = 10;
        while (n-- > 0) {
            getTxt("Names.txt");
            getTxt("Address.txt");
            getTxt("log4j2.xml");
            getTxt("big.txt");
            getTxt("big2.txt");
            get("Address.txt");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getTxt(final String file) {
        return get(file);
    }

    /**
     * Put in cache.
     *
     * @param key the key
     * @return the T value
     */
    @Override
    public List<String> put(final String key) {
        List<String> list = null;
        try {
            list = Files.readAllLines(Path.of(String.valueOf(directory), key));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        super.cache.put(key, new SoftReference<>(list));
        return list;
    }
}