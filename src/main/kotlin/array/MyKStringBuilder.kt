package array

class MyKStringBuilder {

  var charArray = CharArray(1)
  var numberOfChars = 0

  fun append(char: Char): MyKStringBuilder {
    if (numberOfChars == charArray.size) {
      resizeArray()
    }
    charArray[numberOfChars] = char
    numberOfChars++

    return this
  }

  fun append(string: String): MyKStringBuilder {
    for (char in string) {
      append(char)
    }
    return this
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
    return String(charArray.copyOfRange(0, numberOfChars))
  }
}