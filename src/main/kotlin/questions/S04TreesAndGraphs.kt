package questions

import queue.MyQueue
import stack.MyStack
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.HashSet

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