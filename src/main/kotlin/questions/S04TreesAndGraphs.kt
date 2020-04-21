package questions

import array.MyArrayList
import queue.MyQueue
import stack.MyStack
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.abs
import kotlin.math.max

class S04TreesAndGraphs {

  /**
   * Uses a BFS to find a route between [s] and [e].
   */
  fun q0401aRouteBetweenNodes(s: GraphNode, e: GraphNode): Boolean {
    val visited = HashSet<GraphNode>()
    val queue = MyQueue<GraphNode>()
    queue.enqueue(s)

    while (queue.isNotEmpty()) {
      val current = queue.dequeue()!! // guaranteed by loop
      if (current == e) return true
      visited.add(current)
      for (node in current.children) if (!visited.contains(node)) queue.enqueue(node)
    }
    return false
  }

  /**
   * Uses a DFS to find a route between [s] and [e].
   */
  fun q0401bRouteBetweenNodes(s: GraphNode, e: GraphNode): Boolean {
    val visited = HashSet<GraphNode>()
    val stack = MyStack<GraphNode>()
    stack.push(s)

    while (stack.isNotEmpty()) {
      val current = stack.pop()!! // guaranteed by loop
      if (current == e) return true
      visited.add(current)
      for (node in current.children) if (!visited.contains(node)) stack.push(node)
    }
    return false
  }

  /**
   * Creates a BST of minimal height from [array] assuming it's sorted in ascending order. O(N) time, O(1) additional space.
   */
  fun q0402MinimalTree(array: IntArray): TreeNode {
    return q0402Recur(array, 0, array.size - 1)!!
  }

  private fun q0402Recur(array: IntArray, leftIndex: Int, rightIndex: Int): TreeNode? {
    if (leftIndex > rightIndex) return null

    val mid = (leftIndex + rightIndex) / 2
    val node = TreeNode(array[mid])
    node.left = q0402Recur(array, leftIndex, mid - 1)
    node.right = q0402Recur(array, mid + 1, rightIndex)
    return node
  }

  private data class Q0402Node(val value: Int, var left: Q0402Node?, var right: Q0402Node?)

  /**
   * Returns an [ArrayList] of [LinkedList] of all the [TreeNode] at each depth in [tree] using a modified DFS approach.
   * O(N) time, O(N) space.
   *
   * Other approaches:
   * - [q0403bListOfDepths] BFS approach
   */
  fun q0403aListOfDepths(tree: TreeNode): ArrayList<LinkedList<TreeNode>> {
    val result = ArrayList<LinkedList<TreeNode>>()
    q0402aDfs(tree, result, 0)
    return result
  }

  private fun q0402aDfs(node: TreeNode?, result: ArrayList<LinkedList<TreeNode>>, depth: Int) {
    if (node == null) return

    if (result.size == depth) result.add(LinkedList())

    result[depth].add(node)
    q0402aDfs(node.left, result, depth + 1)
    q0402aDfs(node.right, result, depth + 1)
  }

  /**
   * Returns an [ArrayList] of [LinkedList] of all the [TreeNode] at each depth in [tree] using a modified BFS approach.
   * O(N) time, O(N) space.
   *
   * Other approaches:
   * - [q0403bListOfDepths] DFS approach
   */
  fun q0403bListOfDepths(tree: TreeNode): ArrayList<LinkedList<TreeNode>> {
    val result = ArrayList<LinkedList<TreeNode>>()

    var currentList = LinkedList<TreeNode>()
    currentList.add(tree)

    while (currentList.size > 0) {
      result.add(currentList)
      val parentList = currentList

      currentList = LinkedList()

      for (parent in parentList) {
        if (parent.left != null) currentList.add(parent.left!!)
        if (parent.right != null) currentList.add(parent.right!!)
      }
    }
    return result
  }

  /**
   * Returns true if [tree] is balanced. O(N) time, O(H) space, N = number of nodes, H = height of tree.
   */
  fun q0404CheckBalanced(tree: TreeNode): Boolean {
    return q0404GetHeight(tree) >= -1
  }

