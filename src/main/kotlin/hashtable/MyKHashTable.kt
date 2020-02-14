package hashtable

import java.util.LinkedList

class MyKHashTable<K,V>(val size: Int) : GKHashTable<K,V> {

  // -------- Member variables --------

  val data: Array<LinkedList<KVPair<K,V>?>?> = Array(size) { null }


  // -------- Helpers --------

  fun convertKeyToIndex(key: Any): Int {
    val keyHashCode = key.hashCode()
    return keyHashCode % data.size;
  }


  // -------- GKHashTable callbacks --------

  override fun insert(key: K, value: V) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun get(key: Any): V? {
    val index = convertKeyToIndex(key)
    val pairList = data[index] ?: return null

    for (pair in pairList) {
      pair?.let {
        pair.key?.let { if (pair.key.equals(key)) return pair.value } }
    }
    return null
  }

  override fun remove(key: Any) {
    val index = convertKeyToIndex(key)
    val pairList = data[index] ?: return

    pairList.removeIf {p -> p?.key?.equals(key)!! }
  }

  override fun size(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun set(key: K, value: V) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun contains(key: Any): Boolean {
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