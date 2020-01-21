package linkedlist;

public abstract class GLinkedListNode<N extends GLinkedListNode<N, T>, T> {

  // ------- Member variables
  private T data;
  private N next;

  // ------- Constructor
  public GLinkedListNode(T data) {
    this.data = data;
  }

  // ------- Getters and Setters

  public T getData() {
    return data;
  }
  public void setData(T data) {
    this.data = data;
  }
  public N getNext() {
    return next;
  }
  public void setNext(N next) {
    this.next = next;
  }


  // ------- Helpers
  public void deleteNode() {
    this.next = null;
  }

  // ------- Object callbacks
  @Override
  public String toString() {
    return "[" + data + "] -> " + (next == null ? "null" : next.toString());
  }

}
