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
    private Map<E, SoftReference<T>> cacheT = new HashMap<>();

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public Map<E, SoftReference<T>> getCacheT() {
        return cacheT;
    }

    /**
     * get from cache.
     *
     * @param key key
     * @return T
     */
    @SuppressWarnings("CheckStyle")
    @Override
    public T get(final E key) {
        SoftReference<T> softRef = cacheT.get(key);
        T ob;
        if (softRef == null || (ob = softRef.get()) == null) {
            System.out.println(String.format("Not exist %s = %s", key,
                    softRef == null ? "new" : "null"));
            return put(key);
        }
        return ob;
    }
}


class TextCache extends Cache<String, List<String>> implements TxtCache {
    public static final Logger LOG = LoggerFactory.getLogger(TextCache.class);
    private Path directory = Path.of("src", "main", "resources").toAbsolutePath();

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
        super.getCacheT().put(key, new SoftReference<>(list));
        return list;
    }
}
