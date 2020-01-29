public abstract class ArraySorting {


  // -------- Quick Sort --------

  public static int[] quickSort(int[] array) {
    /*
    Steps:
      1. Call the recursive function quickSort(array, left, right). Initially, left is 0 and right is array.length - 1
      2. Call partition(array, left, right) which will re-arrange the array such that all values less than the pivot
         are to its left, and all values greater are to its right. It will return the index of the pivot which will be
         in its final, correct (sorted) position. The steps to partition are:
            a) Move the pivot to the end (right) of the array
            b) From the start of the array, move a pointer right until you find a value greater than the pivot
            c) From the end of the array, move a pointer left until you find a value less than the pivot
            d) If the pointers haven't passed each other, swap the values found in b) and c)
            e) Swap the pivot value and the value at pointer from the left. The pivot is now in its final position!
            f) Return the new (final) index of the pivot.
      3. Recur left - call quickSort() on the array from zero to correctPivotIndex - 1
      4. Recur right - call quickSort() on the array from correctPivotIndex + 1 to the end of the array
      5. Return the array.
     */

    // 1. Call the recursive function quickSort(array, left, right) (quickSort(array) was a public wrapper).
    return quickSort(array, 0, array.length - 1);
  }

  private static int[] quickSort(int[] array, int leftIndex, int rightIndex) {
    /* 2. Call partition(array, left, right) which will re-arrange the array such that all values less than the pivot
          are to its left, and all values greater are to its right. It will return the index of the pivot which will be
          in its final, correct (sorted) position.      */
    int partitionIndex = partition(array, leftIndex, rightIndex);

    // 3. Recur left - call quickSort() on the array from zero to partitionIndex - 1
    if (leftIndex < partitionIndex) {
      array = quickSort(array, leftIndex, partitionIndex - 1);
    }

    // 4. Recur right - call quickSort() on the array from partitionIndex + 1 to the end of the array
    if (rightIndex > partitionIndex) {
      array = quickSort(array, partitionIndex + 1, rightIndex);
    }

    // 5. Return the array.
    return array;
  }

  private static int partition(int[] array, int leftIndex, int rightIndex) {
    int originalRightIndex = rightIndex;
    int pivotIndex = leftIndex + (rightIndex - leftIndex) / 2;
    int pivotValue = array[pivotIndex];

    // a) Move the pivot to the end (right) of the array
    array[pivotIndex] = array[rightIndex];
    array[rightIndex] = pivotValue;

    while (leftIndex <= rightIndex) {
      // b) From the start of the array, move a pointer right until you find a value greater than the pivot
      while (array[leftIndex] < pivotValue) {
        leftIndex++;
      }

      // c) From the end of the array, move a pointer left until you find a value less than the pivot
      while (array[rightIndex] > pivotValue) {
        rightIndex--;
      }

      // d) If the pointers haven't passed each other, swap the values found in b) and c)
      if (leftIndex <= rightIndex) {
        int leftValue = array[leftIndex];
        array[leftIndex] = array[rightIndex];
        array[rightIndex] = leftValue;
        leftIndex++;
        rightIndex--;
      }
    }
    // e) Swap the pivot value and the value at pointer from the left. The pivot is now in its final position!
    array[originalRightIndex] = array[leftIndex];
    array[leftIndex] = pivotValue;

    // f) Return the new (final) index of the pivot.
    return leftIndex;
  }



  // -------- Merge Sort --------

  public static int[] mergeSort(int[] array) {
    // Base case
    if (array.length <= 1) {
      return array;
    }

    // Get the midpoint
    int midpoint = array.length / 2;

    // Preallocate arrays
    int[] leftArray = new int[midpoint];
    int[] rightArray = new int[array.length - midpoint];

    // Split the array in half
    System.arraycopy(array, 0, leftArray, 0, midpoint);
    System.arraycopy(array, midpoint, rightArray, 0, array.length - midpoint);

    // Recursively sort the array
    leftArray = mergeSort(leftArray);
    rightArray = mergeSort(rightArray);

    // Merge the sorted arrays (starts by sorting arrays of size 1, then 2, then 3....)
    return merge(leftArray, rightArray);
  }

  private static int[] merge(int[] left, int[] right) {
    // Preallocate memory
    int[] result = new int[left.length + right.length];

    // Set up pointers
    int leftPointer = 0, rightPointer = 0, resultPointer = 0;

    // While there are still unsorted numbers....
    while (leftPointer < left.length || rightPointer < right.length) {
      // If both pointers are valid
      if (leftPointer < left.length && rightPointer < right.length) {

        // If the number in the first array is smaller than the right, add it to result and increase its pointer
        if (left[leftPointer] < right[rightPointer]) {
          result[resultPointer] = left[leftPointer];
          leftPointer++;

          // Otherwise the number in the second array is smaller, so add it to the result and advange its pointer
        } else {
          result[resultPointer] = right[rightPointer];
          rightPointer++;
        }

        // Else if just left pointer is valid (right used up)
      } else if (leftPointer < left.length) {
        result[resultPointer] = left[leftPointer];
        leftPointer++;

        // Else if just right pointer is valid (left used up)
      } else if (rightPointer < right.length) {
        result[resultPointer] = right[rightPointer];
        rightPointer++;
      }
      resultPointer++;
    }
    return result;
  }


  // -------- Helpers --------

  public static void printArray(int[] array) {
    System.out.print("[");

    for (int i = 0; i < array.length; i++) {
      if (i != array.length - 1) {
        System.out.print(array[i] + " ");
      } else {
        System.out.print(array[i]);
      }
    }
    System.out.println("]");
  }
}
