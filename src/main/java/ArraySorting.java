public abstract class ArraySorting {

  // -------- Quick Sort 2 --------

  public static int[] quickSort(int[] array) {
    /*
    Steps:
      1. Call the recursive function quickSort(array, left, right). Initially, left is 0 and right is array.length - 1
      2. Call partition(array, left, right) which will reorder the array so that all elements with values less than the
         pivot come before it, and all elements with greater values come after it (equal values go either way). After
         partitioning, the pivot is in its final position.
      3. Recur left - call quickSort() on the array from zero to correctPivotIndex - 1
      4. Recur right - call quickSort() on the array from correctPivotIndex + 1 to the end of the array
      5. Return the array.
     */

    // 1. Call the recursive function quickSort(array, left, right) (quickSort(array) was a public wrapper).
    return quickSort(array, 0, array.length - 1);
  }

  private static int[] quickSort(int[] array, int leftIndex, int rightIndex) {
    /* 2. Call partition(array, left, right) which will reorder the array so that all elements with values less than the
          pivot come before it, and all elements with greater values come after it (equal values go either way). After
          partitioning, the pivot is in its final position.      */
    int partitionIndex = partition(array, leftIndex, rightIndex);

    // Print some stuff for me!
    System.out.print("Left array: ");
    if (partitionIndex - leftIndex >= 0) {
      int[] tempLeftArray = new int[partitionIndex - leftIndex];
      System.arraycopy(array, leftIndex, tempLeftArray, 0, tempLeftArray.length);
      printArray(tempLeftArray);

    } else {
      System.out.println("empty");
    }


    System.out.print("Right array: ");
    if (rightIndex - partitionIndex >= 0) {
      int[] tempRightArray = new int[rightIndex - partitionIndex];
      System.arraycopy(array, partitionIndex + 1, tempRightArray, 0, tempRightArray.length);
      printArray(tempRightArray);

    } else {
      System.out.println("empty");
    }

    // 3. Recur left - call quickSort() on the array from zero to partitionIndex - 1
    if (leftIndex < partitionIndex) array = quickSort(array, leftIndex, partitionIndex - 1);

    // 4. Recur right - call quickSort() on the array from partitionIndex + 1 to the end of the array
    if (rightIndex >= partitionIndex) array = quickSort(array, partitionIndex + 1, rightIndex);

    // 5. Return the array.
    return array;
  }

  private static int partition(int[] array, int leftIndex, int rightIndex) {
    /*
    The steps are:
      1) Move the pivot to the last given index (right) of the array
      2) From a given left index of the array, move a pointer right until you find a value greater than the pivot
      3) From a given right index of the array, move a pointer left until you find a value less than the pivot
      4) If the pointers haven't passed each other, swap the values they point to and update their counts
      5) Repeat steps 2 to 4 until the pointers pass each other
      6) Swap the value at the pointer from the left with the pivot value. The pivot is now in its final position!
      7) Return the new index of the pivot.
     */
    int[] tempArray = new int[rightIndex - leftIndex + 1];
    System.arraycopy(array, leftIndex, tempArray, 0, tempArray.length);
    System.out.println("------------------------------------------");
    System.out.print("partition() called on: ");
    printArray(tempArray);
    System.out.println("leftIndex = " + leftIndex + ", rightIndex = " + rightIndex);
    if (rightIndex-leftIndex < 1) {
      System.out.println("The array is empty! Exiting partition function.");
      return 0;
    }
    int rightPointer = rightIndex;
    int leftPointer = leftIndex;
    int pivotIndex = leftIndex + (rightIndex - leftIndex) / 2;
    int pivotValue = array[pivotIndex];
    System.out.println("Selected pivot value " + pivotValue + " at index " + pivotIndex);

    // 1) Move the pivot to the last given index (right) of the array
    array[pivotIndex] = array[rightIndex];
    array[rightIndex] = pivotValue;
    rightPointer--;

    // 5) Repeat steps 2 to 4 until the pointers pass each other
    System.out.println("\nStarting pointer walk...");
    while (leftPointer < rightPointer) {
      // 2) From a given left index of the array, move a pointer right until you find a value greater than the pivot
      System.out.print("leftPointer: " + leftPointer);
      while (array[leftPointer] < pivotValue) {
        leftPointer++;
      }
      System.out.println(" -> " + leftPointer);

      // 3) From a given right index of the array, move a pointer left until you find a value less than the pivot
      System.out.print("rightPointer: " + rightPointer);
      while (array[rightPointer] > pivotValue) {
        rightPointer--;
      }
      System.out.println(" -> " + rightPointer);

      // 4) If the pointers haven't passed each other, swap the values they point to and update their counts
      if (leftPointer <= rightPointer) {
        int leftValue = array[leftPointer];
        array[leftPointer] = array[rightPointer];
        array[rightPointer] = leftValue;
        leftPointer++;
        rightPointer--;
        System.out.println("The pointers met or passed and have been updated! leftPointer = " + leftPointer + ", rightPointer = " + rightPointer);
      }
    }
    // 6) Swap the value at the pointer from the left with the pivot value. The pivot is now in its final position!
    array[rightIndex] = array[leftPointer];
    array[leftPointer] = pivotValue;
    System.out.print("Pointer walk finished. Array is now more sorted: ");
    System.arraycopy(array, leftIndex, tempArray, 0, tempArray.length);
    printArray(tempArray);
    System.out.println("The pivot (" + pivotValue + ") is at index " + leftPointer + "\n");

    System.out.println("partition() finished");
    System.out.println("------------------------------------------");

    // 7) Return the new index of the pivot.
    return leftPointer;
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
