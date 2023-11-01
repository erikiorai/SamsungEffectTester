package androidx.collection;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: mainsysui33.jar:androidx/collection/LruCache.class */
public class LruCache<K, V> {
    public int createCount;
    public int evictionCount;
    public int hitCount;
    public final LinkedHashMap<K, V> map;
    public int maxSize;
    public int missCount;
    public int putCount;
    public int size;

    public LruCache(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = i;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    public V create(K k) {
        return null;
    }

    public void entryRemoved(boolean z, K k, V v, V v2) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final V get(K k) {
        V put;
        if (k != null) {
            synchronized (this) {
                V v = this.map.get(k);
                if (v != null) {
                    this.hitCount++;
                    return v;
                }
                this.missCount++;
                V create = create(k);
                if (create == null) {
                    return null;
                }
                synchronized (this) {
                    this.createCount++;
                    put = this.map.put(k, create);
                    if (put != null) {
                        this.map.put(k, put);
                    } else {
                        this.size += safeSizeOf(k, create);
                    }
                }
                if (put != null) {
                    entryRemoved(false, k, create, put);
                    return put;
                }
                trimToSize(this.maxSize);
                return create;
            }
        }
        throw new NullPointerException("key == null");
    }

    public final V put(K k, V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(k, v);
            put = this.map.put(k, v);
            if (put != null) {
                this.size -= safeSizeOf(k, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k, put, v);
        }
        trimToSize(this.maxSize);
        return put;
    }

    public final V remove(K k) {
        V remove;
        if (k != null) {
            synchronized (this) {
                remove = this.map.remove(k);
                if (remove != null) {
                    this.size -= safeSizeOf(k, remove);
                }
            }
            if (remove != null) {
                entryRemoved(false, k, remove, null);
            }
            return remove;
        }
        throw new NullPointerException("key == null");
    }

    public final int safeSizeOf(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    public final int size() {
        int i;
        synchronized (this) {
            i = this.size;
        }
        return i;
    }

    public int sizeOf(K k, V v) {
        return 1;
    }

    public final Map<K, V> snapshot() {
        LinkedHashMap linkedHashMap;
        synchronized (this) {
            linkedHashMap = new LinkedHashMap(this.map);
        }
        return linkedHashMap;
    }

    public final String toString() {
        String format;
        synchronized (this) {
            int i = this.hitCount;
            int i2 = this.missCount + i;
            format = String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i2 != 0 ? (i * 100) / i2 : 0));
        }
        return format;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ac, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void trimToSize(int i) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                if (this.size >= 0 && (!this.map.isEmpty() || this.size == 0)) {
                    if (this.size <= i || this.map.isEmpty()) {
                        break;
                    }
                    Map.Entry<K, V> next = this.map.entrySet().iterator().next();
                    key = next.getKey();
                    value = next.getValue();
                    this.map.remove(key);
                    this.size -= safeSizeOf(key, value);
                    this.evictionCount++;
                } else {
                    break;
                }
            }
            entryRemoved(true, key, value, null);
        }
    }
}