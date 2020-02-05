package tree;

public class XMyBinarySearchTree {

  private Node rootNode;

  @Override
  public String toString() {
    if (rootNode == null) {
      return "empty";
    }

    return rootNode.toString();
  }

  public void printTree() {
    if (rootNode == null) {
      System.out.println("Empty");
    }

    System.out.print("Root node: ");
    rootNode.printNode();
  }

  public void add(int data) {

    Node newNode = new Node(data);

    if (rootNode == null) {
      rootNode = newNode;

    } else {
      Node currentNode = rootNode;
      boolean atLeaf = false;

      // Traverse tree until we reach a leaf node OR you reach the same number
      while (!atLeaf) {

        int currentData = currentNode.getData();
        boolean containsLesserNode = currentNode.getLesserNode() != null;
        boolean containsGreaterNode = currentNode.getGreaterNode() != null;

        // Less than current node
        if (data < currentData) {
          if (containsLesserNode) {
            currentNode = currentNode.lesserNode;
            continue;

          } else {
            currentNode.lesserNode = newNode;
            atLeaf = true;
          }

          // Greater than current node
        } else if (data > currentData) {
          if (containsGreaterNode) {
            currentNode = currentNode.greaterNode;
            continue;

          } else {
            currentNode.greaterNode = newNode;
            atLeaf = true;
          }

          // Equal to current node
        } else {
          break;
        }
      }

    }
  }

  public boolean contains(int data) {
    return false;
  }


  private class Node {

    private int data;
    private Node lesserNode;
    private Node greaterNode;

    public void printNode() {

      String message = "[" + data + " -> "
          + (lesserNode == null ? "NA" : lesserNode.data)
          + ", "
          + (greaterNode == null ? "NA" : greaterNode.data)
          + "]";

      System.out.println(message);

      if (lesserNode != null) {
        lesserNode.printNode();
      }

      if (greaterNode != null) {
        greaterNode.printNode();
      }
    }

    @Override
    public String toString() {
      return "[" + data + " -> "
          + (lesserNode == null ? "NA" : lesserNode.toString())
          + ", "
          + (greaterNode == null ? "NA" : greaterNode.toString())
          + "]";
    }

    public Node(int data) {
      this.data = data;
    }

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    public Node getLesserNode() {
      return lesserNode;
    }

    public void setLesserNode(Node lesserNode) {
      this.lesserNode = lesserNode;
    }

    public Node getGreaterNode() {
      return greaterNode;
    }

    public void setGreaterNode(Node greaterNode) {
      this.greaterNode = greaterNode;
    }
  }
}
