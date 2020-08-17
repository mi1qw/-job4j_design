package ru.job4j.cache;

public interface CacheSoftReference<E, T> {
    /**
     * get from cache.
     *
     * @param key the key
     * @return T value
     */
    T get(E key);

    /**
     * Put in cache.
     *
     * @param key the key
     * @return the T value
     */
    T put(E key);
}
