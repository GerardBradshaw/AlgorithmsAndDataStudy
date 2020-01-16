import linkedlist.MyLinkedList;
import tree.MyBinarySearchTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelloWorld {

  public static void main(String[] args) {

    // Parameters
    int numberOfDataPoints = 100;
    int maxListSize = 9_999_999;

    Map<Integer, Long> addTimeMap = new HashMap<>();
    Map<Integer, Long> getTimeMap = new HashMap<>();
    Map<Integer, Long> removeTimeMap = new HashMap<>();
    Map<Integer, Long> findTimeMap = new HashMap<>();

    for (int i = 2; i <= maxListSize; i = i + (maxListSize / numberOfDataPoints)) {
      // Created using add at end
      long startTimeNs = System.nanoTime();
      MyLinkedList list = createRandLinkedList(i);
      long endTimeNs = System.nanoTime();
      long timeElapsedMs = (endTimeNs - startTimeNs) / 1000000;
      addTimeMap.put(i, timeElapsedMs);
      System.out.println("Map size " + i + " created in " + timeElapsedMs + " ms");

      // Get from end
      assert list != null;
      startTimeNs = System.nanoTime();
      list.getAtEnd();
      endTimeNs = System.nanoTime();
      timeElapsedMs = (endTimeNs - startTimeNs) / 1000000;
      getTimeMap.put(i, timeElapsedMs);
      System.out.println("Got from end in " + timeElapsedMs + " ms");

      // Remove from end
      startTimeNs = System.nanoTime();
      list.deleteAtEnd();
      endTimeNs = System.nanoTime();
      timeElapsedMs = (endTimeNs - startTimeNs) / 1000000;
      removeTimeMap.put(i, timeElapsedMs);
      System.out.println("Deleted last item in " + timeElapsedMs + " ms");

      // Find random value
      startTimeNs = System.nanoTime();
      int randomInt = new Random().nextInt();
      boolean found = list.contains(randomInt);
      endTimeNs = System.nanoTime();
      timeElapsedMs = (endTimeNs - startTimeNs) / 1000000;
      findTimeMap.put(i, timeElapsedMs);
      System.out.println((found ? "Found random item in " : "Did not find random item in ") + timeElapsedMs + " ms");

      System.out.println("\n");
    }

    System.out.println("Finished. Saving to notepad.");

    //TODO save to notepad

  }

  private static MyLinkedList createRandLinkedList(int size) {
    if (size <= 0) {
      return null;
    }

    Integer[] ints = new Integer[size];
    Random rand = new Random();

    for (int i = 0; i < size; i++) {
      ints[i] = rand.nextInt();
    }

    return new MyLinkedList(ints);
  }

  private static void createTree() {
    MyBinarySearchTree myTree = new MyBinarySearchTree();

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

  private static void compareLinkedList() {
    MyLinkedList myLinkedList1 = new MyLinkedList(6, 7, 0, -4);
    System.out.println("List 1:");
    System.out.println(myLinkedList1 + "\n");

    MyLinkedList myLinkedList2 = new MyLinkedList(6, 7, 0, -4);
    System.out.println("List 2:");
    System.out.println(myLinkedList2 + "\n");

    String message = (!myLinkedList1.equals(myLinkedList2) ? "not equal." : "equal!");

    System.out.println("Lists are " + message);
  }

}
