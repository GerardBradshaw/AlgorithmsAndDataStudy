import array.JArraySorting;
import hashtable.MyJHashTable;
import hashtable.MyJHashTable2;
import map.Word;
import linkedlist.MyJDoublyLinkedList;
import questions.S08RecursionAndDynamicProgramming;
import tree.XMyBinarySearchTree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HelloWorld {

  public static void main(String[] args) {

    S08RecursionAndDynamicProgramming questions = new S08RecursionAndDynamicProgramming();

    S08RecursionAndDynamicProgramming.Box box1 = new S08RecursionAndDynamicProgramming.Box(1,1,1);
    S08RecursionAndDynamicProgramming.Box box2 = new S08RecursionAndDynamicProgramming.Box(20,20,2);
    S08RecursionAndDynamicProgramming.Box box3 = new S08RecursionAndDynamicProgramming.Box(30,30,3);
    S08RecursionAndDynamicProgramming.Box box4 = new S08RecursionAndDynamicProgramming.Box(40,40,4);
    S08RecursionAndDynamicProgramming.Box box5 = new S08RecursionAndDynamicProgramming.Box(100,25,10);

    S08RecursionAndDynamicProgramming.Box[] boxes = new S08RecursionAndDynamicProgramming.Box[] {box1, box2, box3,box4, box5};

    System.out.println(questions.stackOfBoxes(boxes));

  }

  // -------- Arrays --------

  private static void testQuickSortArray() {
    boolean isAllSorted = true;
    boolean isAllSumPreserved = true;
    Random random = new Random();

    for (int i = 0; i < 1; i++) {
      int[] array = createRandIntArray(random.nextInt(10) + 2, 10);
      int arraySum = sumArray(array);
      JArraySorting.quickSort3(array);

      if (arraySum != sumArray(array)) isAllSumPreserved = false;
      if (!isSorted(array)) isAllSorted = false;
    }

    System.out.println("All sorted: " + isAllSorted);
    System.out.println("Sum preserved: "+ isAllSumPreserved);
  }

  private static int[] createRandIntArray(int size, int maxData) {
    if (size <= 0) {
      return null;
    }

    int[] array = new int[size];
    Random rand  = new Random();

    if (maxData < 0) {
      for (int i = 0; i < size; i++) {
        array[i] = rand.nextInt();
      }

    } else {
      for (int i = 0; i < size; i++) {
        array[i] = rand.nextInt(maxData);
      }
    }
    return array;
  }

  private static boolean isSorted(int[] array) {

    for (int i = 0; i < array.length - 2; i++) {
      if (array[i] <= array[i + 1]) continue;
      return false;
    }
    if (array[array.length - 2] <= array[array.length - 1]) return true;
    return false;
  }

  private static int sumArray(int[] array) {
    int sum = 0;
    for (int i : array) {
      sum += i;
    }
    return sum;
  }

  // -------- LinkedList --------

  private static MyJDoublyLinkedList<Integer> createRandLinkedList(int size, int maxData) {
    if (size <= 0) {
      return null;
    }

    Integer[] ints = new Integer[size];
    Random rand = new Random();

    if (maxData < 0) {
      for (int i = 0; i < size; i++) {
        ints[i] = rand.nextInt();
      }

    } else {
      for (int i = 0; i < size; i++) {
        ints[i] = rand.nextInt(maxData);
      }
    }

    return new MyJDoublyLinkedList<>(ints);
  }

  private static MyJDoublyLinkedList<Integer> createRandLinkedList(int size) {
    return createRandLinkedList(size, -1);
  }

  private static void detectLoop() {
    MyJDoublyLinkedList<Integer> list = new MyJDoublyLinkedList<>(0,1,2,3,4,5,6,7,8,9);
    System.out.println(list);

    int replacementIndex = 9;

    for (int i = 0; i <= 9; i++) {
      MyJDoublyLinkedList.Node<Integer> loopStartNode = list.getNodeAtIndex(i);
      MyJDoublyLinkedList.Node<Integer> replacementNode = new MyJDoublyLinkedList.Node<>(9);
      MyJDoublyLinkedList.Node<Integer> replacedNode = list.getNodeAtIndex(replacementIndex);

      replacementNode.setNext(loopStartNode);
      replacementNode.setPrev(replacedNode.getPrev());

      if (replacedNode.equals(loopStartNode)) loopStartNode.setNext(replacementNode);
      else replacedNode.getPrev().setNext(replacementNode);

      System.out.println("Loop size (from " + list.getAtIndex(i) + " to end): " + list.detectLoopSizeWithDictionary());
    }
  }

  private static void createTree() {
    XMyBinarySearchTree myTree = new XMyBinarySearchTree();

    myTree.add(6);
    myTree.add(99);
    myTree.add(0);
    myTree.add(4);
    myTree.add(7);
    myTree.add(-4);
    myTree.add(88);
    myTree.add(1);

    myTree.printTree();

  }

  private static void writeToFile(String fileName, List<String> data) {
    try  {
      PrintWriter writer = new PrintWriter(fileName, "UTF-8");

      for (String s : data) {
        writer.println(s);
      }

      writer.close();
      System.out.println("done!");

    } catch (FileNotFoundException e) {
      System.out.println("failed. File not found.");

    } catch (UnsupportedEncodingException e) {
      System.out.println("failed. Unsupported encoding type.");
    }
  }

  private static void outputLinkedListEfficiency() {
    // Parameters
    int numberOfDataPoints = 200;
    int maxListSize = 5000000;

    List<String> timeList = new ArrayList<>();
    timeList.add("MapSize,AddToStartTime,GetAtEndTime,RemoveFromEndTime,FindRandomValueTime,RandomValueFound");
    System.out.print("Running...");

    for (int i = 2; i <= maxListSize; i = i + (maxListSize / numberOfDataPoints)) {
      String timeListString = Integer.toString(i);

      // Created using addAtEnd
      long startTimeNs = System.nanoTime();
      MyJDoublyLinkedList<Integer> list = createRandLinkedList(i);
      long endTimeNs = System.nanoTime();
      long timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      // getFromEnd
      assert list != null;
      startTimeNs = System.nanoTime();
      list.getLast();
      endTimeNs = System.nanoTime();
      timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      // removeFromEnd
      startTimeNs = System.nanoTime();
      list.removeLast();
      endTimeNs = System.nanoTime();
      timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      // contains
      startTimeNs = System.nanoTime();
      int randomInt = new Random().nextInt();
      boolean found = list.contains(randomInt);
      endTimeNs = System.nanoTime();
      timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs + "," + found;

      timeList.add(timeListString);
    }

    System.out.println("done!");

    System.out.print("Writing to file...");
    writeToFile("LinkedListEfficiency.txt", timeList);
  }


  // -------- HashTables --------

  private static MyJHashTable<Integer, Integer> createRandomHashTable(int size, int entries) {
    if (size <= 0) return null;

    Random rand = new Random();
    MyJHashTable<Integer, Integer> table = new MyJHashTable<>(size);

    for (int i = 0; i < entries; i++) table.insert(rand.nextInt(), rand.nextInt());

    return table;
  }

  private static void outputHashTableEfficiency(int numberOfTables, int size) {
    List<String> timeList = new ArrayList<>();
    timeList.add("Table Size, insert() time, get() time, remove() time, contains() time, contains() result");
    System.out.print("Running...");

    for (int currentSize = 2; currentSize <= size; currentSize = currentSize + (size / numberOfTables)) {
      String timeListString = Integer.toString(currentSize);

      // Created using addAtEnd
      long startTimeNs = System.nanoTime();
      MyJHashTable<Integer, Integer> table = createRandomHashTable(currentSize,currentSize);
      long endTimeNs = System.nanoTime();
      long timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      if (table != null) {
        // First, get a valid key from the table
        int randomKey;
        if (table.getRandomKey() == null) randomKey = 0;
        else randomKey = table.getRandomKey();

        // get()
        startTimeNs = System.nanoTime();
        table.get(randomKey);
        endTimeNs = System.nanoTime();
        timeElapsedNs = (endTimeNs - startTimeNs);

        timeListString += "," + timeElapsedNs;


        // remove()
        startTimeNs = System.nanoTime();
        table.remove(randomKey);
        endTimeNs = System.nanoTime();
        timeElapsedNs = (endTimeNs - startTimeNs);

        timeListString += "," + timeElapsedNs;


        // contains()
        startTimeNs = System.nanoTime();
        int randomInt = new Random().nextInt();
        boolean found = table.contains(randomInt);
        endTimeNs = System.nanoTime();
        timeElapsedNs = (endTimeNs - startTimeNs);

        timeListString += "," + timeElapsedNs + "," + found;

        timeList.add(timeListString);
      }
    }

    System.out.println("done!");

    System.out.print("Writing to file...");
    writeToFile("HashTableEfficiency.csv", timeList);
  }

  private static void showDistributionWhenUsingDoubleHash() {
    String[] array = new String[]{
        "100", "510", "170", "214", "268", "398", "235", "802", "900", "723",
        "699", "1", "16", "999", "890", "725", "998", "978", "988", "990",
        "989", "984", "320", "321", "400", "415", "450", "50", "660", "624"};

    MyJHashTable2 hashTable = new MyJHashTable2(60);

    hashTable.addToArrayUsingModHash(array);
    System.out.println(hashTable.toString());

    hashTable = new MyJHashTable2(60);
    hashTable.addToArrayUsingDoubModHash(array);
    System.out.println(hashTable.toString());

  }

  private static Word[] getWords() {
    return new Word[]{
        new Word("pin", "a sharp thing"),
        new Word("use", "to use"),
        new Word("bad", "not good"),
        new Word("eat", "to consume"),
        new Word("nap", "a mini-sleep"),
        new Word("ask", "to inquire"),
        new Word("joy", "happiness"),
        new Word("far", "not close"),
        new Word("top", "the highest point of something"),
        new Word("tin", "a metal container")};
  }

  private static Word[] getMoreWords() {
    return new Word[] {
        new Word("cat", "a feline"),
        new Word("bow", "a fancy knot"),
        new Word("gun", "pew pew"),
        new Word("car", "a personal motor vehicle"),
        new Word("war", "what is it good for?"),
        new Word("oak", "a type of tree"),
        new Word("dry", "not wet"),
        new Word("pot", "a vessel for cooking"),
        new Word("bad", "a soft place for sleeping"),
        new Word("tog", "bathers")};
  }

}
