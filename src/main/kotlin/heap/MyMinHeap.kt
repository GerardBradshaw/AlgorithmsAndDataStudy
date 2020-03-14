package heap

@Suppress("UNCHECKED_CAST") // Type safe because insert(data) only accepts type T
class MyMinHeap<T : Comparable<T>>() {

  // ---------------- Fields & Constructor ----------------

  private var array: Array<Any?> = arrayOfNulls<Any?>(3)
  private var size = 0

  constructor(data: Array<T?>) : this() {
    for (obj in data) {
      insert(obj)
    }
  }


  // ---------------- Public fun ----------------

  fun insert(element: T?) {
    if (element == null)
      return

    if (!isEnoughSpace())
      resizeArray()

    addToData(element)
  }

  fun getMin(): T {
    val max = array[0] ?: throw NullPointerException()
    return max as T
  }

  fun popMin(): T {
    val max = array[0] ?: throw NullPointerException()
    deleteMin()
    return max as T
  }

  fun deleteMin() {
    if (array[0] == null)
      throw NullPointerException()

    if (size == 0) {
      return
    }
    else if (size == 1) {
      array[0] = null
      size--
    }
    else {
      array[0] = array[size - 1]
      array[size - 1] = null
      size--

      moveDownToCorrectPosition(0)
    }

    if (array.size > 3 * size)
      resizeArray()

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

  fun contains(element: T): Boolean {
    for (i in 0 until size) {
      if (array[i] == element) return true
    }
    return false
  }

  fun getAllUnordered(): Array<T> {
    return Array<Any>(size) { index -> array[index] as T } as Array<T>
  }

  override fun equals(other: Any?): Boolean {
    // TODO need to learn how to sort array
    if (other !is MyMinHeap<*> || other.size != size) return false

    for (i in 0 until size) {
      if (other.array[i] != array[i]) return false
    }
    return true
  }

  override fun toString(): String {
    if (size == 0) return "[]"

    val builder = StringBuilder().append("[").append(array[0])

    for (i in 1 until size) {
      builder.append(", ").append(array[i].toString())
    }

    return builder.append("]").toString()
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 0

    for (i in 0 until size) {
      result = result * prime + array[i].hashCode()
    }
    return result
  }


  // -------- Helpers --------

  private fun isEnoughSpace(): Boolean {
    return array.size >= size + 1
  }

  private fun addToData(element: T) {
    this.array[size] = element
    size++;
    moveUpToCorrectPosition(size - 1)
  }

  private fun resizeArray() {
    val tempData: Array<Any?> = arrayOfNulls(size * 2)
    System.arraycopy(array, 0, tempData, 0, size)
    array = tempData
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
      currentValue = array[currentIndex] as T
      leftValue = array[leftIndex] as T
      if (currentValue > leftValue) swap(currentIndex, leftIndex)
    }

    while (leftIndex < maxIndex && rightIndex <= maxIndex) {
      currentValue = array[currentIndex] as T? ?: break
      leftValue = array[leftIndex] as T? ?: break
      rightValue = array[rightIndex] as T?

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
    val value = array[index] as T? ?: return
    var newIndex = index
    var parentIndex: Int
    var parentValue: T?

    while (newIndex > 0) {
      parentIndex = parentIndexOf(newIndex)
      parentValue = array[parentIndex] as T?

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

    val value1 = array[index1]
    array[index1] = array[index2]
    array[index2] = value1
  }
}