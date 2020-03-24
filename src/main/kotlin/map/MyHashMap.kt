package map

import array.MyArrayList
import array.MyStringBuilder
import java.lang.NullPointerException
import kotlin.math.abs

class MyHashMap<K,V> : Iterable<Pair<K,V>> {

  // ---------------- Fields ----------------

  private var array: Array<Pair<K, V>?> = arrayOfNulls(2)
  private var numberOfEntries = 0

  private val maxIndex: Int
    get() = array.size - 1

  val size: Int
    get() = numberOfEntries

  /**
   * Returns a [Collection] of [Pair] where Pair.first is the key ([K]) and Pair.second is the value ([V]).
   *
   * Efficiency: O(n) time, O(n) space, n = number of keys
   */
  val entries: Collection<Pair<K, V>>
    get() {
      val collection = MyArrayList<Pair<K,V>>()

      for (pair in array) {
        if (pair != null) collection.add(pair)
      }
      return collection
    }

  /**
   * Returns a [Collection] of keys ([K]).
   *
   * Efficiency: O(n) time, O(n) space, n = number of keys
   */
  val keys: Collection<K>
    get() {
      val collection = MyArrayList<K>()

      for (pair in array) {
        if (pair != null) collection.add(pair.first)
      }
      return collection
    }

  /**
   * Returns a [Collection] of values ([V]).
   *
   * Efficiency: O(n) time, O(n) space, n = number of values
   */
  val values: Collection<V>
    get() {
      val collection = MyArrayList<V>()

      for (pair in array) {
        if (pair != null) collection.add(pair.second)
      }
      return collection
    }


  // ---------------- Public fun ----------------

