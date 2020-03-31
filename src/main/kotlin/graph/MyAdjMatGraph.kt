package graph

import array.MyStringBuilder
import map.MyHashMap
import queue.MyPriorityQueue
import queue.MyQueue
import set.MyHashSet
import stack.MyStack

class MyAdjMatGraph<T>(vertices: List<T>) {

  // ---------------- Member variables ----------------

  private val adjacencyMatrix: Array<IntArray> = Array(vertices.size) { _ -> IntArray(vertices.size)}
  private val vertexValues: Array<Any>
  private var edgeCount = 0

  val numberOfVertices: Int
    get() = adjacencyMatrix.size

  val numberOfEdges: Int
    get() = edgeCount

  init {
    this.vertexValues = Array(vertices.size) { index -> vertices[index] as Any }
  }


  // ---------------- Public methods ----------------

  /**
   * Returns true if an edge from [fromValue] to [toValue] of length [edgeLength] is successfully created (length >= 0, vertices exist
   * and are unique). If length is 0, any existing edge is removed.
   *
   * Efficiency: O(v) time, O(1) space, v = number of vertices
   */
  fun addEdge(fromValue: T, toValue: T, edgeLength: Int): Boolean {
    if (fromValue == toValue) return false

    val fromIndex = vertexValues.indexOf(fromValue as Any)
    val toIndex = vertexValues.indexOf(toValue as Any)

    if (fromIndex == -1 || toIndex == -1) return false

    if (adjacencyMatrix[fromIndex][toIndex] == 0 && edgeLength != 0) edgeCount++
    else if (adjacencyMatrix[fromIndex][toIndex] != 0 && edgeLength == 0) edgeCount--

    adjacencyMatrix[fromIndex][toIndex] = edgeLength
    return true
  }

  /**
   * Returns true if an edge between [value1] and [value2] of length [edgeLength] is successfully created in both
   * directions (length >=0, vertices exist and are unique). If length is 0, any existing edge is removed.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun addUndirectedEdge(value1: T, value2: T, edgeLength: Int): Boolean {
    return addEdge(value1, value2, edgeLength) && addEdge(value2, value1, edgeLength)
  }

  /**
   * Returns true if [fromValue] and [toValue] are valid and unique and the edge between them (from [fromValue] to [toValue]) is zero at the
   * end of this operation.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun removeEdge(fromValue: T, toValue: T): Boolean {
    return addEdge(fromValue, toValue, 0)
  }

  /**
   * Returns an array containing the values of all vertices.
   *
   * Efficiency: O(1) time, O(1) space
   */
  fun getVertexData(): Array<T> {
    @Suppress("UNCHECKED_CAST") // type T is guaranteed internally
    return vertexValues as Array<T>
  }

