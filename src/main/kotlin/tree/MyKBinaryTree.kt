package tree

class MyKBinaryTree {


  private data class Node(var value: Int) {
    var left: Node? = null
    var right: Node? = null

    override fun toString(): String {
      return value.toString()
    }
  }
}