  /**
   * Returns 'true' if [value] is successfully inserted into the map with identifier [key] (key must be unique), 'false'
   * otherwise.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  fun put(key: K, value: V): Boolean {
    return putHelper(key, value)
  }

  /**
   * Returns 'true' if a value with identifier [key] exists and was successfully removed from the map, 'false' otherwise.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n), n = number of entries
   */
  fun remove(key: K): Boolean {
    val startIndex = getIndexFromKey(key)
    var currentIndex = startIndex
    var current = array[startIndex]

    while (current != null) {
      if (current.first == key) {
        array[currentIndex] = null
        numberOfEntries--
        resizeArray()
        return true
      }

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) break
      current = array[currentIndex]
    }
    return false
  }

  /**
   * Empties the map.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun clear() {
    array = arrayOfNulls(2)
    numberOfEntries = 0
  }

  /**
   * Returns 'true' if the map contains [key], 'false' otherwise.
   *
   * Efficiency: Typically O(1) time, O(1) space, n = number of keys. Worst case O(n) time, O(1) space
   */
  fun containsKey(key: K): Boolean {
    return containsKeyHelper(key, { pair -> pair.first == key })
  }

  /**
   * Returns 'true' if the map contains [value], false otherwise.
   *
   * Efficiency: O(n) time, O(1) space, n = number of values
   */
  fun containsValue(value: V): Boolean {
    if (numberOfEntries == 0) return false

    for (pair in array) {
      if (pair?.second == value) return true
    }
    return false
  }

  /**
   * Returns 'true' if the map contains [value] with reference [key], 'false' otherwise.
   *
   * Efficiency: O(n) time, O(1) space, n = number of entries
   */
  fun containsEntry(key: K, value: V): Boolean {
    return containsEntryAny(key as Any, value as Any)
  }

  /**
   * Returns the value corresponding to [key], or null if no such key exists in the map.
   *
   * Efficiency: O(n) time, O(1) space, n = number of keys
   */
  fun get(key: K): V? {
    if (numberOfEntries == 0) return null

    val startIndex = getIndexFromKey(key)
    var currentIndex = startIndex
    var current = array[startIndex]

    while (current != null) {
      if (current.first == key) return current.second

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) return null
      current = array[currentIndex]
    }
    return null
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun isEmpty(): Boolean {
    return numberOfEntries == 0
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun isNotEmpty(): Boolean {
    return numberOfEntries != 0
  }

  /**
   * Indicates whether [other] is "equal to" this one. Other must contain all the same key-value pairs (order not
   * important).
   *
   * Efficiency: O(n^2) time, O(1) space, n = number of entries
   */
  override fun equals(other: Any?): Boolean {
    if (other !is MyHashMap<*, *> || other.numberOfEntries != numberOfEntries) return false

    for (pair in array) {
      if (pair != null) {
        if (!other.array.contains(pair)) {
          return false
        }
      }
    }
    return true
  }

  /**
   * Returns a hash code value for the object.
   *
   * Efficiency: O(n) time, O(1) space, n = number of entries
   */
  override fun hashCode(): Int {
    val prime = 31
    var result = 1

    for (pair in array) {
      result = prime * result + (if (pair != null) 0 else pair.hashCode())
    }
    return result
  }

  /**
   * Returns a String representation of the object.
   *
   * Efficiency: O(n) time, O(?) space, n = number of entries
   */
  override fun toString(): String {
    if (isEmpty()) return "empty"

    val strB = MyStringBuilder()

    for (pair in array) {
      if (pair != null) strB.append("(${pair.first}, ${pair.second})\n")
    }
    return strB.toString()
  }

  override fun iterator(): Iterator<Pair<K, V>> {
    return object : Iterator<Pair<K, V>> {
      var currentIndex = 0
      var returnCount = 0

      override fun hasNext(): Boolean {
        return returnCount < numberOfEntries
      }

      override fun next(): Pair<K, V> {
        var currentPair: Pair<K, V>? = null

        while (currentPair == null && currentIndex < array.size) {
          currentPair = array[currentIndex]
          currentIndex++
        }
        returnCount++
        return currentPair ?: throw NullPointerException()
      }
    }
  }

  // ---------------- Helpers ----------------

  /**
   * Returns 'true' if the map contains [value] with reference [key], 'false' otherwise. Accepts key and value of type
   * [Any] due to type-erasure problems encountered when using types [K] and [V].
   *
   * Efficiency: O(n) time, O(1) space, n = number of entries
   */
  private fun containsEntryAny(key: Any, value: Any): Boolean {
    if (numberOfEntries == 0) return false

    val startIndex = getIndexFromAny(key)
    var currentIndex = startIndex
    var current = array[startIndex]

    while (current != null) {
      if (current.first == key && current.second == value) return true

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) break
      current = array[currentIndex]
    }
    return false
  }

  /**
   * Puts [key] and [value] in the array as a [Pair] (Pair.first = key, Pair.second = value) after ensuring [array] has
   * an null slot if [resizing] is 'false' (default). Returns 'true' only if successfully inserted (key is unique).
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n), n = number of entries
   */
  private fun putHelper(key: K, value: V, resizing: Boolean = false): Boolean {
    if (!resizing) resizeArray()

    val index = getNextEmptyIndex(key)

    if (index != -1) {
      if (array[index] == null) {
        array[index] = Pair(key, value)
        if (!resizing) numberOfEntries++
        return true
      }
    }
    return false
  }

  /**
   * Returns the first index with a null value in [array] appropriate for [key], or -1 if the key already exists in
   * [array].
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(1) space, n = number of entries
   */
  private fun getNextEmptyIndex(key: K): Int {
    val startIndex = getIndexFromKey(key)
    var currentIndex = startIndex
    var current = array[currentIndex]

    while (current != null) {
      if (current.first == key) return -1

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) return -1

      current = array[currentIndex]
    }
    return currentIndex
  }

  /**
   * Returns the first index in [array] for inserting [key] (index may not contain null value).
   *
   * Efficiency: O(1) time, O(1) space
   */
  private fun getIndexFromKey(key: K): Int {
    return getIndexFromAny(key as Any)
  }

  /**
   * Returns the first index in [array] for inserting [key] (index may not contain null value). Accepts type [Any] due
   * to type-erasure problems encountered when using [K].
   *
   * Efficiency: O(1) time, O(1) space
   */
  private fun getIndexFromAny(any: Any): Int {
    return abs(any.hashCode() % array.size)
  }

  /**
   * Changes the size of [array] to 150% of the number of entries if it's full or less than half full.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  private fun resizeArray() {
    val requiredSize = numberOfEntries + 1
    val actualSize = array.size

    if (actualSize < requiredSize || actualSize > requiredSize * 2) {
      val arrayHolder = array
      val newArraySize = nextPrime((numberOfEntries + 1) * 3 / 2)
      array = arrayOfNulls(newArraySize)

      for (pair in arrayHolder) {
        if (pair != null) putHelper(pair.first, pair.second, true)
      }
    }
  }

  /**
   * Returns the next prime number after [n].
   *
   * Efficiency: O(1) time, O(1) space
   */
  private fun nextPrime(n: Int): Int {
    var next = n
    if (n % 2 == 0) next++

    while (true) {
      if (isPrime(next)) return next
      next += 2
    }
  }

  /**
   * Returns 'true' if [n] is prime.
   *
   * Efficiency: O(1) time, O(1) space
   */
  private fun isPrime(n: Int): Boolean {
    if (n % 2 == 0) return false

    for (i in 3..n/2 step 2) {
      if (n % i == 0) return false
    }
    return true
  }

  /**
   * Returns 'true' if the map contains [key], 'false' otherwise. [keyMatches] function returns 'true' if key matches
   * a given key-value [Pair].
   *
   * Efficiency: Typically O(1) time, O(1) space, n = number of keys. Worst case O(n) time, O(1) space
   */
  private fun containsKeyHelper(key: K, keyMatches: (pair: Pair<K,V>) -> Boolean): Boolean {
    if (numberOfEntries == 0) return false

    val startIndex = getIndexFromKey(key)
    var currentIndex = startIndex
    var current = array[startIndex]

    while (current != null) {
      if (keyMatches(current)) return true // if (current.first == key) return true

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) break
      current = array[currentIndex]
    }
    return false
  }

}