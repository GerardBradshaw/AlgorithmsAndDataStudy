package tree

import java.lang.NullPointerException
import java.lang.StringBuilder
import java.util.*

class MyKAvlTree : Iterable<Int> {

  // ---------------- Member variables ----------------

  private var head: Node? = null
  private var size = 0


  // ---------------- Public methods ----------------

  fun insert(value: Int) {
    println("Inserting $value")
    var current = head

    if (current == null) {
      head = Node(value)
      size++
      return
    }

    val newNode = Node(value)
    while (current != null) {
      if (value >= current.value) {

        if (current.right == null) {
          current.right = newNode
          newNode.parent = current
          size++

          balanceTree(newNode)
          break
        }

        else current = current.right


      }
      else if (value < current.value) {

        if (current.left == null) {
          current.left = newNode
          newNode.parent = current
          size++

          balanceTree(newNode)
          break
        }

        else current = current.left
      }
    }
  }

  fun delete(value: Int) {
    var current = head

    if (current == null)
      throw NullPointerException()

    while (current != null) {
      if (current.value == value) deleteNode(current)

      current = if (value > current.value)
        current.right
      else
        current.left
    }
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

  fun getHead(): Node? {
    return head
  }

  fun printInOrder() {
    return printInOrder(head)
  }


  // ---------------- Helpers ----------------

  private fun balanceTree(node: Node?, fullBalance: Boolean = false) {
    /*
    Scenarios:
    1. Node has parent and grandparent
      current
         |
    currentChild
         |
        node

    2. Node has parent but no grandparent

     */

    if (node == null) return

    var currentChild: Node? = node.parent
    var current: Node? = currentChild?.parent

    if (currentChild != null && current == null) {
      if (node == currentChild.left) current = currentChild.right
      else current = currentChild.left
    }

    while (current != null) {
      val balanceFactor = current.getBalanceFactor()

      if (balanceFactor >= 2) {
        leftRotation(currentChild)
        if (fullBalance) balanceTree(currentChild)
        else return
      }
      else if (balanceFactor <= -2) {
        rightRotation(currentChild)
        if (fullBalance) balanceTree(currentChild)
        else return
      }
      else {
        currentChild = current
        current = current.parent
      }
    }
  }

  private fun leftRotation(pivotChild: Node?) {
    /*
    Scenarios

    LL rotation (pivotChild balance factor = 1)
                pivot
                /   \
      pivotChild    ...
              /
            ...

              OR

    LR rotation (pivotChild balance factor = -1)
                 pivot
                /     \
      pivotChild      ...
               \
               ...
     */

    val pivot = pivotChild?.parent

    if (pivot != null) {
      val pivotChildBalanceFactor = pivotChild.getBalanceFactor()

      if (pivotChildBalanceFactor == 1) rotationLL(pivot)
      else rotationLR(pivot)
    }
  }

  private fun rotationLL(a: Node) {
    val parent = a.parent
    val b = a.left
    val c = b?.left
    val bR = b?.right

    if (parent != null) {
      if (parent.left == a)
        parent.left = b
      else if (parent.right == a)
        parent.right = b
      else return
    }
    else head = b

    b?.parent = parent
    a.parent = b
    b?.left = c
    b?.right = a
    a.left = bR
    bR?.parent = a
  }

  private fun rotationLR(a: Node) {
    val parent = a.parent
    val b = a.left
    val c = b?.right
    val cL = c?.left
    val cR = c?.right

    if (parent != null)
      if (parent.left == a)
        parent.left = c
      else if (parent.right == a)
        parent.right = c
      else head = c

    c?.parent = parent
    a.parent = c
    b?.parent = c
    c?.left = b
    c?.right = a
    b?.right = cL
    a.left = cR
    cL?.parent = b
    cR?.parent = a
  }

  private fun rightRotation(pivotChild: Node?) {
    /*
    Scenarios

    RR rotation (pivotChild balance factor = -1)
                pivot
                /   \
              ...   pivotChild
                     \
                    ...

              OR

    RL rotation (pivotChild balance factor = 1)
                 pivot
                /     \
              ...     pivotChild
                     /
                   ...
 */

    val pivot = pivotChild?.parent

    if (pivot != null) {
      val childBalFactor = pivotChild.getBalanceFactor()

      if (childBalFactor == 1) rotationRL(pivot)
      else rotationRR(pivot)
    }
  }

  private fun rotationRR(a: Node) {
    val parent = a.parent
    val b = a.right
    val c = b?.right
    val bL = b?.left

    if (parent != null) {
      if (parent.left == a)
        parent.left = b
      else if (parent.right == a)
        parent.right = b
    }
    else head = b

    b?.parent = parent
    a.parent = b
    a.right = bL
    bL?.parent = a
    b?.left = a
    b?.right = c
  }

  private fun rotationRL(a: Node) {
    val parent = a.parent
    val b = a.right
    val c = b?.left
    val cL = c?.left
    val cR = c?.right

    if (parent != null) {
      if (parent.left == a)
        parent.left = c
      else if (parent.right == a)
        parent.right = c
    }
    else head = c

    c?.parent = parent
    a.parent = c
    b?.parent = c
    c?.left = a
    c?.right = b
    a.right = cL
    b?.left = cR
    cL?.parent = a
    cR?.parent = b
  }

  private fun linkParentToChild(parent: Node?, child: Node) {
    if (parent == null) {
      head = child
      child.parent = null
    }
    else {
      child.parent = parent
      if (child.value < parent.value)
        parent.left = child
      else
        parent.right = child
    }

    balanceTree(child, true)
  }

  private fun removeReferencesFromNode(xNode: Node) {
    xNode.parent = null
    xNode.left = null
    xNode.right = null
  }

  private fun deleteLeaf(xNode: Node) {
    val parent = xNode.parent
    val parentLeft = parent?.left
    val parentRight = parent?.right

    if (parent == null) head = null

    else {
      if (parentLeft == xNode) {
        parent.left = null
        balanceFromSubTree(parentRight)
        balanceFromHead()
      }

      else {
        parent.right = null
        balanceFromSubTree(parentLeft)
        balanceFromHead()
      }
    }
    removeReferencesFromNode(xNode)
  }

  private fun balanceFromHead() {
    val left = head?.left
    val right = head?.right

    if (left != null) {
      val leftLeft = left.left
      val leftRight = left.right
      when {
        leftLeft != null -> balanceTree(leftLeft)
        leftRight != null -> balanceTree(leftRight)
      }
    }
    if (right != null) {
      val rightLeft = right.left
      val rightRight = right.right
      when {
        rightLeft != null -> balanceTree(rightLeft)
        rightRight != null -> balanceTree(rightRight)
      }
    }
  }

  private fun balanceFromSubTree(subTree: Node?) {
    if (subTree != null) {
      val left = subTree.left
      val right = subTree.right

      when {
        left != null -> balanceTree(left, true)
        right != null -> balanceTree(right, true)
        else -> balanceTree(subTree, true)
      }
    }
  }

  private fun deleteNode(xNode: Node) {
    /* Naming scheme (xNode = node to delete)
           parent
             |
           xNode
         /      \
        cL      cR
       /  \    /  \
     cLL cLR cRL  cRR
     */

    val parent = xNode.parent
    val childLeft = xNode.left
    val childRight = xNode.right

    val childLeftRight = childLeft?.right

    if (childLeft == null) {
      if (childRight == null) {
        deleteLeaf(xNode)
      }
      else {
        linkParentToChild(parent, childRight)
      }
    }
    else {
      linkParentToChild(parent, childLeft)
      if (childRight != null) {
        linkParentToChild(childLeft, childRight)
        balanceFromSubTree(childRight)
      }
      if (childLeftRight != null) insertSubTree(childLeftRight)
    }
    removeReferencesFromNode(xNode)
    println("Deleted ${xNode.value}")
  }

  private fun insertSubTree(subTree: Node?) {
    if (subTree == null) return
    insert(subTree.value)

    val left = subTree.left
    val right = subTree.right

    if (left != null) insertSubTree(left)
    if (right != null) insertSubTree(right)
  }

  private fun printInOrder(node: Node?) {
    val left = node?.left
    if (left != null) return printInOrder(left)

    println(node?.value)

    val right = node?.right
    if (right != null) return printInOrder(right)
  }


  // ---------------- Any callbacks ----------------

  override fun equals(other: Any?): Boolean {
    if (other is MyKAvlTree) {
      if (other.size() != size) return false

      val otherIterator = other.iterator()
      val thisIterator = iterator()

      while (otherIterator.hasNext() && thisIterator.hasNext()) {
        if (otherIterator.next() != thisIterator.next()) return false
      }

      return otherIterator.hasNext() == thisIterator.hasNext()
    }
    return false
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    val iterator: Iterator<Int> = iterator()
    val builder = StringBuilder()

    if (iterator.hasNext()) builder.append("[").append(iterator.next())
    else return builder.append("empty").toString()

    while (iterator.hasNext()) {
      builder.append(", ").append(iterator.next())
    }

    return builder.append("]").toString()
  }


  // ---------------- Iterator callbacks ----------------

  override fun iterator(): Iterator<Int> {
    return object : Iterator<Int> {
      val stack: Stack<Node> = Stack()

      init {
        var current = head
        while (current != null) {
          stack.push(current)
          current = current.left
        }
      }

      override fun hasNext(): Boolean {
        return !stack.isEmpty()
      }

      override fun next(): Int {
        val node = stack.pop()
        val result = node.value

        val right = node.right

        if (right != null) {
          var current = right
          while (current != null) {
            stack.push(current)
            current = current!!.left
          }
        }
        return result
      }
    }
  }


  // ---------------- Node class ----------------

  data class Node(var value: Int, var parent: Node? = null) {
    var left: Node? = null
    var right: Node? = null

    override fun toString(): String {
      return value.toString()
    }

    fun getLeftHeight(): Int {
      val tempLeft = left
      if (tempLeft != null)
        return maxOf(tempLeft.getLeftHeight(), tempLeft.getRightHeight()) + 1

      return 0
    }

    fun getRightHeight(): Int {
      val tempRight = right
      if (tempRight != null)
        return maxOf(tempRight.getLeftHeight(), tempRight.getRightHeight()) + 1

      return 0
    }

    fun getBalanceFactor(): Int {
      return getLeftHeight() - getRightHeight()
    }
  }
}