package tree;

public class MyBinarySearchTree implements GBinaryTree<Integer> {

  private Node head;

  @Override
  public void insert(Integer data) {
    Node newNode = new Node(data);

    if (head == null) {
      head = newNode;
      return;
    }

    Node currentNode = head;

    while (currentNode != null) {
      // New data is less than current Node -> either move to left or add as child to current
      if (data < currentNode.getData()) {
        if (currentNode.getLesser() != null) {
          currentNode = currentNode.getLesser();
        } else {
          currentNode.setLesser(newNode);
          return;
        }

      // New data is greater than current Node -> either move to right or add as child to current
      } else if (data >= currentNode.getData()){
        if (currentNode.getGreater() != null) {
          currentNode = currentNode.getGreater();
        } else {
          currentNode.setGreater(newNode);
          return;
        }
      }
    }
  }

  @Override
  public void remove(Integer data) {

  }

  @Override
  public Integer get() {
    return null;
  }

  @Override
  public boolean contains(Integer data) {
    if (head == null) throw new NullPointerException();

    Node current = head;

    while (current != null) {
      if (data < current.getData()) current = current.getLesser();
      else if (data > current.getData()) current = current.getGreater();
      else if (data == current.getData()) return true;
    }
    return false;
  }

  @Override
  public void printInOrder() {

  }

  @Override
  public void printPreOrder() {

  }

  @Override
  public void printPostOrder() {

  }

  // -------- Node class --------

  private static class Node {
    private int data;
    private Node lesser, greater;

    Node(int data) {
      this.data = data;
    }

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    public Node getLesser() {
      return lesser;
    }

    public void setLesser(Node lesser) {
      this.lesser = lesser;
    }

    public Node getGreater() {
      return greater;
    }

    public void setGreater(Node greater) {
      this.greater = greater;
    }
  }
}
