package set

import hashtable.MyHashMap
import java.util.*


class MyHashSet<T> : Collection<T> {

  // ---------------- Member variables ----------------

  private var map: MyHashMap<T, Any> = MyHashMap()
  private val dummyVal = Any()

  override val size: Int
    get() = map.size


  // ---------------- Public methods ----------------

  /**
   * Returns true if [element] is unique and was successfully inserted into the map.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  fun add(element: T): Boolean {
    return map.put(element, dummyVal)
  }

  /**
   * Returns true if all of [elements] was successfully inserted into the map.
   *
   * Efficiency: Typically O(e) time, O(1) space, e = number of elements to insert. Worst case O(e * n) time, O(n + e)
   * space, n = number of existing entries.
   */
  fun addAll(elements: Collection<T>): Boolean {
    var isAllAdded = true

    for (e in elements) {
      if (!add(e)) isAllAdded = false
    }

    return isAllAdded
  }

  /**
   * Returns true if [element] exists and was successfully removed from the map.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  fun remove(element: T): Boolean {
    return map.remove(element)
  }

  /**
   * Returns true if all of [elements] was successfully removed from the map.
   *
   * Efficiency: Typically O(e) time, O(1) space, e = number of elements to remove. Worst case O(e * n) time, O(n + e)
   * space, n = number of existing entries.
   */
  fun removeAll(elements: Collection<T>): Boolean {
    var isAllRemoved = true

    for (e in elements) {
      if (!remove(e)) isAllRemoved = false
    }

    return isAllRemoved
  }

  /**
   * Empties the set.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun clear() {
    map.clear()
  }

  /**
   * Returns elements in the set as a [List].
   *
   * Efficiency: O(n) time, O(n) space, n = number of entries
   */
  fun <L> asList(): List<T> {
    val returnList = ArrayList<T>()

    for (element in map.keys) {
      returnList.add(element)
    }

    return returnList
  }

  /**
   * Returns true if the set contains [element].
   *
   * Efficiency: Typically O(1) time, O(1) space, n = number of keys. Worst case O(n) time, O(1) space
   */
  override fun contains(element: T): Boolean {
    return map.containsKey(element)
  }

  /**
   * Returns true of the set contains all in [elements].
   *
   * Efficiency: Typically O(c) time, O(1) space, c = number of elements in elements. Worst case O(c * n) time, O(1)
   * space, n = number of entries
   */
  override fun containsAll(elements: Collection<T>): Boolean {
    for (e in elements) {
      if (!contains(e)) return false
    }
    return true
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  override fun isEmpty(): Boolean {
    return map.isEmpty()
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun isNotEmpty(): Boolean {
    return map.isNotEmpty()
  }

  override fun iterator(): Iterator<T> {
    return object: Iterator<T> {

      val iterator = map.keys.iterator()

      override fun hasNext(): Boolean {
        return iterator.hasNext()
      }

      override fun next(): T {
        return iterator.next()
      }
    }
  }

  /**
   * Indicates whether [other] is "equal to" this one. Other must contain all the same elements.
   *
   * Efficiency: O(n^2) time, O(n) space, n = number of keys
   */
  override fun equals(other: Any?): Boolean {
    return when {
      other !is MyHashSet<*> -> false
      other.size != size -> false
      other.containsAll(map.keys) -> true
      else -> false
    }
  }

  /**
   * Returns a hash code value for the object.
   *
   * Efficiency: At least O(n) time, O(n) space
   */
  override fun hashCode(): Int {
    return map.keys.hashCode()
  }

  /**
   * Returns a String representation of the set.
   *
   * Efficiency: At least O(n) time, O(n) space
   */
  override fun toString(): String {
    return map.keys.toString()
  }
}
