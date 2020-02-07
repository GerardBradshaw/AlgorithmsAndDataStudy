package tree;

public interface GBinaryTree<T> {

  // -------- Insert
  void insert(T data);

  // -------- Remove
  void remove(T data);

  // -------- Get
  T get();

  // -------- Check
  boolean contains(T data);

  // -------- Print
  void printInOrder();
  void printPreOrder();
  void printPostOrder();
}
