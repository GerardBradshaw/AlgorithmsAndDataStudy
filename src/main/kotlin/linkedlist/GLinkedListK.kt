package linkedlist

interface GLinkedListK<T> {

  // ------- Add
  fun addFirst(data: T)
  fun addLast(data: T)
  fun addAtIndex(data: T, index: Int)

  // ------- Set
  fun setFirst(data: T)
  fun setLast(data: T)
  fun setAtIndex(data: T, index: Int)

  // ------- Delete
  fun removeFirst()
  fun removeLast()
  fun removeAtIndex(index: Int)
  fun remove(data: T)

  // ------- Get
  fun getFirst(): T
  fun getLast(): T
  fun getAtIndex(index: Int): T

  // ------- Check
  fun contains(data: T): Boolean

}