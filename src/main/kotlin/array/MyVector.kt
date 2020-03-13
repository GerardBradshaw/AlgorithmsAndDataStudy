package array

import java.lang.IndexOutOfBoundsException

// Type safe as add(T) and set(Int, T) only allow type T & it cannot be otherwise modified
@Suppress("UNCHECKED_CAST")
class MyVector<T> : Collection<T>, Iterable<T> {

  // ---------------- Fields ----------------

  private var array: Array<Any?> = arrayOfNulls(1)
  private var nextNullIndex = 0

  override val size: Int
    get() = nextNullIndex


  // ---------------- Public fun ----------------

  @Synchronized
  fun add(element: T) {
    add(nextNullIndex, element)
  }

  @Synchronized
  fun add(index: Int, element: T) {
    if (index < 0 || index > nextNullIndex) throw IndexOutOfBoundsException()
    if (nextNullIndex >= array.size) resizeArray()
    makeRoomAtIndexAndInsert(index, element)
  }

  @Synchronized
  fun get(index: Int): T {
    if (index > nextNullIndex - 1 || index < 0) throw IndexOutOfBoundsException()
    return array[index] as T
  }

  @Synchronized
  fun set(index: Int, element: T) {
    if (index > nextNullIndex - 1 || index < 0) throw IndexOutOfBoundsException()
    array[index] = element
  }

  @Synchronized
  fun delete(index: Int) {
    if (index < 0 || index > nextNullIndex - 1) throw IndexOutOfBoundsException()
    moveNextElementsDown(index)
    if (nextNullIndex < 4 * this.array.size) resizeArray()
  }

  override fun iterator(): Iterator<T> {
    return object: Iterator<T> {

      var currentIndex = 0

      override fun hasNext(): Boolean {
        return currentIndex < size
      }

      override fun next(): T {
        val listItem = array[currentIndex] as T
        currentIndex++
        return listItem
      }
    }
  }

  override fun isEmpty(): Boolean {
    return nextNullIndex == 0
  }

  fun isNotEmpty(): Boolean {
    return nextNullIndex != 0
  }

  @Synchronized
  override fun contains(element: T): Boolean {
    for (dataPoint in array) {
      if (dataPoint == null) break
      if (dataPoint as T == element) return true
    }
    return false
  }

  @Synchronized
  override fun containsAll(elements: Collection<T>): Boolean {
    for (element in elements) {
      if (!contains(element)) return false
    }
    return true
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyVector<*> || other.size != size) return false

    for (i in 0 until nextNullIndex) {
      if (array[i] != other.array[i]) return false
    }
    return true
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 1

    for (i in 0 until nextNullIndex) {
      result = prime * result + array[i].hashCode()
    }
    return result
  }

  override fun toString(): String {
    if (nextNullIndex == 0) return "[]"

    val builder = MyStringBuilder().append("[").append(array[0].toString())

    for (i in 1 until nextNullIndex)
      builder.append(", ").append(array[i].toString())

    return builder.append("]").toString()
  }


  // ---------------- Helpers ----------------

  private fun resizeArray() {
    val tempData: Array<Any?> = arrayOfNulls((nextNullIndex * 2 + 1))
    System.arraycopy(array, 0, tempData, 0, nextNullIndex)
    array = tempData
  }

  private fun makeRoomAtIndexAndInsert(index: Int, element: T) {
    for (i in nextNullIndex downTo index + 1) {
      this.array[i] = this.array[i - 1]
    }
    this.array[index] = element
    nextNullIndex++
  }

  private fun moveNextElementsDown(index: Int) {
    for (i in index..(nextNullIndex-2)) {
      array[i] = array[i + 1]
    }
    array[nextNullIndex - 1] = null
    nextNullIndex--
  }

}