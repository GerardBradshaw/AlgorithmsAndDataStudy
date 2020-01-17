package linkedlist

interface GLinkedListK<T> {

  // ------- Add
  fun addAtStart(data: T)
  fun addAtEnd(data: T)
  fun addAtIndex(data: T, index: Int)

  // ------- Set
  fun setAtEnd(data: T)
  fun setAtStart(data: T)
  fun setAtIndex(data: T, index: Int)

  // ------- Delete
  fun deleteAtStart()
  fun deleteAtEnd()
  fun deleteAtIndex(index: Int)
  fun deleteItem(data: T)

  // ------- Get
  fun getAtStart(): T
  fun getAtEnd(): T
  fun getAtIndex(index: Int): T

  // ------- Check
  fun contains(data: T): Boolean

}