package graph

import array.MyStringBuilder
import hashtable.MyHashMap
import set.MyHashSet

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val verticesAndNodes: MyHashMap<T, Node<T>> = MyHashMap()

  val numberOfNodes: Int
    get() = verticesAndNodes.size

  val numberOfEdges: Int
    get() {
      var edgeCount = 0
      for (node in verticesAndNodes.values) edgeCount += node.edges.size
      return edgeCount
    }


  // ---------------- Public methods ----------------

  fun addVertexAndEdges(vertex: T, edges: Collection<T>?) {
    verticesAndNodes.put(vertex, Node(vertex))

    if (edges != null) {
      verticesAndNodes.get(vertex)?.edges?.addAll(edges)

      for (edge in edges) {
        verticesAndNodes.put(edge, Node(edge))
      }
    }
  }

  fun addVertex(vertex: T) {
    addVertexAndEdges(vertex, null)
  }

  fun addEdge(source: T, destination: T) {
    verticesAndNodes.put(source, Node(source))
    verticesAndNodes.put(source, Node(destination))
    verticesAndNodes.get(source)?.edges?.add(destination)
  }

  fun removeVertex(vertex: T) {
    verticesAndNodes.remove(vertex)

    for (node in verticesAndNodes.values) {
      node.edges.remove(vertex)
    }
  }

  fun removeEdge(vertex: T, edge: T) {
    verticesAndNodes.get(vertex)?.edges?.remove(edge)
  }

  /**
   * Returns an Array of type <T> containing all Vertices.
   *
   * @return an Array containing all vertices.
   * @suppress "UNCHECKED_CAST" as type is guaranteed to be T due to internal operations
   */
  fun vertices(): Array<T> {
    @Suppress("UNCHECKED_CAST")
    return Array(verticesAndNodes.size) { index -> verticesAndNodes.keys.elementAt(index) as Any } as Array<T>
  }

  /**
   * Returns the edges for a vertex if it exists, or null otherwise.
   * @param vertex the vertex with edges of interest.
   *
   * @return set of edges for given vertex, or null if vertex is invalid.
   */
  fun getEdgesForVertex(vertex: T): MyHashSet<T>? {
    return verticesAndNodes.get(vertex)?.edges
  }

  fun edges(): MyHashSet<MyHashSet<T>> {
    val vertices = vertices()
    val returnSet = MyHashSet<MyHashSet<T>>()

    for (vertex in vertices) {
      returnSet.add(verticesAndNodes.get(vertex)!!.edges)
    }
    return returnSet
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfNodes != numberOfNodes) return false

    for (otherNode in other.verticesAndNodes.values) {
      if (!verticesAndNodes.values.contains(otherNode)) {
        return false
      }
    }

    return true
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (node in verticesAndNodes.values) {

      builder.append("[").append(node.vertex.toString()).append(" -> ")

      if (node.edges.isNotEmpty()) {
        for (edge in node.edges) {
          builder.append(edge.toString()).append(", ")
        }
        builder.removeEnd(2)
      }

      builder.append("]\n")
    }
    return builder.toString()
  }

  // ---------------- Data class ----------------

  data class Node<T>(var vertex: T) {
    val edges: MyHashSet<T> = MyHashSet()

    override fun equals(other: Any?): Boolean {
      return !(other !is Node<*> || other.vertex != vertex || other.edges != edges)
    }

    override fun hashCode(): Int {
      val prime = 31
      var result = prime + vertex.hashCode()

      for (edge in edges) {
        result = prime * result + (edge?.hashCode() ?: 0)
      }
      return result
    }
  }
}