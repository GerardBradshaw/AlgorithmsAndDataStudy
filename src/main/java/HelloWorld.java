import array.ArraySorting;
import hashtable.MyHashTable;
import linkedlist.MyDoubleLinkedList;
import tree.Node;
import tree.XMyBinarySearchTree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HelloWorld {

  public static void main(String[] args) {

    MyHashTable<Integer, String> hashTable = new MyHashTable<>(5);

    hashTable.insert(1, "Gerard");
    hashTable.insert(3, "Bae");
    hashTable.insert(7, "Mr. O");
    hashTable.insert(5, "Lewie");
    hashTable.insert(567, "Cuddles");

    String test = hashTable.get(55);
    System.out.println(test);

  }

  // -------- Arrays --------

  private static void testQuickSortArray() {
    boolean isAllSorted = true;
    boolean isAllSumPreserved = true;
    Random random = new Random();

    for (int i = 0; i < 1; i++) {
      int[] array = createRandIntArray(random.nextInt(10) + 2, 10);
      int arraySum = sumArray(array);
      ArraySorting.quickSort3(array);

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

  private static MyDoubleLinkedList<Integer> createRandLinkedList(int size, int maxData) {
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

    return new MyDoubleLinkedList<>(ints);
  }

  private static MyDoubleLinkedList<Integer> createRandLinkedList(int size) {
    return createRandLinkedList(size, -1);
  }

  private static void detectLoop() {
    MyDoubleLinkedList<Integer> list = new MyDoubleLinkedList<>(0,1,2,3,4,5,6,7,8,9);
    System.out.println(list);

    int replacementIndex = 9;

    for (int i = 0; i <= 9; i++) {
      MyDoubleLinkedList.Node<Integer> loopStartNode = list.getNodeAtIndex(i);
      MyDoubleLinkedList.Node<Integer> replacementNode = new MyDoubleLinkedList.Node<>(9);
      MyDoubleLinkedList.Node<Integer> replacedNode = list.getNodeAtIndex(replacementIndex);

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
      MyDoubleLinkedList<Integer> list = createRandLinkedList(i);
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

}
