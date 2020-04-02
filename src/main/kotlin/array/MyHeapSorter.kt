package array

import java.lang.IndexOutOfBoundsException

class MyHeapSorter {

  fun sort(array: IntArray): IntArray {
    val heap = constructHeap(array)
    sortHeap(heap)
    return heap
  }

  private fun constructHeap(array: IntArray): IntArray {
    val result = IntArray(array.size)
    var nextFreeIndex = 0

    for (int in array) {
      result[nextFreeIndex] = int
      moveLastNumberUp(nextFreeIndex, result)
      nextFreeIndex++
    }
    return result
  }

  private fun moveLastNumberUp(lastIndex: Int, heap: IntArray) {
    var currentIndex = lastIndex
    val currentValue = heap[currentIndex]
    var parentIndex = getParentIndex(currentIndex)

    while (parentIndex >= 0) {
      val parentValue = heap[parentIndex]

      if (parentValue > currentValue) {
        swap(parentIndex, currentIndex, heap)
        currentIndex = parentIndex
        parentIndex = getParentIndex(currentIndex)
      }
      else break
    }
  }

  private fun moveFirstNumberDown(lastIndex: Int, array: IntArray) {
    var currentIndex = 0
    val currentValue = array[currentIndex]
    var leftIndex = getLeftIndex(currentIndex)

    while (leftIndex <= lastIndex) {
      val smallerIndex = getIndexOfSmallerChild(currentIndex, lastIndex, array)

      if (smallerIndex != null) {
        val smallerChildValue = array[smallerIndex]

        if (smallerChildValue < currentValue) {
          swap(smallerIndex, currentIndex, array)
          currentIndex = smallerIndex
          leftIndex = getLeftIndex(currentIndex)
        }
        else return
      }
      else return
    }
  }

  private fun sortHeap(heap: IntArray) {
    val result = IntArray(heap.size)
    var currentInsertionIndex = 0
    val maxIndex = heap.size - 1
    var lastIndex = maxIndex

    while (currentInsertionIndex <= maxIndex) {
      result[currentInsertionIndex] = heap[0]

      //heap[0] = heap[lastIndex]
      swap(0, lastIndex, heap)

      currentInsertionIndex++
      lastIndex--

      moveFirstNumberDown(lastIndex, heap)
    }
  }

  private fun swap(index1: Int, index2: Int, array: IntArray) {
    val maxIndex = array.size -1
    if (index1 > maxIndex || index2 > maxIndex) throw IndexOutOfBoundsException()

    val value1 = array[index1]
    array[index1] = array[index2]
    array[index2] = value1
  }

  private fun getParentIndex(index: Int): Int {
    return if (index % 2 != 0) ((index - 1) / 2) else ((index - 2) / 2)
  }

  private fun getLeftIndex(index: Int): Int {
    return 2 * index + 1;
  }

  private fun getRightIndex(index: Int): Int {
    return 2 * index + 2
  }

  private fun getIndexOfSmallerChild(parent: Int, maxIndex: Int, array: IntArray): Int? {
    val leftIndex = getLeftIndex(parent)
    val rightIndex = getRightIndex(parent)

    if (leftIndex <= maxIndex) {
      val leftValue = array[leftIndex]
      val rightValue = if (rightIndex <= maxIndex) array[rightIndex] else null

      return if (rightValue != null) {
        when {
          leftValue < rightValue -> leftIndex
          rightValue < leftValue -> rightIndex
          rightValue == leftValue -> leftIndex
          else -> null
        }
      } else leftIndex
    }
    return null
  }

}