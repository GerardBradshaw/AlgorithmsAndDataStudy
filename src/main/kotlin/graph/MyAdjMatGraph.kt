package graph

import array.MyStringBuilder
import hashtable.MyHashMap
import queue.MyQueue
import set.MyHashSet
import stack.MyStack

class MyAdjMatGraph<T>(vertices: List<T>) {

  // ---------------- Member variables ----------------

  private val matrix: Array<IntArray> = Array(vertices.size) { _ -> IntArray(vertices.size)}
  private val vertices: Array<Any>
  private var edgeCount = 0

  val numberOfVertices: Int
    get() = matrix.size

  val numberOfEdges: Int
    get() = edgeCount

  init {
    this.vertices = Array(vertices.size) { index -> vertices[index] as Any }
  }


  // ---------------- Public methods ----------------

  /**
   * Returns true if an edge from [from] to [to] of length [length] is successfully created (length >= 0, vertices exist
   * and are unique). If length is 0, any existing edge is removed.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun addEdge(from: T, to: T, length: Int): Boolean {
    if (length < 0 || from == to) return false

    val fromIndex = vertices.indexOf(from as Any)
    val toIndex = vertices.indexOf(to as Any)

    if (fromIndex == -1 || toIndex == -1) return false

    if (matrix[fromIndex][toIndex] == 0 && length != 0) edgeCount++
    else if (matrix[fromIndex][toIndex] != 0 && length == 0) edgeCount--

    matrix[fromIndex][toIndex] = length
    return true
  }

  /**
   * Returns true if an edge between [vertexData1] and [vertexData2] of length [length] is successfully created in both
   * directions (length >=0, vertices exist and are unique). If length is 0, any existing edge is removed.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun addUndirectedEdge(vertexData1: T, vertexData2: T, length: Int): Boolean {
    return addEdge(vertexData1, vertexData2, length) && addEdge(vertexData2, vertexData1, length)
  }

  /**
   * Returns true if [from] and [to] are valid and unique and the edge between them (from [from] to [to]) is zero at the
   * end of this operation.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun removeEdge(from: T, to: T): Boolean {
    return addEdge(from, to, 0)
  }

  /**
   * Returns an array containing the data from all vertices.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun getVertexData(): Array<T> {
    @Suppress("UNCHECKED_CAST") // type T is guaranteed internally
    return vertices as Array<T>
  }

  /**
   * Returns a [MyHashSet] of [Pair] where Pair.first is the edge vertex data, and Pair.second is the distance from the
   * vertex with data [vertexData].
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun getEdgesForVertexData(vertexData: T): MyHashSet<Pair<T, Int>> {
    val result = MyHashSet<Pair<T, Int>>()

    val index = vertices.indexOf(vertexData as Any)
    if (index == -1) return result

    val toEdges = matrix[index]

    for (i in vertices.indices) {
      // Suppressing unchecked cast warning as vertices only contains type T
      @Suppress("UNCHECKED_CAST")
      val vertex = vertices[i] as T

      val distance = toEdges[i]

      if (distance != 0) {
        result.add(Pair(vertex, distance))
      }
    }
    return result
  }

  /**
   * Returns an [Array] of [IntArray] (matrix) containing the distance between vertices. Each column and row corresponds
   * to the vertices return by vertices() in the same order.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun edgesAsMatrix(): Array<IntArray> {
    return matrix
  }

  /**
   * Returns true if a vertex exists at [vertexData]. Existence checked used a Breadth-First Search (BFS) approach.
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  fun bfsContains(vertexData: T): Boolean {
    return bfs(vertexData)
  }

  /**
   * Uses a Breadth-First Search (BFS) to print the graph in BFS order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun bfsPrint() {
    bfs(null, true)
  }

  /**
   * Returns true if a vertex exists at [vertexData]. Existence checked used a Depth-First Search (DFS) approach.
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  fun dfsContains(vertexData: T): Boolean {
    return dfs(vertexData)
  }

  /**
   * Uses a Depth-First Search (DFS) to print the graph in DFS order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun dfsPrint() {
    dfs(null, true)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjMatGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfVertices != numberOfVertices) return false

    val otherVerticesList = other.getVertexData().asList()
    val otherMatrix = other.edgesAsMatrix()
    val thisIndexToOtherIndex: MyHashMap<Int, Int> = MyHashMap()

    for (i in vertices.indices) {
      val otherIndex = otherVerticesList.indexOf(vertices[i])

      if (otherIndex != -1) thisIndexToOtherIndex.put(i, otherIndex)
      else return false
    }

    for (i in matrix.indices) {
      val otherI = thisIndexToOtherIndex.get(i) ?: return false

      for (j in matrix.indices) {
        val otherJ = thisIndexToOtherIndex.get(j) ?: return false
        if (matrix[i][j] != otherMatrix[otherI][otherJ]) return false
      }
    }
    return true
  }

  override fun hashCode(): Int {
    var hc = 0

    for (i in matrix.indices) {
      hc += vertices[i].hashCode()
      for (j in matrix.indices) {
        hc += matrix[i][j]
      }
    }
    return hc
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    builder.append("Columns: ")

    for (i in 0 until numberOfVertices) {
      builder.append(vertices[i].toString()).append(", ")
    }

    builder.apply {
      removeEnd(2)
      append("\n") }

    for (i in 0 until vertices.size) {
      builder
        .append("Row ")
        .append(vertices[i].toString())
        .append(": ")
        .append(intArrayToString(matrix[i]))
        .append("\n")
    }

    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun intArrayToString(array: IntArray): String {
    val builder = MyStringBuilder().append("[")
    for (int in array) {
      builder.append(int.toString()).append(", ")
    }
    builder.removeEnd(2)
    return builder.append("]").toString()
  }

  /**
   * Returns true if vertex at [vertexData] is contained in [vertices] and prints the route if [print] is true.
   * Operation is performed using a Breadth-First Search.
   *
   * Efficiency: O(n) time, O(p) space, n = number of vertices, p = n if print is true, 1 otherwise
   */
  private fun bfs(vertexData: T?, print: Boolean = false): Boolean {
    var success = false
    val queue = MyQueue<T>()
    val visited = MyHashSet<T>()
    val message = MyStringBuilder().append("BFS: ")
    var currentData: T

    mainLoop@
    for (i in 0 until numberOfVertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
      currentData = vertices[i] as T

      if (!visited.contains(currentData)) {
        if (currentData == vertexData) {
          success = true
          break@mainLoop
        }
        bfsExplore(currentData, queue, visited, message, print, i)
      }

      while (queue.isNotEmpty()) {
        currentData = queue.dequeue()!! // not null due to loop condition
        bfsExplore(currentData, queue, visited, message, print)
      }
    }
    if (print) println(message.removeEnd(2).toString())
    return success
  }

