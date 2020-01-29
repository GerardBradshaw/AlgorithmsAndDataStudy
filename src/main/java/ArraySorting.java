public abstract class ArraySorting {


  // -------- Quick Sort --------

  public static int[] quickSort(int[] array) {
    return quickSort(array, 0, array.length-1);
  }

  private static int[] quickSort(int[] array, int leftIndex, int rightIndex) {
    int index = partition(array, leftIndex, rightIndex);
    if (leftIndex < index - 1) {
      array = quickSort(array, leftIndex, index - 1);
    }
    if (index < rightIndex) {
      array = quickSort(array, index, rightIndex);
    }
    return array;
  }

  private static int partition(int[] array, int leftIndex, int rightIndex) {
    // Pick the pivot value (I'll use the midpoint)
    int pivotValue = array[leftIndex + (rightIndex - leftIndex) / 2];

    while (leftIndex <= rightIndex) {
      // Find the element on the left that should be on the right
      while (array[leftIndex] < pivotValue) {
        leftIndex++;
      }

      // Find the element on the right that should be on the left
      while (array[rightIndex] > pivotValue) {
        rightIndex--;
      }

      // swap elements and move the indices
      if (leftIndex <= rightIndex) {
        swapElements(array, leftIndex, rightIndex);
        leftIndex++;
        rightIndex--;
      }
    }
    return leftIndex;
  }

  private static int[] swapElements(int[] array, int firstIndex, int secondIndex) {
    if (array.length < firstIndex || array.length < secondIndex || firstIndex < 0 || secondIndex <0) {
      throw new IndexOutOfBoundsException();
    }
    int firstValue = array[firstIndex];
    array[firstIndex] = array[secondIndex];
    array[secondIndex] = firstValue;
    return array;
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
