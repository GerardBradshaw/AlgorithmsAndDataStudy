package questions

import array.MyArrayList
import queue.MyQueue
import stack.MyStack
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max

class S04TreesAndGraphs {

  /**
   * Uses a BFS to find a route between [s] and [e].
   */
  fun routeBetweenNodes(s: GraphNode, e: GraphNode): Boolean {
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
  fun routeBetweenNodes2(s: GraphNode, e: GraphNode): Boolean {
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
  fun minimalTree(array: IntArray): TreeNode {
    return createNodeFromMid(array, 0, array.size - 1)!!
  }

  private fun createNodeFromMid(array: IntArray, leftIndex: Int, rightIndex: Int): TreeNode? {
    if (leftIndex > rightIndex) return null

    val mid = (leftIndex + rightIndex) / 2
    val node = TreeNode(array[mid])
    node.left = createNodeFromMid(array, leftIndex, mid - 1)
    node.right = createNodeFromMid(array, mid + 1, rightIndex)
    return node
  }

  private data class BasicNode(val value: Int, var left: BasicNode?, var right: BasicNode?)

  /**
   * Returns an [ArrayList] of [LinkedList] of all the [TreeNode] at each depth in [tree] using a modified DFS approach.
   * O(N) time, O(N) space.
   *
   * Other approaches:
   * - [listOfDepths2] BFS approach
   */
  fun listOfDepths(tree: TreeNode): ArrayList<LinkedList<TreeNode>> {
    val result = ArrayList<LinkedList<TreeNode>>()
    dfs(tree, result, 0)
    return result
  }

  private fun dfs(node: TreeNode?, result: ArrayList<LinkedList<TreeNode>>, depth: Int) {
    if (node == null) return

    if (result.size == depth) result.add(LinkedList())

    result[depth].add(node)
    dfs(node.left, result, depth + 1)
    dfs(node.right, result, depth + 1)
  }

  /**
   * Returns an [ArrayList] of [LinkedList] of all the [TreeNode] at each depth in [tree] using a modified BFS approach.
   * O(N) time, O(N) space.
   *
   * Other approaches:
   * - [listOfDepths2] DFS approach
   */
  fun listOfDepths2(tree: TreeNode): ArrayList<LinkedList<TreeNode>> {
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
  fun checkBalanced(tree: TreeNode): Boolean {
    return getHeight(tree) >= -1
  }

  private fun getHeight(node: TreeNode?): Int {
    if (node == null) return -1

    val leftHeight = getHeight(node.left)
    if (leftHeight == Int.MIN_VALUE) return Int.MIN_VALUE

    val rightHeight = getHeight(node.right)
    if (rightHeight == Int.MIN_VALUE) return Int.MIN_VALUE

    val heightDiff = abs(leftHeight - rightHeight)

    return if (heightDiff > 1) Int.MIN_VALUE else max(leftHeight, rightHeight) + 1
  }

  /**
   * Returns true if [tree] is a BST using min/max values of trees. O(N) time, O(H) space, where H = height of tree.
   *
   * Other approaches:
   * - [isValidBstB] in-order traversal approach. Simpler but not as robust (accuracy not guaranteed if duplicate
   * values are present).
   */
  fun isValidBst(tree: TreeNode): Boolean {
    return isValidNode(tree, Int.MIN_VALUE, Int.MAX_VALUE)
  }

  private fun isValidNode(node: TreeNode?, min: Int, max: Int): Boolean {
    if (node == null) return true

    if (node.value >= max || node.value < min) return false

    val leftValid = isValidNode(node.left, min, node.value)
    if (!leftValid) return false

    val rightValid = isValidNode(node.right, node.value, max)
    if (!rightValid) return false

    return true
  }

  /**
   * Returns true if [tree] is a BST by creating an array using in-order traversal and checking if the array is sorted
   * in ascending order. O(N) time, O(N) space.
   * CAUTION: Can give false positive if tree contains duplicate values.
   *
   * Other approaches:
   * - [isValidBst] min/max values of subtree approach. Can handle duplicates and uses less space.
   */
  fun isValidBstB(tree: TreeNode): Boolean {
    val result = MyArrayList<Int>()
    inOrderFillResult(tree, result)

    var prev = Int.MIN_VALUE

    for (i in result) {
      if (prev > i) return false
      prev = i
    }
    return true
  }

  private fun inOrderFillResult(node: TreeNode?, result: MyArrayList<Int>) {
    if (node == null) return

    inOrderFillResult(node.left, result)
    result.add(node.value)
    inOrderFillResult(node.right, result)
  }

  /**
   * Returns the next [TreeNode] after [node] in an in-order traversal of a BST containing Node. O(logN) time, O(1)
   * space. Returns null if node is the last in the traversal.
   * NOTE: Assumes that [TreeNode]s have a reference to their parents (which they don't). This doesn't actually work
   * because of that.
   */
  fun successor(node: TreeNode): TreeNode? {
    if (node.right == null) {
      var parent = getParent(node)
      while (parent != null && parent.value <= node.value) {
        parent = getParent(parent)
      }
      return parent
    }

    var current = node.right!!
    while (current.left != null) {
      current = current.left!!
    }
    return current
  }

  private fun getParent(node: TreeNode): TreeNode? {
    println("Note: q0406GetParent has been called which does not get the parent. The question just assumed that this was a working function.")
    return node
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
  fun buildOrder(projects: Array<String>, dependencies: Array<Array<String>>): Array<ProjectGraph.Project>? {
    val graph = createGraph(projects, dependencies)
    return getSortedProjectArray(graph)
  }

  private fun createGraph(projects: Array<String>, dependencies: Array<Array<String>>): ProjectGraph {
    val result = ProjectGraph()

    addProjectsToGraph(result, projects)
    addDependenciesToGraph(result, dependencies)

    return result
  }

  private fun addProjectsToGraph(graph: ProjectGraph, projects: Array<String>) {
    for (project in projects) {
      graph.addProject(project)
    }
  }

  private fun addDependenciesToGraph(graph: ProjectGraph, dependencies: Array<Array<String>>) {
    for (pair in dependencies) {
      val parent = pair[0]
      val dependent = pair[1]

      graph.addDependency(parent, dependent)
    }
  }

  private fun getSortedProjectArray(graph: ProjectGraph): Array<ProjectGraph.Project>? {
    val result = Array(graph.projects.size) { ProjectGraph.Project("") }
    val projects = graph.projects

    var resultInsertionIndex = addProjectsWithNoDependenciesToResult(result, 0, projects)
    var resultProcessIndex = 0

    while (resultInsertionIndex < result.size) {
      val current = result[resultProcessIndex]
      if (current.name == "") return null

      val dependents = current.dependents
      for (dependent in dependents) dependent.dependencyCount--

      resultInsertionIndex = addProjectsWithNoDependenciesToResult(result, resultInsertionIndex, dependents)

      resultProcessIndex++
    }
    return result
  }

  private fun addProjectsWithNoDependenciesToResult(result: Array<ProjectGraph.Project>, resultInsertionIndex: Int, projects: ArrayList<ProjectGraph.Project>): Int {
    var resultIndex = resultInsertionIndex

    for (project in projects) {
      if (project.dependencyCount == 0) {
        result[resultIndex] = project
        resultIndex++
      }
    }
    return resultIndex
  }

  class ProjectGraph {
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

  /**
   * Returns the first common ancestor of 2 nodes in a Binary Tree. O(N) time, O(N) space.
   */
  fun firstCommonAncestor(tree: TreeNode, node1: TreeNode, node2: TreeNode): TreeNode? {
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

  private data class CommonAncestorResult(var foundNode1: Boolean = false, var foundNode2: Boolean = false, var commonAncestor: TreeNode? = null)

  /**
   * Returns all possible arrays that could have led to the given BST assuming it was created by traversing an array
   * from left to right and inserting each element. O(N^e) time (I have no idea what e is, but it's big!).
   */
  fun bstSequences(bst: TreeNode): ArrayList<LinkedList<Int>> {
    return getLists(bst)
  }

  private fun getLists(node: TreeNode?): ArrayList<LinkedList<Int>> {
    val result = ArrayList<LinkedList<Int>>()

    if (node == null) {
      result.add(LinkedList<Int>())
      return result
    }

    val prefix = LinkedList<Int>().also { it.add(node.value) }

    val leftResult = getLists(node.left)
    val rightResult = getLists(node.right)

    for (leftList in leftResult) {
      for (rightList in rightResult) {
        weave(leftList, rightList, prefix, result)
      }
    }

    return result
  }

  private fun weave(list1: LinkedList<Int>, list2: LinkedList<Int>, prefix: LinkedList<Int>,
                    result: ArrayList<LinkedList<Int>>) {
    if (list1.isEmpty() || list2.isEmpty()) {
      @Suppress("UNCHECKED_CAST")
      val res = prefix.clone() as LinkedList<Int>

      res.addAll(list1)
      res.addAll(list2)
      result.add(res)
      return
    }

    prefix.addLast(list1.removeFirst())
    weave(list1, list2, prefix, result)
    list1.addFirst(prefix.removeLast())

    prefix.addLast(list2.removeFirst())
    weave(list1, list2, prefix, result)
    list2.addFirst(prefix.removeLast())
  }

  /**
   * Returns a random node in a Binary Tree. O(D) time to retrieve random node, where D is the maximum depth of the tree.
   */
  fun randomNode(tree: TreeNodeWithSize): TreeNodeWithSize {
    return tree.getRandomNode()
  }

  class TreeNodeWithSize(var value: Int, var left: TreeNodeWithSize? = null, var right: TreeNodeWithSize? = null, var size: Int = 1) {

    fun getRandomNode(): TreeNodeWithSize {
      val rand = Random()
      val index = rand.nextInt(size)
      return getIthNode(index)
    }

    private fun getIthNode(index: Int): TreeNodeWithSize {
      val leftSize = left?.size ?: 0

      return when {
        leftSize == index -> this
        leftSize > index -> left!!.getIthNode(index)
        else -> right!!.getIthNode(index - leftSize - 1)
      }
    }

    fun insert(data: Int) {
      size++

      if (data < value) {
        if (left != null) left!!.insert(data)
        else left = TreeNodeWithSize(data)
      }
      else {
        if (right != null) right!!.insert(data)
        else right = TreeNodeWithSize(data)
      }
    }

    fun insert(node: TreeNodeWithSize) {
      size += node.size

      if (node.value < value) {
        if (left != null) left!!.insert(node)
        else left = node
      }
      else {
        if (right != null) right!!.insert(node)
        else right = node
      }
    }

    fun delete(data: Int): Boolean {
      return deleteSelfOrKeepSearching(null, data)
    }

    private fun deleteSelfOrKeepSearching(parent: TreeNodeWithSize?, data: Int): Boolean {
      return when {
        data < value -> searchLeftToDelete(data)
        data > value -> searchRightToDelete(data)
        else -> deleteSelf(parent)
      }
    }

    private fun searchLeftToDelete(data: Int): Boolean {
      if (left == null) return false
      size--
      return left!!.deleteSelfOrKeepSearching(this, data)
    }

    private fun searchRightToDelete(data: Int): Boolean {
      if (right == null) return false
      size--
      return right!!.deleteSelfOrKeepSearching(this, data)
    }

    private fun deleteSelf(parent: TreeNodeWithSize?): Boolean {
      if (parent != null) moveRightUpAndRelocateLeft(parent)
      else deleteSelfAmRoot()
      return true
    }

    private fun moveRightUpAndRelocateLeft(parent: TreeNodeWithSize) {
      if (parent.left == this) parent.left = right
      else parent.right = right
      if (left != null) {
        parent.size -= left!!.size
        parent.insert(left!!)
      }
    }

    private fun deleteSelfAmRoot() {
      if (right != null) {
        value = right!!.value
        size--
        right!!.deleteSelfOrKeepSearching(this, right!!.value)
      }
      else if (left != null) {
        value = left!!.value
        size--
        left!!.deleteSelfOrKeepSearching(this, left!!.value)
      }
    }

    fun find(data: Int): TreeNodeWithSize? {
      if (data == value) return this

      return when {
        data < value && left != null -> left!!.find(data)
        data >= value && right != null -> right!!.find(data)
        else -> null
      }
    }

    fun print() {
      if (left != null) left!!.print()
      println(value.toString())
      if (right != null) right!!.print()
    }
  }

  /**
   * Returns the number of paths that sum to a given value. The path does not need to start or end at a root or leaf.
   */
  fun pathsWithSum(bt: TreeNode, value: Int): Int {
    return getSumCountInTree(bt, value).sumCount
  }

  private fun getSumCountInTree(node: TreeNode?, targetSum: Int): Result {
    if (node == null) return Result()

    val leftSums = getSumCountInTree(node.left, targetSum)
    val rightSums = getSumCountInTree(node.right, targetSum)

    val ongoingSums = ArrayList<Int>()
    ongoingSums.add(node.value)

    var sumCount = leftSums.sumCount + rightSums.sumCount
    if (node.value == targetSum) sumCount++

    for (sum in leftSums.ongoingSums) {
      val newSum = node.value + sum
      ongoingSums.add(newSum)
      if (newSum == targetSum) sumCount++
    }

    for (sum in rightSums.ongoingSums) {
      val newSum = node.value + sum
      ongoingSums.add(newSum)
      if (newSum == targetSum) sumCount++
    }

    println("loop ${ongoingSums.size} times")

    return Result(ongoingSums, sumCount)
  }

  fun pathsWithSumB(tree: TreeNode, value: Int): Int {
    val cumSumToCount = HashMap<Int, Int>()
    return getPathCountFromNode(tree, value, 0, cumSumToCount)
  }

  fun getPathCountFromNode(node: TreeNode?, target: Int, cumSum: Int, cumSumToCount: HashMap<Int, Int>): Int {
    if (node == null) return 0

    val cumSumToNode = cumSum + node.value
    val targetCumSum = cumSumToNode - target

    var result = cumSumToCount.getOrDefault(targetCumSum, 0)
    if (cumSumToNode == target) result++

    updateCumSumCount(cumSumToCount, cumSumToNode, 1)
    result += getPathCountFromNode(node.left, target, cumSumToNode, cumSumToCount)
    result += getPathCountFromNode(node.right, target, cumSumToNode, cumSumToCount)
    updateCumSumCount(cumSumToCount, cumSumToNode, -1)

    return result
  }

  private fun updateCumSumCount(cumSumToCount: HashMap<Int, Int>, cumSum: Int, delta: Int) {
    val newCount = cumSumToCount.getOrDefault(cumSum, 0) + delta
    if (newCount == 0) cumSumToCount.remove(cumSum)
    else cumSumToCount.put(cumSum, newCount)
  }


  private data class Result(val ongoingSums: ArrayList<Int> = ArrayList<Int>(), val sumCount: Int = 0)

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