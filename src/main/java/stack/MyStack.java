package stack;

public class MyStack<T> {

  private Node<T> top = null;
  private Node<T> base = null;

  public void push(T data) {
    Node<T> newNode = new Node<>(data);
    if (base == null) {
      base = newNode;
      top = newNode;

    } else {
      newNode.previous = top;
      top = newNode;
    }
  }

  public T peek() {
    if (top == null)
      return null;

    return top.data;
  }

  public T pop() {
    if (top == null)
      return null;

    Node<T> temp = top;
    top = top.previous;
    return temp.data;
  }

  @Override
  public String toString() {
    if (top == null)
      return "empty";

    StringBuilder builder = new StringBuilder();
    builder.append("TOP ");

    Node<T> currentNode = top;

    while (currentNode != null) {
      builder.append("<- [").append(currentNode.toString()).append("] ");
      currentNode = currentNode.previous;
    }

    builder.append("<- BASE");
    return builder.toString();
  }

  private static class Node<T> {
    T data;
    Node<T> previous;

    Node(T data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

}
