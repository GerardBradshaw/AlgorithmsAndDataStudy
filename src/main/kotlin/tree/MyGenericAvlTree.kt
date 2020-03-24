package tree

import array.MyStringBuilder
import stack.MyStack

class MyGenericAvlTree<T : Comparable<T>> : Iterable<T> {

  // ---------------- Member variables ----------------

  private var root: Node<T>? = null
  private var size = 0


  // ---------------- Public fun ----------------

  /**
   * Adds [data] to the tree.
   *
   * Efficiency: O(log(n)) time, O(1) space, n = number of nodes
   */
  fun insert(data: T) {
    var current = root
    val newNode = Node(data)

    if (current == null) insertNewIntoEmptyTree(newNode)

    else {
      while (current != null) {
        if (data >= current.value) {
          if (current.right == null) {
            insertNewToRightOfNode(newNode, current)
            return
          }
          else current = current.right
        }

        else if (data < current.value) {
          if (current.left == null) {
            insertNewToLeftOfNode(newNode, current)
            return
          }
          else current = current.left
        }
      }
    }
  }

  /**
   * Removes all instances of [data] from the tree if they exist.
   *
   * Efficiency: O(log(n)) time, O() space, n = number of nodes
   */
  fun delete(data: T): Boolean {
    var current: Node<T>? = root

    while (current != null) {
      if (current.value == data) {
        deleteNode(current)
        return true
      }
      current = if (data > current.value) current.right else current.left
    }
    return false
  }

