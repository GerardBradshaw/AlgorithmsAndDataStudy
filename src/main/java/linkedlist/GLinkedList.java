package linkedlist;

public interface GLinkedList<T> {

  // ------- Add
  void addAtStart(T data);
  void addAtEnd(T data);
  void addAtIndex(T data, int index);

  // ------- Set
  void setAtStart(T data);
  void setAtEnd(T data);
  void setAtIndex(T data, int index);

  // ------- Delete
  void deleteAtStart();
  void deleteAtEnd();
  void deleteAtIndex(int index);
  void deleteItem(T data);

  // ------- Get
  T getAtStart();
  T getAtEnd();
  T getAtIndex(int index);

  // ------- Check
  boolean contains(T data);
}
