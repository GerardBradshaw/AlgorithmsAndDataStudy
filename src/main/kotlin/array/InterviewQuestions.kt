package array

import map.MyHashMap

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
}