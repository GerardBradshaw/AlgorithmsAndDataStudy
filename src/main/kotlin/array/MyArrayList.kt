package array

import java.lang.IndexOutOfBoundsException

// Type safe as add(T) and set(Int, T) only allow type T & it cannot be otherwise modified
@Suppress("UNCHECKED_CAST")
class MyArrayList<T> : Collection<T> {

  // ---------------- Member variables ----------------

  private var data: Array<Any?> = arrayOfNulls(1)
  private var dataPoints = 0

  override val size: Int
    get() = dataPoints


  // ---------------- Public fun ----------------

  fun add(data: T) {
    add(dataPoints, data)
  }

  fun add(index: Int, data: T) {
    if (index < 0 || index > dataPoints) throw IndexOutOfBoundsException()

    if (dataPoints >= this.data.size) resizeArray()

    for (i in dataPoints downTo index + 1) {
      this.data[i] = this.data[i - 1]
    }
    this.data[index] = data
    dataPoints++
  }

  fun get(index: Int): T {
    if (index > dataPoints - 1 || index < 0) throw IndexOutOfBoundsException()
    return data[index] as T
  }

  fun set(index: Int, data: T) {
    if (index > dataPoints - 1 || index < 0) throw IndexOutOfBoundsException()
    this.data[index] = data
  }

  fun delete(index: Int) {
    if (index < 0 || index > dataPoints - 1) throw IndexOutOfBoundsException()

    for (i in index..(dataPoints-2)) {
      this.data[i] = this.data[i + 1]
    }
    data[dataPoints - 1] = null
    dataPoints--

    if (dataPoints < 2 * this.data.size) resizeArray()
  }


  // ---------------- helpers ----------------

  private fun resizeArray() {
    val tempData: Array<Any?> = arrayOfNulls((dataPoints * 1.5 + 1).toInt())
    System.arraycopy(data, 0, tempData, 0, dataPoints)
    data = tempData
  }


  // ---------------- Iterator callbacks ----------------

  override fun iterator(): Iterator<T> {
    return object: Iterator<T> {

      var currentIndex = 0

      override fun hasNext(): Boolean {
        return currentIndex < size
      }

      override fun next(): T {
        val listItem = data[currentIndex] as T
        currentIndex++
        return listItem
      }
    }
  }


  // ---------------- Collection callbacks ----------------

  override fun isEmpty(): Boolean {
    return dataPoints == 0
  }

  override fun contains(element: T): Boolean {
    for (dataPoint in data) {
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


  // ---------------- Any callbacks ----------------

  override fun equals(other: Any?): Boolean {
    if (other !is MyArrayList<*> || other.size != size) return false

    val otherIterator = other.iterator()

    for (i in 0 until dataPoints) {
      if (otherIterator.hasNext()) {
        if (otherIterator.next() == data[i]) continue
      }
      return false
    }
    return !otherIterator.hasNext()
  }

  override fun hashCode(): Int {
    // I know this is really lazy and only works properly if T is String
    return toString().hashCode()
  }

  override fun toString(): String {
    if (dataPoints == 0) return "empty"

    val builder = MyStringBuilder().append("[").append(data[0].toString())

    for (i in 1 until dataPoints)
      builder.append(", ").append(data[i].toString())

    return builder.append("]").toString()
  }
}