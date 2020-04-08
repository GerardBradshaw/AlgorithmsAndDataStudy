package questions

import array.MyStringBuilder
import set.MyHashSet

class S02LinkedLists {

  /**
   * Removes duplicates from linked list given by [head] using a set. O(N) time, O(N) space.
   *
   * Other approaches:
   * - See [q0201bRemoveDups] for method that trades space for time.
   */
  fun q0201aRemoveDups(head: Node) {
    val set = MyHashSet<Int>()

    var current: Node? = head
    var prev: Node? = null

    while (current != null) {
      if (set.contains(current.data)) {
        prev!!.next = current.next
        current.next = null
        current = prev.next
      }
      else {
        set.add(current.data)
        prev = current
        current = current.next
      }
    }
  }

  /**
   * Removes duplicates from linked list given by [head] using 2 pointers. O(N^2) time, O(1) space.
   *
   * Other approaches:
   * - See [q0201aRemoveDups] for method that trades time for space.
   */
  fun q0201bRemoveDups(head: Node) {
    var slowPointer: Node? = head
    var fastPointerPrev: Node? = head
    var fastPointer: Node? = head.next

    while (slowPointer != null) {
      when {
        fastPointer == null -> {
          slowPointer = slowPointer.next
          fastPointer = slowPointer?.next
          fastPointerPrev = slowPointer
        }
        fastPointer.data == slowPointer.data -> {
          fastPointerPrev!!.next = fastPointer.next
          fastPointer.next = null
          fastPointer = fastPointerPrev.next
        }
        else -> {
          fastPointer = fastPointer.next
          fastPointerPrev = fastPointerPrev!!.next
        }
      }
    }
  }

  /**
   * Returns the value of the [k]th value from the end of the linked list given by [head] using 2 pointers. O(N) time,
   * O(1) space.
   *
   * Other approaches:
   * - Loop through to get the size, then loop through again and return value at size - k. O(N) time, O(1) space. But
   * takes twice as long as the method I implemented since you have to loop over the list twice.
   * - ([q0202bReturnKthToLast]) Use recursion to get to the end of the list, then return an increasing counter on the
   * way back. When the counter is equal to k, print the value. O(N) time, O(N) space.
   * - (C) Use recursion as above, but add a wrapper class to the counter and use it as a function parameter instead of a
   * return value. Return the node instead. O(N) time, O(N) space.
   */
  fun q0202aReturnKthToLast(head: Node, k: Int): Int? {
    var slow = head
    var fast: Node? = head

    // Move fast k away from slow
    for (i in 0..k) {
      if (fast == null) return null
      fast = fast.next
    }

    // Move fast and slow at same speed. When fast is at the end, slow is at k from the end!
    while (fast != null) {
      slow = slow.next!!
      fast = fast.next
    }
    return slow.data
  }

  /**
   * Prints the value of the [k]th value from the end of the linked list given by [node] using recursion. O(N) space and
   * time.
   *
   * See [q0202aReturnKthToLast] and [q0202cReturnKthToLast] for alternate approaches.
   */
  fun q0202bReturnKthToLast(node: Node, k: Int) {
    q0202bReturnKthToLast(node, k)
  }

  private fun q0202bReturnKthToLastHelper(node: Node?, k: Int): Int {
    if (node == null) return 0

    val index = q0202bReturnKthToLastHelper(node.next, k) + 1
    if (index == k) println(node.data)
    return index
  }

  /**
   * Returns the value of the [k]th vale from the end of the linked list given by [node] using recusion. O(N) space and
   * time.
   *
   * See [q0202aReturnKthToLast] and [q0202bReturnKthToLast] for alternate approaches.
   */
  fun q0202cReturnKthToLast(node: Node, k: Int): Int? {
    return q0202cReturnKthToLastHelper(node, k, Index(0))?.data
  }

  private fun q0202cReturnKthToLastHelper(head: Node?, k: Int, index: Index): Node? {
    if (head == null) return null

    val node = q0202cReturnKthToLastHelper(head.next, k, index)
    index.value++
    if (index.value == k + 1) return head
    return node
  }

  data class Index(var value: Int)

  /**
   * Removes [node] from the list if it's not the last in the list by replacing its data with the data from its child.
   * O(1) space and time.
   *
   * Other approaches:
   * - I can't think of any! Could potentially add that if it's the last node (node.next == null), it's marked as a
   * dummy (this would have to be a property of Node, though).
   */
  fun q0203DeleteMiddleNode(node: Node) {
    val next = node.next ?: return
    node.data = next.data
    node.next = next.next
    next.next = null
  }

  class Node(var data: Int) {
    var next: Node? = null

    fun appendToTail(data: Int) {
      var last = this
      while (last.next != null) last = last.next!!
      last.next = Node(data)
    }

    fun appendAllToTail(vararg data: Int) {
      var last = this
      while (last.next != null) last = last.next!!

      for (d in data) {
        last.next = Node(d)
        last = last.next!!
      }
    }

    override fun toString(): String {
      val builder = MyStringBuilder().append("[")

      var current: Node? = this

      while (current != null) {
        builder.append(current.data.toString()).append(", ")
        current = current.next
      }
      return builder.removeEnd(2).append("]").toString()
    }
  }

}