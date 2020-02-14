package hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyHashTable2 {

  String[] data;
  int arraySize;
  int itemsInArray = 0;

  // -------- Constructor --------

  public MyHashTable2(int size) {
    arraySize = size;
    data = new String[size];
    fillDataWithDash();
  }


  // -------- Hash functions --------

  public void addToArrayUsingCrappyHash(String[] stringsForArray) {
    for (String s : stringsForArray) {
      int index = Integer.parseInt(s);
      data[index] = s;
    }
  }

  public void addToArrayUsingModHash(String[] stringsForArray) {
    for (String s : stringsForArray) {
      int index = Integer.parseInt(s) % data.length;
      //System.out.println("Index = " + index + " for value " + s);

      while (!data[index].equals("-")) {
        index++;
        //System.out.println("Collision! Trying index = " + index + " instead");
        index = index % arraySize;
      }
      data[index] = s;
    }
  }

  public void addToArrayUsingDoubModHash(String[] stringsForArray) {
    for (String s : stringsForArray) {
      int index = Integer.parseInt(s) % data.length;

      int stepSize = 7 - (Integer.parseInt(s) % 7);

      //System.out.println("Index = " + index + " for value " + s);

      while (!data[index].equals("-")) {
        index += stepSize;
        //System.out.println("Collision! Trying index = " + index + " instead");
        index = index % arraySize;
      }
      data[index] = s;
    }
  }


  // -------- Get --------

  public String findKeyInModHashedTable(String key) {
    int index = Integer.parseInt(key) % arraySize;
    while (!data[index].equals("-")) {
      if (data[index].equals(String.valueOf(index))) {
        System.out.println(key + " found at index " + index);
        return data[index];
      }
      System.out.println(key + " not found at " + index + ". Moving along...");
      index++;
      index = index % arraySize;
    }
    return "not found";
  }

  public String findKeyInDoubModHashTable(String key) {
    int index = Integer.parseInt(key) % arraySize;
    int stepSize = 7 - (Integer.parseInt(key) % 7);
    while (!data[index].equals("-")) {
      if (data[index].equals(String.valueOf(index))) {
        System.out.println(key + " found at index " + index);
        return data[index];
      }
      System.out.println(key + " not found at " + index + ". Moving along...");
      index += stepSize;
      index = index % arraySize;
    }
    return "not found";
  }


  // -------- Prime helpers --------

  private boolean isPrime(int number) {
    if (number % 2 == 0) return false;

    for (int i = 3; i*i <= number; i+=2) {
      if (number % i == 0) return false;
    }
    return true;
  }

  private int getNextPrime(int number) {
    for (int i = number; true; i++) {
      if (isPrime(i)) return i;
    }
  }

  public void increaseArraySize(int minArraySize) {
    int size = getNextPrime(minArraySize);
    System.out.println("New array size is " + size);
    moveOldArray(size);
  }

  private void moveOldArray(int newSize) {
    String[] dataValues = removeEmptySpacesInArray();
    data = new String[newSize];
    arraySize = newSize;
    fillDataWithDash();
    addToArrayUsingModHash(dataValues);
  }

  private String[] removeEmptySpacesInArray() {

    List<String> stringList = new ArrayList<String>();

    for (String s : data)
      if (!s.equals("-"))
        stringList.add(s);

    return stringList.toArray(new String[stringList.size()]);
  }

  private void fillDataWithDash() {
    Arrays.fill(data, "-");
  }


  // -------- Object callbacks --------

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[ ");
    for (String s : data) {
      builder.append(s).append(" ");
    }
    builder.append("]");
    return builder.toString();
  }
}
