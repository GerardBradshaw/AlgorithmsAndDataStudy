package heap

@Suppress("UNCHECKED_CAST") // Type safe because insert(data) only accepts type T
class MyKMinHeap<T : Comparable<T>>() {

  // -------- Member variables --------

  private var data: Array<Any?> = arrayOfNulls<Any?>(3)
  private var size = 0


  // -------- Constructor --------

  constructor(data: Array<T?>) : this() {
    for (obj in data) {
      insert(obj)
    }
  }


  // -------- Public methods --------

  fun insert(data: T?) {
    if (data == null)
      return

    if (!isEnoughSpace())
      resizeArray()

    addToData(data)
  }

  fun getMin(): T {
    val max = data[0] ?: throw NullPointerException()
    return max as T
  }

  fun popMin(): T {
    val max = data[0] ?: throw NullPointerException()
    deleteMin()
    return max as T
  }

  fun deleteMin() {
    if (data[0] == null)
      throw NullPointerException()

    if (size == 0) {
      return
    }
    else if (size == 1) {
      data[0] = null
      size--
    }
    else {
      data[0] = data[size - 1]
      data[size - 1] = null
      size--

      moveDownToCorrectPosition(0)
    }

    if (data.size > 3 * size)
      resizeArray()

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

  private fun addToData(data: T) {
    this.data[size] = data
    size++;
    moveUpToCorrectPosition(size - 1)
  }

  private fun resizeArray() {
    val tempData: Array<Any?> = arrayOfNulls(size * 2)
    System.arraycopy(data, 0, tempData, 0, size)
    data = tempData
  }

  private fun moveDownToCorrectPosition(index: Int) {
    var currentIndex = index
    var leftIndex = leftIndexOf(currentIndex)
    var rightIndex = rightIndexOf(currentIndex)
    var currentValue: T
    var leftValue: T
    var rightValue: T?
    val maxIndex = size - 1

    if (size == 2) {
      currentValue = data[currentIndex] as T
      leftValue = data[leftIndex] as T
      if (currentValue > leftValue) swap(currentIndex, leftIndex)
    }

    while (leftIndex < maxIndex && rightIndex <= maxIndex) {
      currentValue = data[currentIndex] as T? ?: break
      leftValue = data[leftIndex] as T? ?: break
      rightValue = data[rightIndex] as T?

      if (rightValue == null || leftValue <= rightValue) {
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
    val value = data[index] as T? ?: return
    var newIndex = index
    var parentIndex: Int
    var parentValue: T?

    while (newIndex > 0) {
      parentIndex = parentIndexOf(newIndex)
      parentValue = data[parentIndex] as T?

      if (parentValue != null && parentValue > value) {
        swap(newIndex, parentIndex)
        newIndex = parentIndex
      }
      else return
    }
  }

  private fun leftIndexOf(index: Int): Int {
    return 2 * index + 1;
  }

  private fun rightIndexOf(index: Int): Int {
    return 2 * index + 2
  }

  private fun parentIndexOf(index: Int): Int {
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
    if (other !is MyKMaxHeap<*>) return false
    if (other.size() != size) return false

    var isEqual = false
    val saveSize = size
    val saveData: Array<Any?> = arrayOfNulls(data.size)
    System.arraycopy(data, 0, this, 0, data.size)

    while (!other.isEmpty() && size > 0)
      if (other.popMax() != popMin())
        break

    if (other.isEmpty() && size == 0)
      isEqual = true

    data = saveData
    size = saveSize

    return isEqual
  }

  override fun toString(): String {
    if (size == 0) return "empty"

    val builder = StringBuilder().append("[").append(data[0])

    for (i in 1..(size - 1)) builder.append(", ").append(data[i].toString())

    return builder.append("]").toString()
  }

  override fun hashCode(): Int {
    return data.hashCode()
  }
}