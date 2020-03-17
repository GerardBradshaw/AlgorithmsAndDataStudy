package graph

import array.MyStringBuilder
import set.MyHashSet

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val vertices = MyHashSet<Vertex<T>>()

  val numberOfNodes: Int
    get() = vertices.size

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
   * Adds a vertex at [vertexData] with no edges if a vertex does not currently exist there. No action is taken if an
   * edge already exists.
   * @return 'true' if the vertex at [vertexData] has no edges, 'false' if it still exists with edges.
   */
  fun addVertexWithNoEdges(vertexData: T): Boolean {
    return addVertexToSet(Vertex(vertexData))
  }

  /** Adds an edge from vertex [from] to vertex [to]. If a vertex doesn't exist, it's created. */
  fun addEdge(from: T, to: T) {
    val fromVertex = bfsHelper(from) ?: run { createAndStoreVertex(from) }
    val toVertex = bfsHelper(to) ?: run { createAndStoreVertex(to) }
    fromVertex.addEdge(toVertex)
  }

  /** Removes vertex and edges to and from [vertexData] if they exist. */
  fun removeVertex(vertexData: T) {
    val vertex = bfsHelper(vertexData)

    if (vertex != null) {
      for (v in vertices) {
        v.removeEdge(vertexData)
      }
    }
  }

  /**
   * Removes an edge from vertex [from] to vertex [to] if the vertices are valid and an edge exists between them.
   */
  fun removeEdge(from: T, to: T) {
    val fromVertex = bfsHelper(from)

    if (fromVertex != null) {
      val toVertex = bfsHelper(to)
      if (toVertex != null) {
        fromVertex.removeEdge(toVertex.data)
      }
    }
  }

  fun bfs(vertexData: T): Boolean {
    return bfsHelper(vertexData) != null
  }

  fun dfs(vertexData: T): Boolean {
    return dfsHelper(vertexData) != null
  }

  private fun bfsHelper(vertexData: T): Vertex<T>? {
    TODO()
  }

  private fun dfsHelper(vertexData: T): Vertex<T>? {
    TODO()
  }

  /** Returns all vertices. */
  fun getVertexData(): MyHashSet<T> {
    val set = MyHashSet<T>()

    for (v in vertices) {
      set.add(v.data)
    }

    return set
  }

  /** Returns all edges from [vertexData] if they exist, or null if the vertex does not exist. */
  fun getEdgesFromData(vertexData: T): MyHashSet<T>? {
    val vertex = bfsHelper(vertexData)

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

  /** Returns all edges as [Pair]s where 'first' and 'second' in the Pair are the to and from vertex respectively. */
  fun getAllEdges(): MyHashSet<Pair<T, T>> {
    val set = MyHashSet<Pair<T, T>>()

    for (v in vertices) {
      set.addAll(v.getEdgesAsPairs())
    }
    return set
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfNodes != numberOfNodes) return false

    for (oV in other.vertices) {
      if (!vertices.contains(oV)) {
        return false
      }
    }
    return true
  }

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
    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun addVertexToSet(vertex: Vertex<T>): Boolean {
    return vertices.add(vertex)
  }

  /**
   * Returns a new [Vertex] created from [vertexData] which is stored in [dataAndVertices]. If vertexData already maps
   * to a Vertex, this is returned instead.
   */
  private fun createAndStoreVertex(vertexData: T): Vertex<T> {
    val vertex = Vertex(vertexData)
    val success = vertices.add(vertex)

    return if (success) vertex else bfsHelper(vertexData)!! // Null-safe as value was just inserted
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
      val prime = 31
      var result = prime + data.hashCode()

      for (edges in edges) {
        result += edges.data.hashCode()
      }
      return prime * result
    }
  }
}