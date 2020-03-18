package queue

import java.lang.StringBuilder

class MyQueue<T> {

  // ---------------- Fields ----------------

  private var front: Node<T>? = null
  private var back: Node<T>? = null;


  // ---------------- Public fun ----------------

  fun enqueue(element: T) {
    val newNode = Node(element)
    val frontHolder = front;
    val backHolder = back;

    when {
      frontHolder == null -> insertFirst(newNode)
      backHolder == null -> insertSecond(newNode, frontHolder)
      else -> insertThirdAndBeyond(newNode, backHolder)
    }
  }

  fun dequeue(): T? {
    val frontHolder = front ?: return null

    front = frontHolder.prev
    frontHolder.prev = null
    return frontHolder.data
  }

  fun isEmpty(): Boolean {
    return front == null
  }

  fun isNotEmpty(): Boolean {
    return front != null
  }

  override fun toString(): String {
    val frontHolder = front ?: return "[]"
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
    if (other !is MyQueue<*>) return false

    var current = front
    var otherCurrent = other.front

    while (current != null && otherCurrent != null) {
      if (current != otherCurrent) return false
      current = current.prev
      otherCurrent = otherCurrent.prev
    }
    return current == otherCurrent
  }

  override fun hashCode(): Int {
    var result = 1
    var current = front

    while (current != null) {
      result = result * 31 + current.hashCode()
      current = current.prev
    }
    return result
  }


  // ---------------- Helpers ----------------

  private fun insertFirst(node: Node<T>) {
    front = node
  }

  private fun insertSecond(node: Node<T>, frontHolder: Node<T>) {
    back = node
    frontHolder.prev = back
    front = frontHolder
  }

  private fun insertThirdAndBeyond(node: Node<T>, backHolder: Node<T>) {
    backHolder.prev = node
    back = node
  }


  // ---------------- Node data class ----------------

  private data class Node<T>(var data: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return data.toString();
    }

    override fun equals(other: Any?): Boolean {
      if (other !is Node<*> || other.prev?.data != data) return false

      return other.data == data
    }

    override fun hashCode(): Int {
      return 31 * (data.hashCode() + prev?.data.hashCode())
    }
  }
}