package set

class MyHashSet<T> : Set<T>, Iterable<T> {

  // Member variables

  

  // Public methods

  fun insert(element: T) {
    TODO()
  }

  fun delete(element: T) {
    TODO()
  }

  override val size: Int
    get() = TODO()

  override fun contains(element: T): Boolean {
    TODO()
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    TODO()
  }

  override fun isEmpty(): Boolean {
    TODO()
  }

  override fun iterator(): Iterator<T> {
    TODO()
  }


  // Private methods

  private fun getHash(element: T): Int {
    //return element.hashCode() %
    TODO()
  }


}