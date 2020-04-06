package sort

class MyMergeSorter {

  fun sort(array: IntArray): IntArray {
    val endPointer = array.size - 1
    val startPointer = 0
    val result = IntArray(array.size)

    System.arraycopy(array, 0, result, 0, array.size)

    mergeSortRecur(startPointer, endPointer, result)
    return result
  }

  private fun mergeSortRecur(lowIndex: Int, highIndex: Int, array: IntArray) {
    if (lowIndex < highIndex) {
      val midIndex = lowIndex / 2 + highIndex / 2

      mergeSortRecur(lowIndex, midIndex, array)
      mergeSortRecur(midIndex + 1, highIndex, array)
      mergeSubArrays(lowIndex, highIndex, array)
    }
  }

  private fun mergeSubArrays(lowIndex: Int, highIndex: Int, array: IntArray) {
    val midIndex = lowIndex / 2 + highIndex / 2
    val resultSize = highIndex - lowIndex + 1
    val result = IntArray(resultSize)

    var array1Pointer = lowIndex
    var array2Pointer = midIndex + 1
    var resultPointer = 0

    while (array1Pointer <= midIndex && array2Pointer <= highIndex) {
      val value1 = array[array1Pointer]
      val value2 = array[array2Pointer]
      result[resultPointer++] = if (value1 <= value2) array[array1Pointer++] else array[array2Pointer++]
    }

    while (array1Pointer <= midIndex) result[resultPointer++] = array[array1Pointer++]
    while (array2Pointer <= highIndex) result[resultPointer++] = array[array2Pointer++]
    for (i in 0 until resultSize) array[i + lowIndex] = result[i]
  }

  fun mergeArrays(array1: IntArray, array2: IntArray): IntArray {
    val resultSize = array1.size + array2.size
    val result = IntArray(resultSize)

    var array1Pointer = 0
    var array2Pointer = 0
    var resultPointer = 0

    while (array1Pointer < array1.size && array2Pointer < array2.size) {
      val value1 = array1[array1Pointer]
      val value2 = array2[array2Pointer]
      result[resultPointer++] = if (value1 <= value2) array1[array1Pointer++] else array2[array2Pointer++]
    }

    while (array1Pointer < array1.size) result[resultPointer++] = array1[array1Pointer++]
    while (array2Pointer < array2.size) result[resultPointer++] = array2[array2Pointer++]
    return result
  }
}