package hashtable

interface GKHashTable<K,V> {

  fun insert(key: K, value: V)
  fun get(key: K): V
  fun remove(key: K)
  fun set(key: K, value: V)
  fun contains(key: K): Boolean

}