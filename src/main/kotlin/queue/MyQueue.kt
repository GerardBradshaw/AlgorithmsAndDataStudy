package queue

import java.lang.StringBuilder

class MyQueue<T> {

  private var front: Node<T>? = null
  private var back: Node<T>? = null;

  fun enqueue(data: T) {
    val newNode = Node(data)
    val frontHolder = front;
    val backHolder = back;

    // Case: queue is empty -> set front as newNode
    if (frontHolder == null)
      front = newNode

    else {
      // Case: queue only contains front -> set back as newNode and link front to back
      if (backHolder == null) {
        back = newNode
        frontHolder.prev = back
        front = frontHolder

        // Case: queue has a front and back already -> add newNode to back and link to it from the old back
      } else {
        backHolder.prev = newNode;
        back = newNode;
      }
    }
  }

  fun dequeue(): T? {
    // Case: queue is empty -> return null
    val frontHolder = front ?: return null

    // Case: queue is not empty -> return front.data and set front as front.prev
    front = frontHolder.prev
    frontHolder.prev = null
    return frontHolder.data

  }

  override fun toString(): String {
    val frontHolder = front ?: return "empty"
    val builder = StringBuilder()
    var current: Node<T>? = frontHolder

    builder.append("FRONT ")

    while (current != null) {
      builder.append("[").append(current.data).append("] <- ")
      current = current.prev
    }
    return builder.append("BACK").toString()
  }

  override fun equals(other: Any?): Boolean {
    // Case: other is not a MyQueue
    if (other !is MyQueue<*>) return false

    var current: Node<T>? = front
    var otherData: Any?

    while (current != null) {
      otherData = other.dequeue()

      if (otherData == null || !otherData.equals(current.data))
        return false

      current = current.prev
    }
    return other.dequeue() == null
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  private data class Node<T>(var data: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return data.toString();
    }
  }

}