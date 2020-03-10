package stack

import java.lang.NullPointerException

class MyStack<T> : Collection<T> {

  private var top: Node<T>? = null
  private var base: Node<T>? = null
  private var height = 0

  /**
   * Pushes input data to the top of the stack.
   */
  fun push(data: T?) {
    if (data == null) return

    val newNode = Node(data)
    val topHolder = top
    val baseHolder = base

    when {
      topHolder == null -> addToEmptyStack(newNode)
      baseHolder == null -> addToStackOfOne(topHolder, newNode)
      else -> addToStackOfTwoOrMore(newNode)
    }
  }

  private fun addToEmptyStack(newNode: Node<T>) {
    top = newNode
    height++
  }

  private fun addToStackOfOne(top: Node<T>, newNode: Node<T>) {
    base = top
    newNode.prev = base
    this.top = newNode
    height++
  }

  private fun addToStackOfTwoOrMore(newNode: Node<T>) {
    newNode.prev = top
    top = newNode
    height++
  }

  /**
   * Removes and returns the data from the top of the stack.
   */
  fun pop(): T {
    val topHolder = top ?: throw NullPointerException()
    top = topHolder.prev
    topHolder.prev = null
    height--
    return topHolder.element
  }

  /**
   * Returns the data from the top of the stack without removing it.
   */
  fun peek(): T? {
    val topHolder = top ?: return null
    return topHolder.element
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyStack<*>) return false

    var current = top
    var otherData: Any?

    while (current != null) {
      otherData = other.pop()

      if (otherData == null || !otherData.equals(current.element))
        return false

      current = current.prev
    }
    return other.pop() == null
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    if (top == null) return "empty"

    val builder = StringBuilder()
    builder.append("TOP ")

    var current = top

    while (current != null) {
      builder.append("<- [").append(current.toString()).append("] ")
      current = current.prev
    }

    return builder.append("<- BASE").toString()
  }

  override val size: Int
    get() = height

  override fun contains(element: T): Boolean {
    var current = top

    while (current != null) {
      if (current.element == element) return true
      current = current.prev
    }
    return false
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    for (element in elements) {
      if (!contains(element)) return false
    }
    return true
  }

  override fun isEmpty(): Boolean {
    return height == 0
  }

  override fun iterator(): Iterator<T> {
    return object : Iterator<T> {
      var current = top

      override fun hasNext(): Boolean {
        return current != null
      }

      override fun next(): T {
        val currentCopy = current ?: throw NullPointerException()

        val element = currentCopy.element
        current = currentCopy.prev
        return element
      }
    }
  }

  private data class Node<T>(var element: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return element.toString()
    }
  }
}