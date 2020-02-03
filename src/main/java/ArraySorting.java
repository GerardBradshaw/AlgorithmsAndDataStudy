public abstract class ArraySorting {

  // -------- Quick Sort 3 --------

  public static void quickSort3(int[] array) {
    quickSort3(array, 0, array.length - 1);
  }

  private static void quickSort3(int[] array, int left, int right) {
    // Sort around index point
    int index = partition3(array, left, right);
    //System.out.print("Index is " + index + " in: ");
    //printArray(array);

    // Recur sub arrays (if statements deflect base case)
    if (left < index-1) quickSort3(array, left, index-1);
    if (right > index) quickSort3(array, index, right);
  }

  private static int partition3(int[] array, int left, int right) {
    // Select pivot (we'll use middle)
    int pivot = array[left + (right - left) / 2];
    //String message = "\nRunning partition3() with pivot " + pivot + " at i = " + (left + (right - left) / 2) + " between i = " + left + " and " + right + " on ";
    //System.out.print(message);
    //printArray(array);


    // Sort
    // Use equal-to check to ensure something??
    while (left <= right) {
      // Move pointers until they find value on the wrong side of pivot
      //System.out.print("  Left (greater): " + left);
      while (array[left] < pivot) left++;
      //System.out.println(" -> " + left);

      //System.out.print("  Right (lesser): " + right);
      while (array[right] > pivot) right--;
      //System.out.println(" -> " + right);

      // Swap values if left and right haven't passed each other
      // Use equal-to check to prevent infinite loop!
      if (left <= right) {
        swap(array, left, right);
        //System.out.print("Values swapped: ");
        //printArray(array);
        left++;
        right--;
      }
    }
    //System.out.println("Partition ended");
    return left;
  }

  // -------- Quick Sort 2 --------

  public static void quickSort2(int[] array) {
    quickSort2(array, 0, array.length - 1);
  }

  private static void quickSort2(int[] array, int left, int right) {
    int index = partition2(array, left, right);
    if (left < index-1) quickSort2(array, left, index-1);
    if (right > index) quickSort2(array, index, right);
  }

  private static int partition2(int[] array, int left, int right) {
    // Get the pivot value
    int pivotValue = array[left + (right - left) / 2];

    // Loop through and swap values
    while (left <= right) {
      // Make 'left' point to first value greater than pivot from left, 'right' to first lesser from right
      while (array[left] < pivotValue) left++;
      while (array[right] > pivotValue) right--;

      // Swap values at left and right if they haven't passed each other
      if (left <= right) {
        swap(array, left, right);
        left++;
        right--;
      }
    }
    return left;
  }


  // -------- Quick Sort --------

  public static void quickSort(int[] array) {
    quickSort(array, 0, array.length - 1);
  }

  private static void quickSort(int[] array, int left, int right) {
    int index = partition(array, left, right);
    if (left < index - 1) quickSort(array, left, index - 1);
    if (index < right) quickSort(array, index, right);
  }

  private static int partition(int[] array, int left, int right) {
    int pivotValue = (array[left] + array[left + (right - left) / 2] + array[right])/3;

    while (left <= right) {
      while (array[left] < pivotValue) left++;
      while (array[right] > pivotValue) right--;

      if (left <= right) {
        swap(array, left, right);
        left++;
        right--;
      }
    }
    return left;
  }

  private static void swap(int[] array, int index1, int index2) {
    int tempValue = array[index1];
    array[index1] = array[index2];
    array[index2] = tempValue;
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
