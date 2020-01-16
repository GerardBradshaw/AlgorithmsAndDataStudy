import linkedlist.MyLinkedList;
import tree.MyBinarySearchTree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HelloWorld {

  public static void main(String[] args) {

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
      MyLinkedList list = createRandLinkedList(i);
      long endTimeNs = System.nanoTime();
      long timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      // getFromEnd
      assert list != null;
      startTimeNs = System.nanoTime();
      list.getAtEnd();
      endTimeNs = System.nanoTime();
      timeElapsedNs = (endTimeNs - startTimeNs);

      timeListString += "," + timeElapsedNs;


      // removeFromEnd
      startTimeNs = System.nanoTime();
      list.deleteAtEnd();
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

}
