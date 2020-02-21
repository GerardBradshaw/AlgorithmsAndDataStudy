package heap;

public class MyHeap {

  // ---------------- Member variables ----------------

  private int capacity = 10;
  private int size = 0;
  private int[] data;


  // ---------------- Constructors ----------------

  public MyHeap(int[] data) {
    capacity = data.length;
    this.data = new int[capacity];
    for (int i : data)
      insert(i);
  }

  public MyHeap() {
    data = new int[capacity];
  }


  // ---------------- Public methods ----------------

  public int findMax() {
    if (size > 0)
      return data[0];

    else throw new NullPointerException();
  }

  public void insert(int i) {
    // Resize array if required
    if (data.length == size)
      resizeArray();

    // Insert the number in the first available spot in the array
    data[size] = i;
    size++;

    // Move the number to the correct position
    moveUpToCorrectIndex(size - 1);
  }

  public int popMax() {
    if (size <= 0)
      throw new NullPointerException();

    int max = data[0];
    deleteMax();
    return max;
  }

  public void deleteMax() {
    if (size <= 0)
      throw new NullPointerException();

    // Overwrite the max number (index 0) with the last inserted number and delete the duplicate
    int lastInsertedIndex = size - 1;
    data[0] = data[lastInsertedIndex];
    data[lastInsertedIndex] = 0;

    // Reduce size of array
    size --;

    // Move head down to correct index
    moveDownToCorrectIndex(0);
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }


  // ---------------- Object callbacks ----------------

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder().append("[");

    for (int i = 0; i < size - 1; i++)
      builder.append(data[i]).append(", ");

    builder.append(data[size - 1]).append("]");
    return builder.toString();
  }


  // ---------------- Helpers ----------------

  private int leftChildIndex(int parentIndex) {
    return 2 * parentIndex + 1;
  }

  private int rightChildIndex(int parentIndex) {
    return 2 * parentIndex + 2;
  }

  private int parentIndex(int childIndex) {
    if (childIndex % 2 != 0)
      return (childIndex - 1) / 2;
    else
      return (childIndex - 2) / 2;
  }

  private void resizeArray() {
    int[] newArray = new int[size * 2];
    System.arraycopy(data, 0, newArray, 0, size);
    data = newArray;
  }

  private void moveUpToCorrectIndex(int startingIndex) {
    int currentIndex = startingIndex;
    int marker = data[currentIndex];

    // Loop while the number is larger than its parent
    while (currentIndex != 0 && data[currentIndex] > data[parentIndex(currentIndex)]) {
      int parentIndex = parentIndex(currentIndex);
      // Swap the larger child with its smaller parent
      swap(currentIndex, parentIndex);

      // The child is now at the index its parent was
      currentIndex = parentIndex;
    }
  }

  private void moveDownToCorrectIndex(int startingIndex) {
    // Setup variables
    int currentIndex = startingIndex;
    int leftIndex = leftChildIndex(currentIndex);
    int rightIndex = rightChildIndex(currentIndex);

    // Loop until we reach the last filled index
    while (leftIndex <= size - 1 && rightIndex <= size - 1) {

      // If left side larger...
      if (data[currentIndex] < data[leftIndex]) {

        // ... and right side larger
        if (data[currentIndex] < data[rightIndex]) {

          // If left is larger than right...
          if (data[leftIndex] > data[rightIndex])
            swap(currentIndex, leftIndex);

          // ... else the right is larger than left (or equal)
          else
            swap(currentIndex, rightIndex);
        }

        // Else if right side larger (but left side not)
      } else if (data[currentIndex] < data[rightIndex])
        swap(currentIndex, rightIndex);

      // Else it's the same as left and right so do nothing
      else
        return;

      // Update indices
      currentIndex++;
      leftIndex = leftChildIndex(currentIndex);
      rightIndex = rightChildIndex(currentIndex);
    }
  }

  private void swap(int index1, int index2) {
    int value1 = data[index1];
    data[index1] = data[index2];
    data[index2] = value1;
  }
}
