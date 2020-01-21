package linkedlist;

public interface GLinkedList<T> {

  // ------- Add
  void addFirst(T data);
  void addLast(T data);
  void addAtIndex(T data, int index);

  // ------- Set
  void setFirst(T data);
  void setLast(T data);
  void setAtIndex(T data, int index);

  // ------- Delete
  void removeFirst();
  void removeLast();
  void removeAtIndex(int index);
  void remove(T data);

  // ------- Get
  T getFirst();
  T getLast();
  T getAtIndex(int index);

  // ------- Check
  boolean contains(T data);
}
