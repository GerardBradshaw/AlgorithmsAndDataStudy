package sort

object MyQuickSorter2 {
  fun sort(array: IntArray) {
    quickSort(array, 0, array.size - 1)
  }

  private fun quickSort(array: IntArray, leftIndex: Int, rightIndex: Int) {
    val index = partition(array, leftIndex, rightIndex)

    if (leftIndex < index - 1) quickSort(array, leftIndex, index - 1)
    if (rightIndex > index) quickSort(array, index, rightIndex)
  }

  private fun partition(array: IntArray, leftIndex: Int, rightIndex: Int): Int {
    val mid = leftIndex + (rightIndex - leftIndex) / 2
    val pivot = array[mid]

    var left = leftIndex
    var right = rightIndex

    while (left <= right) {
      while (left <= rightIndex && array[left] < pivot) left++
      while (right >= leftIndex && array[right] > pivot) right--

      if (left <= right) {
        swap(array, left, right)
        left++
        right--
      }
    }
    return left
  }

  private fun swap(array: IntArray, i: Int, j: Int) {
    val value1 = array[i]
    array[i] = array[j]
    array[j] = value1
  }
}