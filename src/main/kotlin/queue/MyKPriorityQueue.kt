package queue

import heap.MyKHeap
import heap.MyKTHeap

class MyKPriorityQueue {

  val heap: MyKTHeap<Item> = MyKTHeap()

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

  data class Item(val value: Int, var priority: Int = 0) : Comparable<Item> {
    override fun toString(): String {
      return value.toString()
    }

    override fun compareTo(other: Item): Int {
      val otherPriority = other.priority

      return when {
        priority > otherPriority -> 1
        priority < otherPriority -> -1
        else -> 0
      }
    }
  }
}