package graph

//import set.MyHashSet

class MyAdjListGraph<T : Comparable<T>> {

  // ---------------- Member variables ----------------
  //private val vertices: MyHashSet<Node<T>> = MyHashSet()

  var numberOfNodes = 0
    private set

  var numberOfEdges = 0
    private set


  // ---------------- Public methods ----------------

  fun insert(vertex: T, edges: Collection<T>) {
    TODO()
  }

  fun addVertex(vertex: T) {
    //vertices.add(Node(vertex))
  }

  fun addEdge(source: T, destination: T) {
    //for (node in vertices) {
    // if (node.vertex == source) {
    //    node.edges.add(Node(destination))
    //    return
    //  }
    //}

    TODO()
  }


  // ---------------- Data class ----------------
  data class Node<T : Comparable<T>>(var vertex: T) {

    //val edges: MyHashSet<Node<T>> = MyHashSet()

  }
}