package set

import hashtable.MyHashMap

class MyHashSet<T> : Collection<T> {

  // ---------------- Member variables ----------------

  private var map: MyHashMap<T, Any> = MyHashMap()
  private val DUMMY_VAL = Any()

  override val size: Int
    get() = map.size


  // ---------------- Public methods ----------------

  /**
   * Inserts the element into the set map if it is unique, otherwise no action is taken.
   * @param element the element to insert
   *
   * @return 'true' if the element was inserted (i.e. it was unique), 'false' otherwise.
   */
  fun add(element: T): Boolean {
    return map.put(element, DUMMY_VAL)
  }

  fun addAll(elements: Collection<T>): Boolean {
    var isAllAdded = true

    for (e in elements) {
      if (!add(e)) isAllAdded = false
    }

    return isAllAdded
  }

  /**
   * Removes the value if it exists, otherwise no action is taken.
   * @param element the element to remove
   */
  fun remove(element: T): Boolean {
    return map.remove(element)
  }

  fun removeAll(elements: Collection<T>): Boolean {
    var isAllRemoved = true

    for (e in elements) {
      if (!remove(e)) isAllRemoved = false
    }

    return isAllRemoved
  }

  fun clear() {
    map.clear()
  }

  override fun contains(element: T): Boolean {
    return map.containsKey(element)
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    for (e in elements) {
      if (!contains(e)) return false
    }
    return true
  }

  override fun isEmpty(): Boolean {
    return map.isEmpty()
  }

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

  override fun equals(other: Any?): Boolean {
    return when {
      other !is MyHashSet<*> -> false
      other.size != size -> false
      other.containsAll(map.keys) -> true
      else -> false
    }
  }

  override fun hashCode(): Int {
    return map.hashCode()
  }

  override fun toString(): String {
    return map.keys.toString()
  }
}
