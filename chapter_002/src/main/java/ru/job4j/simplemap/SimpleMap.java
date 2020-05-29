package ru.job4j.simplemap;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Iterable<SimpleMap.Node<K, V>> {
    /**
     * The default initial capacity - MUST be a power of two.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 3; // 1 << 4 aka 16
    /**
     * The load factor used when none specified in constructor.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    private Node<K, V>[] table;
    /**
     * The number of key-value mappings contained in this map.
     */
    private int size;
    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    private int modCount;
    /**
     * The next size value at which to resize (capacity * load factor).
     *
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    private int threshold;
    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    private float loadFactor;

    /**
     * Constructs an empty {@code HashMap} with the specified initial
     * capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *                                  or the load factor is nonpositive
     */
    public SimpleMap(final int initialCapacity, final float loadFactor) {
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * Constructs an empty {@code HashMap} with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public SimpleMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     *
     * @param key the key
     * @return the int
     */
    private static int hash(final Object key) {
        int h;
        if (key == null) {
            return 0;
        } else {
            h = key.hashCode();
        }
        return h ^ (h >>> 16);
    }

    /**
     * Returns a power of two size for the given target capacity.
     *
     * @param cap the cap
     * @return the int
     */
    private static int tableSizeFor(final int cap) {
        return (-1 >>> Integer.numberOfLeadingZeros(cap - 1)) + 1;
    }

    /**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    private Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap;
        int newThr = 0;
        if (oldCap != 0) {
            newCap = oldCap << 1;
            newThr = (int) (loadFactor * newCap);
        } else if (oldThr > 0) { // initial capacity was placed in threshold
            newCap = oldThr;
            newThr = (int) (loadFactor * newCap);
        } else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        threshold = newThr;

        @SuppressWarnings("unchecked")
        Node<K, V>[] newTab = new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e = oldTab[j];
                if (e != null) {
                    oldTab[j] = null;
                    newTab[e.hash & (newCap - 1)] = e;
                }
            }
        }
        return newTab;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Сравнить используя хеш и ключи.
     *
     * @param p    значение хештаблицы
     * @param hash хеш ключа
     * @param key  ключ
     * @return True если равны
     */
    private boolean equalKeys(final Node<K, V> p, final int hash, final K key) {
        return p.hash == hash && ((p.key) == key || (key != null && key.equals(p.key)));
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * distinguish these two cases.
     *
     * @param key the key
     * @return the v
     */
    public V get(final K key) {
        Node<K, V> e = getNode(hash(key), key);
        return (e == null) ? null : e.value;
    }

    /**
     * Implements Map.get and related methods.
     *
     * @param hash hash for key
     * @param key  the key
     * @return the node, or null if none
     */
    private Node<K, V> getNode(final int hash, final K key) {
        if (table != null) {
            int n = table.length;
            Node<K, V> p = table[(n - 1) & hash];
            if (p != null && equalKeys(p, hash, key)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Create a regular  node
     *
     * @param hash  hash
     * @param key   key
     * @param value value
     * @param next  next
     * @return Node
     */
    private Node<K, V> newNode(final int hash, final K key, final V value, final Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or {@code null} if there was no mapping for {@code key}. (A {@code null} return can also indicate that the map previously associated {@code null} with {@code key}.)
     */
    public boolean insert(final K key, final V value) {
        return putVal(hash(key), key, value);
    }

    /**
     * Implements Map.put and related methods.
     *
     * @param hash  hash for key
     * @param key   the key
     * @param value the value to put
     * @return previous value, or null if none
     */
    private boolean putVal(final int hash, final K key, final V value) {
        Node<K, V>[] tab = table;
        Node<K, V> p;
        int n = (tab == null) ? 0 : tab.length;
        if (n == 0) {
            tab = resize();
            n = tab.length;
        }
        int i = (n - 1) & hash;
        p = tab[i];
        if (p == null) {
            tab[i] = newNode(hash, key, value, null);
        } else {
            if (equalKeys(p, hash, key)) {
                tab[i].value = value;
                return true; // обновляем значение
            }
            return false;    //коллизия. Выходим, без добавления элемента
        }
        ++modCount;
        if (++size > threshold) {
            resize();
        }
        return true;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or {@code null} if there was no mapping for {@code key}. (A {@code null} return can also indicate that the map previously associated {@code null} with {@code key}.)
     */
    public boolean delete(final K key) {
        Node<K, V> e = removeNode(hash(key), key);
        return e != null;
    }

    /**
     * Implements Map.remove and related methods.
     *
     * @param hash hash for key
     * @param key  the key
     * @return the node, or null if none
     */
    private Node<K, V> removeNode(final int hash, final K key) {
        Node<K, V>[] tab = table;
        Node<K, V> node = null;
        if (tab != null) {
            int n = tab.length;
            int index = (n - 1) & hash;
            Node<K, V> p = tab[index];
            if (p != null && equalKeys(p, hash, key)) {
                node = p;
                tab[index] = p.next;
                ++modCount;
                --size;
                return node;
            }
        }
        return null;
    }

    private void checkForModification(final int expectedModCount) {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * @return an Iterator.
     */
    @Override
    public Iterator<SimpleMap.Node<K, V>> iterator() {
        return new Iterator<>() {
            private int it = 0;
            private int expectedModCount = modCount;

            /**
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                while (table != null && it < table.length && table[it] == null) {
                    ++it;
                }
                return table != null && it < table.length;
            }

            /**
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public SimpleMap.Node<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                checkForModification(expectedModCount);
                return table[it++];
            }
        };
    }

    static class Node<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private Node<K, V> next;

        Node(final int hash, final K key, final V value,
             final Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final V getValue() {
            return value;
        }
    }
}
