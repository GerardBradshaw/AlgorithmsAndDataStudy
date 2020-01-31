public abstract class ArraySorting {

  // -------- Quick Sort --------

  public static void quickSort(int[] array) {
    quickSort(array, 0, array.length - 1);
  }

  private static void quickSort(int[] array, int left, int right) {
    int index = partition(array, left, right);
    if (left < index - 1) quickSort(array, left, index - 1);
    if (index < right) quickSort(array, index, right);
  }

  private static int partition(int[] array, int leftIndex, int rightIndex) {
    int pivotValue = (array[leftIndex] + array[leftIndex + (rightIndex - leftIndex) / 2] + array[rightIndex])/3;
    int leftPointer = leftIndex;
    int rightPointer = rightIndex;

    while (leftPointer <= rightPointer) {
      while (array[leftPointer] < pivotValue) leftPointer++;
      while (array[rightPointer] > pivotValue) rightPointer--;

      if (leftPointer <= rightPointer) {
        swap(array, leftPointer, rightPointer);
        leftPointer++;
        rightPointer--;
      }
    }
    return leftPointer;
  }

  private static void swap(int[] array, int leftIndex, int rightIndex) {
    int tempValue = array[leftIndex];
    array[leftIndex] = array[rightIndex];
    array[rightIndex] = tempValue;
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
