package linkedlist;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDoubleLinkedList<T> implements GLinkedList<T>, Iterable<T> {

  // -------- Member variables --------
  private Node<T> head;

  // -------- Constructor(s) --------

  @SafeVarargs
  public MyDoubleLinkedList(T... data) {

    for (int i = (data.length - 1); i >= 0; i--) addFirst(data[i]);
  }

  public MyDoubleLinkedList(Node<T> head) {
    this.head = head;
  }


  // -------- Add --------

  @Override
  public void addFirst(T data) {
    Node<T> newHead = new Node<>(data);

    if (head != null) {
      newHead.setNext(head);
      head.setPrev(newHead);
    }

    head = newHead;
  }

  @Override
  public void addLast(T data) {
    Node<T> newNode = new Node<>(data);
    if (head == null) {
      head = newNode;
    }

    Node<T> currentNode = head;
    while (currentNode.getNext() != null) {
      currentNode = currentNode.getNext();
    }

    currentNode.setNext(newNode);
    newNode.setPrev(currentNode);
  }

  @Override
  public void addAtIndex(T data, int index) {
    Node<T> newNode = new Node<>(data);
    if (head == null) {
      if (index == 0) head = newNode;
      else throw new IndexOutOfBoundsException();
    }

    int currentIndex = 0;
    Node<T> currentNode = head;

    // Loop until the index is found or the end of the list is found
    while (currentNode.getNext() != null && currentIndex < index) {
      currentNode = currentNode.getNext();
      currentIndex++;
    }

    if (currentIndex == index) {
      newNode.setNext(currentNode);
      newNode.setPrev(currentNode.getPrev());
      currentNode.getPrev().setNext(newNode);
      currentNode.setPrev(newNode);

    } else if (currentIndex + 1 == index) {
      newNode.setPrev(currentNode);
      currentNode.setNext(newNode);

    } else {
      throw new IndexOutOfBoundsException();
    }
  }


  // -------- Set --------

  @Override
  public void setFirst(T data) {
    if (head == null) throw new NullPointerException();

    head.setData(data);
  }

  @Override
  public void setLast(T data) {
    if (head == null) throw new NullPointerException();

    else {
      Node<T> currentNode = head;
      while (currentNode.getNext() != null) currentNode = currentNode.getNext();
      currentNode.setData(data);
    }
  }

  @Override
  public void setAtIndex(T data, int index) {
    // Error if null list
    if (head == null) throw new NullPointerException();

    // Set first if index 0
    if (index == 0) {
      setFirst(data);

      // Otherwise loop to the index
    } else {
      Node<T> currentNode = head;
      int currentIndex = 0;

      while (currentNode.getNext() != null && currentIndex < index) {
        currentNode = currentNode.getNext();
        currentIndex++;
      }

      if (currentIndex == index) {
        currentNode.setData(data);

      } else if (currentIndex + 1 == index) {
        Node<T> newNode = new Node<>(data);
        newNode.setPrev(currentNode);
        currentNode.setNext(newNode);

      } else throw new IndexOutOfBoundsException();
    }
  }


  // -------- Remove --------

  @Override
  public void removeFirst() {
    if (head.getNext() == null || head == null) head = null;

    else {
      Node<T> newHead = head.getNext();
      head.setNext(null);
      newHead.setPrev(null);
      head = newHead;
    }
  }

  @Override
  public void removeLast() {
    if (head == null) throw new NullPointerException();

    if (head.getNext() == null) {
      head = null;

    } else {
      Node<T> currentNode = head;

      while (currentNode.getNext() != null) currentNode = currentNode.getNext();

      currentNode.getPrev().setNext(null);
      currentNode.setPrev(null);
    }
  }

  @Override
  public void removeAtIndex(int index) {
    if (head == null) throw new NullPointerException();

    if (index == 0) removeFirst();
    else {
      Node<T> currentNode = head;
      int currentIndex = 0;

      while (currentNode.getNext() != null && currentIndex < index) {
        currentNode = currentNode.getNext();
        currentIndex++;
      }

      if (currentIndex == index) removeNode(currentNode);

      else throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public void remove(T data) {

    boolean dataRemoved = false;

    Node<T> currentNode = head;
    while (currentNode != null) {
      if (currentNode.getData() == data) {
        removeNode(currentNode);
        dataRemoved = true;
      }
      currentNode = currentNode.getNext();
    }

    if (!dataRemoved) throw new NullPointerException();
  }

  private void removeNode(Node<T> node) {
    if (node.getPrev() == null) {
      removeFirst();
      return;
    }

    if (node.getNext() != null) {
      node.getPrev().setNext(node.getNext());
      node.getNext().setPrev(node.getPrev());

    } else {
      node.getPrev().setNext(null);
    }

    node.setPrev(null);
    node.setNext(null);
  }


  // -------- Get --------

  @Override
  public T getFirst() {
    if (head == null) throw new NullPointerException();

    return head.getData();
  }

  @Override
  public T getLast() {
    if (head == null) throw new NullPointerException();

    Node<T> currentNode = head;
    while (currentNode.getNext() != null) {
      currentNode = currentNode.getNext();
    }

    return currentNode.getData();
  }

  @Override
  public T getAtIndex(int index) {
    if (head == null) throw new NullPointerException();
    if (index == 0) return getFirst();

    Node<T> currentNode = head;
    int currentIndex = 0;

    while (currentNode.getNext() != null && currentIndex < index) {
      currentNode = currentNode.getNext();
      currentIndex++;
    }

    if (currentIndex == index) return currentNode.getData();
    else throw new IndexOutOfBoundsException();
  }


  // -------- Contains --------

  @Override
  public boolean contains(T data) {
    if (head == null) throw new NullPointerException();

    Node<T> currentNode = head;

    while (currentNode != null) {
      if (currentNode.getData() == data) return true;
      currentNode = currentNode.getNext();
    }
    return false;
  }


  // -------- Iterator callbacks --------

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {

      Node<T> currentNode = head;

      @Override
      public boolean hasNext() {
        return currentNode.getNext() != null;
      }

      @Override
      public T next() {
        // Get the data, move to next, return data
        T data = currentNode.getData();
        currentNode = currentNode.getNext();
        return data;
      }

      public T current() {
        return currentNode.getData();
      }
    };
  }


  // -------- Object callbacks --------

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MyDoubleLinkedList) {
      MyDoubleLinkedList<?> other = (MyDoubleLinkedList<?>) obj;

      Iterator<T> thisIterator = iterator();
      Iterator<?> otherIterator = other.iterator();

      while (thisIterator.hasNext() && otherIterator.hasNext()) {
        if (thisIterator.next().equals(otherIterator.next())) continue;
        return false;
      }

      return thisIterator.hasNext() == otherIterator.hasNext();
    }
    return false;
  }

  @Override
  public String toString() {
    if (head != null) {
      return head.toString();
    }
    return "empty";
  }




  // -------- Extra methods! --------

  public void deleteNodeNotLast(Node<T> node) {
    removeNode(node);
  }

  public void addAfterNode(Node<T> node, T data) {
    // Create a new Node
    Node<T> newNode = new Node<>(data);

    // Link next Node to newNode
    node.getNext().setPrev(newNode);

    // Set data in newNode
    newNode.setNext(node.getNext());
    newNode.setPrev(node);

    // Link previous Node to newNode
    node.setNext(newNode);
  }

  //TODO
  public void quickSort() {

  }

  //TODO
  public void addInOrder() {

  }

  //TODO
  public void mergeWithSorted(MyDoubleLinkedList<T> sortedList) {

  }

  public void removeNthFromEnd(int n) {

    Node<T> lagNode = head;
    Node<T> leadNode = head;
    int counter = 0;

    while (leadNode != null && counter < n + 1) {
      leadNode = leadNode.getNext();
      counter++;
    }

    if (counter == n + 1) {
      while (leadNode != null) {
        leadNode = leadNode.getNext();
        lagNode = lagNode.getNext();
      }
      removeNode(lagNode);

    } else throw new IndexOutOfBoundsException();
  }

  public Node<T> getMiddle() {
    if (head == null) throw new NullPointerException();
    if (head.getNext() == null) return head;

    Node<T> slow = head;
    Node<T> fast = head;

    while (fast != null && fast.getNext() != null) {
      fast = fast.getNext().getNext();
      slow = slow.getNext();
    }
    return slow;
  }

  public int detectLoopSizeWithDictionary() {
    Map<Node<T>, Integer> dict = new HashMap<>();

    Node<T> currentNode = head;
    int position = 0;

    while(currentNode != null) {
      if (dict.containsKey(currentNode)) {
        return position - dict.get(currentNode);
        //TODO fix bug when loop is at the end (last node refers to itself)
      }
      dict.put(currentNode, position);
      currentNode = currentNode.getNext();
      position++;
    }

    return 0;
  }

  public Node<T> getNodeAtIndex(int index) {
    if (index < 0) throw new IndexOutOfBoundsException();
    if (head == null) throw new NullPointerException();

    Node<T> currentNode = head;
    int currentIndex = 0;

    while (currentIndex < index && currentNode.getNext() != null) {
      currentIndex++;
      currentNode = currentNode.getNext();
    }

    if (currentIndex == index) return currentNode;
    throw new IndexOutOfBoundsException();
  }

  public int detectAndCountLoopWithPointers() {
    if (head == null) throw new NullPointerException();
    if (head.getNext() == null) return -1;

    Node<T> slow = head;
    Node<T> fast = head.getNext();


    while (fast.getNext() != null) {
      if (fast.equals(slow)) {

        Node<T> counter = slow.getNext();
        int count = 1;

        while (!counter.equals(slow)) {
          counter = counter.getNext();
          count++;
        }
        return count;
        //TODO fix count when loop size is 1 (node refers to itself)
      }

      fast = fast.getNext().getNext();
      slow = slow.getNext();
    }
    return -1;
  }

  public int size() {
    return size(head);
  }

  private int size(Node<T> node) {
    // Base case
    if (node == null) {
      return 0;
    }

    return 1 + size(node.getNext());
  }

  public boolean detectPalindrome() {
    Node<T> mid = getMiddle();

    MyDoubleLinkedList<T> tempList = new MyDoubleLinkedList<T>(mid.getNext());
    tempList.reverseList();

    Node<T> currentLeftNode = head;
    Node<T> currentRightNode = tempList.head;

    while(currentRightNode != null) {
      if (currentRightNode.getData().equals(currentLeftNode.getData())) {
        currentLeftNode = currentLeftNode.getNext();
        currentRightNode = currentRightNode.getNext();
        continue;
      }
      return false;
    }
    return true;
  }

  public void swapNode(int index1, int index2) {
    if (index1 < 0 || index2 < 0) throw new IndexOutOfBoundsException();
    if (index1 == index2) return;

    int firstIndex = Math.min(index1, index2);
    int secondIndex = Math.max(index1, index2);

    int counter = 0;
    Node<T> currentNode = head;
    Node<T> firstNode = null;

    while(currentNode != null) {
      if (counter == firstIndex) firstNode = currentNode;

      if (counter == secondIndex) {
        if (firstNode != null) {
          swapNode(currentNode, firstNode);
          return;
        }
      }
      currentNode = currentNode.getNext();
      counter++;
    }
    throw new IndexOutOfBoundsException();
  }

  private void swapNode(Node<T> node1, Node<T> node2) {
    T tempData = node1.getData();
    node1.setData(node2.getData());
    node2.setData(tempData);
  }

  public void pairwiseSwap() {
    if (head == null) throw new NullPointerException();
    if (head.getNext() == null) return;

    Node<T> first = head;
    Node<T> second = head.getNext();

    while (second != null && second.getNext() != null) {
      swapNode(first, second);

      first = first.getNext().getNext();
      second = second.getNext().getNext();
    }

    if (second != null) {
      swapNode(first, second);
    }
  }

  // TODO
  public MyDoubleLinkedList<T> intersectionOfLinkedLists(MyDoubleLinkedList<T> sortedList) {
    return null;
  }

  public void reverseList() {
    if (head == null) throw new NullPointerException();
    if (head.getNext() == null) return;

    Node<T> prev = head;
    Node<T> current = prev.getNext();
    Node<T> nextHolder;
    prev.setNext(null);

    while (current != null) {
      nextHolder = current.getNext();
      current.setNext(prev);
      prev = current;
      current = nextHolder;
    }
    head = prev;
  }

  // - - - - - - - - - - - - - - - Node class - - - - - - - - - - - - - - -

  public static class Node<T> extends GLinkedListNode<Node<T>, T> {

    public Node(T data) {
      super(data);
    }

    private Node<T> prev;

    public Node<T> getPrev() {
      return prev;
    }

    public void setPrev(Node<T> prev) {
      this.prev = prev;
    }
  }
}
