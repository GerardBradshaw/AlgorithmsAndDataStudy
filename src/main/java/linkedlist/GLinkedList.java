package linkedlist;

public interface GLinkedList<T> {

  // ------- Add

  void addAtEnd(T data);

  void addAtIndex(T data, int index);

  void addAtStart(T data);


  // ------- Set

  void setAtEnd(T data);

  void setAtIndex(T data, int index);

  void setAtStart(T data);


  // ------- Delete

  void deleteItem(T data);

  void deleteAtIndex(int index);

  void deleteAtEnd();

  void deleteAtStart();


  /// ------- Get

  T getAtIndex(int position);

  T getAtEnd();

  T getAtStart();


  // ------- Check

  boolean contains(T data);
}
