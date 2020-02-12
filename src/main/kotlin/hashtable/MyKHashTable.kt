package hashtable

import java.util.LinkedList

class MyKHashTable<K,V>(val size: Int) : GKHashTable<K,V> {

  // -------- Member variables --------

  val data: Array<LinkedList<KVPair<K,V>?>?> = Array(size) { null }


  // -------- Helpers --------

  fun convertKeyToIndex(key: K): Int {
    val keyHashCode = key.hashCode()
    return keyHashCode % data.size;
  }


  // -------- GKHashTable callbacks --------

  override fun insert(key: K, value: V) {
    val index = convertKeyToIndex(key)

    if (data[index] == null) data[index] = LinkedList()
    data[index]?.add(KVPair(key, value))
  }

  override fun get(key: K): V? {
    val index = convertKeyToIndex(key)
    val pairList = data[index] ?: return null

    for (pair in pairList) {
      pair?.let {
        pair.key?.let { if (pair.key.equals(key)) return pair.value } }
    }
    return null
  }

  override fun remove(key: K) {
    val index = convertKeyToIndex(key)
    val pairList = data[index] ?: return

    pairList.removeIf {p -> p?.key?.equals(key)!! }
  }

  override fun set(key: K, value: V) {
    val index = convertKeyToIndex(key)
    val pairList: LinkedList<KVPair<K,V>?>?

    pairList = if (data[index] == null) LinkedList() else data[index]

    pairList?.let {
      for (pair in pairList) {
        pair?.let {
          pair.key?.let { if (pair.key.equals(key)) pair.value = value } }
      }
    }
  }

  override fun contains(key: K): Boolean {
    val index = convertKeyToIndex(key)
    val pairList = data[index] ?: return false

    for (pair in pairList) {
      pair?.let {
        pair.key?.let { if (pair.key.equals(key)) return true } }
    }
    return false
  }

  data class KVPair<K,V>(val key: K, var value: V)
}