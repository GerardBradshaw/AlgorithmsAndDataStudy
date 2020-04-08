package questions

import array.MyStringBuilder
import set.MyHashSet

class S02LinkedLists {

  fun q0201AremoveDups(head: Node) {
    val set = MyHashSet<Int>()

    var current: Node? = head
    var prev: Node? = null

    while (current != null) {
      if (set.contains(current.data)) {
        prev!!.next = current.next
        current.next = null
        current = prev.next
      }
      else {
        set.add(current.data)
        prev = current
        current = current.next
      }
    }
  }

  fun q0201BremoveDups(head: Node) {
    var slowPointer: Node? = head
    var fastPointerPrev: Node? = head
    var fastPointer: Node? = head.next

    while (slowPointer != null) {
      when {
        fastPointer == null -> {
          slowPointer = slowPointer.next
          fastPointer = slowPointer?.next
          fastPointerPrev = slowPointer
        }
        fastPointer.data == slowPointer.data -> {
          fastPointerPrev!!.next = fastPointer.next
          fastPointer.next = null
          fastPointer = fastPointerPrev.next
        }
        else -> {
          fastPointer = fastPointer.next
          fastPointerPrev = fastPointerPrev!!.next
        }
      }
    }
  }

  class Node(var data: Int) {
    var next: Node? = null

    fun appendToTail(data: Int) {
      var last = this
      while (last.next != null) last = last.next!!
      last.next = Node(data)
    }

    fun appendAllToTail(vararg data: Int) {
      var last = this
      while (last.next != null) last = last.next!!

      for (d in data) {
        last.next = Node(d)
        last = last.next!!
      }
    }

    override fun toString(): String {
      val builder = MyStringBuilder().append("[")

      var current: Node? = this

      while (current != null) {
        builder.append(current.data.toString()).append(", ")
        current = current.next
      }
      return builder.removeEnd(2).append("]").toString()
    }
  }
}