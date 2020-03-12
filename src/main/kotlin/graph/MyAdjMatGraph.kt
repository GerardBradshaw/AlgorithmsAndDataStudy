package graph

import array.MyStringBuilder
import hashtable.MyHashMap
import kotlin.math.abs


class MyAdjMatGraph<T>(vertices: List<T>) {

  // ---------------- Member variables ----------------

  private val matrix: Array<IntArray>
  private val vertices: Array<Any>
  private var edgeCount = 0

  val numberOfVertices: Int
    get() = matrix.size

  val numberOfEdges: Int
    get() = edgeCount

  init {
    matrix = Array(vertices.size) { _ -> IntArray(vertices.size)}
    this.vertices = Array(vertices.size) { index -> vertices[index] as Any }
  }


  // ---------------- Public methods ----------------

  /**
   * Adds an edge of specified distance between two vertices. If the vertices are invalid or the same, or if the
   * distance is a negative number, no action is taken.
   * @param from source vertex.
   * @param to destination vertex
   * @param distance distance between the two vertices
   *
   * @return 'true' if the vertices are valid & unique, and the distance is greater than 0, 'false' otherwise.
   */
  fun addEdge(from: T, to: T, distance: Int): Boolean {
    if (distance < 0 || from == to) return false

    val fromIndex = vertices.indexOf(from as Any)
    val toIndex = vertices.indexOf(to as Any)

    if (fromIndex == -1 || toIndex == -1) return false

    if (matrix[fromIndex][toIndex] == 0 && distance != 0) edgeCount++
    else if (matrix[fromIndex][toIndex] != 0 && distance == 0) edgeCount--

    matrix[fromIndex][toIndex] = distance
    return true
  }

  /**
   * Removes the edge between two edges if one exists. If the vertices are invalid or the same, no action is taken.
   * @param from source vertex
   * @param to destination vertex
   *
   * @return 'true' if the vertices are valid & unique and the edge has been removed or never existed, 'false' otherwise.
   */
  fun removeEdge(from: T, to: T): Boolean {
    return addEdge(from, to, 0)
  }

  /**
   * Returns an array containing a all vertices.
   * @return Array of all vertices of type T
   *
   * @suppress "UNCHECKED_CAST" because type T is guaranteed internally
   */
  @Suppress("UNCHECKED_CAST")
  fun vertices(): Array<T> {
    return vertices as Array<T>
  }

  /**
   * Returns an N by N matrix containing the distance between vertices, where N is the number of vertices and 0
   * indicates no relationship. Each column corresponds and row corresponds to the vertices returned by vertices().
   *
   * @return an Array of IntArrays containing the distance between vertices, where 0 represents no relationship.
   */
  fun edges(): Array<IntArray> {
    return matrix
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyAdjMatGraph<*>
      || other.numberOfEdges != numberOfEdges
      || other.numberOfVertices != numberOfVertices) return false

    val otherVerticesList = other.vertices().asList()
    val otherMatrix = other.edges()
    val thisIndexToOtherIndex: MyHashMap<Int, Int> = MyHashMap()

    for (i in vertices.indices) {
      val otherIndex = otherVerticesList.indexOf(vertices[i])

      if (otherIndex != -1) thisIndexToOtherIndex.put(i, otherIndex)
      else return false
    }

    for (i in matrix.indices) {
      val otherI = thisIndexToOtherIndex.get(i) ?: return false

      for (j in matrix.indices) {
        val otherJ = thisIndexToOtherIndex.get(j) ?: return false
        if (matrix[i][j] != otherMatrix[otherI][otherJ]) return false
      }
    }
    return true
  }

  override fun hashCode(): Int {
    var hc = 0

    for (i in matrix.indices) {
      hc += vertices[i].hashCode()
      for (j in matrix.indices) {
        hc += matrix[i][j]
      }
    }
    return hc
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    builder.append("Columns: ")

    for (i in 0 until numberOfVertices) {
      builder.append(vertices[i].toString()).append(", ")
    }

    builder.apply {
      removeEnd(2)
      append("\n") }

    for (i in 0 until vertices.size) {
      builder
        .append("Row ")
        .append(vertices[i].toString())
        .append(": ")
        .append(intArrayToString(matrix[i]))
        .append("\n")
    }

    return builder.toString()
  }


  // ---------------- Helpers ----------------

  private fun intArrayToString(array: IntArray): String {
    val builder = MyStringBuilder().append("[")
    for (int in array) {
      builder.append(int.toString()).append(", ")
    }
    builder.removeEnd(2)
    return builder.append("]").toString()
  }

  // ---------------- Data class ----------------

  data class Node<T>(var vertex: T) {

  }
}