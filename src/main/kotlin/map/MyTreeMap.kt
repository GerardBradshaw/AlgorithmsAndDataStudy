package map

import linkedlist.MyJDoublyLinkedList
import tree.MyGenericAvlTree

class MyTreeMap<K : Comparable<K>, V> {
  val list = MyJDoublyLinkedList<Pair<K, V>>()
  val tree = MyGenericAvlTree<K>()

  fun insert(key: K, value: V) {
    list.addLast(Pair(key, value))
    tree.insert(key)
  }

  fun delete(key: K) {
    val iterator = list.iterator()
    var pair: Pair<K, V>

    while (iterator.hasNext()) {
      pair = iterator.next()!! // Not null due to loop condition
      if (pair.first == key) {
        list.remove(pair)
        break
      }
    }
    tree.delete(key)
  }

  fun contains(data: K): Boolean {
    return tree.contains(data)
  }

  fun contains(value: V): Boolean {
    val iterator = list.iterator()
    var pair: Pair<K, V>

    while (iterator.hasNext()) {
      pair = iterator.next()!! // not null due to loop condition
      if (pair.second == value) return true
    }
    return false
  }

  fun isEmpty(): Boolean {
    return tree.isEmpty()
  }

  fun isNotEmpty(): Boolean {
    return tree.isNotEmpty()
  }

  fun size(): Int {
    return tree.size()
  }

  /*
  private data class Entry<K : Comparable<K>, V>(val key: K, var value: V) {
    var prev: Entry<K,V>? = null
    var next: Entry<K,V>? = null


    override fun equals(other: Any?): Boolean {
      if (other !is Entry<*,*>) return false

      return other.key == key && other.value == value
          && other.prev?.key == prev?.key && other.prev?.value == prev?.value
          && other.next?.key == next?.key && other.prev?.value == prev?.value
    }

    override fun hashCode(): Int {
      val prime = 31
      var result = (key.hashCode() * prime) * prime + value.hashCode()
      result = (result * prime + prev?.key.hashCode()) * prime + prev?.value.hashCode()
      return (result * prime + next?.key.hashCode()) * prime + next?.value.hashCode()
    }

    override fun toString(): String {
      return "[D:${key.toString()} P:${parent?.data.toString()} L:${left?.data.toString()} R:${right?.data.toString()}]"
    }
  }
   */
}