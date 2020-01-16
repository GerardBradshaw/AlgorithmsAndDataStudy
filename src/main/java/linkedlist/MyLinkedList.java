package linkedlist;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MyLinkedList implements GLinkedList<Integer>, Iterable<Integer> {

  private Node headNode;

  // ------- Constructor -------

  public MyLinkedList(Integer... integers) {
    for (Integer i : integers) {
      this.addAtStart(i);
    }
  }


  // ------- Add -------

  public void addAtEnd(Integer data) {
    Node newNode = new Node(data);

    // If list is null, make the headNode the newNode. Otherwise, find the end and add the new node
    if (headNode == null) {
      headNode = newNode;

    } else {
      Node currentNode  = headNode;

      // Loop to the end of the current list and link the last node to newNode
      while (currentNode.getLinkedNode() != null) {
        currentNode = currentNode.getLinkedNode();
      }
      currentNode.setLinkedNode(newNode);
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
      newSecondNode.setLinkedNode(headNode.getLinkedNode());

      // Create a new Node from the input data and make it the first Node
      newNode.setLinkedNode(newSecondNode);
      headNode = newNode;

    } else {
      Node currentNode = headNode;
      int positionCounter = 0;

      // Iterate until the end of the list
      while (currentNode.getLinkedNode() != null) {
        if (positionCounter == index - 1) {
          newNode.setLinkedNode(currentNode.getLinkedNode());
          currentNode.setLinkedNode(newNode);
          return;
        }

        positionCounter += 1;
        currentNode = currentNode.getLinkedNode();
      }

      // Index is either the last (currently null) position, or out of bounds.
      if (positionCounter == index - 1) {
        currentNode.setLinkedNode(newNode);

      } else {
        throw new IndexOutOfBoundsException();
      }
    }
  }

  public void addAtStart(Integer data) {
    addAtIndex(data, 0);
  }


  // ------- Set -------

  public void setAtEnd(Integer data) {
    nullListCheck();

    Node currentNode = headNode;

    while (currentNode.getLinkedNode() != null) {
      currentNode = currentNode.getLinkedNode();
    }

    currentNode.setData(data);
  }

  public void setAtIndex(Integer data, int index) {
    nullListCheck();

    if (index == 0) {
      setAtStart(data);
      return;
    }

    Node currentNode = headNode;
    int currentIndex = 0;

    while (currentNode != null && currentIndex <= index) {
      if (currentIndex == index) {
        currentNode.setData(data);
        return;
      }
      currentNode = currentNode.getLinkedNode();
      currentIndex += 1;
    }

    throw new IndexOutOfBoundsException();
  }

  public void setAtStart(Integer data) {
    nullListCheck();
    headNode.setData(data);
  }


  // ------- Delete -------

  public void deleteItem(Integer data) {
    nullListCheck();

    boolean dataDeleted = false;

    if (headNode.getData() == data) {
      deleteAtStart();
      dataDeleted = true;
    }

    Node currentNode = headNode;

    do {
      if (currentNode.getLinkedNode().getData() == data) {
        // Get the Node to delete (the next one) and it's reference (the one after).
        Node nodeToDelete = currentNode.getLinkedNode();
        Node nodeToDeleteLinkedNode = nodeToDelete.getLinkedNode();

        // Remove link from nodeToDelete
        nodeToDelete.setLinkedNode(null);

        // Fix the chain
        currentNode.setLinkedNode(nodeToDeleteLinkedNode);
        dataDeleted = true;
      }

      if (currentNode.getLinkedNode() != null) {
        currentNode = currentNode.getLinkedNode();
      }
    } while (currentNode.getLinkedNode() != null);

    if (!dataDeleted) {
      throw new NullPointerException();
    }
  }

  public void deleteAtIndex(int index) {
    nullListCheck();

    // Remove headNode when index is zero, otherwise remove the referenced Node
    if (index == 0) {
      deleteAtStart();

    } else {
      Node currentNode = headNode;
      int currentIndex = 0;

      while (currentNode.getLinkedNode() != null && currentIndex < index) {
        if (currentIndex == index - 1) {
          // Get the Node to remove (the next Node) and it's reference
          Node nodeToDelete = currentNode.getLinkedNode();
          Node nodeToDeleteLinkedNode = nodeToDelete.getLinkedNode();

          // Remove the link to any other Node from the Node to remove
          nodeToDelete.setLinkedNode(null);

          // Set the removed link to the currentNode
          currentNode.setLinkedNode(nodeToDeleteLinkedNode);
          return;
        }

        currentNode = currentNode.getLinkedNode();
        currentIndex += 1;
      }

      throw new IndexOutOfBoundsException();
    }
  }

  public void deleteAtEnd() {
    nullListCheck();

    // If the list contains one element, delete the list.
    if (headNode.getLinkedNode() == null) {
      headNode = null;
      return;
    }

    Node currentNode = headNode;

    // Iterate to find the second-to-last element
    while (currentNode.getLinkedNode().getLinkedNode() != null) {
      currentNode = currentNode.getLinkedNode();
    }

    // Remove the link between the second-to-last element and the last element.
    currentNode.setLinkedNode(null);
  }

  public void deleteAtStart() {
    nullListCheck();

    // Make the headNode the second Node if one exists, otherwise make the headNode null.
    if (headNode.getLinkedNode() != null) {
      headNode = headNode.getLinkedNode();

    } else {
      headNode = null;
    }
  }

  public void deleteList() {
    if (headNode == null) {
      return;
    }

    Node currentNode = headNode;

    while (currentNode.getLinkedNode() != null) {
      Node nextNode = currentNode.getLinkedNode();
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

      currentNode = currentNode.getLinkedNode();
      currentIndex += 1;
    }

    throw new IndexOutOfBoundsException();
  }

  public Integer getAtStart() {
    return headNode.getData();
  }

  public Integer getAtEnd() {
    nullListCheck();

    Node currentNode = headNode;

    while (currentNode.getLinkedNode() != null) {
      currentNode = currentNode.getLinkedNode();
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

      currentNode = currentNode.getLinkedNode();
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
      MyLinkedList comparedList = (MyLinkedList) obj;

      Iterator<Integer> comparedListIterator = comparedList.iterator();
      Iterator<Integer> thisListIterator = this.iterator();

      while (thisListIterator.hasNext() && comparedListIterator.hasNext()) {
        // If the next data in this and the compared are equal, continue iterating. Otherwise, return false.
        if (thisListIterator.next().equals(comparedListIterator.next())) {
          continue;
        }
        return false;
      }

      // Statement reached if this or comparedList cannot be iterated further and have been equal so far.
      return (thisListIterator.hasNext() == comparedListIterator.hasNext());
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
        currentNode = currentNode.getLinkedNode();

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

  private static class Node {

    // - - - - - - - - - - - - - - - Member variables - - - - - - - - - - - - - - -

    private Integer data;
    private Node linkedNode;


    // - - - - - - - - - - - - - - - Helpers - - - - - - - - - - - - - - -

    public void deleteNode() {
      this.setLinkedNode(null);
    }


    // - - - - - - - - - - - - - - - Getters and Setters - - - - - - - - - - - - - - -

    public Node(Integer data) {
      this.data = data;
    }

    public int getData() {
      return data;
    }

    public void setData(Integer data) {
      this.data = data;
    }

    public Node getLinkedNode() {
      return linkedNode;
    }

    public void setLinkedNode(Node linkedNode) {
      this.linkedNode = linkedNode;
    }


    // - - - - - - - - - - - - - - - Object callbacks - - - - - - - - - - - - - - -

    @Override
    public String toString() {
      return "[" + data + "] -> " + (linkedNode == null ? "null" : linkedNode.toString());
    }

  }
}
