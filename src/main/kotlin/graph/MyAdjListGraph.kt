package graph

import array.MyStringBuilder
import hashtable.MyHashMap
import set.MyHashSet

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val dataAndVertices: MyHashMap<T, Vertex<T>> = MyHashMap()

  val numberOfNodes: Int
    get() = dataAndVertices.size

  val numberOfEdges: Int
    get() {
      var result = 0

      for (vertex in dataAndVertices.values) {
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
    addVertexToMap(vertex)

    if (edges != null) {
      for (edge in edges) {
        val edgeVertex = Vertex(edge)
        vertex.addEdge(edgeVertex)
        addVertexToMap(edgeVertex)
      }
    }
  }

  /**
   * Adds a vertex at [vertexData] with no edges if a vertex does not currently exist there. No action is taken if an
   * edge already exists.
   * @return 'true' if the vertex at [vertexData] has no edges, 'false' if it still exists with edges.
   */
  fun addVertexWithNoEdges(vertexData: T): Boolean {
    addVertexToMap(Vertex(vertexData))
    return dataAndVertices.get(vertexData)!!.edgeCount == 0 // Null-safe as a vertex was just inserted
  }

  /** Adds an edge from vertex [from] to vertex [to]. If a vertex doesn't exist, it's created. */
  fun addEdge(from: T, to: T) {
    val fromVertex = dataAndVertices.get(from) ?: run { createAndStoreVertex(from) }
    val toVertex = dataAndVertices.get(to) ?: run { createAndStoreVertex(to) }
    fromVertex.addEdge(toVertex)
  }

  /** Removes vertex and edges to and from [vertexData] if they exist. */
  fun removeVertex(vertexData: T) {
    val success = dataAndVertices.remove(vertexData)

    if (success) {
      for (vertex in dataAndVertices.values) {
        vertex.removeEdge(vertexData)
      }
    }
  }

  /**
   * Removes an edge from vertex [from] to vertex [to] if the vertices are valid and an edge exists between them.
   */
  fun removeEdge(from: T, to: T) {
    dataAndVertices.get(from)?.removeEdge(to)
  }

  fun bfs(vertexData: T): Boolean {
    TODO()
  }

  fun dfs(vertexData: T): Boolean {
    TODO()
  }

  /** Returns all vertices. */
  fun getVertexData(): MyHashSet<T> {
    val set = MyHashSet<T>()
    for (key in dataAndVertices.keys) {
      set.add(key)
    }
    return set
  }

  /** Returns all edges from [vertexData] if they exist, or null if the vertex does not exist. */
  fun getEdgesFromData(vertexData: T): MyHashSet<T>? {
    val vertex = dataAndVertices.get(vertexData)

    if (vertex != null) {
      val edges = vertex.edgeSet

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

    for (vertex in dataAndVertices.values) {
      set.addAll(vertex.getEdgesAsPairs())
    }
    return set
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfNodes != numberOfNodes) return false

    for (otherNode in other.dataAndVertices.values) {
      if (!dataAndVertices.values.contains(otherNode)) {
        return false
      }
    }
    return true
  }

  override fun hashCode(): Int {
    return dataAndVertices.hashCode()
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (vertex in dataAndVertices.values) {

      builder.append("[").append(vertex.data.toString()).append(" -> ")

      if (vertex.edgeSet.isNotEmpty()) {
        for (edge in vertex.edgeSet) {
          builder.append(edge.data.toString()).append(", ")
        }
        builder.removeEnd(2)
      }
      builder.append("]\n")
    }
    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun addVertexToMap(vertex: Vertex<T>): Boolean {
    return dataAndVertices.put(vertex.data, vertex)
  }

  /**
   * Returns a new [Vertex] created from [vertexData] which is stored in [dataAndVertices]. If vertexData already maps
   * to a Vertex, this is returned instead.
   */
  private fun createAndStoreVertex(vertexData: T): Vertex<T> {
    val vertex = Vertex(vertexData)
    dataAndVertices.put(vertexData, vertex)
    return dataAndVertices.get(vertexData)!! // Null-safe as vertexData was just inserted
  }


  // ---------------- Data class ----------------

  data class Vertex<T>(var data: T) {
    // edgeSet could be changed to a MyHashMap with the edge data as the key. This would improve lookup time.
    val edgeSet: MyHashSet<Vertex<T>> = MyHashSet()

    val edgeCount
      get() = edgeSet.size

    fun addEdge(vertex: Vertex<T>) {
      edgeSet.add(vertex)
    }

    fun removeEdge(vertexData: T) {
      for (edge in edgeSet) {
        if (edge.data == vertexData) {
          edgeSet.remove(edge)
        }
      }
    }

    fun getEdgesAsPairs(): MyHashSet<Pair<T, T>> {
      val returnSetUpdate = MyHashSet<Pair<T, T>>()

      for (edge in edgeSet) {
        returnSetUpdate.add(Pair(data, edge.data))
      }
      return returnSetUpdate
    }

    override fun equals(other: Any?): Boolean {
      return !(other !is Vertex<*> || other.data != data || other.getEdgesAsPairs() != getEdgesAsPairs())
    }

    override fun hashCode(): Int {
      val prime = 31
      var result = prime + data.hashCode()

      for (edges in edgeSet) {
        result = prime * result + edges.data.hashCode()
      }
      return result
    }
  }
}