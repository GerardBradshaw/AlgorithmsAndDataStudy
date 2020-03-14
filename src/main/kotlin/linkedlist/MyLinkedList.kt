package linkedlist

import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException

class MyLinkedList() : GLinkedList<Int>, Iterable<Int> {

  // ---------------- Fields & Constructor ----------------

  private var head: Node? = null

  constructor(vararg ints: Int) : this() {
    for (index in (ints.size - 1) downTo 0) {
      addFirst(ints[index])
    }
  }


  // ---------------- Public fun ----------------

  override fun addFirst(data: Int) {
    val newNode = Node(data)

    head?.let { newNode.next = it }
    /*
    Better than:
      if (headNode != null) newNode.linkedNode = headNode
    Same as:
      val tempHead = headNode
      if (tempHead != null) newNode.linkedNode = tempHead
    Explanation:
      In the first method, headNode could become null between the check and assignment of newNode.linkedNode.
     */

    head = newNode
  }

  override fun addLast(data: Int) {
    val newNode = Node(data)

    // If the list is null, make newNode the headNode immediately.
    // Otherwise, iterate to the end of the list and add it there.
    if (head == null) {
      head = newNode

    } else {
      var currentNode = head

      // Iterate to the end of the list, then link newNode to the previous last node
      while (currentNode?.next != null) {
        currentNode = currentNode.next
      }

      currentNode?.next = newNode
    }
  }

  override fun addAtIndex(data: Int, index: Int) {
    if (index < 0) throw IndexOutOfBoundsException()

    // Set up for iteration
    val newNode = Node(data)
    var currentNode = head
    var currentIndex = 0

    // If index is 0, add newNode to start.
    // Otherwise, check if the index exists in the list but is not the last index, and add it there.
    if (index == 0) {
      addFirst(data)
      return

    } else {
      // Loop through this to find index. If found, add and return. Otherwise, add to last index or throw exception.
      while (currentNode != null && currentIndex < index) {
        if (currentIndex == index - 1) {
          newNode.next = currentNode.next
          currentNode.next = newNode
          return
        }
        currentNode = currentNode.next
        currentIndex += 1
      }
    }

    // If the index is the last position, add it there. Otherwise, throw an exception.
    if (currentIndex == index) currentNode?.next = newNode else throw IndexOutOfBoundsException()
  }

  override fun setFirst(data: Int) {
    if (head == null) {
      throw NullPointerException()

    } else {
      val newNode = Node(data)
      newNode.next = head?.next
      head = newNode
    }
  }

  override fun setLast(data: Int) {
    // If the list is null, make newNode the headNode immediately.
    // Otherwise, iterate to the end of the list and add it there.
    if (head == null) {
      throw NullPointerException()

    } else {
      var currentNode = head

      // Iterate to the end and link the last Node to newNode
      while (currentNode?.next != null) currentNode = currentNode.next
      currentNode?.data = data
    }
  }

  override fun setAtIndex(data: Int, index: Int) {
    if (index < 0) throw IndexOutOfBoundsException()
    if (head == null) throw NullPointerException()

    // If index is 0, set headNode to newNode.
    // Otherwise, check if index is in range and add it there.
    if (index == 0) {
      setFirst(data)
      return

    } else {
      var currentNode = head
      var currentIndex = 0

      while (currentNode != null && currentIndex <= index) {
        if (currentIndex == index) {
          currentNode.data = data
          return
        }

        currentNode = currentNode.next
        currentIndex += 1
      }
    }

    // Throw invalid index exception
    throw IndexOutOfBoundsException()
  }

  override fun removeFirst() {
    if (head == null) throw NullPointerException()

    // If the list only contains headNode, delete it
    // Otherwise, make headNode's linkedNode null and make the second Node the new head
    if (head?.next == null) {
      head = null

    } else {
      val newHeadNode = head?.next
      head?.next = null
      head = newHeadNode
    }
  }

