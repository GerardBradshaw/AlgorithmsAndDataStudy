package linkedlist;

public interface MyLinkedListExtras {

  void deleteNodeNotLast(MyLinkedList.Node node);

  void addAfterNode(MyLinkedList.Node node, int data);

  void mergeSort();

  void addInOrder();

  MyLinkedList mergeWithSorted(MyLinkedList sortedList);

  void removeNthFromEnd(int n);

  MyLinkedList.Node getMiddle();

  void detectLoopWithDictionary(); // See https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/ for help

  void detectAndCountLoopWithPointers();  // See https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/ for help

  int size();  // See https://www.geeksforgeeks.org/find-length-of-a-linked-list-iterative-and-recursive/ for help

  boolean detectPalindrome();

  void swapNode(int index1, int index2); // Do this without swapping data

  MyLinkedList pairwiseSwap();

  MyLinkedList intersectionOfLinkedLists(MyLinkedList sortedList);

  MyLinkedList reverseList();
}
