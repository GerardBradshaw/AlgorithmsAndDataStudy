package stack

import java.lang.NullPointerException

class MyStack<T> : Collection<T> {

  // ---------------- Fields ----------------

  private var top: Node<T>? = null
  private var base: Node<T>? = null
  private var height = 0

  override val size: Int
    get() = height


  // ---------------- Public fun ----------------

  /**
   * Pushes input element to the top of the stack.
   * @param element the element to insert.
   */
  fun push(element: T?) {
    if (element == null) return

    val newNode = Node(element)
    val topHolder = top
    val baseHolder = base

    when {
      topHolder == null -> addToEmptyStack(newNode)
      baseHolder == null -> addToStackOfOne(topHolder, newNode)
      else -> addToStackOfTwoOrMore(newNode)
    }
  }

  /**
   * Removes and returns the element from the top of the stack.
   *
   * @return the element at the top of the stack, or null if the stack is empty.
   */
  fun pop(): T? {
    val topHolder = top ?: return null
    top = topHolder.prev
    topHolder.prev = null
    height--
    return topHolder.element
  }

  /**
   * Returns the element from the top of the stack without removing it.
   *
   * @return the element at the top of the stack, or null if the stack is empty.
   */
  fun peek(): T? {
    val topHolder = top ?: return null
    return topHolder.element
  }

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

  fun isNotEmpty(): Boolean {
    return height != 0
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

  override fun equals(other: Any?): Boolean {
    if (other !is MyStack<*> || other.height != height) return false

    var otherCurrent = other.top
    var current = top

    while (current != null && otherCurrent != null) {
      if (otherCurrent != other) return false
      current = current.prev
      otherCurrent = otherCurrent.prev
    }

    return current == otherCurrent
  }

  override fun hashCode(): Int {
    var result = 1
    var current = top

    while (current != null) {
      result = result * 31 + current.hashCode()
      current = current.prev
    }
    return result
  }

  override fun toString(): String {
    if (top == null) return "[]"

    val builder = StringBuilder()
    builder.append("TOP ")

    var current = top

    while (current != null) {
      builder.append("<- [").append(current.toString()).append("] ")
      current = current.prev
    }

    return builder.append("<- BASE").toString()
  }


  // ---------------- Helpers ----------------

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


  // ---------------- Node class ----------------

  private data class Node<T>(var element: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return element.toString()
    }

    override fun equals(other: Any?): Boolean {
      if (other !is Node<*>) return false
      return other.element == element && other.prev?.element == prev?.element
    }

    override fun hashCode(): Int {
      return 31 * (element.hashCode() + prev?.element.hashCode())
    }
  }
}