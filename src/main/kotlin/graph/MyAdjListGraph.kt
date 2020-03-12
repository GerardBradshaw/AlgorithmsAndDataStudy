package graph

import array.MyStringBuilder
import hashtable.MyHashMap
import set.MyHashSet

class MyAdjListGraph<T> {

  // ---------------- Member variables ----------------

  private val vertices: MyHashMap<T, Node<T>> = MyHashMap()

  val numberOfNodes: Int
    get() = vertices.size

  val numberOfEdges: Int
    get() {
      var edgeCount = 0
      for (node in vertices.values) edgeCount += node.edges.size
      return edgeCount
    }


  // ---------------- Public methods ----------------

  fun addVertexAndEdges(vertex: T, edges: Collection<T>?) {
    vertices.put(vertex, Node(vertex))

    if (edges != null) {
      vertices.get(vertex)?.edges?.addAll(edges)

      for (edge in edges) {
        vertices.put(edge, Node(edge))
      }
    }
  }

  fun addVertex(vertex: T) {
    addVertexAndEdges(vertex, null)
  }

  fun addEdge(source: T, destination: T) {
    vertices.put(source, Node(source))
    vertices.put(source, Node(destination))
    vertices.get(source)?.edges?.add(destination)
  }

  fun removeVertex(vertex: T) {
    vertices.remove(vertex)

    for (node in vertices.values) {
      node.edges.remove(vertex)
    }
  }

  fun removeEdge(vertex: T, edge: T) {
    vertices.get(vertex)?.edges?.remove(edge)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjListGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfNodes != numberOfNodes) return false

    TODO("Need to add a contains() method for nodes")
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (node in vertices.values) {

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
  }
}