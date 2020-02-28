package queue

import heap.MyKMaxHeap

class MyKPriorityQueue<T> {

  val heap: MyKMaxHeap<Item<T>> = MyKMaxHeap()

  fun isEmpty(): Boolean {
    TODO()
  }

  fun insert(value: T, priority: Int = 0) {
    val newItem = Item(value, priority)
    heap.insert(newItem)
  }

  fun pull(priority: Int = 0) {
    TODO()
  }

  fun peek(priority: Int = 0): Int {
    TODO()
  }

  fun peekPriority(): Int {
    TODO()
  }

  data class Item<T>(val value: T, var priority: Int = 0) : Comparable<Item<T>> {
    override fun toString(): String {
      return value.toString()
    }

    override fun compareTo(other: Item<T>): Int {
      val otherPriority = other.priority

      return when {
        priority > otherPriority -> 1
        priority < otherPriority -> -1
        else -> 0
      }
    }
  }
}