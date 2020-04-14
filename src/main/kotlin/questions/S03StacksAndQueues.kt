package questions

import array.MyArrayList
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*

class S03StacksAndQueues {

  /**
   * Uses a single array to implement 3 stacks.
   *
   * Other approaches:
   * - Use a dynamic size for each stack. If a stack exceeds its pre-allocated size, rearrange the array such that it
   * can fit.
   */
  class Q0301ThreeInOne {
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

  /**
   * A stack with a min() function that returns the minimum value in the stack. All functions are O(1) time.
   */
  class Q0302StackMin {

    private var top: Node? = null

    fun push(value: Int) {
      val newNode = Node(value, top)
      if (top != null && top!!.smallestPrev.value < value) newNode.smallestPrev = top!!.smallestPrev
      top = newNode
    }

    fun pop(): Int {
      if (top == null) throw EmptyStackException()
      val result = top!!.value
      top = top!!.prev
      return result
    }

    fun min(): Int {
      if (top != null) return top!!.smallestPrev.value
      else throw EmptyStackException()
    }

    data class Node(val value: Int, var prev: Node?) {
      var smallestPrev: Node = this
    }
  }

  /**
   * A stack composed of several smaller stacks and fun popAt(index: Int) function that pops a value from a specific stack.
   */
  class Q0303StackOfPlates {
    private val stackList = MyArrayList<GenericStack>()
    private val maxStackSize = 3
    private val topStack: Int
      get() = stackList.size - 1

    fun peek(): Int {
      if (stackList.isEmpty()) throw EmptyStackException()
      val stack = stackList.get(topStack)
      return stack.peek()
    }

    fun pop(): Int {
      if (stackList.isEmpty()) throw EmptyStackException()

      var stack = stackList.get(topStack)
      if (stack.isEmpty()) stackList.delete(topStack)

      stack = stackList.get(topStack)
      return stack.pop()
    }

    fun push(value: Int) {
      if (stackList.isEmpty()) stackList.add(GenericStack())
      var stack = stackList.get(topStack)

      if (stack.size == maxStackSize) {
        stackList.add(GenericStack())
        stack = stackList.get(topStack)
      }

      stack.push(value)
    }

    fun popAt(index: Int): Int {
      val stackIndex = index / maxStackSize
      val stack = stackList.get(topStack)
      return stack.pop()
    }

    private data class Node(val value: Int, var prev: Node?)
  }

  /**
   * A queue implemented using 2 stacks.
   *
   * Improvements:
   * - Don't reverse the stacks until absolutely necessary (i.e. when polling).
   */
  class Q0304QueueViaStacks {
    private val tempStack = GenericStack()
    private val reverseStack = GenericStack()

    fun enqueue(value: Int) {
      while (reverseStack.isNotEmpty()) {
        tempStack.push(reverseStack.pop())
      }
      reverseStack.push(value)
      while (tempStack.isNotEmpty()) {
        reverseStack.push(tempStack.pop())
      }
    }

    fun poll(): Int {
      if (reverseStack.isEmpty()) throw NullPointerException()
      return reverseStack.pop()
    }

    fun peek(): Int {
      if (reverseStack.isEmpty()) throw NullPointerException()
      return reverseStack.peek()
    }
  }

  private class GenericStack {
    private var head: Node? = null
    var size = 0
      private set

    fun peek(): Int {
      if (head != null) return head!!.value
      throw EmptyStackException()
    }

    fun pop(): Int {
      if (head == null) throw EmptyStackException()
      val resultNode = head
      head = head!!.prev
      resultNode!!.prev = null
      size--
      return resultNode.value
    }

    fun push(value: Int) {
      val newNode = Node(value, head)
      head = newNode
      size++
    }

    fun isEmpty(): Boolean {
      return head == null
    }

    fun isNotEmpty(): Boolean {
      return head != null
    }

    private data class Node(val value: Int, var prev: Node?)
  }
}