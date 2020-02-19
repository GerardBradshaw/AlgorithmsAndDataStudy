package stack

class MyKStack<T> {

  private var top: Node<T>? = null
  private var base: Node<T>? = null

  /**
   * Pushes input data to the top of the stack.
   */
  fun push(data: T) {
    TODO()
  }

  /**
   * Removes and returns the data from the top of the stack.
   */
  fun pop(): T {
    TODO()
  }

  /**
   * Returns the data from the top of the stack without removing it.
   */
  fun peek(): T {
    TODO()
  }

  override fun equals(other: Any?): Boolean {
    TODO()
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    TODO()
  }

  private data class Node<T>(var data: T) {

    var prev: Node<T>? = null

    override fun toString(): String {
      return data.toString()
    }
  }
}