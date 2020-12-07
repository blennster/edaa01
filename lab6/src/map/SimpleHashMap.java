package map;

import java.util.Random;

public class SimpleHashMap<K, V> implements Map<K, V> {
    private Entry<K, V>[] table;
    private int size = 0;
    private final double LOAD_FACTOR = 0.75;

    public static void main(String[] args) {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>(10);
        Random rnd = new Random(100);
        for (int i = 0; i < 7; i++) {
            int rndNbr = rnd.nextInt(20);
            map.put(rndNbr, rndNbr);
        }
        System.out.println(map.show());
    }

    public SimpleHashMap() {
        table = (Entry<K,V>[]) new Entry[16];
    }

    public SimpleHashMap(int capacity) {
        table = (Entry<K,V>[]) new Entry[capacity];
    }

    @Override
    public V get(Object key) {
        K kkey = (K) key;
        int index = index(kkey);
        Entry<K, V> entry = find(index, kkey);
        if (entry != null) return entry.value;
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V put(K arg0, V arg1) {
        int index = index(arg0);
        Entry<K, V> e = find(index, arg0);

        if (e == null) {
            if (size > table.length * LOAD_FACTOR) {
                rehash();
                index = index(arg0); // Räkna om index pga rehash
            }
            Entry<K, V> entry = new Entry<>(arg0, arg1);
            place(index, entry);
            size++;
            return null;
        }

        V oldVal = e.value;
        e.value = arg1;
        return oldVal;
    }

    @Override
    public V remove(Object key) {
        K kkey = (K) key;
        if (size == 0) return null;

        int index = index(kkey);
        Entry<K, V> entry = table[index];

        if (entry == null) return null;

        if (entry.key.equals(kkey)) {
            table[index] = entry.next;
            size--;
            return entry.value;
        }

        Entry<K, V> previous = entry;
        entry = entry.next;
        while (entry != null) {
            if (entry.key.equals(kkey)) {
                previous.next = entry.next;
                size--;
                return entry.value;
            }
            previous = entry;
            entry = entry.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[i];
            if (entry != null) {
                sb.append(i).append("\t");
                while (entry != null) {
                    sb.append(entry).append(" ");
                    entry = entry.next;
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private int index(K key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index = index + table.length;
        }
        return index;
    }

    private Entry<K, V> find(int index, K key) {
        Entry<K, V> entry = table[index];
        if (entry == null) return null;

        while (!entry.key.equals(key)) {
            if (entry.next == null) return null;
            entry = entry.next;
        }
        return entry;
    }

    private void rehash() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K,V>[]) new Entry[table.length * 2];
        for (Entry<K, V> entry : oldTable) {
            Entry<K, V> e = entry;
            while (e != null) {
                // Lös upp listor så att de inte blir circkulärt länkade.
                Entry<K, V> next = e.next;
                e.next = null;

                int index = index(e.key);
                place(index, e);
                e = next;
            }
        }
    }

    private void place(int index, Entry<K, V> entry) {
        if (table[index] == null) {
            table[index] = entry;
        } else {
            Entry<K, V> e = table[index];
            while (e.next != null) {
                e = e.next;
            }
            e.next = entry;
        }
    }

    private class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }

        @Override
        public String toString() {
            return getKey().toString() + " " + getValue().toString();
        }
    }
}
