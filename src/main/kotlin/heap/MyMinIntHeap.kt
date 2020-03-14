package heap

class MyMinIntHeap() {

  // ---------------- Fields & Constructor ----------------

  private var array: Array<Int?> = arrayOfNulls(3)
  private var size = 0

  constructor(data: Array<Int?>) : this() {
    for (int in data) {
      insert(int)
    }
  }


  // ---------------- Public fun ----------------

  fun findMin(): Int {
    val min = array[0] ?: throw NullPointerException()
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
    val min = array[0] ?: throw NullPointerException()
    deleteMin()
    return min
  }

  fun deleteMin() {
    if (array[0] == null) throw NullPointerException()

    when (size) {
      0 -> return
      1 -> {
        array[0] = null
        size--
      }
      else -> {
        array[0] = array[size - 1]
        array[size - 1] = null
        size--
        moveDownToCorrectPosition(0)
      }
    }

    if (array.size > 3 * size) resizeArray()
  }

  fun size(): Int {
    return size
  }

  fun isEmpty(): Boolean {
    if (size == 0) return true
    return false
  }

  fun isNotEmpty(): Boolean {
    return size != 0
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyMinIntHeap || other.size() != size) return false

    for (i in 0 until size) {
      if (other.array[i] != array[i]) return false
    }
    return true
  }

  override fun toString(): String {
    if (size == 0) return "[]"

    val builder = StringBuilder().append("[")

    for (i in 0..(size - 2)) {
      builder.append(array[i]).append(", ")
    }

    return builder.append(array[size - 1]).append("]").toString()
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 1

    for (i in 0 until size) {
      result = result * prime + array[i].hashCode()
    }
    return result
  }


  // ---------------- Helpers ----------------

  private fun isEnoughSpace(): Boolean {
    return array.size >= size + 1
  }

  private fun addToData(int: Int) {
    array[size] = int
    size++;
    moveUpToCorrectPosition(size - 1)
  }

  private fun resizeArray() {
    val tempData: Array<Int?> = arrayOfNulls(size * 2)
    System.arraycopy(array, 0, tempData, 0, size)
    array = tempData
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
      currentValue = array[currentIndex] ?: return
      leftValue = array[leftIndex] ?: return
      if (currentValue > leftValue) swap(currentIndex, leftIndex)
    }

    while (leftIndex < maxIndex && rightIndex <= maxIndex) {
      currentValue = array[currentIndex] ?: break
      leftValue = array[leftIndex] ?: break
      rightValue = array[rightIndex] ?: Int.MAX_VALUE

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
    val value = array[index] ?: return
    var newIndex = index
    var parentIndex: Int
    var parentValue: Int

    while (newIndex > 0) {
      parentIndex = parentOf(newIndex)
      parentValue = array[parentIndex] ?: Int.MIN_VALUE

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

    val value1 = array[index1]
    array[index1] = array[index2]
    array[index2] = value1
  }
}