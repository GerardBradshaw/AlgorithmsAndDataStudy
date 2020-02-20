package stack

class MyKStack<T> {

  private var top: Node<T>? = null
  private var base: Node<T>? = null

  /**
   * Pushes input data to the top of the stack.
   */
  fun push(data: T) {
    val newNode = Node(data)
    val topHolder = top
    val baseHolder = base

    // Case: stack is empty -> make the top newNode
    if (topHolder == null)
      top = newNode

    // Case: top is not empty but base is -> make current top the previous to newNode and newNode the top
    else if (baseHolder == null) {
      base = topHolder
      newNode.prev = base
      top = newNode

      // Case: top and base are not empty -> move top down one and make top newNode
    } else {
      newNode.prev = top
      top = newNode
    }
  }

  /**
   * Removes and returns the data from the top of the stack.
   */
  fun pop(): T? {
    val topHolder = top ?: return null
    top = topHolder.prev
    topHolder.prev = null
    return topHolder.data
  }

  /**
   * Returns the data from the top of the stack without removing it.
   */
  fun peek(): T? {
    val topHolder = top ?: return null
    return topHolder.data
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyKStack<*>) return false

    var current = top
    var otherData: Any?

    while (current != null) {
      otherData = other.pop()

      if (otherData == null || !otherData.equals(current.data))
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

  private data class Node<T>(var data: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return data.toString()
    }
  }
}