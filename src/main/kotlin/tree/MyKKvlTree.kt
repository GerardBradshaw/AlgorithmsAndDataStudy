package tree

import java.lang.NullPointerException

class MyKKvlTree {

  // ---------------- Member variables ----------------

  private var head: Node? = null
  private var size = 0


  // ---------------- Public methods ----------------

  fun insert(value: Int) {
    var current = head

    if (current == null) {
      head = Node(value)
      size++
      return
    }

    while (current != null) {
      if (value > current.value) {

        val right = current.right
        if (right == null) {
          current.right = Node(value)
          break
        }
        else current = right

      }
      else if (value <= current.value) {

        val left = current.left
        if (left == null) {
          current.left = Node(value)
          break
        }
        else current = left
      }
    }
    size++
    balanceTree()
  }

  fun delete(value: Int) {
    var current = head

    if (current == null)
      throw NullPointerException()

    if (current.value == value)
      deleteHead()

    while (current != null) {
      if (value < current.value) {
        val left = current.left ?: return

        if (value == left.value)
          deleteLeftNodeOf(current)
        else current = left
      }

      else if (value > current.value) {
        val right = current.right ?: return

        if (value == right.value)
          deleteRightNodeOf(current)
        else current = right
      }
    }
    balanceTree()
  }

  fun contains(value: Int): Boolean {
    var current = head

    var currentValue: Int

    while (current != null) {
      currentValue = current.value

      if (value == currentValue)
        return true

      if (value > currentValue)
        current = current.left

      else if (value < currentValue)
        current = current.right
    }
    return false
  }

  fun isEmpty(): Boolean {
    return head == null || size == 0
  }

  fun size(): Int {
    return size
  }


  // ---------------- Helpers ----------------

  private fun balanceTree() {
    TODO()
  }

  private fun deleteHead() {

    size--
    TODO()
  }

  private fun deleteLeftNodeOf(node: Node) {
    
    size--
    TODO()
  }

  private fun deleteRightNodeOf(node: Node) {

    size--
    TODO()
  }

  // ---------------- Any callbacks ----------------

  override fun equals(other: Any?): Boolean {
    TODO()
  }

  override fun hashCode(): Int {
    TODO()
  }

  override fun toString(): String {
    TODO()
  }

  // ---------------- Node class ----------------

  private data class Node(var value: Int) {
    var left: Node? = null
    var right: Node? = null

    override fun toString(): String {
      return value.toString()
    }
  }
}