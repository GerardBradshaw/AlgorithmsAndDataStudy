package linkedlist;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class MyJLinkedList implements GJLinkedList<Integer>, Iterable<Integer>, GJLinkedListExtras {

  private Node head;

  // ------- Constructor -------

  public MyJLinkedList(Integer... ints) {
    for (int i = (ints.length - 1); i >= 0; i--) {
      addFirst(ints[i]);
    }
  }


  // -------- Add --------

  public void addLast(Integer data) {
    Node newNode = new Node(data);

    // If list is null, make the headNode the newNode. Otherwise, find the end and add the new node
    if (head == null) {
      head = newNode;

    } else {
      Node currentNode  = head;

      // Loop to the end of the current list and link the last node to newNode
      while (currentNode.getNext() != null) {
        currentNode = currentNode.getNext();
      }
      currentNode.setNext(newNode);
    }
  }

  public void addAtIndex(Integer data, int index) {
    Node newNode = new Node(data);

    // If the index is 0, add a new head. Otherwise, find the index and add new Node.
    if (index == 0) {
      // If the list is null, create a new head, otherwise copy new head info
      if (head == null) {
        head = newNode;
        return;
      }

      // Copy info from headNode into new Node for second index
      Node newSecondNode = new Node(head.getData());
      newSecondNode.setNext(head.getNext());

      // Create a new Node from the input data and make it the first Node
      newNode.setNext(newSecondNode);
      head = newNode;

    } else {
      Node currentNode = head;
      int positionCounter = 0;

      // Iterate until the end of the list
      while (currentNode.getNext() != null) {
        if (positionCounter == index - 1) {
          newNode.setNext(currentNode.getNext());
          currentNode.setNext(newNode);
          return;
        }

        positionCounter += 1;
        currentNode = currentNode.getNext();
      }

      // Index is either the last (currently null) position, or out of bounds.
      if (positionCounter == index - 1) {
        currentNode.setNext(newNode);

      } else {
        throw new IndexOutOfBoundsException();
      }
    }
  }

  public void addFirst(Integer data) {
    addAtIndex(data, 0);
  }


  // -------- Set --------

  public void setLast(Integer data) {
    nullListCheck();

    Node currentNode = head;

    while (currentNode.getNext() != null) {
      currentNode = currentNode.getNext();
    }

    currentNode.setData(data);
  }

  public void setAtIndex(Integer data, int index) {
    nullListCheck();

    if (index == 0) {
      setFirst(data);
      return;
    }

    Node currentNode = head;
    int currentIndex = 0;

    while (currentNode != null && currentIndex <= index) {
      if (currentIndex == index) {
        currentNode.setData(data);
        return;
      }
      currentNode = currentNode.getNext();
      currentIndex += 1;
    }

    throw new IndexOutOfBoundsException();
  }

  public void setFirst(Integer data) {
    nullListCheck();
    head.setData(data);
  }


  // -------- Remove --------

  public void remove(Integer data) {
    nullListCheck();

    boolean dataDeleted = false;

    if (head.getData().equals(data)) {
      removeFirst();
      dataDeleted = true;
    }

    Node currentNode = head;

    do {
      if (currentNode.getNext().getData().equals(data)) {
        // Get the Node to delete (the next one) and it's reference (the one after).
        Node nodeToDelete = currentNode.getNext();
        Node nodeToDeleteLinkedNode = nodeToDelete.getNext();

        // Remove link from nodeToDelete
        nodeToDelete.setNext(null);

        // Fix the chain
        currentNode.setNext(nodeToDeleteLinkedNode);
        dataDeleted = true;
      }

      if (currentNode.getNext() != null) {
        currentNode = currentNode.getNext();
      }
    } while (currentNode.getNext() != null);

    if (!dataDeleted) {
      throw new NullPointerException();
    }
  }

  public void removeAtIndex(int index) {
    nullListCheck();

    // Remove headNode when index is zero, otherwise remove the referenced Node
    if (index == 0) {
      removeFirst();

    } else {
      Node currentNode = head;
      int currentIndex = 0;

      while (currentNode.getNext() != null && currentIndex < index) {
        if (currentIndex == index - 1) {
          // Get the Node to remove (the next Node) and it's reference
          Node nodeToDelete = currentNode.getNext();
          Node nodeToDeleteLinkedNode = nodeToDelete.getNext();

          // Remove the link to any other Node from the Node to remove
          nodeToDelete.setNext(null);

          // Set the removed link to the currentNode
          currentNode.setNext(nodeToDeleteLinkedNode);
          return;
        }

        currentNode = currentNode.getNext();
        currentIndex += 1;
      }

      throw new IndexOutOfBoundsException();
    }
  }

  public void removeLast() {
    nullListCheck();

    // If the list contains one element, delete the list.
    if (head.getNext() == null) {
      head = null;
      return;
    }

    Node currentNode = head;

    // Iterate to find the second-to-last element
    while (currentNode.getNext().getNext() != null) {
      currentNode = currentNode.getNext();
    }

    // Remove the link between the second-to-last element and the last element.
    currentNode.setNext(null);
  }

  public void removeFirst() {
    nullListCheck();

    // Make the headNode the second Node if one exists, otherwise make the headNode null.
    if (head.getNext() != null) {
      head = head.getNext();

    } else {
      head = null;
    }
  }


  // -------- Get --------

  public Integer getAtIndex(int index) {
    nullListCheck();

    Node currentNode = head;
    int currentIndex = 0;

    while (currentNode != null && currentIndex <= index) {
      if (currentIndex == index) {
        return currentNode.getData();
      }

      currentNode = currentNode.getNext();
      currentIndex += 1;
    }

    throw new IndexOutOfBoundsException();
  }

  public Integer getFirst() {
    return head.getData();
  }

  public Integer getLast() {
    nullListCheck();

    Node currentNode = head;

    while (currentNode.getNext() != null) {
      currentNode = currentNode.getNext();
    }

    return currentNode.getData();
  }


  // -------- Contains --------

  public boolean contains(Integer data) {
    nullListCheck();

    Node currentNode = head;

    while (currentNode != null) {
      if (currentNode.getData().equals(data)) {
        return true;
      }

      currentNode = currentNode.getNext();
    }

    return false;
  }


  // -------- Bonus methods! --------

  public void deleteList() {
    if (head == null) {
      return;
    }

    Node currentNode = head;

    while (currentNode.getNext() != null) {
      Node nextNode = currentNode.getNext();
      currentNode.deleteNode();
      currentNode = nextNode;
    }
  }

  @Override
  public void deleteNodeNotLast(Node node) {
    if (node.getNext() == null) {
      throw new IllegalArgumentException();
    }

    node.setData(node.getNext().getData());
    node.setNext(node.getNext().getNext());
  }

  @Override
  public void addAfterNode(Node node, int data) {
    if (node.getNext() == null) {
      node.setNext(new Node(data));
      return;
    }

    Node newNode = new Node(data);
    newNode.setNext(node.getNext());
    node.setNext(newNode);
  }

  @Override
  public void quickSort() {
  }

  private Node getLastNode() {
    if (head == null) {
      throw new NullPointerException();
    }

    Node current = head;

    while (current.getNext() != null) {
      current = current.getNext();
    }

    return current;
  }

  @Override
  public void addInOrder() {

  }

  @Override
  public MyJLinkedList mergeWithSorted(MyJLinkedList sortedList) {
    return null;
  }

  @Override
  public void removeNthFromEnd(int n) {

  }

  @Override
  public Node getMiddle() {
    if (head == null) {
      return head;
    }

    Node slow = head;
    Node fast = head;

    while (fast.getNext() != null && fast.getNext().getNext() != null) {
      slow = slow.getNext();
      fast = fast.getNext().getNext();
    }
    return slow;
  }

  @Override
  public void detectLoopWithDictionary() {

  }

  @Override
  public void detectAndCountLoopWithPointers() {

  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean detectPalindrome() {
    return false;
  }

  @Override
  public void swapNode(int index1, int index2) {

  }

  @Override
  public MyJLinkedList pairwiseSwap() {
    return null;
  }

  @Override
  public MyJLinkedList intersectionOfLinkedLists(MyJLinkedList sortedList) {
    return null;
  }

  @Override
  public MyJLinkedList reverseList() {
    return null;
  }

  // - - - - - - - - - - - - - - - Helpers - - - - - - - - - - - - - - -

  private void nullListCheck() {
    if (head == null) {
      throw new NullPointerException();
    }
  }

  private void invalidIndexCheck(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException();
    }
  }


  // - - - - - - - - - - - - - - - Object callbacks - - - - - - - - - - - - - - -

  @Override
  public String toString() {
    if (head != null) {
      return head.toString();
    }
    return "empty";
  }

  @Override
  public int hashCode() {
    return super.hashCode();
    //TODO
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MyJLinkedList) {
      // Cast the obj to MyLinkedList
      MyJLinkedList other = (MyJLinkedList) obj;

      Iterator<Integer> otherIterator = other.iterator();
      Iterator<Integer> thisIterator = this.iterator();

      while (thisIterator.hasNext() && otherIterator.hasNext()) {
        // If the next data in this and the compared are equal, continue iterating. Otherwise, return false.
        if (thisIterator.next().equals(otherIterator.next())) {
          continue;
        }
        return false;
      }

      // Statement reached if this or other cannot be iterated further and have been equal so far.
      return (thisIterator.hasNext() == otherIterator.hasNext());
    }
    return false;
  }


  // - - - - - - - - - - - - - - - Iterable callbacks - - - - - - - - - - - - - - -

  @NotNull
  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {

      Node currentNode = head;

      @Override
      public boolean hasNext() {
        return currentNode != null;
      }

      @Override
      public Integer next() {
        // Get the value
        Integer currentData = currentNode.getData();

        // Move to next
        currentNode = currentNode.getNext();

        // Return the value
        return currentData;
      }
    };
  }

  // - - - - - - - - - - - - - - - Node class - - - - - - - - - - - - - - -

  static class Node extends MyJLinkedListNode<Node, Integer> {

    public Node(Integer data) {
      super(data);
    }
  }
}