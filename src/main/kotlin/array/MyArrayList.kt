package array

import java.lang.IndexOutOfBoundsException

// Type safe as add(T) and set(Int, T) only allow type T & it cannot be otherwise modified
@Suppress("UNCHECKED_CAST")
class MyArrayList<T> : Collection<T> {

  // ---------------- Member variables ----------------

  private var array: Array<Any?> = arrayOfNulls(1)
  private var nextNullIndex = 0

  override val size: Int
    get() = nextNullIndex


  // ---------------- Public fun ----------------

  fun add(element: T) {
    add(nextNullIndex, element)
  }

  fun add(index: Int, element: T) {
    if (index < 0 || index > nextNullIndex) throw IndexOutOfBoundsException()
    if (nextNullIndex >= array.size) resizeArray()
    makeRoomAtIndexAndInsert(index, element)
  }

  fun get(index: Int): T {
    if (index > nextNullIndex - 1 || index < 0) throw IndexOutOfBoundsException()
    return array[index] as T
  }

  fun set(index: Int, data: T) {
    if (index > nextNullIndex - 1 || index < 0) throw IndexOutOfBoundsException()
    array[index] = data
  }

  fun delete(index: Int) {
    if (index < 0 || index > nextNullIndex - 1) throw IndexOutOfBoundsException()
    moveNextElementsDown(index)
    if (nextNullIndex < 2 * this.array.size) resizeArray()
  }

  override fun iterator(): Iterator<T> {
    return object: Iterator<T> {

      var currentIndex = 0

      override fun hasNext(): Boolean {
        return currentIndex < nextNullIndex
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

  override fun contains(element: T): Boolean {
    for (dataPoint in array) {
      if (dataPoint == null) break
      if (dataPoint as T == element) return true
    }
    return false
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    for (element in elements) {
      if (!contains(element)) return false
    }
    return true
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyArrayList<*> || other.size != size) return false

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
    val tempData: Array<Any?> = arrayOfNulls((nextNullIndex * 1.5 + 1).toInt())
    System.arraycopy(array, 0, tempData, 0, nextNullIndex)
    array = tempData
  }

  private fun makeRoomAtIndexAndInsert(insertIndex: Int, element: T) {
    for (i in nextNullIndex downTo insertIndex + 1) {
      array[i] = array[i -1]
    }
    array[insertIndex] = element
    nextNullIndex++
  }

  private fun moveNextElementsDown(startIndex: Int) {
    for (i in startIndex..(nextNullIndex-2)) {
      array[i] = array[i + 1]
    }
    array[nextNullIndex - 1] = null
    nextNullIndex--
  }
}