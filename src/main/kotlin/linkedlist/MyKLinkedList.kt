package linkedlist

import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException

class MyKLinkedList() : GKLinkedList<Int>, Iterable<Int> {

  constructor(vararg ints: Int) : this() {
    for (index in (ints.size - 1) downTo 0) {
      addFirst(ints[index])
    }
  }

  // - - - - - - - - - - - - - - - Properties/Fields - - - - - - - - - - - - - - -

  private var headNode: Node? = null


  // - - - - - - - - - - - - - - - Add - - - - - - - - - - - - - - -

  override fun addFirst(data: Int) {
    val newNode = Node(data)

    headNode?.let { newNode.linkedNode = it }
    /*
    Better than:
      if (headNode != null) newNode.linkedNode = headNode
    Same as:
      val tempHead = headNode
      if (tempHead != null) newNode.linkedNode = tempHead
    Explanation:
      In the first method, headNode could become null between the check and assignment of newNode.linkedNode.
     */

    headNode = newNode
  }

  override fun addLast(data: Int) {
    val newNode = Node(data)

    // If the list is null, make newNode the headNode immediately.
    // Otherwise, iterate to the end of the list and add it there.
    if (headNode == null) {
      headNode = newNode

    } else {
      var currentNode = headNode

      // Iterate to the end of the list, then link newNode to the previous last node
      while (currentNode?.linkedNode != null) {
        currentNode = currentNode.linkedNode
      }

      currentNode?.linkedNode = newNode
    }
  }

