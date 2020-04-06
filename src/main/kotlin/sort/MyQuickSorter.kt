package sort

class MyQuickSorter {

  fun sort(array: IntArray): IntArray {
    val result = IntArray(array.size)
    System.arraycopy(array, 0, result, 0, array.size)
    quickSort(0, array.size - 1, result)
    return result
  }

  private fun quickSort(lowIndex: Int, highIndex: Int, array: IntArray) {
    if (lowIndex < highIndex) {
      val index = partition(lowIndex, highIndex, array)
      quickSort(lowIndex, index - 1, array)
      quickSort(index + 1, highIndex, array)
    }
  }

  private fun partition(lowIndex: Int, highIndex: Int, array: IntArray): Int {
    val pivotValue = array[lowIndex]

    var lowPointer = lowIndex
    var highPointer = highIndex

    while (lowPointer < highPointer) {
      lowPointer = getIndexOfNextValueLargerThanPivot(lowPointer, highIndex, pivotValue, array)
      highPointer = getIndexOfNextValueSmallerThanPivot(highPointer, lowIndex, pivotValue, array)

      if (lowPointer < highPointer) swap(lowPointer, highPointer, array)
    }
    swap(lowIndex, highPointer, array)
    return highPointer
  }

  private fun swap(index1: Int, index2: Int, array: IntArray) {
    val value1 = array[index1]
    array[index1] = array[index2]
    array[index2] = value1
  }

  private fun getIndexOfNextValueLargerThanPivot(lowPointer: Int, highIndex: Int, pivotValue: Int, array: IntArray): Int {
    var result = lowPointer
    while (result < highIndex) {
      if (array[result] > pivotValue) break else result++
    }
    return result
  }

  private fun getIndexOfNextValueSmallerThanPivot(highPointer: Int, lowIndex: Int, pivotValue: Int, array: IntArray): Int {
    var result = highPointer
    while (result >= lowIndex) {
      if (array[result] <= pivotValue) break else result--
    }
    return result
  }

}