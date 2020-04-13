package questions

import set.MyHashSet
import stack.MyStack

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
      if (set.contains(current.value)) {
        prev!!.next = current.next
        current.next = null
        current = prev.next
      }
      else {
        set.add(current.value)
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
        fastPointer.value == slowPointer.value -> {
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
    return slow.value
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
    if (index == k) println(node.value)
    return index
  }

  /**
   * Returns the value of the [k]th vale from the end of the linked list given by [node] using recusion. O(N) space and
   * time.
   *
   * See [q0202aReturnKthToLast] and [q0202bReturnKthToLast] for alternate approaches.
   */
  fun q0202cReturnKthToLast(node: Node, k: Int): Int? {
    return q0202cReturnKthToLastHelper(node, k, Q0202cIndex(0))?.value
  }

  private fun q0202cReturnKthToLastHelper(head: Node?, k: Int, index: Q0202cIndex): Node? {
    if (head == null) return null

    val node = q0202cReturnKthToLastHelper(head.next, k, index)
    index.value++
    if (index.value == k + 1) return head
    return node
  }

  private data class Q0202cIndex(var value: Int)

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
    node.value = next.value
    node.next = next.next
    next.next = null
  }

  /**
   * Partitions a linked list starting at [head] by moving all elements larger than [x] to the right, and all elements
   * less than it to the left. Note that the partition element [x] can appear anywhere in the right side of the list.
   * O(N) time, O(1) space.
   *
   * Other approaches:
   * - Create 2 lists (one less than x, one greater than x), then join them. O(N) time and space.
   * - Grow the list at the head and tail. This is the same as the approach I implemented except an initial walkthrough
   * doesn't have to be done! O(N) time, O(N) space if a new list is created. O(1) space if the existing list is used
   * (but need to keep track of the first element added to the right!).
   */
  fun q0204Partition(head: Node, x: Int) {
    var maxPos = 0
    var last = head

    while (last.next != null) {
      last = last.next!!
      maxPos++
    }

    var pos = 0
    var prev = head
    var current = head.next ?: return

    while (pos < maxPos) {
      if (current.value >= x) {
        prev.next = current.next
        current.next = null
        last.next = current
        last = last.next!! // Just set to current which is not null
        current = prev.next!! // Won't be null until pos > maxPos which is not possible in this loop
      }
      else {
        current = current.next!! // Won't be null until pos > maxPos which is not possible in this loop
        prev = prev.next!! // Won't be null until pos > maxPos which is not possible in this loop
      }
      pos++
    }
  }

  /**
   * Returns a [Node] representing the result of adding [head1] and [head2] using an iterative approach. All LLs are in
   * the form [1's, 10's, 100's, ...]. O(N + M) time, O(L) space, M = length of head1, N = length of head 2, L =
   * max(M, N)
   *
   * Other approaches:
   * - Use recursion (see [q0205bSumLists]).
   */
  fun q0205aSumLists(head1: Node, head2: Node): Node {
    var current1: Node? = head1
    var current2: Node? = head2
    var carryOver = false
    var currentResult = Node(0)
    val resultHeadParent = currentResult

    while (current1 != null || current2 != null) {
      val value1 = current1?.value ?: 0
      val value2 = current2?.value ?: 0
      var sum = value1 + value2 + if(carryOver) 1 else 0
      carryOver = false

      if (sum > 9) {
        carryOver = true
        sum -= 10
      }

      currentResult.next = Node(sum)
      currentResult = currentResult.next!! // next just added
      current1 = current1?.next
      current2 = current2?.next
    }

    if (carryOver) currentResult.next = Node(1)

    return resultHeadParent.next!!
  }

  /** Returns a [Node] representing the result of adding [head1] and [head2] using a recursive approach. All LLs are in
   * the form [1's, 10's, 100's, ...]. O(N + M) time, O(L) space, M = length of head1, N = length of head2, L = max(M,N).
   *
   * Other approaches:
   * - Use iteration (see [q0205aSumLists]).
   */
  fun q0205bSumLists(head1: Node, head2: Node): Node {
    return q0205bHelper(head1, head2, false)!!
  }

  private fun q0205bHelper(node1: Node?, node2: Node?, carryOver: Boolean): Node? {
    if (node1 == null && node2 == null && !carryOver) {
      return null
    }

    var value = (node1?.value ?: 0) + (node2?.value ?: 0) + if (carryOver) 1 else 0
    val returnCarry = value > 9
    if (returnCarry) value -= 10

    val result = Node(value)

    if (node1 != null || node2 != null) {
      val resultNext = q0205bHelper(node1?.next, node2?.next, returnCarry)
      result.next = resultNext
    }

    return result
  }

  /**
   * Returns a [Node] representing the result of adding [head1] and [head2] using a recursive approach. All LLs are in
   * the form [..., 100's, 10's, 1's]. O(N + M) time, O(L) space, M = length of head1, N = length of head2, L = max(M,N).
   *
   * Other approaches:
   * - Use iteration.
   */
  fun q0205cSumLists(head1: Node, head2: Node): Node {
    var current1: Node? = head1
    var current2: Node? = head2

    while (current1 != null && current2 != null) {
      current1 = current1.next
      current2 = current2.next
    }

    while (current1 != null) head2.appendToHead(0)
    while (current2 != null) head1.appendToHead(0)

    val result = q0205cHelper(head1, head2)
    if (result.carryOver) result.list!!.appendToHead(1)
    return result.list!!
  }

  private fun q0205cHelper(node1: Node?, node2: Node?): Q0205cPartialSum {
    if (node1 == null && node2 == null) {
      return Q0205cPartialSum()
    }

    val result = q0205cHelper(node1?.next, node2?.next)

    var sum = (node1?.value ?: 0) + (node2?.value ?: 0) + if (result.carryOver) 1 else 0
    val nextCarryOver = sum > 9
    if (nextCarryOver) sum -= 10

    if (result.list == null) result.list = Node(sum) else result.list!!.appendToHead(sum)
    result.carryOver = nextCarryOver

    return result
  }

  private data class Q0205cPartialSum(var list: Node? = null, var carryOver: Boolean = false)

  /**
   * Returns true if [head] is a palindrome using iteration. O(N) time and space.
   *
   * Other approaches:
   * - Reverse and compare (see [q0206bPalindrome]). O(N) time and space.
   * - Recursive approach (see [q0206cPalindrome]). O(N) time and space.
   */
  fun q0206aPalindrome(head: Node): Boolean {
    if (head.next == null) return true

    var slow: Node? = head
    var fast: Node? = head
    val stack = MyStack<Node>()

    while (fast?.next != null) {
      stack.push(slow)
      slow = slow!!.next!! // always behind fast
      fast = fast.next!!.next // condition of loop
    }

    val isOddSize = fast != null
    if (isOddSize) slow = slow!!.next!! // size must be at least 3, so there's at least one in line

    while (slow != null) {
      if (slow.value != stack.pop()!!.value) return false // Not null since loop prevented null from being added
      slow = slow.next
    }
    return true
  }

  /**
   * Returns true if [head] is a palindrome by reversing the list and comparing until the middle is reached. O(N) time and space.
   *
   * Other approaches:
   * - Iterative approach (see [q0206aPalindrome]). O(N) time and space.
   * - Recursive approach (see [q0206cPalindrome]). O(N) time and space.
   */
  fun q0206bPalindrome(head: Node): Boolean {
    if (head.next == null) return true

    val reverseList = reverseList(head)
    return isFirstHalfEqual(head, reverseList)
  }

  private fun reverseList(head: Node): Node {
    val result = Node(head.value)

    var current: Node? = head.next

    while (current != null) {
      result.appendToHead(current.value)
      current = current.next
    }

    return result
  }

  private fun isFirstHalfEqual(head1: Node, head2: Node): Boolean {
    var slow1 = head1
    var slow2 = head2
    var fast: Node? = head1

    while (fast?.next != null) {
      if (slow1.value == slow2.value) {
        slow1 = slow1.next!!
        slow2 = slow2.next!!
        fast = fast.next!!.next
      }
      else return false
    }
    return true
  }

  /**
   * Returns true if [head] is a palindrome by using a recursive approach. O(N) time and space.
   *
   * Other approaches:
   * - Iterative approach (see [q0206aPalindrome]). O(N) time and space.
   * - Reverse and compare (see [q0206bPalindrome]). O(N) time and space.
   */
  fun q0206cPalindrome(head: Node): Boolean {
    val size = head.size()

    return if (size <= 1) true
    else {
      val result = q0206cHelper(head, size)
      result.isPalindromeSoFar
    }
  }

  private fun q0206cHelper(node: Node?, length: Int): Q0206cReturnObject {
    if (node == null || length <= 0) return Q0206cReturnObject(node, true) // Even number of nodes
    else if (length == 1) return Q0206cReturnObject(node.next, true) // Odd number of nodes

    val result = q0206cHelper(node.next!!, length - 2)

    if (result.node == null || !result.isPalindromeSoFar) return result

    result.isPalindromeSoFar = result.node!!.value == node.value
    result.node = result.node!!.next
    return result
  }

  private data class Q0206cReturnObject(var node: Node?, var isPalindromeSoFar: Boolean)

  /**
   * Returns the first common [Node] of [head1] and [head2] if one exists. O(A + B) time, O(1) space, A & B are the size
   * of each list.
   *
   * Other approaches:
   * - Compare each node in head1 with each node in head2. Super simple but time increases to O(A * B).
   * - Create a set containing each node. Same time but requires O(A + B) space.
   */
  fun q0207aIntersection(head1: Node, head2: Node): Node? {
    if (head1 == head2) return head1

    var lengthDifference = q0207aGetSizeDiff(head1, head2)

    var list1 = head1
    var list2 = head2

    if (lengthDifference > 0) {
      while (lengthDifference != 0) {
        list1 = list1.next!!
        lengthDifference--
      }
    }
    else if (lengthDifference < 0) {
      while (lengthDifference != 0) {
        list2 = list2.next!!
        lengthDifference++
      }
    }

    return q0207aGetIntersectionOfSameSizeLists(list1, list2)
  }

  private fun q0207aGetSizeDiff(head1: Node, head2: Node): Int {
    var result = 0
    var current1 = head1
    var current2 = head2

    while (current1.next != null || current2.next != null) {
      if (current1.next != null) {
        current1 = current1.next!!
        result++
      }
      if (current2.next != null) {
        current2 = current2.next!!
        result--
      }
    }
    return result
  }

  private fun q0207aGetIntersectionOfSameSizeLists(list1: Node, list2: Node): Node? {
    var current1: Node? = list1
    var current2: Node? = list2

    while (current1 != null && current2 != null) {
      if (current1 == current2) return current1
      current1 = current1.next
      current2 = current2.next
    }
    return null
  }

  /**
   * Returns the [Node] at the start of a loop in [head] if a loop exists. O(N) time, O(1) space.
   *
   * Alternate approaches:
   * - Use a set to keep track of visited nodes. When the first duplicate comes up, return it. Increases space to O(N).
   */
  fun q0208LoopDetection(head: Node): Node? {
    if (head.next == null) return null

    var slow = head
    var fast: Node? = head

    while (fast != null) {
      slow = slow.next!!
      fast = fast.next?.next
      if (fast == slow) break
    }
    if (fast == null) return null

    fast = head

    while (fast != slow) {
      fast = fast!!.next
      slow = slow.next!!
    }
    return fast
  }

  class Node(var value: Int) {
    var next: Node? = null

    fun appendToTail(data: Int) {
      var last = this
      while (last.next != null) last = last.next!!
      last.next = Node(data)
    }

    fun appendToHead(data: Int) {
      val oldHead = Node(this.value)
      oldHead.next = this.next
      this.next = oldHead
      this.value = data
    }

    fun appendListToTail(node: Node) {
      var last = this
      while (last.next != null) last = last.next!!
      last.next = node
    }

    fun appendAllToTail(vararg data: Int) {
      var last = this
      while (last.next != null) last = last.next!!

      for (d in data) {
        last.next = Node(d)
        last = last.next!!
      }
    }

    fun size(): Int {
      var result = 0
      var current: Node? = this

      while (current != null) {
        current = current.next
        result++
      }
      return result
    }

    /*
    override fun toString(): String {
      val builder = MyStringBuilder().append("[")

      var current: Node? = this

      while (current != null) {
        builder.append(current.value.toString()).append(", ")
        current = current.next
      }
      return builder.removeEnd(2).append("]").toString()
    }
     */
  }
}