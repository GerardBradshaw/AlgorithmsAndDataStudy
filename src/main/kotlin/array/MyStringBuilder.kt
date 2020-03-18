package array

class MyStringBuilder() {

  // ---------------- Fields & Constructor ----------------

  private var array = CharArray(1)

  private var numberOfChars = 0

  constructor(string: String) : this() {
    for (char in string) {
      append(char)
    }
  }


  // ---------------- Public fun ----------------

  fun append(char: Char?): MyStringBuilder {
    if (char != null) {
      if (numberOfChars == array.size) {
        resizeArray()
      }
      array[numberOfChars] = char
      numberOfChars++
    }
    return this
  }

  fun append(string: String?): MyStringBuilder {
    if (string != null) {
      for (char in string) append(char)
    }
    return this
  }

  fun removeEnd(numberOfChars: Int): MyStringBuilder {
    if (this.numberOfChars < numberOfChars) {
      array = CharArray(1)
      this.numberOfChars = 0
    }
    else {
      val startIndex = this.numberOfChars - numberOfChars
      for (i in startIndex until this.numberOfChars) array[i] = '\u0000'
      this.numberOfChars = startIndex
    }
    resizeArray()
    return this
  }

  fun length(): Int {
    return numberOfChars
  }

  fun report() {
    println("There are $numberOfChars chars stored in an array of size ${array.size}")
  }

  fun reverse() {
    if (numberOfChars == 0) return

    var charAtI: Char?
    var oppositeI: Int

    for (i in 0..((numberOfChars - 1) / 2)) {
      oppositeI = numberOfChars - i - 1
      charAtI = array[i]
      array[i] = array[oppositeI]
      array[oppositeI] = charAtI
    }
  }

  override fun toString(): String {
    if (numberOfChars == 0) return ""
    return String(array.copyOfRange(0, numberOfChars))
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyStringBuilder || other.numberOfChars != numberOfChars) return false

    for (i in 0 until numberOfChars) {
      if (other.array[i] != array[i]) return false
    }
    return true
  }

  override fun hashCode(): Int {
    val prime = 31
    var result = 1

    for (i in 0 until numberOfChars) {
      result = result * prime + array[i].hashCode()
    }
    return result
  }

  // ---------------- Helpers ----------------

  private fun resizeArray() {
    val temp =  CharArray(array.size * 2)
    System.arraycopy(array, 0, temp, 0, array.size)
    array = temp
  }
}