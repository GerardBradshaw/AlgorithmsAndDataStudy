package tree;

public class Node {

  // -------- Member variables --------

  int value;
  Node left;
  Node right;


  // -------- Constructor --------

  public Node(int value) {
    this.value = value;
  }

  public Node(int... values) {
    value = values[0];
    for (int i = 1; i < values.length; i++) {
      insert(values[i]);
    }
  }


  // -------- Non-traversal methods --------

  public void insert(int value) {
    // Less than value case
    if (value < this.value) {
      if (left == null) left = new Node(value);
      else left.insert(value);

    // Greater than value case
    } else {
      if (right == null) right = new Node(value);
      else right.insert(value);
    }
  }

  public boolean contains(int value) {
    // Base case
    if (value == this.value) return true;

    // Less than value case
    if (value < this.value) {
      if (left == null) return false;
      else return left.contains(value);

    // Greater than value case
    } else {
      if (right == null) return false;
      else return right.contains(value);
    }
  }

  public void delete(int value) {
    delete(this, value);
  }

  private void delete(Node parent, int value) {
    Node deleteNode;

    // Node to delete is to the left of parent
    if (parent.left.value == value) {
      deleteNode = parent.left;

      // No child exists on left and...
      if (deleteNode.left == null) {

        // ... no child exists on right either -> delete link from parent to child and return
        if (deleteNode.right == null) {
          parent.left = null;

        // ... child does exist on right -> replace link from parent with right child
        } else {
          parent.left = deleteNode.right;
        }

      // Child does exist on left and...
      } else {

        // ... no child exist on right -> replace link from parent with left child
        if (deleteNode.right == null) {
          parent.left = deleteNode.left;

        // ... child does exist on right -> it gets complicated!
        } else {
          Node nodeToMove = deleteNode.left.right;
          parent.left = deleteNode.left;
          parent.left.right = deleteNode.right;
          insertNodeLeftMost(nodeToMove, parent.left.right);
        }
      }
    }

    // Node to delete is to the right of parent
    else if (parent.right.value == value) {
      deleteNode = parent.right;

      // No child exists on left and...
      if (deleteNode.left == null) {

        // ... no child exists on right either -> delete link from parent to child and return
        if (deleteNode.right == null) {
          parent.right = null;
          return;

          // ... child does exist on right -> replace link from parent with right child
        } else {
          parent.right = deleteNode.right;
          return;
        }

        // Child does exist on left and...
      } else {

        // ... no child exist on right -> replace link from parent with left child
        if (deleteNode.right == null) {
          parent.right = deleteNode.left;
          return;

          // ... child does exist on right -> it gets complicated!
        } else {
          Node nodeToMove = deleteNode.left.right;
          parent.right = deleteNode.left;
          parent.right.right = deleteNode.right;
          insertNodeLeftMost(nodeToMove, parent.left.right);
          return;
        }
      }
    }
  }

  private void insertNodeLeftMost(Node insert, Node parent) {
    if (parent.left == null) parent.left = insert;
    else {
      Node currentNode = parent;
      while (currentNode.left != null) currentNode = currentNode.left;
      currentNode.left = insert;
    }
  }


  // -------- Traversal --------

  public void printInOrder() {
    if (left != null) left.printInOrder();
    System.out.println(value);
    if (right != null) right.printInOrder();
  }

  public void printPreOrder() {
    System.out.println(value);
    if (left != null) left.printInOrder();
    if (right != null) right.printInOrder();
  }

  public void printPostOrder() {
    System.out.println(value);
    if (right != null) right.printInOrder();
    if (left != null) left.printInOrder();
  }
}
