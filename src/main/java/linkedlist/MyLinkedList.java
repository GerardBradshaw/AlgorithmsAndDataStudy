package linkedlist;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MyLinkedList implements GLinkedList<Integer>, Iterable<Integer> {

  private Node headNode;

  // ------- Constructor -------

  public MyLinkedList(Integer... ints) {
    for (int i = (ints.length - 1); i >= 0; i--) {
      addFirst(ints[i]);
    }
  }


  // ------- Add -------

  public void addLast(Integer data) {
    Node newNode = new Node(data);

    // If list is null, make the headNode the newNode. Otherwise, find the end and add the new node
    if (headNode == null) {
      headNode = newNode;

    } else {
      Node currentNode  = headNode;

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
      if (headNode == null) {
        headNode = newNode;
        return;
      }

      // Copy info from headNode into new Node for second index
      Node newSecondNode = new Node(headNode.getData());
      newSecondNode.setNext(headNode.getNext());

      // Create a new Node from the input data and make it the first Node
      newNode.setNext(newSecondNode);
      headNode = newNode;

    } else {
      Node currentNode = headNode;
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


  // ------- Set -------

  public void setLast(Integer data) {
    nullListCheck();

    Node currentNode = headNode;

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

    Node currentNode = headNode;
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
    headNode.setData(data);
  }


  // ------- Delete -------

  public void remove(Integer data) {
    nullListCheck();

    boolean dataDeleted = false;

    if (headNode.getData() == data) {
      removeFirst();
      dataDeleted = true;
    }

    Node currentNode = headNode;

    do {
      if (currentNode.getNext().getData() == data) {
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
      Node currentNode = headNode;
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
    if (headNode.getNext() == null) {
      headNode = null;
      return;
    }

    Node currentNode = headNode;

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
    if (headNode.getNext() != null) {
      headNode = headNode.getNext();

    } else {
      headNode = null;
    }
  }

  public void deleteList() {
    if (headNode == null) {
      return;
    }

    Node currentNode = headNode;

    while (currentNode.getNext() != null) {
      Node nextNode = currentNode.getNext();
      currentNode.deleteNode();
      currentNode = nextNode;
    }
  }


  // ------- Get -------

  public Integer getAtIndex(int index) {
    nullListCheck();

    Node currentNode = headNode;
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
    return headNode.getData();
  }

  public Integer getLast() {
    nullListCheck();

    Node currentNode = headNode;

    while (currentNode.getNext() != null) {
      currentNode = currentNode.getNext();
    }

    return currentNode.getData();
  }


  // ------- Contains -------

  public boolean contains(Integer data) {
    nullListCheck();

    Node currentNode = headNode;

    while (currentNode != null) {
      if (currentNode.getData() == data) {
        return true;
      }

      currentNode = currentNode.getNext();
    }

    return false;
  }


  // - - - - - - - - - - - - - - - Helpers - - - - - - - - - - - - - - -

  private void nullListCheck() {
    if (headNode == null) {
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
    if (headNode != null) {
      return headNode.toString();
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
    if (obj instanceof MyLinkedList) {
      // Cast the obj to MyLinkedList
      MyLinkedList other = (MyLinkedList) obj;

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

      Node currentNode = headNode;

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

  @Override
  public void forEach(Consumer<? super Integer> action) {

  }

  @Override
  public Spliterator<Integer> spliterator() {
    return null;
  }

  // - - - - - - - - - - - - - - - Node class - - - - - - - - - - - - - - -

  private static class Node extends GLinkedListNode<Node, Integer> {

    public Node(Integer data) {
      super(data);
    }
  }
}
