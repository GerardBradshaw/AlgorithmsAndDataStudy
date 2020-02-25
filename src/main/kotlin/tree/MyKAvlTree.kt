package tree

import java.io.InvalidObjectException
import java.lang.NullPointerException

class MyKAvlTree {

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
      if (current.value == value)
        deleteNode(current)

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


  // ---------------- Helpers ----------------

  private fun balanceTree(insertedNode: Node) {
    val c = insertedNode
    val b: Node? = c.parent
    val a: Node? = b?.parent

    var current: Node? = a
    var currentChild = b

    while (current != null) {
      val balanceFactor = current.getBalanceFactor()

      // Left-heavy if balanceFactor == 2
      if (balanceFactor == 2) {
        val childBalFactor = currentChild?.getBalanceFactor()

        if (childBalFactor == 1) {
          println("LL rotation to insert ${insertedNode.value}")
          rotationLL(current)
          return
        } else if (childBalFactor == -1) {
          println("LR rotation to insert ${insertedNode.value}")
          rotationLR(current)
          return
        } else return
      }

      // Right-heavy if balanceFactor == -2
      else if (balanceFactor == -2) {
        val childBalFactor = currentChild?.getBalanceFactor()

        if (childBalFactor == 1) {
          println("RL rotation to insert ${insertedNode.value}")
          rotationRL(current)
          return
        } else if (childBalFactor == -1) {
          println("RR rotation to insert ${insertedNode.value}")
          rotationRR(current)
          return
        } else return
      }
      currentChild = current
      current = current.parent
    }

    /*
while (b != null && a != null) {
  val aBal = a.getBalanceFactor()
  val bBal = b.getBalanceFactor()

  if (aBal + bBal == -2) {
    doRRRotation(a)
    println("RR rotation")
    return
  }
  else if (aBal + bBal == 2) {
    doLLRotation(a)
    println("LL rotation")
    return
  }
  else if (aBal - bBal == 2) {
    doLRRotation(a)
    println("LR rotation")
    return
  }
  else if (-aBal + bBal == 2) {
    doRLRotation(a)
    println("RL rotation")
    return
  }
  else if (aBal + bBal == 0)
    return

  b = b.parent
  a = b?.parent
}
 */
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

  private fun deleteLeaf(node: Node) {
    val parent = node.parent

    if (parent != null) {
      if (parent.left == node)
        parent.left = null
      else if (parent.right == node)
        parent.right = null
    }
    else head = null
  }

  private fun deleteNodeWithOnlyRightChild(node: Node, right: Node) {
    if (node.right != right)
      throw InvalidObjectException()

    val parent = node.parent

    if (parent != null) {
      if (parent.left == node)
        parent.left = right
      else if (parent.right == node)
        parent.right = right
    }
    else head = right
  }

  private fun deleteNodeWithOnlyLeftChild(node: Node, left: Node) {
    if (node.left != left)
      throw InvalidObjectException()

    val parent = node.parent

    if (parent != null) {
      if (parent.left == node)
        parent.left = left
      else if (parent.right == node)
        parent.right = left
    }
    else head = left
  }

  private fun deleteNodeWithTwoChildren(node: Node) {

  }

  private fun deleteNode(node: Node) {
    val left = node.left
    val right = node.right

    if (left == null)
      if (right == null)
        deleteLeaf(node)
      else
        deleteNodeWithOnlyRightChild(node, right)

    else if (right == null)
      deleteNodeWithOnlyLeftChild(node, left)

    else {
      deleteNodeWithTwoChildren(node)
    }

    node.parent = null
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