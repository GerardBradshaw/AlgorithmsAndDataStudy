package hashtable

import linkedlist.MyDoubleLinkedList
import linkedlist.MyKLinkedList
import linkedlist.MyLinkedList

class MyKHashTable : GKHashTable<String, String> {

  // -------- Properties --------

  private var dataTable = arrayOf(MyDoubleLinkedList<ListItem>())
  private var tableSize = 0


  // -------- Constructor --------
  



  override fun insert(key: String, value: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun get(key: Any): String? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun remove(key: Any) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun set(key: String, value: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun contains(key: Any): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun size(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  // -------- ListItem class --------

  private data class ListItem(val key: String, val value: String) {
    override fun toString(): String {
      return "$key: $value"
    }
  }
}