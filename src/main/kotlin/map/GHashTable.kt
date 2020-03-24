package map

interface GHashTable<K,V> {

  fun insert(key: K, value: V)
  fun get(key: Any): V?
  fun remove(key: Any)
  fun set(key: K, value: V)
  fun contains(key: Any): Boolean
  fun size(): Int

}