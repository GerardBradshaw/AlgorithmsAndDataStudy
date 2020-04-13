package questions

import java.lang.Exception
import java.util.*

class S03StacksAndQueues {

  /**
   * Uses a single array to implement 3 stacks.
   *
   * Other approaches:
   * - Use a dynamic size for each stack. If a stack exceeds its pre-allocated size, rearrange the array such that it
   * can fit.
   */
  class Q0301Stack() {
    private val values = IntArray(30)
    private val sizes = IntArray(3)

    fun peek(stackNumber: Int): Int {
      if (isEmpty(stackNumber)) throw EmptyStackException()
      return values[indexOfTop(stackNumber)]
    }

    fun pop(stackNumber: Int): Int {
      if (isEmpty(stackNumber)) throw EmptyStackException()
      val index = indexOfTop(stackNumber)
      sizes[stackNumber]--
      return values[index]
    }

    fun push(stackNumber: Int, value: Int) {
      if (isFull(stackNumber)) throw FullStackException()
      sizes[stackNumber]++
      values[indexOfTop(stackNumber)] = value
    }

    fun isEmpty(stackNumber: Int): Boolean {
      checkStackNumber(stackNumber)
      return sizes[stackNumber] == 0
    }

    fun isFull(stackNumber: Int): Boolean {
      checkStackNumber(stackNumber)
      return sizes[stackNumber] == 10
    }

    private fun indexOfTop(stackNumber: Int): Int {
      val offset = stackNumber * 3
      return offset + sizes[stackNumber]
    }

    private fun checkStackNumber(stackNumber: Int) {
      if (stackNumber > 2 || stackNumber < 0) throw InvalidStackReference()
    }

    class InvalidStackReference : Exception()
    class FullStackException : Exception()
  }
}