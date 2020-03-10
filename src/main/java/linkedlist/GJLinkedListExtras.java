package linkedlist;

public interface GJLinkedListExtras {

  void deleteNodeNotLast(MyJLinkedList.Node node);

  void addAfterNode(MyJLinkedList.Node node, int data);

  void quickSort();

  void addInOrder();

  MyJLinkedList mergeWithSorted(MyJLinkedList sortedList);

  void removeNthFromEnd(int n);

  MyJLinkedList.Node getMiddle();

  void detectLoopWithDictionary(); // See https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/ for help

  void detectAndCountLoopWithPointers();  // See https://www.geeksforgeeks.org/detect-loop-in-a-linked-list/ for help

  int size();  // See https://www.geeksforgeeks.org/find-length-of-a-linked-list-iterative-and-recursive/ for help

  boolean detectPalindrome();

  void swapNode(int index1, int index2); // Do this without swapping data

  MyJLinkedList pairwiseSwap();

  MyJLinkedList intersectionOfLinkedLists(MyJLinkedList sortedList);

  MyJLinkedList reverseList();
}
