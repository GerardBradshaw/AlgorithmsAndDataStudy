package questions

import array.MyArrayList
import array.MyStringBuilder
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
   * A stack composed of several smaller stacks and fun popAt(index: Int) function that pops a value from a specific
   * stack.
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
      //val stackIndex = index / maxStackSize
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

  /**
   * Sorts [stack] using only another [GenericStack] as a temporary data structure. O(N^2) time, O(N) space.
   */
  fun q0305aSortStack(stack: GenericStack) {
    if (stack.isEmpty()) return

    val sortedStack = GenericStack()
    sortedStack.push(stack.pop())

    while (stack.isNotEmpty()) {
      q0305aPushToSortedStack(stack.pop(), sortedStack, stack)
    }

    while (sortedStack.isNotEmpty()) stack.push(sortedStack.pop())
  }

  private fun q0305aPushToSortedStack(current: Int, sortedStack: GenericStack, unsortedStack: GenericStack) {
    while (sortedStack.isNotEmpty()) {
      if (current > sortedStack.peek()) {
        sortedStack.push(current)
        return
      }
      else unsortedStack.push(sortedStack.pop())
    }
    sortedStack.push(current)
  }

  /**
   * Sorts [stack] using a MergeSort approach and only [GenericStack]s for temp data storage. O(N * log(N)) time,
   * O(N) space.
   *
   * Improvements:
   * - Could use queue or array instead of stack for sorting. This would eliminate the need to reverse the stack each
   * time using [q0305EnsureStackAscending].
   */
  fun q0305bSortStack(stack: GenericStack) {
    val result = q0305bMergeSort(stack)
    while (result.isNotEmpty()) stack.push(result.pop())
  }

  private fun q0305bMergeSort(stack: GenericStack): GenericStack {
    if (stack.size == 1) return stack

    var pushToStack1 = true
    var stack1 = GenericStack()
    var stack2 = GenericStack()

    while (stack.isNotEmpty()) {
      if (pushToStack1) stack1.push(stack.pop())
      else stack2.push(stack.pop())
      pushToStack1 = !pushToStack1
    }

    stack1 = q0305bMergeSort(stack1)
    stack2 = q0305bMergeSort(stack2)
    return q0305bMergeStacks(stack1, stack2)
  }

  private fun q0305bMergeStacks(stack1: GenericStack, stack2: GenericStack): GenericStack {
    val result = GenericStack()

    q0305EnsureStackAscending(stack1)
    q0305EnsureStackAscending(stack2)

    while (stack1.isNotEmpty() && stack2.isNotEmpty()) {
      if (stack1.peek() <= stack2.peek()) result.push(stack1.pop())
      else result.push(stack2.pop())
    }

    while (stack1.isNotEmpty()) result.push(stack1.pop())
    while (stack2.isNotEmpty()) result.push(stack2.pop())

    return result
  }

  private fun q0305EnsureStackAscending(stack: GenericStack) {
    if (stack.size <= 1) return

    val first = stack.pop()
    if (first > stack.peek()) {
      stack.push(first)
      stack.reverse()
    }
    else stack.push(first)
  }

  /**
   * Sorts [stack] using a QuickSort approach and only [GenericStack]s for temp data storage. O(N * log(N)) time,
   * O(N) space (for recursion stack).
   */
  fun q0305cSortStack(stack: GenericStack) {
    val result = q0305cQuickSort(stack)
    while (result.isNotEmpty()) stack.push(result.pop())
  }

  private fun q0305cQuickSort(stack: GenericStack): GenericStack {
    if (stack.size <= 1) return stack

    val pivot = stack.pop()
    var lesserOrEqualStack = GenericStack()
    var greaterStack = GenericStack()

    while (stack.isNotEmpty()) {
      if (stack.peek() <= pivot) lesserOrEqualStack.push(stack.pop())
      else greaterStack.push(stack.pop())
    }

    lesserOrEqualStack = q0305cQuickSort(lesserOrEqualStack)
    greaterStack = q0305cQuickSort(greaterStack)
    return q0305cCombineStacks(lesserOrEqualStack, pivot, greaterStack)
  }

  private fun q0305cCombineStacks(lesserOrEqualStack: GenericStack, pivot: Int, greaterStack: GenericStack): GenericStack {
    val result = GenericStack()

    while (greaterStack.isNotEmpty()) result.push(greaterStack.pop())
    result.push(pivot)
    while (lesserOrEqualStack.isNotEmpty()) result.push(lesserOrEqualStack.pop())

    result.reverse()
    return result
  }

  /**
   * Imitates an animal shelter of dogs and cats where a request for a dog/cat/either will return the least-recently
   * added animal of that type.
   *
   * Other approaches:
   * - I kind of misunderstood the question but my functionality is correct. An intended approach is to use a LL each
   * for dogs and cats, along with a custom Animal class which stores its species, name, and timestamp. All you'd have
   * to do is peek at both lists and retrieve the animal with oldest timestamp for dequeueAny().
   */
  class Q0306AnimalShelter {
    private val isDogAddedLastList = LinkedList<Boolean>()
    private val dogQueue = Queue()
    private val catQueue = Queue()
    private var animalNumber = 0

    fun enqueue(isDog: Boolean, name: String) {
      if (isDog) enqueueDog(name) else enqueueCat(name)
    }

    private fun enqueueDog(name: String) {
      dogQueue.enqueue(name + animalNumber)
      isDogAddedLastList.add(true)
      animalNumber++
    }

    private fun enqueueCat(name: String) {
      catQueue.enqueue(name + animalNumber)
      isDogAddedLastList.add(false)
      animalNumber++
    }

    fun dequeueAny(): String {
      val result = if (isDogAddedLastList.first) dogQueue.dequeue() else catQueue.dequeue()
      isDogAddedLastList.removeFirst()
      return result
    }

    fun dequeueDog(): String {
      isDogAddedLastList.remove(true)
      return dogQueue.dequeue()
    }

    fun dequeueCat(): String {
      isDogAddedLastList.remove(false)
      return catQueue.dequeue()
    }

    private class Queue {
      private var oldest: Node? = null
      private var newest: Node? = null
      private var size = 0

      fun enqueue(name: String) {
        val newNode = Node(name)

        when {
          oldest == null -> oldest = newNode
          newest == null -> {
            newest = newNode
            oldest!!.prev = newest
          }
          else -> {
            newest!!.prev = newNode
            newest = newNode
          }
        }
        size++
      }

      fun dequeue(): String {
        val result = oldest!!.nameAndId
        oldest = oldest!!.prev
        return result
      }

      private data class Node(val nameAndId: String, var prev: Node? = null)
    }
  }

  class GenericStack {
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

    fun reverse() {
      val temp = GenericStack()
      temp.head = head
      head = null
      while (temp.isNotEmpty()) push(temp.pop())
    }

    fun isEmpty(): Boolean {
      return head == null
    }

    fun isNotEmpty(): Boolean {
      return head != null
    }

    override fun toString(): String {
      var current = head

      return if (current == null) "[]"
      else {
        val result = MyStringBuilder().append("[")

        while (current != null) {
          result.append(current.value.toString()).append(", ")
          current = current.prev
        }

        result.removeEnd(2).append("]").toString()
      }
    }

    private data class Node(val value: Int, var prev: Node?)
  }
}