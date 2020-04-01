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

  /**
   * Uses the Dijkstra shortest path algorithm to find the shortest distance from [sourceValue] to all others.
   *
   * Efficiency: Typically O(V * log(E)) time, O(E) space. Additional O(V) time if index lookup included. V = number of
   * vertices, E = number of edges. Worst case O(V^2) time.
   */
  fun dijkstra(sourceValue: T) {
    val sourceIndex = getIndexOfValue(sourceValue)
    val validIndex = sourceIndex != -1

    if (validIndex) {
      val indexToDistanceMap = dijkstraGetIndexToDistanceMap(sourceIndex)
      return printIndexToDistanceMap(sourceIndex, indexToDistanceMap)
    }
    println("\"${sourceValue.toString()}\" invalid")
  }

  /**
   * Prints the shortest distance between the vertex at [sourceValue] (if it exists) and all other vertices using the
   * Bellman-Ford shortest path algorithm.
   *
   * Efficiency: Typically O(V * E) time, O(V) space. V = number of vertices, E = number of edges
   */
  fun bellmanFord(sourceValue: T) {
    val index = getIndexOfValue(sourceValue)
    val isValidIndex = index != -1

    if (isValidIndex) {
      val indexToDistanceMap = bfGetCompletedIndexToDistanceMap(index)
      printIndexToDistanceMap(index, indexToDistanceMap)
    }
    else println("No such value (${sourceValue.toString()})")
  }

  /**
   * Returns true of a negative loop is present in the graph. Loop is detected using Bellman-Ford shortest path
   * algorithm.
   *
   * Efficiency: O(V * E) time, O(V) space. V = number of vertices, E = number of edges
   */
  fun detectNegativeLoopWithBellmanFord(): Boolean {
    val indexToDistanceMap = bfGetCompletedIndexToDistanceMap(0)
    return bfRelaxDistancesWithSinglePassOverAllEdges(indexToDistanceMap)
  }

  /**
   * Prints a matrix representing the shortest path from each vertex to each other vertex using the
   * Floyd-Warshall shortest path algorithm.
   *
   * Efficiency: O(V^3) time, O(V^2) space, V = number of vertices
   */
  fun floydWarshall() {
    println(intMatrixToString(fwGetMatrix()))
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

    builder.append("Col: ")

    for (i in 0 until numberOfVertices) {
      builder.append(vertexValues[i].toString()).append(", ")
    }

    builder.apply {
      removeEnd(2)
      append("\n") }

    builder.append(intMatrixToString(adjacencyMatrix))

    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun fwGetMatrix(): Array<IntArray> {
    val fwMatrix = fwGetInitialMatrix()
    val numberOfPasses = vertexValues.size - 1

    for (vertexIndex in 0..numberOfPasses) {
      fwUpdateMatrix(vertexIndex, fwMatrix)
    }
    return fwMatrix
  }

  private fun fwUpdateMatrix(vertexIndex: Int, fwMatrix: Array<IntArray>) {
    for (fromIndex in fwMatrix.indices) {
      if (fromIndex == vertexIndex) return
      val intArray = fwMatrix[fromIndex]

      for (toIndex in intArray.indices) {
        if (toIndex == vertexIndex) continue

        val fromToVertexDistance = fwMatrix[fromIndex][vertexIndex]
        val vertexToToDistance = fwMatrix[vertexIndex][toIndex]

        val newValue = if (fromToVertexDistance != Int.MAX_VALUE && vertexToToDistance != Int.MAX_VALUE)
          fromToVertexDistance + vertexToToDistance else Int.MAX_VALUE

        if (newValue < fwMatrix[fromIndex][toIndex]) fwMatrix[fromIndex][toIndex] = newValue
      }
    }
  }

  private fun fwGetInitialMatrix(): Array<IntArray> {
    val result: Array<IntArray> = Array(vertexValues.size) { _ -> IntArray(vertexValues.size) }

    for (i in adjacencyMatrix.indices) {
      val intArray = adjacencyMatrix[i]

      for (j in intArray.indices) {
        val distance = intArray[j]

        result[i][j] = when {
          i == j -> 0
          distance == 0 -> Int.MAX_VALUE
          else -> distance
        }
      }
    }
    return result
  }

  private fun bfRelaxDistancesWithSinglePassOverAllEdges(indexToDistanceMap: MyHashMap<Int, Int>): Boolean {
    var changeCount = 0

    for (index in indexToDistanceMap.keys) {
      if (bfRelaxNeighbours(index, indexToDistanceMap)) changeCount++
    }
    return changeCount != 0
  }

  private fun bfRelaxNeighbours(index: Int, indexToDistanceMap: MyHashMap<Int, Int>): Boolean {
    val distanceToVertex = indexToDistanceMap.get(index)!! // Guaranteed to have value for all indices
    val neighbourDistances = adjacencyMatrix[index]
    var mapDataChanged = false

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
        mapDataChanged = true
      }
    }
    return mapDataChanged
  }

  private fun bfGetCompletedIndexToDistanceMap(sourceIndex: Int): MyHashMap<Int, Int> {
    val indexToDistanceMap = bfInitializeIndexToDistanceMap(sourceIndex)
    val numberOfPassesOverAllEdges = vertexValues.size - 1

    for (i in 0 until numberOfPassesOverAllEdges) {
      if (!bfRelaxDistancesWithSinglePassOverAllEdges(indexToDistanceMap)) break
    }
    return indexToDistanceMap
  }

  private fun bfInitializeIndexToDistanceMap(sourceIndex: Int): MyHashMap<Int, Int> {
    val result = MyHashMap<Int, Int>()

    for (i in vertexValues.indices) {
      if (i == sourceIndex) result.put(i, 0)
      else result.put(i, Int.MAX_VALUE)
    }
    return result
  }

  private fun dijkstraGetIndexToDistanceMap(sourceIndex: Int): MyHashMap<Int, Int> {
    val result = MyHashMap<Int, Int>()
    val queue = MyPriorityQueue<IndexAndDistancePair>()
    queue.add(IndexAndDistancePair(sourceIndex, 0))

    while (queue.isNotEmpty()) {
      val indexAndDistancePair = queue.poll()!! // loop guarantees result
      val index = indexAndDistancePair.index
      val tailDistance = indexAndDistancePair.distance

      if (!result.containsKey(index)) {
        result.put(index, tailDistance)

        for (neighbourIndex in vertexValues.indices) {
          if (!result.containsKey(neighbourIndex)) {
            dijkstraAddNeighbourIndexAndDistanceToQueue(index, tailDistance, neighbourIndex, queue)
          }
        }
      }
    }
    return result
  }

  private fun dijkstraAddNeighbourIndexAndDistanceToQueue(index: Int, tailDistance: Int, neighbourIndex: Int, queue: MyPriorityQueue<IndexAndDistancePair>) {
    val additionalDistanceToNeighbour = adjacencyMatrix[index][neighbourIndex]

    if (additionalDistanceToNeighbour != 0) {
      val newDistance = tailDistance + additionalDistanceToNeighbour
      queue.add(IndexAndDistancePair(neighbourIndex, newDistance), newDistance)
    }
  }

  private fun printIndexToDistanceMap(sourceIndex: Int, indexToDistanceMap: MyHashMap<Int, Int>) {
    println("Distances from ${vertexValues[sourceIndex]} to other vertices: ")

    for (indexAndDistancePair in indexToDistanceMap) {
      val vertexValue = vertexValues[indexAndDistancePair.first]
      val distance = indexAndDistancePair.second

      if (distance != Int.MAX_VALUE) println("$vertexValue = $distance")
      else println("$vertexValue = inf")
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
      if (int == Int.MAX_VALUE) builder.append("---")
      else {
        when {
          int < -10 -> builder.append(" ")
          int < 0 -> builder.append(" ")
          int > 10 -> builder.append("  ")
          int >= 0 -> builder.append("  ")
        }
        builder.append(int.toString())
      }

      builder.append(", ")
    }
    builder.removeEnd(2)
    return builder.append("]").toString()
  }

  private fun intMatrixToString(matrix: Array<IntArray>): String {
    if (matrix.size != vertexValues.size) return "error writing matrix"
    val string = MyStringBuilder()

    for (i in matrix.indices) {
      string.append(vertexValues[i].toString()).append(": ").append(intArrayToString(matrix[i])).append("\n")
    }
    return string.toString()
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

  private data class IndexAndDistancePair(val index: Int, val distance: Int = Int.MAX_VALUE) {
    override fun toString(): String {
      return "($index, $distance)"
    }
  }

}