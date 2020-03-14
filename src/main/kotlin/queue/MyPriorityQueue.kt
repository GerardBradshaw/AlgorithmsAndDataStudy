package queue

import heap.MyMinHeap
import java.lang.StringBuilder

class MyPriorityQueue<T> {

  // ---------------- Fields ----------------

  private var heap: MyMinHeap<Item<T>> = MyMinHeap()


  // ---------------- Public fun ----------------

  fun isEmpty(): Boolean {
    return heap.isEmpty()
  }

  fun insert(element: T, priority: Int = Int.MAX_VALUE) {
    heap.insert(Item(element, priority))
  }

  fun pull(): T? {
    return heap.popMin().value
  }

  fun peek(): T {
    return heap.getMin().value
  }

  override fun toString(): String {
    // TODO Change to an array heap sort

    if (heap.isEmpty()) return "[]"

    val heapCopy = MyMinHeap<Item<T>>()
    heapCopy.insert(heap.popMin())
    val builder = StringBuilder().append("[").append(heapCopy.getMin())

    while (!heap.isEmpty()) {
      builder.append(", ").append(heap.getMin())
      heapCopy.insert(heap.popMin())
    }

    heap = heapCopy

    return builder.append("]").toString()
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyPriorityQueue<*> || other.heap.size() != heap.size()) return false
    return other.heap == heap
    // NOTE: Equality evaluation in heap has to be fixed for this to work!
  }

  override fun hashCode(): Int {
    return heap.hashCode()
  }

  // ---------------- Item data class ----------------

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