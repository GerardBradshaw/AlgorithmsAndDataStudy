package queue

import heap.MyKMaxHeap
import heap.MyKMinHeap
import java.lang.StringBuilder

class MyKPriorityQueue<T> {

  private var heap: MyKMinHeap<Item<T>> = MyKMinHeap()

  fun isEmpty(): Boolean {
    return heap.isEmpty()
  }

  fun insert(value: T, priority: Int = Int.MAX_VALUE) {
    heap.insert(Item(value, priority))
  }

  fun pull(): T? {
    return heap.popMin().value
  }

  fun peek(): T {
    return heap.getMin().value
  }

  override fun toString(): String {
    // TODO Change to an array heap sort

    if (heap.isEmpty()) return "empty"

    val heapCopy = MyKMinHeap<Item<T>>()
    heapCopy.insert(heap.popMin())
    val builder = StringBuilder().append("[").append(heapCopy.getMin())

    while (!heap.isEmpty()) {
      builder.append(", ").append(heap.getMin())
      heapCopy.insert(heap.popMin())
    }

    heap = heapCopy

    return builder.append("]").toString()
  }

  data class Item<T>(val value: T, var priority: Int = Int.MAX_VALUE) : Comparable<Item<T>> {
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