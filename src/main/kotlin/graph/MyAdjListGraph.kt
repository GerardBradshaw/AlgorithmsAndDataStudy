package graph

import array.MyStringBuilder
import queue.MyQueue
import set.MyHashSet
import stack.MyStack

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val vertices = MyHashSet<Vertex<T>>()

  val numberOfNodes: Int
    get() = vertices.size

  /**
   * Returns the total number of edges.
   *
   * Efficiency: O(n) time, O(1) space, n = number of edges
   */
  val numberOfEdges: Int
    get() {
      var result = 0

      for (vertex in vertices) {
        result += vertex.edgeCount
      }
      return result
    }


  // ---------------- Public fun ----------------

  /**
   * Adds a vertex at [vertexData] and links from this to vertices in [edges]. A vertex is created for each edge vertex
   * if it does not currently exist.
   *
   * Efficiency: Typically O(e) time, O(1) space, e = number of edges. Worst case O(n * e) time, O(n) space, n = number of vertices
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
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of vertices
   */
  fun addVertexWithNoEdges(vertexData: T): Boolean {
    return addVertexToSet(Vertex(vertexData))
  }

  /**
   * Adds an edge from [vertexData1] to [vertexData2] and vice-versa. If a vertex doesn't exist, it's created.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  fun addUndirectedEdge(vertexData1: T, vertexData2: T) {
    addEdge(vertexData1, vertexData2)
    addEdge(vertexData2, vertexData1)
  }

  /**
   * Adds an edge from [from] to [to]. If a vertex doesn't exist, it's created.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  fun addEdge(from: T, to: T) {
    val fromVertex = bfs(from) ?: run { createAndStoreVertex(from) }
    val toVertex = bfs(to) ?: run { createAndStoreVertex(to) }
    fromVertex.addEdge(toVertex)
  }

  /**
   * Removes vertex and edges to and from [vertexData] if they exist.
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
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
   * Efficiency: O(n) time, O(n) space, n = number of vertices
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
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun bfsContains(vertexData: T): Boolean {
    return bfs(vertexData) != null
  }

  /**
   * Returns the graph in Breadth-First Search (BFS) order (in-order).
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun bfsPrint() {
    bfs(null, true)
  }

  /**
   * Returns true if the graph contains a vertex with [vertexData].
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun dfsContains(vertexData: T): Boolean {
    return dfs(vertexData) != null
  }

  /**
   * Returns the graph in Depth-First Search (DFS) order (pre-order).
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun dfsPrint() {
    dfs(null, true)
  }

  // ---------------- Helpers ----------------

  /**
   * Performs a Breadth-First Search (BFS) of [vertices] for [vertexData] and prints to the console if [print] is true.
   *
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  private fun bfs(vertexData: T?, print: Boolean = false): Vertex<T>? {
    val visited = MyHashSet<Vertex<T>>()
    val queue = MyQueue<Vertex<T>>()
    var result: Vertex<T>? = null
    val str = MyStringBuilder().append("BFS: ")

    mainLoop@
    for (v in vertices) {
      if (!visited.contains(v)) {
        if (v.data == vertexData) {
          result = v
          break@mainLoop
        }

        visited.add(v)
        queue.enqueue(v)
        if (print) str.append(v.data.toString()).append(", ")
      }

      while (queue.isNotEmpty()) {
        for (e in queue.dequeue()!!.edges) { // Null-safe ensured by isNotEmpty condition
          if (!visited.contains(e)) {
            if (e.data == vertexData) {
              result = e
              break@mainLoop
            }

            visited.add(e)
            queue.enqueue(e)
            if (print) str.append(e.data.toString()).append(", ")
          }
        }
      }
    }
    if (print) println(str.removeEnd(2).toString())
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

    for (edge in vertex.edges) {
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
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  private fun dfs(vertexData: T?, print: Boolean = false): Vertex<T>? {
    val visited = MyHashSet<Vertex<T>>()
    val stack = MyStack<Vertex<T>>()
    val strBuilder = MyStringBuilder().append("DFS: ")

    var current: Vertex<T>? = null

    mainLoop@
    for (v in vertices) {
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
   * Returns all vertices.
   *
   * Efficiency: O(n) time, O(n) space, n = number of entries
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
   * Efficiency: O(n) time, O(n) space, n = number of vertices
   */
  fun getEdgesFromData(vertexData: T): MyHashSet<T>? {
    val vertex = bfs(vertexData)

    if (vertex != null) {
      val edges = vertex.edges

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
   * Efficiency: O(n * e) time, O(n * e) space, n = number of vertices, e = number of edges
   */
  fun getAllEdges(): MyHashSet<Pair<T, T>> {
    val set = MyHashSet<Pair<T, T>>()

    for (v in vertices) {
      set.addAll(v.getEdgesAsPairs())
    }
    return set
  }

  /**
   * Efficiency: At least O(n^2) time, O(n) space, n = number of entries
   */
  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfNodes != numberOfNodes) return false

    for (oV in other.vertices) {
      if (!vertices.contains(oV)) return false
    }
    return true
  }

  /**
   * Efficiency: O(n) time, O(1) space, n = number of vertices
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

      if (v.edges.isNotEmpty()) {
        for (edge in v.edges) {
          builder.append(edge.data.toString()).append(", ")
        }
        builder.removeEnd(2)
      }
      builder.append("]\n")
    }
    return builder.removeEnd(1).toString()
  }


  // ---------------- Helpers ----------------

  /**
   * Adds [vertex] to [vertices] if it's unique.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of vertices
   */
  private fun addVertexToSet(vertex: Vertex<T>): Boolean {
    return vertices.add(vertex)
  }

  /**
   * Returns the [Vertex] at [vertexData] in [vertices] if one exists, otherwise a new one is created and that is
   * returned.
   *
   * Efficiency: Typically O(1) time, O(1) space. Worst case O(n) time, O(n) space, n = number of entries
   */
  private fun createAndStoreVertex(vertexData: T): Vertex<T> {
    val vertex = Vertex(vertexData)
    val success = vertices.add(vertex)

    return if (success) vertex else bfs(vertexData)!! // Null-safe as value was just inserted
  }


  // ---------------- Data class ----------------

  data class Vertex<T>(var data: T) {
    val edges: MyHashSet<Vertex<T>> = MyHashSet()

    val edgeCount
      get() = edges.size

    fun addEdge(vertex: Vertex<T>) {
      edges.add(vertex)
    }

    fun removeEdge(vertexData: T) {
      for (e in edges) {
        if (e.data == vertexData) {
          edges.remove(e)
        }
      }
    }

    fun getEdgesAsPairs(): MyHashSet<Pair<T, T>> {
      val returnSetUpdate = MyHashSet<Pair<T, T>>()

      for (e in edges) {
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
  }
}