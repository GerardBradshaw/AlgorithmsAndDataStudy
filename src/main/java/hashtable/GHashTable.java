package hashtable;

public interface GHashTable<K, V> {

  void insert(K key, V value);
  V get(K key);
  void remove(K key);
  void set(K key, V value);
  boolean contains(K key);
}
