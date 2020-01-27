public abstract class ArraySorting {

  // -------- Sorting --------

  private static int[] mergeSortArray(int[] array) {
    return mergeSortRecur(array, new int[array.length], 0, array.length - 1);
  }

  private static int[] mergeSortRecur(int[] array, int[] tempArray, int low, int high) {
    if (low >= high) {
      return array;
    }

    int mid = (low + high) / 2;
    mergeSortRecur(array, tempArray, low, mid);
    mergeSortRecur(array, tempArray, mid + 1, high);
    mergeHalvesG(low, mid, high);
    return mergeHalves(array, tempArray, low, high);
  }

  private static void mergeHalvesG(int low, int mid, int high) {

  }

  private static int[] mergeHalves(int[] array, int[] tempArray, int leftStart, int rightEnd) {
    int leftEnd = (rightEnd + leftStart) / 2;
    int rightStart = leftEnd + 1;
    int size = rightEnd - leftStart + 1;

    int left = leftStart;
    int right = rightStart;
    int index = leftStart;

    while (left <= leftEnd && right < rightEnd) {
      if (array[left] <= array[right]) {
        tempArray[index] = array[left];
        left++;

      } else {
        tempArray[index] = array[right];
        right++;
      }
      index++;

    }

    System.arraycopy(array, left, tempArray, index, leftEnd - left + 1);
    System.arraycopy(array, right, tempArray, index, rightEnd - right + 1);
    //System.arraycopy(tempArray, leftStart, array, leftStart, size);
    return tempArray;

  }


  // -------- Searching --------

  private int binarySearchForIndex(int[] array, int i) {
    return -1;
  }


  // -------- Helpers --------

  private static void printArray(int[] array) {
    System.out.print("[");

    for (int i = 0; i < array.length; i++) {
      if (i != array.length - 1) {
        System.out.print(array[i] + ", ");
      } else {
        System.out.print(array[i]);
      }
    }
    System.out.println("]");
  }
}
