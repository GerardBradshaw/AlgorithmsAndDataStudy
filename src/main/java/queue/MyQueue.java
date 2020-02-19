package queue;

public class MyQueue<T> {

  private Node<T> front;
  private Node<T> back;


  /**
   * Places data at the back of the queue.
   * @param data: the data to enqueue
   */
  public void enqueue(T data) {
    Node<T> newNode = new Node<>(data);

    // Case: Queue is empty -> set front as newNode and previous as back (null)
    if (front == null) {
      front = newNode;

      // Case: Queue only has a front Node -> set back as newNode
    } else if (back == null) {
      back = newNode;
      front.previous = back;

      // Case: Queue has a current front and back -> set newNode as back and link to old back
    } else {
      back.previous = newNode;
      back = newNode;
    }
  }

  /**
   * Retrieves the data at the front of the queue.
   * @return T: the data at the front of the queue
   */
  public T dequeue() {
    // Return null if the queue is empty
    if (front == null) return null;

    // Store the current front Node
    Node<T> temp = front;

    // Set the front node to the next in queue
    front = front.previous;

    // Clear the 'previous' reference from the stored Node
    temp.previous = null;

    // Return the stored Node's data
    return temp.data;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    if (front == null) {
      return builder.append("empty").toString();

    } else {
      builder.append("FRONT <- ");

      Node<T> currentNode = front;
      while (currentNode != null) {
        builder.append("[").append(currentNode.data.toString()).append("] <- ");
        currentNode = currentNode.previous;
      }
      return builder.append("BACK").toString();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MyQueue) {
      MyQueue<?> other = (MyQueue<?>) obj;

      Node<T> currentNode = front;
      while (currentNode != null) {
        if (currentNode.data.equals(other.dequeue())) {
          currentNode = currentNode.previous;
          continue;
        }
        return false;
      }
      return other.dequeue() == null;
    }
    return false;
  }

  private static class Node<T> {

    T data;
    Node<T> previous;

    Node(T data) {
      this.data = data;
    }

  }
}
