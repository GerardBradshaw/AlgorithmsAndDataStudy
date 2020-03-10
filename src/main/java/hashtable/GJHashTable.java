package hashtable;

public interface GJHashTable<K, V> {

  void insert(K key, V value);
  V get(Object key);
  void remove(Object key);
  void set(K key, V value);
  boolean contains(Object key);
  int size();
}
