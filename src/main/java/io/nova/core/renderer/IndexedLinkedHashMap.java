package io.nova.core.renderer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class IndexedLinkedHashMap<String, V> extends LinkedHashMap<String, V> {

    private final List<String> keys;

    public IndexedLinkedHashMap() {
        this.keys = new ArrayList<>();
    }

    public int putIndexed(String key, V value) {
        super.put(key, value);
        keys.add(key);
        return keys.size() - 1;
    }

    public V getValue(int index) {
        var key = keys.get(index);
        return this.get(key);
    }

    public int getIndex(String path) {
        for (int i=0; i < keys.size(); i++) {
            if (keys.get(i).equals(path)) {
                return i;
            }
        }
        return -1;
    }
}