package linkedlist

import java.lang.IndexOutOfBoundsException

class MyLinkedListK : GLinkedList<Int> {

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

    // If this is empty, make newNode the headNode immediately. Otherwise, copy old headNode link to newNode and add.
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
    // If index is less than zero, throw immediate exception
    if (index < 0) throw IndexOutOfBoundsException()

    // Set up for iteration
    val newNode = Node(data)
    var currentNode = headNode
    var currentIndex = 0

    // If index is 0, add to start. Otherwise if valid index, add it there. Otherwise, throw exception.
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

    if (currentIndex == index) currentNode?.linkedNode = newNode else throw IndexOutOfBoundsException()
  }


  // - - - - - - - - - - - - - - - Set - - - - - - - - - - - - - - -

  override fun setAtStart(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setAtEnd(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setAtIndex(data: Int?, index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Delete - - - - - - - - - - - - - - -

  override fun deleteAtStart() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteAtEnd() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteAtIndex(index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteItem(data: Int?) {
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


  private data class Node(var data: Int) {
    var linkedNode: Node? = null

    override fun toString(): String {
      return "[" + data + "]" + " -> " + if (linkedNode == null) "null" else linkedNode.toString()
    }
  }

}