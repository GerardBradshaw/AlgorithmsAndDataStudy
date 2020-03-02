package heap

import kotlin.math.max

class MyKMinIntHeap() {

  // -------- Member variables --------

  var data: Array<Int?> = arrayOfNulls(3)
  var size = 0


  // -------- Constructor --------

  constructor(data: Array<Int?>) : this() {
    for (int in data) {
      insert(int)
    }
  }


  // -------- Public methods --------

  fun findMin(): Int {
    val min = data[0] ?: throw NullPointerException()
    return min
  }

  fun insert(int: Int?) {
    if (int == null)
      return

    if (!isEnoughSpace())
      resizeArray()

    addToData(int)
  }

  fun popMin(): Int {
    val min = data[0] ?: throw NullPointerException()
    deleteMin()
    return min
  }

  fun deleteMin() {
    if (data[0] == null) throw NullPointerException()

    when (size) {
      0 -> return
      1 -> {
        data[0] = null
        size--
      }
      else -> {
        data[0] = data[size - 1]
        data[size - 1] = null
        size--
        moveDownToCorrectPosition(0)
      }
    }

    if (data.size > 3 * size) resizeArray()
  }

  fun size(): Int {
    return size
  }

  fun isEmpty(): Boolean {
    if (size == 0) return true
    return false
  }


  // -------- Helpers --------

  private fun isEnoughSpace(): Boolean {
    return data.size >= size + 1
  }

  private fun addToData(int: Int) {
    data[size] = int
    size++;
    moveUpToCorrectPosition(size - 1)
  }

  private fun resizeArray() {
    val tempData: Array<Int?> = arrayOfNulls(size * 2)
    System.arraycopy(data, 0, tempData, 0, size)
    data = tempData
  }

  private fun moveDownToCorrectPosition(index: Int) {
    var currentIndex = index
    var leftIndex = leftIndexOf(currentIndex)
    var rightIndex = rightIndexOf(currentIndex)
    var currentValue: Int
    var leftValue: Int
    var rightValue: Int
    val maxIndex = size - 1

    if (size == 2) {
      currentValue = data[currentIndex] ?: return
      leftValue = data[leftIndex] ?: return
      if (currentValue > leftValue) swap(currentIndex, leftIndex)
    }

    while (leftIndex < maxIndex && rightIndex <= maxIndex) {
      currentValue = data[currentIndex] ?: break
      leftValue = data[leftIndex] ?: break
      rightValue = data[rightIndex] ?: Int.MAX_VALUE

      if (leftValue <= rightValue) {
        if (currentValue > leftValue) {
          swap(currentIndex, leftIndex)
          currentIndex = leftIndex
        }
        else return
      }
      else if (rightValue < leftValue) {
        if (currentValue > rightValue) {
          swap(currentIndex, rightIndex)
          currentIndex = rightIndex
        }
        else return
      }
      else return

      leftIndex = leftIndexOf(currentIndex)
      rightIndex = rightIndexOf(currentIndex)
    }
  }

  private fun moveUpToCorrectPosition(index: Int) {
    val value = data[index] ?: return
    var newIndex = index
    var parentIndex: Int
    var parentValue: Int

    while (newIndex > 0) {
      parentIndex = parentOf(newIndex)
      parentValue = data[parentIndex] ?: Int.MIN_VALUE

      if (parentValue > value) {
        swap(newIndex, parentIndex)
        newIndex = parentIndex
      }
      else
        return
    }
  }

  private fun leftIndexOf(index: Int): Int {
    return 2 * index + 1;
  }

  private fun rightIndexOf(index: Int): Int {
    return 2 * index + 2
  }

  private fun parentOf(index: Int): Int {
    return if (index % 2 != 0) ((index - 1) / 2) else ((index - 2) / 2)
  }

  private fun swap(index1: Int, index2: Int) {
    if (index1 >= size || index2 >= size) throw IndexOutOfBoundsException();

    val value1 = data[index1]
    data[index1] = data[index2]
    data[index2] = value1
  }


  // -------- Any callbacks --------

  override fun equals(other: Any?): Boolean {
    if (other !is MyKMinIntHeap) return false
    if (other.size() != size) return false

    var isEqual = false
    val saveSize = size
    val saveData: Array<Int?> = arrayOfNulls(data.size)
    System.arraycopy(data, 0, this, 0, data.size)

    while (!other.isEmpty() && size > 0)
      if (other.popMin() != popMin())
        break

    if (other.isEmpty() && size == 0)
      isEqual = true

    data = saveData
    size = saveSize

    return isEqual
  }

  override fun toString(): String {
    if (size == 0)
      return "empty"

    val builder = StringBuilder().append("[")

    for (i in 0..(size - 2))
      builder
        .append(data[i])
        .append(", ")

    builder
      .append(data[size - 1])
      .append("]")

    return builder.toString()
  }

  override fun hashCode(): Int {
    return data.hashCode()
  }
}