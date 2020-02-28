package queue

import heap.MyKHeap

class MyKPriorityQueue {

  val heap: MyKHeap = MyKHeap()

  fun isEmpty(): Boolean {
    TODO()
  }

  fun insert(value: Int) {
    TODO()
  }

  fun pull() {
    TODO()
  }

  fun peek(): Int {
    TODO()
  }

  fun insertPriority(value: Int) {
    TODO()
  }

  fun pullPriority(): Int {
    TODO()
  }

  fun peekPriority(): Int {
    TODO()
  }

  data class Item(val value: Int, var priority: Int = 0) {
    override fun toString(): String {
      return value.toString()
    }
  }
}