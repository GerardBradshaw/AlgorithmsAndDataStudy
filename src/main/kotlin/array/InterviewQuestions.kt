package array

class InterviewQuestions {

  /**
   * Returns true if [string] has all unique characters. O(n) time, O(1) space. Assumes string is ASCII.
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
}