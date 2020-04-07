package array

import map.MyHashMap
import java.lang.Exception

class InterviewQuestions {

  /**
   * Returns true if [string] has all unique characters. O(n) time, O(1) space. Assumes string is ASCII.
   *
   * Other methods:
   * - Use a set to achieve O(n log(n)) time (needs extra space though)
   * - Sort the array using heap sort for O(n log(n)) time (no extra space needed!) if modification of string allowed.
   * - Compare every char with all others for O(n^2) time.
   */
  fun q1_1_isUnique(string: String): Boolean {
    if (string.length > 128) return false

    val asciiCharInString = BooleanArray(128)
    for (i in string.indices) {
      val index = string[i].toInt()
      if (asciiCharInString[index]) return false
      asciiCharInString[index] = true
    }
    return true
  }

  /**
   * Returns true if [string1] and [string2] are permutations of each other. O(n) time, O(n) space.
   *
   * Other methods:
   * - Sort strings then check if they are equal for O(n log(n)) time.
   * - Use an approach similar to [q1_1_isUnique] if character space is known.
   */
  fun q1_2_checkPermutation(string1: String, string2: String): Boolean {
    if (string1.length != string2.length) return false

    val charToCount = MyHashMap<Char, Int>()

    for (c in string1) {
      val prevCount = charToCount.get(c)
      var newCount = 1

      if (prevCount != null) {
        newCount += prevCount
        charToCount.remove(c)
      }
      charToCount.put(c, newCount)
    }

    for (c in string2) {
      val prevCount = charToCount.get(c)

      if (prevCount != null) {
        if (prevCount == 1) charToCount.remove(c)
        else {
          charToCount.remove(c)
          charToCount.put(c, prevCount - 1)
        }
      }
      else return false
    }
    return true
  }

  /**
   * Replaces spaces in [charArray] with '%20'. Assumes there are blank spaces at the end of the array to accommodate
   * the extra space required for additional characters. [length] is the initial length of charArray excluding end blanks.
   * O(n) time, O(1) space.
   *
   * Other methods:
   * - Copy to new array (O(n) time but O(n + s) space, s = number of spaces)
   */
  fun q1_3_URLify(charArray: CharArray, length: Int) {
    var spaceCount = 0
    for (i in 0..length - 1) if (charArray[i] == ' ') spaceCount++ // O(n)

    val requiredLength = length + spaceCount * 2
    if (charArray.size < requiredLength) throw Exception()
    else if (length == requiredLength) return

    val lastRequiredIndex = requiredLength - 1

    var urlPointer = length - 1
    var insertionPointer = lastRequiredIndex

    while (insertionPointer >= 0) {
      when (charArray[urlPointer]) {
        ' ' -> {
          charArray[insertionPointer--] = '0'
          charArray[insertionPointer--] = '2'
          charArray[insertionPointer--] = '%'
          urlPointer--
        }
        else -> {
          charArray[insertionPointer--] = charArray[urlPointer--]
        }
      }
    }
  }
}