package hashtable

import array.MyArrayList

class MyHashMap<K,V> {

  // ---------------- Fields ----------------

  private var array: Array<Pair<K, V>?> = arrayOfNulls(2)
  private var numberOfEntries = 0

  private val maxIndex: Int
    get() = array.size - 1

  val size: Int
    get() = numberOfEntries

  val entries: Collection<Pair<K, V>>
    get() {
      val collection = MyArrayList<Pair<K,V>>()

      for (pair in array) {
        if (pair != null) collection.add(pair)
      }
      return collection
    }

  val keys: Collection<K>
    get() {
      val collection = MyArrayList<K>()

      for (pair in array) {
        if (pair != null) collection.add(pair.first)
      }
      return collection
    }

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
   * Puts the key-value pair into the hash map if they key is unique, otherwise no action is taken.
   * @param key the element key
   * @param value the value corresponding to the key
   *
   * @return 'true' if the value was successfully inserted, 'false' otherwise.
   */
  fun put(key: K, value: V): Boolean {
    return putWithEntryCountModifier(key, value)
  }

  /**
   * Removes the value with corresponding key if it exists, otherwise returns without any action.
   * @param key the key corresponding to the value to remove
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

  fun clear() {
    array = arrayOfNulls(2)
    numberOfEntries = 0
  }

  fun containsKey(key: K): Boolean {
    return containsKeyHelper(key, { pair -> pair.first == key })
  }

  fun containsValue(value: V): Boolean {
    if (numberOfEntries == 0) return false

    for (pair in array) {
      if (pair?.second == value) return true
    }
    return false
  }

  fun containsEntry(key: K, value: V): Boolean {
    return containsEntryAny(key as Any, value as Any)
  }

  /**
   * Checks the Hash Map for an exact key-value match.
   * Param types are 'Any' to allow usage when type 'K' and 'V' cannot be guaranteed due to type erasure (such as in
   * equals()).
   * @param key the key of any type
   * @param value the value of any type
   *
   * @return 'true' if an exact key and value match is found, otherwise 'false'
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
   * Gets the value corresponding to the given key or null if no such key exists.
   * @param key the key
   *
   * @return the value corresponding to the key, or null if no such key exists.
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

  fun isEmpty(): Boolean {
    return numberOfEntries == 0
  }

  fun isNotEmpty(): Boolean {
    return numberOfEntries != 0
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyHashMap<*,*> || other.numberOfEntries != numberOfEntries) return false

    for (pair in array) {
      if (pair != null) {
        if (!other.array.contains(pair)) {
          return false
        }
      }
    }
    return true
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 1

    for (pair in array) {
      result = prime * result + (if (pair != null) 0 else pair.hashCode())
    }
    return result
  }

  override fun toString(): String {
    return entries.toString()
  }


  // ---------------- Helpers ----------------

  /**
   * Same as put(key, value) but adds parameter to increase entry count. Entry count should not be increased when
   * resizing array.
   * @param key the element key
   * @param value the value corresponding to the key
   * @param increaseNumberOfEntries numberOfEntries is increased if entry is successfully added when 'true' (default)
   * and ignored when 'false'
   *
   * @return 'true' if the value was successfully inserted, 'false' otherwise.
   */
  private fun putWithEntryCountModifier(key: K, value: V, increaseNumberOfEntries: Boolean = true): Boolean {
    resizeArray()

    val index = getNextEmptyIndex(key)

    if (index != -1) {
      if (array[index] == null) {
        array[index] = Pair(key, value)
        if (increaseNumberOfEntries) numberOfEntries++
        return true
      }
    }
    return false
  }

  /**
   * Finds the first empty slot in array starting at the found by converting the key into an index.
   * @param key the key.
   *
   * @return -1 if the key is not unique, otherwise an Int index.
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

  private fun getIndexFromKey(key: K): Int {
    return getIndexFromAny(key as Any)
  }

  /**
   * Finds the index in the array corresponding to the modulus of 'any' with the size of the array.
   * @param any the key
   *
   * @return a valid index in the array
   */
  private fun getIndexFromAny(any: Any): Int {
    return any.hashCode() % array.size
  }

  /**
   * Changes the size of the array to be 150% of the number of entries if it's full or less than half full.
   * The array size is changed by reinserting each value into a new array and reassigning 'array' to this Array.
   */
  private fun resizeArray() {
    val requiredSize = numberOfEntries + 1
    val actualSize = array.size

    if (actualSize < requiredSize || actualSize > requiredSize * 2) {
      val arrayHolder = array
      val newArraySize = nextPrime((numberOfEntries + 1) * 3 / 2)
      array = arrayOfNulls(newArraySize)

      for (pair in arrayHolder) {
        if (pair != null) putWithEntryCountModifier(pair.first, pair.second, false)
      }
    }
  }

  private fun nextPrime(n: Int): Int {
    var next = n
    if (n % 2 == 0) next++

    while (true) {
      if (isPrime(next)) return next
      next += 2
    }
  }

  private fun isPrime(n: Int): Boolean {
    if (n % 2 == 0) return false

    for (i in 3..n/2 step 2) {
      if (n % i == 0) return false
    }
    return true
  }

  private fun containsKeyHelper(key: K, keyMatches: (pair: Pair<K,V>) -> Boolean): Boolean {
    if (numberOfEntries == 0) return false

    val startIndex = getIndexFromKey(key)
    var currentIndex = startIndex
    var current = array[startIndex]

    while (current != null) {
      if (keyMatches(current)) return true
      // if (current.first == key) return true

      if (currentIndex < maxIndex) currentIndex++
      else currentIndex = 0

      if (currentIndex == startIndex) break
      current = array[currentIndex]
    }
    return false
  }

}