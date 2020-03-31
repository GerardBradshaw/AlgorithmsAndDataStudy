package graph

import array.MyStringBuilder
import map.MyHashMap
import queue.MyPriorityQueue
import queue.MyQueue
import set.MyHashSet
import stack.MyStack
import java.lang.IndexOutOfBoundsException

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val vertices = MyHashSet<Vertex<T>>()

  val numberOfVertices: Int
    get() = vertices.size

  /**
   * Returns the total number of edges.
   *
   * Efficiency: O(v) time, O(1) space, v = number of edges
   */
  val numberOfEdges: Int
    get() {
      var result = 0

      for (vertex in vertices) {
        result += vertex.neighbourCount
      }
      return result
    }


  // ---------------- Public fun ----------------

  /**
   * Adds a vertex at [vertexData] and links from this to vertices in [edges]. A vertex is created for each edge vertex
   * if it does not currently exist.
   *
   * Efficiency: Typically O(e) time, O(1) space, e = number of edges in collection. Worst case O(v * e) time, O(v) space, v = number of vertices
   */
  fun addVertex(vertexData: T, edges: Collection<T>?) {
    val vertex = Vertex(vertexData)
    addVertexToSet(vertex)

    if (edges != null) {
      for (edge in edges) {
        val edgeVertex = Vertex(edge)
        vertex.addEdge(edgeVertex)
        addVertexToSet(edgeVertex)
      }
    }
  }

  /**
   * Returns true if a vertex with [vertexData] was inserted with no edges, or if one already exists with no edges.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(v) time, O(v) space, v = number of vertices
   */
  fun addVertexWithNoEdges(vertexData: T): Boolean {
    return addVertexToSet(Vertex(vertexData))
  }

  /**
   * Adds an edge from [vertexData1] to [vertexData2] and vice-versa. If a vertex doesn't exist, it's created.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(v) time, O(v) space, v = number of entries
   */
  fun addUndirectedEdge(vertexData1: T, vertexData2: T) {
    addEdge(vertexData1, vertexData2)
    addEdge(vertexData2, vertexData1)
  }

  /**
   * Adds an edge from [from] to [to]. If a vertex doesn't exist, it's created.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(v + e) time, O(v) space, v = number of entries, e = number of edges
   */
  fun addEdge(from: T, to: T) {
    val fromVertex = bfs(from) ?: run { createAndStoreVertex(from) }
    val toVertex = bfs(to) ?: run { createAndStoreVertex(to) }
    fromVertex.addEdge(toVertex)
  }

  /**
   * Removes vertex and edges to and from [vertexData] if they exist.
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun removeVertex(vertexData: T) {
    val vertex = bfs(vertexData)

    if (vertex != null) {
      for (v in vertices) {
        v.removeEdge(vertexData)
      }
    }
  }

  /**
   * Removes an edge from vertex [from] to vertex [to] if the vertices are valid and an edge exists between them.
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun removeEdge(from: T, to: T) {
    val fromVertex = bfs(from)

    if (fromVertex != null) {
      val toVertex = bfs(to)
      if (toVertex != null) {
        fromVertex.removeEdge(toVertex.data)
      }
    }
  }

  /**
   * Returns true if the graph contains a vertex with [vertexData].
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun bfsContains(vertexData: T): Boolean {
    return bfs(vertexData) != null
  }

  /**
   * Returns the graph in Breadth-First Search (BFS) order (in-order).
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun bfsPrint() {
    bfs(null, true)
  }

  /**
   * Returns true if the graph contains a vertex with [vertexData].
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun dfsContains(vertexData: T): Boolean {
    return dfs(vertexData) != null
  }

  /**
   * Returns the graph in Depth-First Search (DFS) order (pre-order).
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun dfsPrint() {
    dfs(null, true)
  }

  fun dijkstraWithQueue(fromValue: T, toValue: T): Int {
    if (fromValue == toValue) return 0

    val fromVertex = bfs(fromValue)
    if (fromVertex != null) {
      val toVertex = bfs(toValue)
      if (toVertex != null) return dijkstraWithVerticesAndQueue(fromVertex, toVertex)
    }
    throw IndexOutOfBoundsException()
  }

  /**
   * Returns the shortest distance from vertex at [from] to [to], or -1 if no path exists.
   *
   * TODO Efficiency: O() time, O() space
   */
  fun dijkstra(from: T, to: T): Int {
    if (from == to) return 0

    val fromVertex = bfs(from)
    if (fromVertex != null) {
      val toVertex = bfs(to)
      if (toVertex != null) return dijkstraWithVertices(fromVertex, toVertex)
    }
    throw Exception()
  }

  /**
   * Returns all vertices.
   *
   * Efficiency: O(v) time, O(v) space, v = number of entries
   */
  fun getVertexData(): MyHashSet<T> {
    val set = MyHashSet<T>()

    for (v in vertices) {
      set.add(v.data)
    }
    return set
  }

  /**
   * Returns all edges from [vertexData] if they exist, or null if the vertex does not exist.
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  fun getEdgesFromData(vertexData: T): MyHashSet<T>? {
    val vertex = bfs(vertexData)

    if (vertex != null) {
      val edges = vertex.neighbours

      return if (edges.isEmpty()) null
      else {
        val result = MyHashSet<T>()

        for (edge in edges) {
          result.add(edge.data)
        }
        result
      }
    }
    return null
  }

  /**
   * Returns all edges as [Pair] where Pair.first and Pair.second are the 'to' and 'from' vertex data respectively.
   *
   * Efficiency: O(v * e) time, O(v * e) space, v = number of vertices, e = number of edges
   */
  fun getAllEdges(): MyHashSet<Pair<T, T>> {
    val set = MyHashSet<Pair<T, T>>()

    for (v in vertices) {
      set.addAll(v.getEdgesAsPairs())
    }
    return set
  }

  /**
   * Efficiency: At least O(v^2) time, O(v) space, v = number of entries
   */
  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfVertices != numberOfVertices) return false

    for (oV in other.vertices) {
      if (!vertices.contains(oV)) return false
    }
    return true
  }

  /**
   * Efficiency: O(v) time, O(1) space, v = number of vertices
   */
  override fun hashCode(): Int {
    var result = 0

    for (v in vertices) {
      result += v.hashCode()
    }
    return 31 * result
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (v in vertices) {

      builder.append("[").append(v.data.toString()).append(" -> ")

      if (v.neighbours.isNotEmpty()) {
        for (edge in v.neighbours) {
          builder.append(edge.data.toString()).append(", ")
        }
        builder.removeEnd(2)
      }
      builder.append("]\n")
    }
    return builder.removeEnd(1).toString()
  }

  fun bellmanFord(sourceVertexData: T): MyHashMap<Vertex<T>, Int> {
    val sourceVertex = bfs(sourceVertexData) ?: throw Exception()
    return bellmanFordWithVertex(sourceVertex)
  }

  fun bellmanFordDetectLoop(sourceVertexData: T): Boolean {
    val sourceVertex = bfs(sourceVertexData) ?: throw Exception()
    return bellmanFordDetectLoopWithVertex(sourceVertex)
  }


  // ---------------- Helpers ----------------

  private fun bellmanFordDetectLoopWithVertex(sourceVertex: Vertex<T>): Boolean {
    val vertexToDistanceMap = bellmanFordWithVertex(sourceVertex)
    var changeCount = 0

    for (vertex in vertexToDistanceMap.keys) {
      if (!bellmanFordRelaxVertexNeighbours(vertex, vertexToDistanceMap)) changeCount++
    }
    return changeCount != 0
  }

  private fun bellmanFordWithVertex(sourceVertex: Vertex<T>): MyHashMap<Vertex<T>, Int> {
    val vertexToDistanceMap = initializeVertexToDistanceMap(sourceVertex)

    val numberOfLoops = vertices.size - 1

    for (i in 0 until numberOfLoops) {
      bellmanFordRelaxVertices(vertexToDistanceMap)
    }

    return vertexToDistanceMap
  }

  private fun initializeVertexToDistanceMap(sourceVertex: Vertex<T>): MyHashMap<Vertex<T>, Int> {
    val result = MyHashMap<Vertex<T>, Int>()

    for (vertex in vertices) {
      if (vertex == sourceVertex) result.put(vertex, 0)
      else result.put(vertex, Int.MAX_VALUE)
    }

    return result
  }

  private fun bellmanFordRelaxVertices(vertexToDistanceMap: MyHashMap<Vertex<T>, Int>) {
    for (vertex in vertexToDistanceMap.keys) {
      bellmanFordRelaxVertexNeighbours(vertex, vertexToDistanceMap)
    }
  }

  private fun bellmanFordRelaxVertexNeighbours(vertex: Vertex<T>, vertexToDistanceMap: MyHashMap<Vertex<T>, Int>): Boolean {
    val distanceToVertex = vertexToDistanceMap.get(vertex)!! // Not null due to method usage
    var mapDataChanged = false

    for (neighbour in vertex.neighbours) {
      val currentDistanceToNeighbour = vertexToDistanceMap.get(neighbour)!! // Not null as all neighbours exist as vertices
      val newDistanceToNeighbour = if (distanceToVertex != Int.MAX_VALUE) distanceToVertex + 1 else distanceToVertex

      if (newDistanceToNeighbour < currentDistanceToNeighbour) {
        vertexToDistanceMap.remove(neighbour)
        vertexToDistanceMap.put(neighbour, newDistanceToNeighbour)
        mapDataChanged = true
      }
    }
    return mapDataChanged
  }


  private fun dijkstraWithVerticesAndQueue(from: Vertex<T>, to: Vertex<T>): Int {
    val visited = MyHashSet<Vertex<T>>()
    val queue = MyPriorityQueue<VertexAndWeightPair<T>>()
    queue.add(VertexAndWeightPair(from, 0),0)

    while (!queue.isEmpty()) {
      val currentVertexAndWeightPair = queue.poll()!! // loop ensures queue has data
      val currentVertex = currentVertexAndWeightPair.vertex
      val currentWeight = currentVertexAndWeightPair.weight

      if (!visited.contains(currentVertex)) {
        if (currentVertex == to) return currentWeight

        for (neighbourVertex in currentVertex.neighbours) {
          if (!visited.contains(neighbourVertex)) addVertexToQueue(neighbourVertex, queue, currentWeight)
        }
        visited.add(currentVertexAndWeightPair.vertex)
      }
    }
    return -1
  }

  private fun addVertexToQueue(vertex: Vertex<T>, queue: MyPriorityQueue<VertexAndWeightPair<T>>, currentWeight: Int) {
    val neighbourWeight = currentWeight + 1
    val neighbourVertexAndWeightPair = VertexAndWeightPair(vertex, neighbourWeight)
    queue.add(neighbourVertexAndWeightPair, neighbourWeight)
  }

  private fun bfsAddToVisitedAndQueueAndPrint(vertex: Vertex<T>, visited: MyHashSet<Vertex<T>>, queue: MyQueue<Vertex<T>>, message: MyStringBuilder, print: Boolean) {
    visited.add(vertex)
    queue.enqueue(vertex)
    if (print) message.append(vertex.data.toString()).append(", ")
  }

  /**
   * Returns a [MyHashMap] containing the distances from [from] to all other vertices in the map. If a vertex is not a
   * neighbour of [from], the distance is set to Int.MAX_VALUE.
   *
   * Efficiency: O(v) time, O(1) space. v = number of vertices
   */
  private fun populateVerticesToCostsMap(from: Vertex<T>): MyHashMap<Vertex<T>, Int> {
    val result = MyHashMap<Vertex<T>, Int>()

    for (v in vertices) {
      when {
        from.neighbours.contains(v) -> result.put(v, 1)
        v != from -> result.put(v, Int.MAX_VALUE)
      }
    }
    return result
  }

  // TODO doc and replace Pairs with custom class
  /**
   * Returns the [Vertex] with least expensive cost to move to.
   */
  private fun getCheapestUnexplored(unexploredDistances: MyHashMap<Vertex<T>, Int>): Pair<Vertex<T>, Int> {
    var result: Pair<Vertex<T>, Int>? = null

    for (pair in unexploredDistances.entries) {
      if (result == null) result = pair
      else if (pair.second < result.second) result = pair
    }

    return result!!
  }

  // TODO doc and replace Pairs with custom class
  private fun updateDistances(current: Pair<Vertex<T>, Int>, unexploredVerticesToCosts: MyHashMap<Vertex<T>, Int>) {
    val currentVertex = current.first
    val currentCost = current.second

    for (vertexAndCost in unexploredVerticesToCosts) {
      if (currentVertex.neighbours.contains(vertexAndCost.first)) {
        val newCost = currentCost + 1

        // TODO don't use pairs because distances are final
        if (newCost < vertexAndCost.second) {
          val vertex = vertexAndCost.first
          unexploredVerticesToCosts.remove(vertex)
          unexploredVerticesToCosts.put(vertex, newCost)
        }
      }
    }
  }

  private fun dijkstraWithVertices(from: Vertex<T>, to: Vertex<T>): Int {

    val unexploredVerticesToCosts = populateVerticesToCostsMap(from)

    var currentVertexAndCost: Pair<Vertex<T>, Int>
    var currentCost: Int

    while (true) {
      currentVertexAndCost = getCheapestUnexplored(unexploredVerticesToCosts)

      currentCost = currentVertexAndCost.second
      if (currentCost == Int.MAX_VALUE) return -1

      updateDistances(currentVertexAndCost, unexploredVerticesToCosts)

      unexploredVerticesToCosts.remove(currentVertexAndCost.first)

      if (currentVertexAndCost.first == to) return currentCost
    }
  }

  /**
   * Performs a Breadth-First Search (BFS) of [vertices] for [vertexData] and prints to the console if [print] is true.
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  private fun bfs(vertexData: T?, print: Boolean = false): Vertex<T>? {
    val visited = MyHashSet<Vertex<T>>()
    val queue = MyQueue<Vertex<T>>()
    var result: Vertex<T>? = null
    val message = MyStringBuilder().append("BFS: ")

    mainLoop@
    for (v in vertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      if (!visited.contains(v)) {
        if (v.data == vertexData) {
          result = v
          break@mainLoop
        }
        bfsAddToVisitedAndQueueAndPrint(v, visited, queue, message, print)
      }

      while (queue.isNotEmpty()) {
        for (e in queue.dequeue()!!.neighbours) { // Null-safe ensured by isNotEmpty condition
          if (!visited.contains(e)) {
            if (e.data == vertexData) {
              result = e
              break@mainLoop
            }
            bfsAddToVisitedAndQueueAndPrint(e, visited, queue, message, print)
          }
        }
      }
    }
    if (print) println(message.removeEnd(2).toString())
    return result
  }

  /**
   * Returns an unexplored [Vertex] connected to [vertex] if one exists. Also: adds vertex to [stack] if its never been
   * explored (so its edges can be explored later); adds vertex data to [strBuilder] using toString(); adds vertex to
   * [visited] so it won't be added to strBuilder or stack again.
   */
  private fun dfsExplore(vertex: Vertex<T>, stack: MyStack<Vertex<T>>, visited: MyHashSet<Vertex<T>>, strBuilder: MyStringBuilder, print: Boolean): Vertex<T>? {

    if (!visited.contains(vertex)) {
      stack.push(vertex)
      visited.add(vertex)
      if (print) strBuilder.append(vertex.data.toString()).append(", ")
    }

    for (edge in vertex.neighbours) {
      if (!visited.contains(edge)) {
        stack.push(vertex)
        return edge
      }
    }

    return null
  }

  /**
   * Performs a Depth-First Search (DFS) of [vertices] for [vertexData] and prints to the console if it's null.
   *
   * Efficiency: O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  private fun dfs(vertexData: T?, print: Boolean = false): Vertex<T>? {
    val visited = MyHashSet<Vertex<T>>()
    val stack = MyStack<Vertex<T>>()
    val strBuilder = MyStringBuilder().append("DFS: ")

    var current: Vertex<T>? = null

    mainLoop@
    for (v in vertices) {
      if (visited.size == numberOfVertices) break@mainLoop

      current = v

      while (current != null) {
        if (current.data == vertexData) break@mainLoop
        current = dfsExplore(current, stack, visited, strBuilder, print)
      }

      while (stack.isNotEmpty()) {
        current = stack.pop()

        while (current != null) {
          if (current.data == vertexData) break@mainLoop
          current = dfsExplore(current, stack, visited, strBuilder, print)
        }
      }
    }
    if (print) println(strBuilder.removeEnd(2).toString())
    return current
  }

  /**
   * Adds [vertex] to [vertices] if it's unique.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(v) time, O(v) space, v = number of vertices
   */
  private fun addVertexToSet(vertex: Vertex<T>): Boolean {
    return vertices.add(vertex)
  }

  /**
   * Returns the [Vertex] at [vertexData] in [vertices] if one exists, otherwise a new one is created and that is
   * returned.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(v + e) time, O(v) space, v = number of vertices, e = number of edges
   */
  private fun createAndStoreVertex(vertexData: T): Vertex<T> {
    val vertex = Vertex(vertexData)
    val success = vertices.add(vertex)

    return if (success) vertex else bfs(vertexData)!! // Null-safe as value was just inserted
  }


  // ---------------- Data classes ----------------

  data class Vertex<T>(var data: T) {
    val neighbours: MyHashSet<Vertex<T>> = MyHashSet()

    val neighbourCount
      get() = neighbours.size

    fun addEdge(vertex: Vertex<T>) {
      neighbours.add(vertex)
    }

    fun removeEdge(vertexData: T) {
      for (e in neighbours) {
        if (e.data == vertexData) {
          neighbours.remove(e)
        }
      }
    }

    fun getEdgesAsPairs(): MyHashSet<Pair<T, T>> {
      val returnSetUpdate = MyHashSet<Pair<T, T>>()

      for (e in neighbours) {
        returnSetUpdate.add(Pair(data, e.data))
      }
      return returnSetUpdate
    }

    override fun equals(other: Any?): Boolean {
      return !(other !is Vertex<*> || other.data != data || other.getEdgesAsPairs() != getEdgesAsPairs())
    }

    override fun hashCode(): Int {
      return 31 * data.hashCode()
    }

    override fun toString(): String {
      val stringBuilder = MyStringBuilder().append("[").append(data.toString()).append(" -> ")

      if (neighbours.isNotEmpty()) {
        for (neighbour in neighbours) {
          stringBuilder.append(neighbour.data.toString()).append(", ")
        }
        stringBuilder.removeEnd(2)
      }
      return stringBuilder.append("]").toString()
    }
  }

  private data class VertexAndWeightPair<T>(val vertex: Vertex<T>, var weight: Int)  {
    override fun toString(): String {
      return "(${vertex.data.toString()}, $weight)"
    }
  }
}