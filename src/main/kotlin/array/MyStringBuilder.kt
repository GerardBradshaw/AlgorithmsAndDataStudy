package array

class MyStringBuilder() {

  private var charArray = CharArray(1)
  private var numberOfChars = 0

  constructor(string: String) : this() {
    for (char in string) {
      append(char)
    }
  }

  fun append(char: Char?): MyStringBuilder {
    if (char != null) {
      if (numberOfChars == charArray.size) {
        resizeArray()
      }
      charArray[numberOfChars] = char
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

  fun removeEnd(numberOfChars: Int) {
    if (this.numberOfChars < numberOfChars) {
      charArray = CharArray(1)
      this.numberOfChars = 0
    }
    else {
      val startIndex = this.numberOfChars - numberOfChars
      for (i in startIndex until this.numberOfChars) charArray[i] = '\u0000'
      this.numberOfChars = startIndex
    }
    resizeArray()
  }

  fun length(): Int {
    return numberOfChars
  }

  fun report() {
    println("There are $numberOfChars chars stored in an array of size ${charArray.size}")
  }

  fun reverse() {
    if (numberOfChars == 0) return

    var charAtI: Char?
    var oppositeI: Int

    for (i in 0..((numberOfChars - 1) / 2)) {
      oppositeI = numberOfChars - i - 1
      charAtI = charArray[i]
      charArray[i] = charArray[oppositeI]
      charArray[oppositeI] = charAtI
    }
  }

  private fun resizeArray() {
    val temp =  CharArray(charArray.size * 2)
    System.arraycopy(charArray, 0, temp, 0, charArray.size)
    charArray = temp
  }

  override fun toString(): String {
    if (numberOfChars == 0) return ""
    return String(charArray.copyOfRange(0, numberOfChars))
  }
}