  override fun removeLast() {
    if (head == null) throw NullPointerException()

    // If the list only contains headNode, delete it
    // Otherwise, iterate to the end of the list and make the second-to-last Node link null (unlink the last Node)
    if (head?.next == null) {
      head = null

    } else {
      var currentNode = head

      // Iterate until currentNode is the second-to-last Node, then delete it's reference to the last Node
      while (currentNode?.next?.next != null) currentNode = currentNode.next
      currentNode?.next = null
    }
  }

  override fun removeAtIndex(index: Int) {
    if (head == null) throw NullPointerException()

    if (index == 0) {
      removeFirst()
      return
    }

    var currentNode = head
    var currentIndex = 0

    while (currentNode?.next != null && currentIndex < index) {
      if (currentIndex == index - 1) {
        val newRef = currentNode.next?.next
        currentNode.next?.next = null
        currentNode.next = newRef
        return
      }

      currentNode = currentNode.next
      currentIndex += 1
    }
    throw IndexOutOfBoundsException()
  }

  override fun remove(data: Int) {
    if (head == null) throw NullPointerException()

    var currentNode = head

    if (currentNode?.data == data) {
      removeFirst()
      return
    }

    while (currentNode?.next != null) {
      if (currentNode.next?.data == data) {
        val link = currentNode.next?.next
        currentNode.next?.next = null
        currentNode.next = link
        return
      }
      currentNode = currentNode.next
    }
    throw NullPointerException()
  }

  override fun getFirst(): Int {
    val tempNode = head;
    if (tempNode == null) throw NullPointerException() else return tempNode.data
  }

  override fun getLast(): Int {
    if (head == null) throw NullPointerException()

    var currentNode = head
    while (currentNode?.next != null) currentNode = currentNode.next
    if (currentNode?.data != null) return currentNode.data else throw NullPointerException()
  }

  override fun getAtIndex(index: Int): Int {
    if (head == null) throw NullPointerException()
    if (index < 0) throw IndexOutOfBoundsException()

    var currentNode = head
    var currentIndex = 0

    while (currentNode != null && currentIndex <= index) {
      if (currentIndex == index) return currentNode.data
      currentNode = currentNode.next
      currentIndex += 1
    }
    throw IndexOutOfBoundsException()
  }

  override fun contains(data: Int): Boolean {
    if (head == null) throw NullPointerException()

    var currentNode = head

    while (currentNode != null) {
      if (currentNode.data == data) return true
      currentNode = currentNode.next
    }
    return false
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyLinkedList) return false

    var current = head
    var otherCurrent = other.head

    while (current != null && otherCurrent != null) {
      if (current != otherCurrent) return false
      current = current.next
      otherCurrent = otherCurrent.next
    }
    return current == otherCurrent
  }

  override fun toString(): String {
    if (head != null) {
      return head.toString()
    }
    return "empty"
  }

  override fun hashCode(): Int {
    var result = 1
    var current = head

    while (current != null) {
      result = result * 31 + current.hashCode()
      current = current.next
    }
    return result
  }

  override fun iterator(): Iterator<Int> {
    return object : Iterator<Int> {

      var currentNode = head;

      override fun hasNext(): Boolean {
        return currentNode != null
      }

      override fun next(): Int {
        val returnInt: Int? = currentNode?.data

        if (returnInt == null) {
          throw NullPointerException()

        } else {
          currentNode = currentNode?.next
          return returnInt
        }
      }
    }
  }

  fun isEmpty(): Boolean {
    return head == null
  }

  fun isNotEmpty(): Boolean {
    return head != null
  }


  // ---------------- Node data class ----------------

  private data class Node(var data: Int) {
    var next: Node? = null

    override fun toString(): String {
      return "[" + data + "]" + " -> " + if (next == null) "null" else next.toString()
    }

    override fun hashCode(): Int {
      val nextData = next?.data ?: 0
      return 31 * (data.hashCode() + nextData.hashCode())
    }

    override fun equals(other: Any?): Boolean {
      if (other !is Node) return false

      return other.data == data && other.next == next
    }
  }
}