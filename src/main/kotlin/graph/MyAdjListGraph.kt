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
      var edgeCount = 0
      for (vertex in dataAndVertices.values) edgeCount += vertex.edges.size
      return edgeCount
    }


  // ---------------- Public methods ----------------

  fun addVertex(data: T, edges: Collection<T>?) {
    dataAndVertices.put(data, Vertex(data))

    if (edges != null) {
      dataAndVertices.get(data)?.edges?.addAll(edges)

      for (edge in edges) {
        dataAndVertices.put(edge, Vertex(edge))
      }
    }
  }

  fun addVertexWithNoEdges(vertexData: T) {
    addVertex(vertexData, null)
  }

  fun addEdge(from: T, to: T) {
    dataAndVertices.put(from, Vertex(from))
    dataAndVertices.put(from, Vertex(to))
    dataAndVertices.get(from)?.edges?.add(to)
  }

  fun removeVertex(vertexData: T) {
    dataAndVertices.remove(vertexData)

    for (vertex in dataAndVertices.values) {
      vertex.edges.remove(vertexData)
    }
  }

  fun removeEdge(from: T, to: T) {
    dataAndVertices.get(from)?.edges?.remove(to)
  }

  /**
   * Returns an Array of type <T> containing all Vertices.
   *
   * @return an Array containing all vertices.
   * @suppress "UNCHECKED_CAST" as type is guaranteed to be T due to internal operations
   */
  fun getVertexData(): Array<T> {
    @Suppress("UNCHECKED_CAST")
    return Array(dataAndVertices.size) { index -> dataAndVertices.keys.elementAt(index) as Any } as Array<T>
  }

  /**
   * Returns the edges for a vertex if it exists, or null otherwise.
   * @param vertexData the vertex with edges of interest.
   *
   * @return set of edges for given vertex, or null if vertex is invalid.
   */
  fun getEdgesFromData(vertexData: T): MyHashSet<T>? {
    return dataAndVertices.get(vertexData)?.edges
  }

  fun getAllEdges(): MyHashSet<MyHashSet<T>> {
    // TODO rewrite

    val vertexData = getVertexData()
    val returnSet = MyHashSet<MyHashSet<T>>()

    for (vertex in vertexData) {
      returnSet.add(dataAndVertices.get(vertex)!!.edges)
    }
    return returnSet
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

      if (vertex.edges.isNotEmpty()) {
        for (edge in vertex.edges) {
          builder.append(edge.toString()).append(", ")
        }
        builder.removeEnd(2)
      }

      builder.append("]\n")
    }
    return builder.toString()
  }

  // ---------------- Data class ----------------

  data class Vertex<T>(var data: T) {
    val edges: MyHashSet<T> = MyHashSet()

    override fun equals(other: Any?): Boolean {
      return !(other !is Vertex<*> || other.data != data || other.edges != edges)
    }

    override fun hashCode(): Int {
      val prime = 31
      var result = prime + data.hashCode()

      for (edge in edges) {
        result = prime * result + (edge?.hashCode() ?: 0)
      }
      return result
    }
  }
}