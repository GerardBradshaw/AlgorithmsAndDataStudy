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


  // ---------------- Public fun ----------------

  fun addVertex(vertexData: T, edges: Collection<T>?) {
    dataAndVertices.put(vertexData, Vertex(vertexData))

    if (edges != null) {
      dataAndVertices.get(vertexData)?.edges?.addAll(edges)

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
   */
  fun getVertexData(): MyHashSet<T> {
    val set = MyHashSet<T>()
    for (key in dataAndVertices.keys) {
      set.add(key)
    }
    return set
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

    fun getEdgesAsPairs(): MyHashSet<Pair<T, T>> {
      val iterator = edges.iterator()
      val returnSet = MyHashSet<Pair<T, T>>()

      while (iterator.hasNext()) {
        returnSet.add(Pair(data, iterator.next()))
      }
      return returnSet
    }

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