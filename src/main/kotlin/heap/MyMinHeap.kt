package heap

@Suppress("UNCHECKED_CAST") // Type safe because insert(data) only accepts type T
class MyMinHeap<T : Comparable<T>>() {

  // ---------------- Fields & Constructor ----------------

  private var array: Array<Any?> = arrayOfNulls<Any?>(3)
  private var numberOfElements = 0

  constructor(data: Array<T?>) : this() {
    for (obj in data) {
      insert(obj)
    }
  }


  // ---------------- Public fun ----------------

  fun insert(element: T?) {
    if (element == null) return
    resizeArrayIfRequired()
    addToArray(element)
  }

  fun getMin(): T {
    return array[0] as T? ?: throw NullPointerException()
  }

  fun popMin(): T {
    val result = array[0] as T? ?: throw NullPointerException()
    deleteMin()
    return result
  }

  fun size(): Int {
    return numberOfElements
  }

  fun isEmpty(): Boolean {
    return numberOfElements == 0
  }

  fun isNotEmpty(): Boolean {
    return numberOfElements != 0
  }

  fun contains(element: T): Boolean {
    for (i in 0 until numberOfElements) {
      if (array[i] == element) return true
    }
    return false
  }

  fun getAllUnordered(): Array<T> {
    return Array<Any>(numberOfElements) { index -> array[index] as T } as Array<T>
  }

  override fun equals(other: Any?): Boolean {
    // TODO need to learn how to sort array
    if (other !is MyMinHeap<*> || other.numberOfElements != numberOfElements) return false

    for (i in 0 until numberOfElements) {
      if (other.array[i] != array[i]) return false
    }
    return true
  }

  override fun toString(): String {
    if (numberOfElements == 0) return "[]"

    val builder = StringBuilder().append("[").append(array[0])

    for (i in 1 until numberOfElements) {
      builder.append(", ").append(array[i].toString())
    }

    return builder.append("]").toString()
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 0

    for (i in 0 until numberOfElements) {
      result = result * prime + array[i].hashCode()
    }
    return result
  }


  // -------- Helpers --------

  private fun deleteMin() {
    when {
      numberOfElements == 1 -> deleteMinSmallHeap()
      numberOfElements > 1 -> deleteMinLargeHeap()
    }
  }

  private fun deleteMinSmallHeap() {
    array[0] = null
    numberOfElements--
  }

  private fun deleteMinLargeHeap() {
    val lastIndexNotNull = numberOfElements - 1

    array[0] = array[lastIndexNotNull]
    array[lastIndexNotNull] = null
    numberOfElements--

    moveDownToCorrectPosition(0)
    resizeArrayIfRequired()
  }

  private fun addToArray(element: T) {
    val insertionIndex = numberOfElements

    array[insertionIndex] = element
    numberOfElements++;

    moveUpToCorrectPosition(insertionIndex)
  }

  private fun resizeArrayIfRequired() {
    val minimumRequiredSize = numberOfElements + 2
    val isRequired = array.size <= minimumRequiredSize || array.size > 2 * minimumRequiredSize
    if (isRequired) resizeArray()
  }

  private fun resizeArray() {
    val largerArray: Array<Any?> = if (numberOfElements != 0) arrayOfNulls(numberOfElements * 2) else arrayOfNulls(4)
    System.arraycopy(array, 0, largerArray, 0, numberOfElements)
    array = largerArray
  }

  private fun reorderArrayWithTwoElements() {
    val firstElement = array[0] as T
    val secondElement = array[1] as T

    if (firstElement > secondElement) {
      swapElementsInArray(0, 1)
    }
  }

  private fun reorderArrayWithMoreThanTwoElementsAfterDeletion(index: Int) {
    var currentIndex = index
    var leftIndex = leftIndexOf(currentIndex)
    var rightIndex = rightIndexOf(currentIndex)
    val maxIndex = numberOfElements - 1

    while (leftIndex < maxIndex && rightIndex <= maxIndex) {
      val currentValue = array[currentIndex] as T? ?: break
      val leftValue = array[leftIndex] as T? ?: break
      val rightValue = array[rightIndex] as T?

      if (rightValue == null) {
        if (currentValue > leftValue) {
          swapElementsInArray(currentIndex, leftIndex)
          currentIndex = leftIndex
        }
        else return
      }
      else {
        val lesserChildIndex = if (leftValue <= rightValue) leftIndex else rightIndex
        val lesserChildValue = array[lesserChildIndex]!! as T // Not null as array smallerIndex is either left or right index

        if (currentValue < lesserChildValue) {
          swapElementsInArray(currentIndex, lesserChildIndex)
          currentIndex = lesserChildIndex
        }
        else return
      }
      leftIndex = leftIndexOf(currentIndex)
      rightIndex = rightIndexOf(currentIndex)
    }
  }

  private fun moveDownToCorrectPosition(index: Int) {
    when {
      numberOfElements <= 1 -> return
      numberOfElements == 2 -> reorderArrayWithTwoElements()
      else -> reorderArrayWithMoreThanTwoElementsAfterDeletion(index)
    }
  }

  private fun moveUpToCorrectPosition(index: Int) {
    val value = array[index] as T? ?: return
    var newIndex = index

    while (newIndex > 0) {
      val parentIndex = parentIndexOf(newIndex)
      val parentValue = array[parentIndex] as T? ?: return

      if (parentValue > value) {
        swapElementsInArray(newIndex, parentIndex)
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

  private fun swapElementsInArray(index1: Int, index2: Int) {
    if (index1 >= numberOfElements || index2 >= numberOfElements) throw IndexOutOfBoundsException();

    val value1 = array[index1]
    array[index1] = array[index2]
    array[index2] = value1
  }
}