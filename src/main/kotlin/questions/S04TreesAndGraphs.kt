package questions

import array.MyArrayList
import queue.MyQueue
import stack.MyStack
import java.lang.NullPointerException
import java.util.*
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

  /**
   * Returns the next [TreeNode] after [node] in an in-order traversal of a BST containing Node. O(logN) time, O(1)
   * space. Returns null if node is the last in the traversal.
   * NOTE: Assumes that [TreeNode]s have a reference to their parents (which they don't). This doesn't actually work
   * because of that.
   */
  fun q0406Successor(node: TreeNode): TreeNode? {
    if (node.right == null) {
      var parent = q0406GetParent(node)
      while (parent != null && parent.value <= node.value) {
        parent = q0406GetParent(parent)
      }
      return parent
    }

    var current = node.right!!
    while (current.left != null) {
      current = current.left!!
    }
    return current
  }

  private fun q0406GetParent(node: TreeNode): TreeNode? {
    println("Note: q0406GetParent has been called which does not get the parent. The question just assumed that this was a working function.")
    return null
  }

  /**
   * My attempt and reproducing the CtCI solution by following my notes (not copying their code). O(P + D) time, O(P) space.
   *
   * Steps:
   *
   * 1. Create a graph where each project is a vertex and the directed edges represent a dependency from the parent to the dependent. Nodes/Vertices should contain:
   *    - project title (String)
   *    - a list of dependent nodes
   *    - a map from dependent titles to Node
   *    - a count of the number of dependencies (number of projects that must precede it)
   *
   * 2. Create a result data structure of projects (Nodes)
   *
   * 3. Iterate over the vertices in the graph and add any with no dependencies to the result.
   *
   * 4. Iterate over the result as follows:
   *    a) If the project has dependents, decrease their dependencies count by 1
   *    b) Iterate over then project's dependents and add them to the result if their dependency count is 0
   *    Stop iterating over the result when the result insertion index is out of bounds or the current project to perform the above steps is null.
   *
   * 5. Return the result, or null if step 4 iteration was terminated prematurely.
   */
  fun q0407BuildOrder(projects: Array<String>, dependencies: Array<Array<String>>): Array<Q0407Graph.Project>? {
    val graph = q0407CreateGraph(projects, dependencies)
    return q0407GetSortedProjectArray(graph)
  }

  private fun q0407CreateGraph(projects: Array<String>, dependencies: Array<Array<String>>): Q0407Graph {
    val result = Q0407Graph()

    q0407AddProjectsToGraph(result, projects)
    q0407AddDependenciesToGraph(result, dependencies)

    return result
  }

  private fun q0407AddProjectsToGraph(graph: Q0407Graph, projects: Array<String>) {
    for (project in projects) {
      graph.addProject(project)
    }
  }

  private fun q0407AddDependenciesToGraph(graph: Q0407Graph, dependencies: Array<Array<String>>) {
    for (pair in dependencies) {
      val parent = pair[0]
      val dependent = pair[1]

      graph.addDependency(parent, dependent)
    }
  }

  private fun q0407GetSortedProjectArray(graph: Q0407Graph): Array<Q0407Graph.Project>? {
    val result = Array(graph.projects.size) { Q0407Graph.Project("") }
    val projects = graph.projects

    var resultInsertionIndex = q0407AddProjectsWithNoDependenciesToResult(result, 0, projects)
    var resultProcessIndex = 0

    while (resultInsertionIndex < result.size) {
      val current = result[resultProcessIndex]
      if (current.name == "") return null

      val dependents = current.dependents
      for (dependent in dependents) dependent.dependencyCount--

      resultInsertionIndex = q0407AddProjectsWithNoDependenciesToResult(result, resultInsertionIndex, dependents)

      resultProcessIndex++
    }
    return result
  }

  private fun q0407AddProjectsWithNoDependenciesToResult(result: Array<Q0407Graph.Project>, resultInsertionIndex: Int, projects: ArrayList<Q0407Graph.Project>): Int {
    var resultIndex = resultInsertionIndex

    for (project in projects) {
      if (project.dependencyCount == 0) {
        result[resultIndex] = project
        resultIndex++
      }
    }
    return resultIndex
  }

  class Q0407Graph {
    val projects = ArrayList<Project>()
    private val projectIdToProjectMap = HashMap<String, Project>()

    fun addIfNewAndGetProject(name: String): Project {
      if (name == "") throw IllegalArgumentException()

      if (!projectIdToProjectMap.containsKey(name)) {
        val project = Project(name)
        projects.add(project)
        projectIdToProjectMap.put(name, project)
      }
      return projectIdToProjectMap[name]!!
    }

    fun addProject(name: String) {
      addIfNewAndGetProject(name)
    }

    fun addDependency(parent: String, dependent: String) {
      val parentProject = addIfNewAndGetProject(parent)
      val dependentProject = addIfNewAndGetProject(dependent)

      parentProject.addDependent(dependentProject)
    }

    data class Project(val name: String) {
      val dependents = ArrayList<Project>()
      val dependentIdToProjectMap = HashMap<String, Project>()
      var dependencyCount = 0

      fun addDependent(project: Project) {
        if (!dependentIdToProjectMap.containsKey(project.name)) {
          dependents.add(project)
          dependentIdToProjectMap.put(project.name, project)
          project.dependencyCount++
        }
      }
    }
  }

  fun testingInOrderTraversal(tree: TreeNode): String {
    val builder = StringBuilder()
    inOrderHelper(tree, builder)
    return builder.toString()
  }

  private fun inOrderHelper(node: TreeNode?, builder: StringBuilder) {
    if (node == null) return

    inOrderHelper(node.left, builder)
    builder.append(node.value.toString())
    inOrderHelper(node.right, builder)
  }

  /**
   * TODO
   */
  fun q0408FirstCommonAncestor(tree: TreeNode, node1: TreeNode, node2: TreeNode): TreeNode? {
    val result = searchSubtreeForTargets(tree, node1, node2)
    return result.commonAncestor
  }

  private fun searchSubtreeForTargets(currentNode: TreeNode?, targetNode1: TreeNode, targetNode2: TreeNode): CommonAncestorResult {
    if (currentNode == null) return CommonAncestorResult()

    val leftSearch = searchSubtreeForTargets(currentNode.left, targetNode1, targetNode2)

    var foundNode1 = leftSearch.foundNode1 || currentNode == targetNode1
    var foundNode2 = leftSearch.foundNode2 || currentNode == targetNode2
    var commonAncestor = leftSearch.commonAncestor

    if (foundNode1 && foundNode2) {
      return CommonAncestorResult(foundNode1, foundNode2, commonAncestor ?: currentNode)
    }

    val rightSearch = searchSubtreeForTargets(currentNode.right, targetNode1, targetNode2)

    foundNode1 = foundNode1 || rightSearch.foundNode1
    foundNode2 = foundNode2 || rightSearch.foundNode2
    commonAncestor = commonAncestor ?: rightSearch.commonAncestor

    if (foundNode1 && foundNode2) {
      return CommonAncestorResult(foundNode1, foundNode2, commonAncestor ?: currentNode)
    }

    return CommonAncestorResult(foundNode1, foundNode2, null)
  }

  private fun updateAncestorResult(): CommonAncestorResult {
    TODO()
  }

  private fun q0408GetEarliestNode(currentNode: TreeNode?, targetNode1: TreeNode, targetNode2: TreeNode, result: CommonAncestorResult) {
    if (currentNode == null) return

    q0408GetEarliestNode(currentNode.left, targetNode1, targetNode2, result)
    q0408GetEarliestNode(currentNode.right, targetNode1, targetNode2, result)

    when (currentNode) {
      targetNode1 -> result.foundNode1 = true
      targetNode2 -> result.foundNode2 = true
    }

    if (result.foundNode1 && result.foundNode2) {
      if (result.commonAncestor == null) result.commonAncestor = currentNode
    }
  }

  private data class CommonAncestorResult(var foundNode1: Boolean = false, var foundNode2: Boolean = false, var commonAncestor: TreeNode? = null)



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

    override fun equals(other: Any?): Boolean {
      if (other is TreeNode) return other.value == value

      return false
    }

    override fun toString(): String {
      return value.toString()
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