package linkedlist

class MyLinkedListK : GLinkedList<Int> {

  // - - - - - - - - - - - - - - - Properties/Fields - - - - - - - - - - - - - - -




  // - - - - - - - - - - - - - - - Add - - - - - - - - - - - - - - -

  override fun addAtStart(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addAtEnd(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addAtIndex(data: Int?, index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Set - - - - - - - - - - - - - - -

  override fun setAtStart(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setAtEnd(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setAtIndex(data: Int?, index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Delete - - - - - - - - - - - - - - -

  override fun deleteAtStart() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteAtEnd() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteAtIndex(index: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteItem(data: Int?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Get - - - - - - - - - - - - - - -

  override fun getAtStart(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAtEnd(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAtIndex(index: Int): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // - - - - - - - - - - - - - - - Check - - - - - - - - - - - - - - -

  override fun contains(data: Int?): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  private data class Node(var data: Int) {
    var linkedNode: Node? = null

    override fun toString(): String {
      return "[" + data + ""
    }
  }

}