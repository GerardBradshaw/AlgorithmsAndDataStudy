package tree

import array.MyKStringBuilder
import java.lang.Exception

class MyKrbTree<T : Comparable<T>> : Collection<T>, Iterable<T> {

  // ---------------- Member variables ----------------

  private var root: Node<T>? = null
  private var nodeCount = 0


  // ---------------- Tree methods ----------------

  fun getRoot(): Node<T>? {
    return root
  }

  fun insert(element: T) {
    val newNode = Node(element)

    if (root == null) {
      root = newNode
      newNode.isRed = false
      nodeCount++
      return
    }

    var current = root
    var currentElement: T
    var currentLeft: Node<T>?
    var currentRight: Node<T>?

    while (current != null) {
      currentElement = current.element
      currentLeft = current.left
      currentRight = current.right

      if (element >= currentElement) {
        if (currentRight != null) current = currentRight
        else {
          linkParentAndChild(current, newNode)
          nodeCount++
          balance(newNode)
          return
        }
      }
      else if (element < currentElement) {
        if (currentLeft != null) current = currentLeft
        else {
          linkParentAndChild(current, newNode)
          nodeCount++
          balance(newNode)
          return
        }
      }
    }
  }

  fun remove(element: T) {
    if (root == null) return

    var current = root
    var currentElement: T

    while (current != null) {
      currentElement = current.element

      when {
        element == currentElement -> deleteNode(current)
        element > currentElement -> current = current.left
        else -> current = current.right
      }
    }
  }


  // ---------------- Helpers ----------------

  private fun balance(node: Node<T>) {
    val parent = node.parent

    if (parent == null) {
      root?.isRed = false
      return
    }

    if (!parent.isRed) return

    val grandparent = getGrandparent(node)
    val uncle = getUncle(node)

    if (grandparent == null) return
    else if (uncle == null || !uncle.isRed) blackUncleCorrection(node, parent, grandparent, uncle)
    else {
      redUncleCorrection(parent, grandparent, uncle)
      balance(grandparent)
    }
  }

  private fun redUncleCorrection(parent: Node<T>, grandparent: Node<T>, uncle: Node<T>) {
    parent.isRed = false
    grandparent.isRed = true
    uncle.isRed = false
  }

  private fun blackUncleCorrection(node: Node<T>, parent: Node<T>, grandparent: Node<T>, uncle: Node<T>?) {
    if (grandparent.left == parent && parent.left == node) {
      parentAndNodeAreBothLeftOrRightRotation(parent.right, parent, grandparent)
    }
    else if (grandparent.left == parent && parent.right == node) {
      parentIsLeftAndNodeIsRightRotation(node, parent, grandparent)
    }
    else if (grandparent.right == parent && parent.right == node) {
      parentAndNodeAreBothLeftOrRightRotation(parent.left, parent, grandparent)
    }
    else if (grandparent.right == parent && parent.left == node) {
      parentIsRightAndNodeIsLeftRotation(node, parent, grandparent)
    }
  }

  private fun parentAndNodeAreBothLeftOrRightRotation(nodeSibling: Node<T>?, parent: Node<T>, grandparent: Node<T>) {
    /*
    Key:
    GG = greatGrandparent
    G = grandparent
    P = parent
    U = uncle
    NS = nodeSibling
    N = node
    NL = nodeLeft
    NR = nodeRight

          GG
          |                         GG
          G                         |
         / \                        P
        P   U        --->         /   \
       / \                       N     G
      N   NS                   / \    /  \
     / \                      NL NR  NS  U
   NL  NR

                      OR
          GG
          |                         GG
          G                         |
         / \                        P
        U   P        --->         /   \
           / \                   G     N
         NS   N                / \    /  \
             / \              U  NL  NR  NS
           NL  NR
   */

    val greatGrandparent = grandparent.parent

    unlinkParentAndChild(parent, nodeSibling)
    unlinkParentAndChild(grandparent, parent)
    unlinkParentAndChild(grandparent.parent, grandparent)
    linkParentAndChild(parent, grandparent)

    if (greatGrandparent != null) linkParentAndChild(greatGrandparent, parent)
    else root = parent

    if (nodeSibling != null) linkParentAndChild(grandparent, nodeSibling)

    parent.isRed = false
    grandparent.isRed = true
  }