  /**
   * Returns true if [data] is in the tree.
   *
   * Efficiency: O(log(n)) time, O(1) space, n = number of nodes
   */
  fun contains(data: T): Boolean {
    var current = root
    var currentValue: T

    while (current != null) {
      currentValue = current.value

      if (data == currentValue) return true

      if (data > currentValue) current = current.left
      else if (data < currentValue) current = current.right
    }
    return false
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun isEmpty(): Boolean {
    return root == null || size == 0
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun isNotEmpty(): Boolean {
    return root != null || size != 0
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun size(): Int {
    return size
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  fun getRoot(): Node<T>? {
    return root
  }

  /**
   * Prints the tree in order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of nodes
   */
  fun printInOrder() {
    val node = root
    if (node != null) printInOrderHelper(node)
    else println("empty")
  }

  /**
   * Prints the tree in post-order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of nodes
   */
  fun printPostOrder() {
    val node = root
    if (node != null) printPostOrderHelper(node)
    else println("empty")
  }

  /**
   * Prints the tree in pre-order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of nodes
   */
  fun printPreOrder() {
    val node = root
    if (node != null) printPreOrderHelper(node)
    else println("empty")
  }

  override fun equals(other: Any?): Boolean {
    if (other is MyAvlTree) {
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
    val iterator: Iterator<T> = iterator()
    val builder = MyStringBuilder()

    if (iterator.hasNext()) builder.append("[").append(iterator.next().toString())
    else return builder.append("[]").toString()

    while (iterator.hasNext()) {
      builder.append(", ").append(iterator.next().toString())
    }
    return builder.append("]").toString()
  }

  override fun iterator(): Iterator<T> {
    return object : Iterator<T> {
      val stack: MyStack<Node<T>> = MyStack()

      init {
        var current = root
        while (current != null) {
          stack.push(current)
          current = current.left
        }
      }

      override fun hasNext(): Boolean {
        return !stack.isEmpty()
      }

      override fun next(): T {
        val node = stack.pop()!! // not null while hasNext is true
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


  // ---------------- Helpers ----------------

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun printInOrderHelper(node: Node<T>) {
    node.left?.let { printInOrderHelper(it) }
    println(node.value)
    node.right?.let { printInOrderHelper(it) }
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun printPostOrderHelper(node: Node<T>) {
    println(node.value)
    node.right?.let { printPostOrderHelper(it) }
    node.left?.let { printPostOrderHelper(it) }
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun printPreOrderHelper(node: Node<T>) {
    println(node.value)
    node.left?.let { printPreOrderHelper(it) }
    node.right?.let { printPreOrderHelper(it) }
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun insertNewIntoEmptyTree(newNode: Node<T>) {
    root = newNode
    size++
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun insertNewToRightOfNode(newNode: Node<T>, node: Node<T>) {
    node.right = newNode
    newNode.parent = node
    size++
    balanceTree(newNode)
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun insertNewToLeftOfNode(newNode: Node<T>, node: Node<T>) {
    node.left = newNode
    newNode.parent = node
    size++
    balanceTree(newNode)
  }

  /**
   * Balances the tree around [node] and continues balancing up to the root if [fullBalance] is true.
   *
   * Efficiency: Not full-balance O(1) time, O(1) space. Full-balance O(log(n)) time, O(1) space, n = number of nodes
   */
  private fun balanceTree(node: Node<T>?, fullBalance: Boolean = false) {
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

    var currentChild: Node<T>? = node.parent
    var current: Node<T>? = currentChild?.parent

    if (currentChild != null && current == null) {
      current = if (node == currentChild.left) currentChild.right else currentChild.left
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

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun leftRotation(pivotChild: Node<T>?) {
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

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun rotationLL(a: Node<T>) {
    /*
        A
       /          B
      B     ->   / \
     /          C   A
    C
     */

    val parent = a.parent
    val b = a.left
    val c = b?.left
    val bR = b?.right

    if (parent != null) {
      when {
        parent.left == a -> parent.left = b
        parent.right == a -> parent.right = b
        else -> return
      }
    }
    else root = b

    b?.parent = parent
    a.parent = b
    b?.left = c
    b?.right = a
    a.left = bR
    bR?.parent = a
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun rotationLR(a: Node<T>) {
    /*
        A
       /          C
      B     ->   / \
       \        B   A
        C
     */

    val parent = a.parent
    val b = a.left
    val c = b?.right
    val cL = c?.left
    val cR = c?.right

    if (parent != null) {
      when {
        parent.left == a -> parent.left = c
        parent.right == a -> parent.right = c
        else -> root = c
      }
    }

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

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun rightRotation(pivotChild: Node<T>?) {
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

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun rotationRR(a: Node<T>) {
    /*
     A
      \            B
       B     ->   / \
        \        A   C
         C
    */

    val parent = a.parent
    val b = a.right
    val c = b?.right
    val bL = b?.left

    if (parent != null) {
      if (parent.left == a) parent.left = b
      else if (parent.right == a) parent.right = b
    }
    else root = b

    b?.parent = parent
    a.parent = b
    a.right = bL
    bL?.parent = a
    b?.left = a
    b?.right = c
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun rotationRL(a: Node<T>) {
    /*
    A
     \            C
      B     ->   / \
     /          A   B
    C
    */
    val parent = a.parent
    val b = a.right
    val c = b?.left
    val cL = c?.left
    val cR = c?.right

    if (parent != null) {
      if (parent.left == a) parent.left = c
      else if (parent.right == a) parent.right = c
    }
    else root = c

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

  /**
   * Links [parent] to [child] and vice-versa. If [parent] is null, [child] is made the root. A balance is performed
   * afterwards if [balance] is true.
   *
   * Efficiency: No balance O(1) time, O(1) space. With balance O(log(n)) time, O(1) space, n = number of nodes.
   */
  private fun linkParentToChild(parent: Node<T>?, child: Node<T>, balance: Boolean = true) {
    if (parent == null) {
      root = child
      child.parent = null
    }
    else {
      child.parent = parent
      if (child.value < parent.value) parent.left = child
      else parent.right = child
    }
    if (balance) balanceTree(child, true)
  }

  /**
   * Efficiency: O(1) time, O(1) space
   */
  private fun removeReferencesFromNode(xNode: Node<T>) {
    xNode.parent = null
    xNode.left = null
    xNode.right = null
  }
  
  private fun deleteLeaf(xNode: Node<T>) {
    val parent = xNode.parent
    val parentLeft = parent?.left
    val parentRight = parent?.right

    if (parent == null) root = null

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
    val left = root?.left
    val right = root?.right

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

  private fun balanceFromSubTree(subTree: Node<T>?) {
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

  private fun deleteNode(xNode: Node<T>) {
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
      if (childRight == null) deleteLeaf(xNode)
      else linkParentToChild(parent, childRight)
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
  }

  private fun insertSubTree(subTree: Node<T>?) {
    if (subTree == null) return
    insert(subTree.value)

    val left = subTree.left
    val right = subTree.right

    if (left != null) insertSubTree(left)
    if (right != null) insertSubTree(right)
  }


  // ---------------- Node class ----------------

  data class Node<T>(var value: T, var parent: Node<T>? = null) {
    var left: Node<T>? = null
    var right: Node<T>? = null

    override fun toString(): String {
      return value.toString()
    }

    override fun equals(other: Any?): Boolean {
      if (other !is Node<*>) return false
      return other.value == value
          && other.left?.value == left?.value
          && other.right?.value == right?.value
          && other.parent?.value == parent?.value
    }

    override fun hashCode(): Int {
      val prime = 31

      var result = prime + value.hashCode()
      result = result * prime + parent?.value.hashCode()
      result = result * prime + left?.value.hashCode()
      result = result * prime + right?.value.hashCode()

      return result
    }

    fun getBalanceFactor(): Int {
      return getLeftHeight() - getRightHeight()
    }

    private fun getLeftHeight(): Int {
      val tempLeft = left
      if (tempLeft != null) return maxOf(tempLeft.getLeftHeight(), tempLeft.getRightHeight()) + 1
      else return 0
    }

    private fun getRightHeight(): Int {
      val tempRight = right
      if (tempRight != null) return maxOf(tempRight.getLeftHeight(), tempRight.getRightHeight()) + 1
      else return 0
    }
  }
}