  /**
   * Returns a [MyHashSet] of [Pair] where Pair.first is the edge value, and Pair.second is the distance from the
   * vertex with value [value].
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun getEdgesForValue(value: T): MyHashSet<Pair<T, Int>> {
    val result = MyHashSet<Pair<T, Int>>()

    val index = vertexValues.indexOf(value as Any)
    if (index == -1) return result

    val toEdges = adjacencyMatrix[index]

    for (i in vertexValues.indices) {
      // Suppressing unchecked cast warning as vertices only contains type T
      @Suppress("UNCHECKED_CAST")
      val vertex = vertexValues[i] as T

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
    return adjacencyMatrix
  }

  /**
   * Returns true if a vertex exists at [value]. Existence checked used a Breadth-First Search (BFS) approach.
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  fun bfsContains(value: T): Boolean {
    return bfs(value)
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
   * Returns true if a vertex exists at [value]. Existence checked used a Depth-First Search (DFS) approach.
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  fun dfsContains(value: T): Boolean {
    return dfs(value)
  }

  /**
   * Uses a Depth-First Search (DFS) to print the graph in DFS order.
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun dfsPrint() {
    dfs(null, true)
  }

  fun dijkstra(fromValue: T, toValue: T): Int {
    val fromIndex = getIndexOfValue(fromValue)

    if (fromValue != -1) {
      val toIndex = getIndexOfValue(toValue)
      if (toIndex != -1) return dijkstraWithIndices(fromIndex, toIndex)
    }
    return -1
  }

  fun bellmanFord(sourceValue: T) {
    val index = getIndexOfValue(sourceValue)

    if (index != -1) bellmanFordWithIndex(index)
    else println("No such value (${sourceValue.toString()})")
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjMatGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfVertices != numberOfVertices) return false

    val otherVerticesList = other.getVertexData().asList()
    val otherMatrix = other.edgesAsMatrix()
    val thisIndexToOtherIndex: MyHashMap<Int, Int> = MyHashMap()

    for (i in vertexValues.indices) {
      val otherIndex = otherVerticesList.indexOf(vertexValues[i])

      if (otherIndex != -1) thisIndexToOtherIndex.put(i, otherIndex)
      else return false
    }

    for (i in adjacencyMatrix.indices) {
      val otherI = thisIndexToOtherIndex.get(i) ?: return false

      for (j in adjacencyMatrix.indices) {
        val otherJ = thisIndexToOtherIndex.get(j) ?: return false
        if (adjacencyMatrix[i][j] != otherMatrix[otherI][otherJ]) return false
      }
    }
    return true
  }

  override fun hashCode(): Int {
    var result = 0

    for (i in adjacencyMatrix.indices) {
      result += vertexValues[i].hashCode()
      for (j in adjacencyMatrix.indices) {
        result += adjacencyMatrix[i][j]
      }
    }
    return result
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    builder.append("Columns: ")

    for (i in 0 until numberOfVertices) {
      builder.append(vertexValues[i].toString()).append(", ")
    }

    builder.apply {
      removeEnd(2)
      append("\n") }

    for (i in 0 until vertexValues.size) {
      builder
        .append("Row ")
        .append(vertexValues[i].toString())
        .append(": ")
        .append(intArrayToString(adjacencyMatrix[i]))
        .append("\n")
    }

    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun bellmanFordRelaxVertices(indexToDistanceMap: MyHashMap<Int, Int>) {
    for (index in vertexValues.indices) {
      bellmanFordRelaxVertexNeighbours(index, indexToDistanceMap)
    }
  }

  private fun bellmanFordRelaxVertexNeighbours(index: Int, indexToDistanceMap: MyHashMap<Int, Int>) {
    val distanceToVertex = indexToDistanceMap.get(index)!! // Guaranteed to have value for all indices

    val neighbourDistances = adjacencyMatrix[index]

    for (neighbourIndex in neighbourDistances.indices) {
      val currentDistanceToNeighbour = indexToDistanceMap.get(neighbourIndex)!! // Guaranteed to have value for all indices
      val additionalDistanceToNeighbour = neighbourDistances[neighbourIndex]

      val newDistanceToNeighbour =
        if (distanceToVertex != Int.MAX_VALUE && additionalDistanceToNeighbour != 0) {
          distanceToVertex + additionalDistanceToNeighbour
        }
        else Int.MAX_VALUE

      if (newDistanceToNeighbour < currentDistanceToNeighbour) {
        indexToDistanceMap.remove(neighbourIndex)
        indexToDistanceMap.put(neighbourIndex, newDistanceToNeighbour)
      }
    }
  }

  private fun printIndexToDistanceMap(sourceIndex: Int, indexToDistanceMap: MyHashMap<Int, Int>) {
    println("Distances from ${vertexValues[sourceIndex]} to other vertices: ")

    for (indexAndDistancePair in indexToDistanceMap) {
      val vertexValue = vertexValues[indexAndDistancePair.first]
      val distance = indexAndDistancePair.second

      if (distance != Int.MAX_VALUE) println("${vertexValue.toString()} = $distance")
      else println("${vertexValue.toString()} = inf")
    }
  }

  private fun bellmanFordWithIndex(sourceIndex: Int) {
    val indexToDistanceMap = initializeIndexToDistanceMap(sourceIndex)

    val numberOfLoops = vertexValues.size - 1

    for (i in 0 until numberOfLoops) {
      bellmanFordRelaxVertices(indexToDistanceMap)
    }

    printIndexToDistanceMap(sourceIndex, indexToDistanceMap)
  }

  private fun initializeIndexToDistanceMap(sourceIndex: Int): MyHashMap<Int, Int> {
    val result = MyHashMap<Int, Int>()

    for (i in vertexValues.indices) {
      if (i == sourceIndex) result.put(i, 0)
      else result.put(i, Int.MAX_VALUE)
    }
    return result
  }

  private fun dijkstraWithIndices(fromIndex: Int, toIndex: Int): Int {
    val visitedIndices = MyHashSet<Int>()
    val queue = MyPriorityQueue<IndexAndWeightPair>()
    queue.add(IndexAndWeightPair(fromIndex, 0))

    while (queue.isNotEmpty()) {
      val currentIndexAndWeightPair = queue.poll()!! // loop guarantees result
      val currentIndex = currentIndexAndWeightPair.index
      val currentWeight = currentIndexAndWeightPair.weight

      if (currentIndex == toIndex) return currentWeight

      if (!visitedIndices.contains(currentIndex)) {
        val currentNeighbourWeights = adjacencyMatrix[currentIndex]

        for (i in currentNeighbourWeights.indices) {
          if (!visitedIndices.contains(i)) {
            if (currentNeighbourWeights[i] != 0) addNeighbourIndexAndWeightToQueue(currentIndex, currentWeight, i, queue)
          }
        }
        visitedIndices.add(currentIndex)
      }
    }
    return -1
  }

  private fun addNeighbourIndexAndWeightToQueue(parentIndex: Int, parentWeight: Int, neighbourIndex: Int, queue: MyPriorityQueue<IndexAndWeightPair>) {
    val newWeight = parentWeight + adjacencyMatrix[parentIndex][neighbourIndex]
    val neighbourIndexAndWeightPair = IndexAndWeightPair(neighbourIndex, newWeight)
    queue.add(neighbourIndexAndWeightPair, newWeight)
  }

  private data class IndexAndWeightPair(val index: Int, val weight: Int = Int.MAX_VALUE) {
    override fun toString(): String {
      return "($index, $weight)"
    }
  }

  private fun getIndexOfValue(value: T): Int {
    for (i in vertexValues.indices) {
      if (vertexValues[i] == value as Any) return i
    }
    return -1
  }

  private fun intArrayToString(array: IntArray): String {
    val builder = MyStringBuilder().append("[")
    for (int in array) {
      builder.append(int.toString()).append(", ")
    }
    builder.removeEnd(2)
    return builder.append("]").toString()
  }

  /**
   * Returns true if vertex at [value] is contained in [vertexValues] and prints the route if [print] is true.
   * Operation is performed using a Breadth-First Search.
   *
   * Efficiency: O(n) time, O(p) space, n = number of vertices, p = n if print is true, 1 otherwise
   */
  private fun bfs(value: T?, print: Boolean = false): Boolean {
    var success = false
    val queue = MyQueue<T>()
    val visited = MyHashSet<T>()
    val message = MyStringBuilder().append("BFS: ")
    var currentData: T

    mainLoop@
    for (i in 0 until numberOfVertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
      currentData = vertexValues[i] as T

      if (!visited.contains(currentData)) {
        if (currentData == value) {
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
   * Returns true if vertex at [value] is contained in [vertexValues] and prints the route if [print] is true.
   * Operation is performed using a Depth-First Search (DFS).
   *
   * Efficiency: O(n) time, O(p) space, n = number of vertices, p = n if print is true, 1 otherwise
   */
  private fun dfs(value: T?, print: Boolean = false): Boolean {
    var success = false
    val stack = MyStack<T>()
    val visited = MyHashSet<T>()
    val message = MyStringBuilder().append("BFS: ")
    var currentData: T

    mainLoop@
    for (i in 0 until numberOfVertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
      currentData = vertexValues[i] as T

      if (!visited.contains(currentData)) {
        if (currentData == value) {
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
   * If vertex with [value] (its index in [vertexValues] can be provided with [index]) is not in [visited], it is:
   * - Added to [visited]
   * - Added to [message] using toString() if [print] is true
   * - The vertices at its edges are added to [queue] if they are not in [visited].
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  private fun bfsExplore(value: T,
                         queue: MyQueue<T>,
                         visited: MyHashSet<T>,
                         message: MyStringBuilder,
                         print: Boolean,
                         index: Int = -1) {

    if (!visited.contains(value)) {
      visited.add(value)
      if (print) message.append("${value.toString()}, ")

      val row = if (index == -1) vertexValues.indexOf(value as Any) else index

      for (col in 0 until numberOfVertices) {
        @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
        if (!visited.contains(vertexValues[col] as T)) {
          if (adjacencyMatrix[row][col] != 0) queue.enqueue(vertexValues[col] as T)
        }
      }
    }
  }

  /**
   * If vertex with [value] (its index in [vertexValues] can be provided with [index]) is not in [visited], it is:
   * - Added to [visited]
   * - Added to [message] using toString() if [print] is true
   * - The vertices at its edges are added to [stack] if they are not in [visited].
   *
   * Efficiency: O(n) time, O(1) space, n = number of vertices
   */
  private fun dfsExplore(value: T,
                         stack: MyStack<T>,
                         visited: MyHashSet<T>,
                         message: MyStringBuilder,
                         print: Boolean,
                         index: Int = -1) {

    if (!visited.contains(value)) {
      visited.add(value)
      if (print) message.append("${value.toString()}, ")

      val row = if (index == -1) vertexValues.indexOf(value as Any) else index

      for (col in 0 until numberOfVertices) {
        @Suppress("UNCHECKED_CAST") // type T guaranteed in vertices
        if (!visited.contains(vertexValues[col] as T)) {
          if (adjacencyMatrix[row][col] != 0) stack.push(vertexValues[col] as T)
        }
      }
    }
  }
}