  /**
   * Returns true if vertex at [vertexData] is contained in [vertices] and prints the route if [print] is true.
   * Operation is performed using a Depth-First Search (DFS).
   *
   * Efficiency: O(n) time, O(p) space, n = number of vertices, p = n if print is true, 1 otherwise
   */
  private fun dfs(vertexData: T?, print: Boolean = false): Boolean {
    var success = false
    val stack = MyStack<T>()
    val visited = MyHashSet<T>()
    val message = MyStringBuilder().append("BFS: ")
    var currentData: T

    mainLoop@
    for (i in 0 until numberOfVertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
      currentData = vertices[i] as T

      if (!visited.contains(currentData)) {
        if (currentData == vertexData) {
          success = true
          break@mainLoop
        }
        dfsExplore(currentData, stack, visited, message, print, i)
      }

      while (stack.isNotEmpty()) {
        currentData = stack.pop()!! // not null due to loop condition
        dfsExplore(currentData, stack, visited, message, print)
      }
    }
    if (print) println(message.removeEnd(2).toString())
    return success
  }

  /**
   * If vertex with [vertexData] (its index in [vertices] can be provided with [index]) is not in [visited], it is:
   * - Added to [visited]
   * - Added to [message] using toString() if [print] is true
   * - The vertices at its edges are added to [queue] if they are not in [visited].
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  private fun bfsExplore(vertexData: T,
                         queue: MyQueue<T>,
                         visited: MyHashSet<T>,
                         message: MyStringBuilder,
                         print: Boolean,
                         index: Int = -1) {

    if (!visited.contains(vertexData)) {
      visited.add(vertexData)
      if (print) message.append("${vertexData.toString()}, ")

      val row = if (index == -1) vertices.indexOf(vertexData as Any) else index

      for (col in 0 until numberOfVertices) {
        @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
        if (!visited.contains(vertices[col] as T)) {
          if (matrix[row][col] != 0) queue.enqueue(vertices[col] as T)
        }
      }
    }
  }

  /**
   * If vertex with [vertexData] (its index in [vertices] can be provided with [index]) is not in [visited], it is:
   * - Added to [visited]
   * - Added to [message] using toString() if [print] is true
   * - The vertices at its edges are added to [stack] if they are not in [visited].
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  private fun dfsExplore(vertexData: T,
                         stack: MyStack<T>,
                         visited: MyHashSet<T>,
                         message: MyStringBuilder,
                         print: Boolean,
                         index: Int = -1) {

    if (!visited.contains(vertexData)) {
      visited.add(vertexData)
      if (print) message.append("${vertexData.toString()}, ")

      val row = if (index == -1) vertices.indexOf(vertexData as Any) else index

      for (col in 0 until numberOfVertices) {
        @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
        if (!visited.contains(vertices[col] as T)) {
          if (matrix[row][col] != 0) stack.push(vertices[col] as T)
        }
      }
    }
  }
}