  override fun addAtIndex(data: Int, index: Int) {
    if (index < 0) throw IndexOutOfBoundsException()

    // Set up for iteration
    val newNode = Node(data)
    var currentNode = headNode
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
          newNode.linkedNode = currentNode.linkedNode
          currentNode.linkedNode = newNode
          return
        }
        currentNode = currentNode.linkedNode
        currentIndex += 1
      }
    }

    // If the index is the last position, add it there. Otherwise, throw an exception.
    if (currentIndex == index) currentNode?.linkedNode = newNode else throw IndexOutOfBoundsException()
  }


  // - - - - - - - - - - - - - - - Set - - - - - - - - - - - - - - -

  override fun setFirst(data: Int) {
    if (headNode == null) {
      throw NullPointerException()

    } else {
      val newNode = Node(data)
      newNode.linkedNode = headNode?.linkedNode
      headNode = newNode
    }
  }

  override fun setLast(data: Int) {
    // If the list is null, make newNode the headNode immediately.
    // Otherwise, iterate to the end of the list and add it there.
    if (headNode == null) {
      throw NullPointerException()

    } else {
      var currentNode = headNode

      // Iterate to the end and link the last Node to newNode
      while (currentNode?.linkedNode != null) currentNode = currentNode.linkedNode
      currentNode?.data = data
    }
  }

  override fun setAtIndex(data: Int, index: Int) {
    if (index < 0) throw IndexOutOfBoundsException()
    if (headNode == null) throw NullPointerException()

    // If index is 0, set headNode to newNode.
    // Otherwise, check if index is in range and add it there.
    if (index == 0) {
      setFirst(data)
      return

    } else {
      var currentNode = headNode
      var currentIndex = 0

      while (currentNode != null && currentIndex <= index) {
        if (currentIndex == index) {
          currentNode.data = data
          return
        }

        currentNode = currentNode.linkedNode
        currentIndex += 1
      }
    }

    // Throw invalid index exception
    throw IndexOutOfBoundsException()
  }


  // - - - - - - - - - - - - - - - Delete - - - - - - - - - - - - - - -

  override fun removeFirst() {
    if (headNode == null) throw NullPointerException()

    // If the list only contains headNode, delete it
    // Otherwise, make headNode's linkedNode null and make the second Node the new head
    if (headNode?.linkedNode == null) {
      headNode = null

    } else {
      val newHeadNode = headNode?.linkedNode
      headNode?.linkedNode = null
      headNode = newHeadNode
    }
  }

  override fun removeLast() {
    if (headNode == null) throw NullPointerException()

    // If the list only contains headNode, delete it
    // Otherwise, iterate to the end of the list and make the second-to-last Node link null (unlink the last Node)
    if (headNode?.linkedNode == null) {
      headNode = null

    } else {
      var currentNode = headNode

      // Iterate until currentNode is the second-to-last Node, then delete it's reference to the last Node
      while (currentNode?.linkedNode?.linkedNode != null) currentNode = currentNode.linkedNode
      currentNode?.linkedNode = null
    }
  }

  override fun removeAtIndex(index: Int) {
    if (headNode == null) throw NullPointerException()

    if (index == 0) {
      removeFirst()
      return
    }

    var currentNode = headNode
    var currentIndex = 0

    while (currentNode?.linkedNode != null && currentIndex < index) {
      if (currentIndex == index - 1) {
        val newRef = currentNode.linkedNode?.linkedNode
        currentNode.linkedNode?.linkedNode = null
        currentNode.linkedNode = newRef
        return
      }

      currentNode = currentNode.linkedNode
      currentIndex += 1
    }
    throw IndexOutOfBoundsException()
  }

  override fun remove(data: Int) {
    if (headNode == null) throw NullPointerException()

    var currentNode = headNode

    if (currentNode?.data == data) {
      removeFirst()
      return
    }

    while (currentNode?.linkedNode != null) {
      if (currentNode.linkedNode?.data == data) {
        val link = currentNode.linkedNode?.linkedNode
        currentNode.linkedNode?.linkedNode = null
        currentNode.linkedNode = link
        return
      }
      currentNode = currentNode.linkedNode
    }
    throw NullPointerException()
  }


  // - - - - - - - - - - - - - - - Get - - - - - - - - - - - - - - -

  override fun getFirst(): Int {
    val tempNode = headNode;
    if (tempNode == null) throw NullPointerException() else return tempNode.data
  }

  override fun getLast(): Int {
    if (headNode == null) throw NullPointerException()

    var currentNode = headNode
    while (currentNode?.linkedNode != null) currentNode = currentNode.linkedNode
    if (currentNode?.data != null) return currentNode.data else throw NullPointerException()
  }

  override fun getAtIndex(index: Int): Int {
    if (headNode == null) throw NullPointerException()
    if (index < 0) throw IndexOutOfBoundsException()

    var currentNode = headNode
    var currentIndex = 0

    while (currentNode != null && currentIndex <= index) {
      if (currentIndex == index) return currentNode.data
      currentNode = currentNode.linkedNode
      currentIndex += 1
    }
    throw IndexOutOfBoundsException()
  }


  // - - - - - - - - - - - - - - - Check - - - - - - - - - - - - - - -

  override fun contains(data: Int): Boolean {
    if (headNode == null) throw NullPointerException()

    var currentNode = headNode

    while (currentNode != null) {
      if (currentNode.data == data) return true
      currentNode = currentNode.linkedNode
    }
    return false
  }


  // - - - - - - - - - - - - - - - Object callbacks - - - - - - - - - - - - - - -

  override fun equals(other: Any?): Boolean {
    if (other is MyKLinkedList) {
      val thisIterator = this.iterator()
      val otherIterator = other.iterator()

      while (thisIterator.hasNext() && otherIterator.hasNext()) {
        if (thisIterator.next() == otherIterator.next()) continue else return false
      }

      return (!thisIterator.hasNext() == !otherIterator.hasNext())
    }
    return false
  }

  override fun toString(): String {
    if (headNode != null) {
      return headNode.toString()
    }
    return "empty"
  }


  // - - - - - - - - - - - - - - - Iterable callbacks - - - - - - - - - - - - - - -

  override fun iterator(): Iterator<Int> {
    return object : Iterator<Int> {

      var currentNode = headNode;

      override fun hasNext(): Boolean {
        return currentNode != null
      }

      override fun next(): Int {
        val returnInt: Int? = currentNode?.data

        if (returnInt == null) {
          throw NullPointerException()

        } else {
          currentNode = currentNode?.linkedNode
          return returnInt
        }
      }
    }
  }

  // - - - - - - - - - - - - - - - Node data class - - - - - - - - - - - - - - -

  private data class Node(var data: Int) {
    var linkedNode: Node? = null

    override fun toString(): String {
      return "[" + data + "]" + " -> " + if (linkedNode == null) "null" else linkedNode.toString()
    }
  }


}