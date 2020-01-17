package linkedlist

import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import java.util.*
import java.util.function.Consumer

class MyLinkedListK : GLinkedList<Int>, Iterable<Int> {

  constructor(vararg ints: Int) {
    for (i in ints) {
      addAtStart(i)
    }
  }

  // - - - - - - - - - - - - - - - Properties/Fields - - - - - - - - - - - - - - -

  private var headNode: Node? = null


  // - - - - - - - - - - - - - - - Add - - - - - - - - - - - - - - -

  override fun addAtStart(data: Int) {
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

  override fun addAtEnd(data: Int) {
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
      addAtStart(data)
      return

    } else {
      // Loop through this to find index. If found, add and return. Otherwise, add to last index or throw exception.
      while (currentNode?.linkedNode != null && currentIndex < index) {
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

  override fun setAtStart(data: Int) {
    if (headNode == null) {
      headNode = Node(data)

    } else {
      val newNode = Node(data)
      newNode.linkedNode = headNode?.linkedNode
      headNode = newNode
    }
  }

  override fun setAtEnd(data: Int) {
    val newNode = Node(data)

    // If the list is null, make newNode the headNode immediately.
    // Otherwise, iterate to the end of the list and add it there.
    if (headNode == null) {
      headNode = newNode

    } else {
      var currentNode = headNode

      // Iterate to the end and link the last Node to newNode
      while (currentNode?.linkedNode != null) currentNode = currentNode.linkedNode
      currentNode?.linkedNode = newNode
    }
  }

  override fun setAtIndex(data: Int, index: Int) {
    if (index < 0) throw IndexOutOfBoundsException()

    val newNode = Node(data)

    // If index is 0, set headNode to newNode.
    // Otherwise, check if index is in range and add it there.
    if (index == 0) {
      setAtStart(data)
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

  override fun deleteAtStart() {
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

  override fun deleteAtEnd() {
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

  override fun deleteAtIndex(index: Int) {
    if (headNode == null) throw NullPointerException()

    if (index == 0) {
      deleteAtStart()
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
    throw NullPointerException()
  }

  override fun deleteItem(data: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Get - - - - - - - - - - - - - - -

  override fun getAtStart(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAtEnd(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAtIndex(index: Int): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Check - - - - - - - - - - - - - - -

  override fun contains(data: Int?): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Object callbacks - - - - - - - - - - - - - - -

  override fun equals(other: Any?): Boolean {
    if (other is MyLinkedListK) {
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