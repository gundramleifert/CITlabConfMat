package de.uros.citlab.confmat;

import java.util.HashMap;
import java.util.Objects;

public class CharMap {
    private HashMap<Character, Integer> map = new HashMap<>();
    private HashMap<Integer, String> map2 = new HashMap<>();
    int size = 1;
    public static final char NaC = '\u0000';
    public static final Character Return = '⏎';

    public CharMap() {
        map.put(NaC, 0);
        map2.put(0, String.valueOf(NaC));
    }

    public CharMap(CharMap cmCopy) {
        for (int i = 0; i < cmCopy.size(); i++) {
            for (char c : cmCopy.get(i).toCharArray()) {
                add(c, i);
            }
        }
    }

    public int size() {
        return size;
    }

    public Integer get(Character character) {
        return map.get(character);
    }

    public String get(Integer index) {
        return map2.get(index);
    }

    public boolean containsChar(Character key) {
        return map.containsKey(key);
    }

    public Integer add(Character key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        map.put(key, size);
        map2.put(size, "" + key);
        size++;
        check(key);
        return size - 1;
    }

    private void check(Character c) {
        try {
            if (!map2.get(map.get(c)).contains("" + c)) {
                throw new RuntimeException("no valid mapping for maps");
            }
        } catch (NullPointerException ex) {
            throw new RuntimeException("no valid mapping for maps");
        }
    }

    public Integer add(String keys) {
        char[] chars = keys.toCharArray();
        int idx = add(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            add(chars[i], idx);
        }
        for (int i = 1; i < chars.length; i++) {
            check(chars[i]);
        }
        return idx;
    }

    public Integer add(Character key, Integer value) {
        if (map.containsKey(key)) {
            throw new RuntimeException("key '" + key + "' is already in charmap at position " + map.get(key));
        }
        if (value > size()) {
            throw new RuntimeException("index must be <= size (=" + size() + ")");
        }
        if (value == size()) {
            add(key);
            return value;
        }
        map.put(key, value);
        String newVal = map2.get(value) == null ? String.valueOf(key) : map2.get(value) + key;
        map2.put(value, newVal);
        check(key);
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharMap charMap = (CharMap) o;
        return size == charMap.size &&
                map2.equals(charMap.map2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map2, size);
    }

    public HashMap<Character, Integer> getMap() {
        return map;
    }
}