  private fun parentIsLeftAndNodeIsRightRotation(node: Node<T>, parent: Node<T>, grandparent: Node<T>) {
    /*
    Key:
    GG = greatGrandparent
    G = grandparent
    P = parent
    U = uncle
    NS = nodeSibling
    N = node
    NL = nodeLeft
    NR = nodeRight

         GG
         |
         G                      GG
        / \                      |
       P   U        --->         N
      / \                      /   \
    NS   N                   P      G
        / \                 / \    / \
      NL  NR              NS  NL  NR  U
     */

    val nodeLeft = node.left
    val nodeRight = node.right
    val greatGrandparent = grandparent.parent

    unlinkParentAndChild(parent, node)
    unlinkParentAndChild(node, nodeLeft)
    unlinkParentAndChild(node, nodeRight)
    unlinkParentAndChild(grandparent, parent)

    if (greatGrandparent != null) linkParentAndChild(greatGrandparent, node)
    else root = node

    linkParentAndChild(node, parent)
    linkParentAndChild(node, grandparent)

    if (nodeLeft != null) linkParentAndChild(parent, nodeLeft)
    if (nodeRight != null) linkParentAndChild(grandparent, nodeRight)

    grandparent.isRed = true
    node.isRed = false
  }

  private fun parentIsRightAndNodeIsLeftRotation(node: Node<T>, parent: Node<T>, grandparent: Node<T>) {
    /*
    Key:
    GG = greatGrandparent
    G = grandparent
    P = parent
    U = uncle
    NS = nodeSibling
    N = node
    NL = nodeLeft
    NR = nodeRight

         GG
         |
         G                      GG
        / \                      |
       U   P        --->         N
          / \                  /   \
         N  NS               G      P
        / \                 / \    / \
      NL  NR              U   NL  NR  NS
    */

    val nodeLeft = node.left
    val nodeRight = node.right
    val greatGrandparent = grandparent.parent

    unlinkParentAndChild(parent, node)
    unlinkParentAndChild(node, nodeLeft)
    unlinkParentAndChild(node, nodeRight)
    unlinkParentAndChild(grandparent, parent)

    if (greatGrandparent != null) linkParentAndChild(greatGrandparent, node)
    else root = node

    linkParentAndChild(node, parent)
    linkParentAndChild(node, grandparent)

    if (nodeRight != null) linkParentAndChild(parent, nodeRight)
    if (nodeLeft != null) linkParentAndChild(grandparent, nodeLeft)

    grandparent.isRed = true
    node.isRed = false
  }

  private fun getGrandparent(node: Node<T>): Node<T>? {
    return node.parent?.parent
  }

  private fun getUncle(node: Node<T>): Node<T>? {
    val parent = node.parent
    val grandparent = getGrandparent(node)

    if (grandparent != null) {
      if (grandparent.left == parent) return grandparent.right
      else if (grandparent.right == parent) return grandparent.left
      else println("Error retrieving uncle of inserted node. Balance not guaranteed.")
    }
    return null
  }

  private fun unlinkParentAndChild(parent: Node<T>?, child: Node<T>?) {
    if (parent?.left == child) parent?.left = null
    if (parent?.right == child) parent?.right = null
    if (child?.parent == parent) child?.parent = null
  }

  private fun linkParentAndChild(parent: Node<T>, child: Node<T>) {
    if (child.element >= parent.element) parent.right = child
    else parent.left = child
    child.parent = parent
  }

  private fun deleteNode(node: Node<T>) {
    TODO()
  }


  // ---------------- Collection interface methods ----------------

  override val size: Int
    get() = nodeCount

  override fun contains(element: T): Boolean {
    if (root == null) return false

    var current = root
    var currentElement: T

    while (current != null) {
      currentElement = current.element

      current = when {
        element == currentElement -> return true
        element > currentElement -> current.right
        else -> current.left
      }
    }
    return false
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    for (element in elements) {
      if (!contains(element)) return false
    }
    return true
  }

  override fun isEmpty(): Boolean {
    return nodeCount == 0
  }


  // ---------------- Any class methods ----------------

  override fun equals(other: Any?): Boolean {
    if (other !is MyKrbTree<*>) return false
    // Recursively go through the tree?
    // TODO
    return false
  }

  override fun hashCode(): Int {
    // Recursively go through the tree?
    // TODO
    return 0
  }

  override fun toString(): String {
    val node = root
    if (node == null) return "empty"

    else {
      var builder = MyKStringBuilder().append("[")
      builder = toStringBuilder(node, builder)
      builder.append("]")
      return builder.toString()
    }
  }

  private fun toStringBuilder(node: Node<T>, builder: MyKStringBuilder): MyKStringBuilder {
    val left = node.left
    val right = node.right

    if (left != null) toStringBuilder(left, builder)
    builder.append(node.toString()).append(", ")
    if (right != null) toStringBuilder(right, builder)
    return builder
  }


  // ---------------- Iterable interface methods ----------------

  override fun iterator(): Iterator<T> {
    return object : Iterator<T> {
      // Use a stack???
      override fun hasNext(): Boolean {
        //TODO
        return false
      }

      override fun next(): T {
        // TODO
        throw Exception()
      }
    }
  }


  // ---------------- Node class ----------------

  data class Node<T>(var element: T,
                             var parent: Node<T>? = null,
                             var left: Node<T>? = null,
                             var right: Node<T>? = null,
                             var isRed: Boolean = true) {

    override fun toString(): String {
      return element.toString() + if (isRed) "r" else ""
    }
  }
}