  private fun q0404GetHeight(node: TreeNode?): Int {
    if (node == null) return -1

    val leftHeight = q0404GetHeight(node.left)
    if (leftHeight == Int.MIN_VALUE) return Int.MIN_VALUE

    val rightHeight = q0404GetHeight(node.right)
    if (rightHeight == Int.MIN_VALUE) return Int.MIN_VALUE

    val heightDiff = abs(leftHeight - rightHeight)

    return if (heightDiff > 1) Int.MIN_VALUE else max(leftHeight, rightHeight) + 1
  }

  /**
   * Returns true if [tree] is a BST using min/max values of trees. O(N) time, O(H) space, where H = height of tree.
   *
   * Other approaches:
   * - [q0405bIsValidBst] in-order traversal approach. Simpler but not as robust (accuracy not guaranteed if duplicate
   * values are present).
   */
  fun q0405aIsValidBst(tree: TreeNode): Boolean {
    return q0405aIsValidNode(tree, Int.MIN_VALUE, Int.MAX_VALUE)
  }

  private fun q0405aIsValidNode(node: TreeNode?, min: Int, max: Int): Boolean {
    if (node == null) return true

    if (node.value >= max || node.value < min) return false

    val leftValid = q0405aIsValidNode(node.left, min, node.value)
    if (!leftValid) return false

    val rightValid = q0405aIsValidNode(node.right, node.value, max)
    if (!rightValid) return false

    return true
  }

  /**
   * Returns true if [tree] is a BST by creating an array using in-order traversal and checking if the array is sorted
   * in ascending order. O(N) time, O(N) space.
   * CAUTION: Can give false positive if tree contains duplicate values.
   *
   * Other approaches:
   * - [q0405aIsValidBst] min/max values of subtree approach. Can handle duplicates and uses less space.
   */
  fun q0405bIsValidBst(tree: TreeNode): Boolean {
    val result = MyArrayList<Int>()
    q0405bInOrderFillResult(tree, result)

    var prev = Int.MIN_VALUE

    for (i in result) {
      if (prev > i) return false
      prev = i
    }
    return true
  }

  private fun q0405bInOrderFillResult(node: TreeNode?, result: MyArrayList<Int>) {
    if (node == null) return

    q0405bInOrderFillResult(node.left, result)
    result.add(node.value)
    q0405bInOrderFillResult(node.right, result)
  }



  data class GraphNode(var value: Int, var children: ArrayList<GraphNode> = ArrayList()) {
    override fun toString(): String {
      return value.toString()
    }

    override fun equals(other: Any?): Boolean {
      if (other !is GraphNode) return false

      if (other.value != value) return false

      for (i in 0 until other.children.size) {
        if (other.children[i].value != children[i].value) return false
      }
      return true
    }

    override fun hashCode(): Int {
      val prime = 31

      var result = prime * value.hashCode()
      for (node in children) result = result * prime + node.value.hashCode()

      return result
    }
  }

  data class TreeNode(var value: Int, var left: TreeNode? = null, var right: TreeNode? = null) {
    fun printInOrder() {
      printInOrderHelper(this)
    }

    private fun printInOrderHelper(node: TreeNode?) {
      if (node != null) {
        printInOrderHelper(node.left)
        println(node.value)
        printInOrderHelper(node.right)
      }
    }
  }

  class Graph {
    val nodeList = ArrayList<GraphNode>()

    fun containsValue(value: Int): Boolean {
      return getNode(value) != null
    }

    fun containsNode(node: GraphNode): Boolean {
      return nodeList.contains(node)
    }

    fun isEmpty(): Boolean {
      return nodeList.isEmpty()
    }

    fun addNode(value: Int) {
      nodeList.add(GraphNode(value))
    }

    fun addEdge(from: Int, to: Int) {
      val fromNode = getNode(from) ?: throw NullPointerException()
      val toNode = getNode(to) ?: throw NullPointerException()
      fromNode.children.add(toNode)
    }

    fun getNode(value: Int): GraphNode? {
      for (node in nodeList) {
        if (node.value == value) return node
      }
      return null
    }
  }
}