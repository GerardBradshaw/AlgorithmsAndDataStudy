package hashtable;

public interface GHashTable<K, V> {

  void insert(K key, V value);
  V get(K key);
  void delete(K key);
  void set(K key);
  boolean contains(K key);
}
