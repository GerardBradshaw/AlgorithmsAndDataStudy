package questions

import array.MyStringBuilder
import map.MyHashMap
import java.lang.Exception
import kotlin.math.abs

class S01ArraysAndStrings {

  /**
   * Returns true if [string] has all unique characters. O(n) time, O(1) space. Assumes string is ASCII.
   *
   * Other methods:
   * - Use a set to achieve O(n log(n)) time (needs extra space though)
   * - Sort the array using heap sort for O(n log(n)) time (no extra space needed!) if modification of string allowed.
   * - Compare every char with all others for O(n^2) time.
   */
  fun q0101isUnique(string: String): Boolean {
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
   * - Use an approach similar to [q0101isUnique] if character space is known.
   */
  fun q0102checkPermutation(string1: String, string2: String): Boolean {
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
  fun q0103urlify(charArray: CharArray, length: Int) {
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

  /**
   * Returns true of [string] can be rearranged into a palindrome. Ignores spaces and characters in extended ASCII. O(n)
   * time, O(1) space.
   *
   * Other methods:
   * - Use a map to store the number of chars then iterate over the map to check there's an appropriate number of
   * repeated letters. This is also O(n) time but O(n) space.
   * - Use bit manipulation (see p198 of CtCI).
   */
  fun q0104palindromePermutation(string: String): Boolean {
    val asciiCharCount = IntArray(128)
    var strCharCount = 0
    var numberOfOddChars = 0

    for (c in string) {
      val cInt = c.toInt()
      if (cInt > 127 || cInt == 32) continue

      asciiCharCount[cInt] += 1
      strCharCount++

      if (asciiCharCount[cInt] % 2 != 0) numberOfOddChars++
      else numberOfOddChars--
    }

    return if (strCharCount % 2 == 0) numberOfOddChars == 0 else numberOfOddChars == 1
  }

  /**
   * Returns true if [string1] and [string2] are one edit away. An edit is defined as insertion of a character, deletion
   * of a character, or replacement of a character. O(N) time, O(1) space.
   *
   * Other approaches:
   * - Brute force (very slow)
   */
  fun q0105oneAway(string1: String, string2: String): Boolean {
    when (abs(string1.length - string2.length)) {
      0 -> {
        var numberOfEdits = 0
        for (i in string1.indices) {
          if (string1[i] != string2[i]) numberOfEdits++
        }
        return numberOfEdits <= 1
      }
      1 -> {
        val longerString = if (string1.length > string2.length) string1 else string2
        val shorterString = if (longerString == string1) string2 else string1

        var longerOffset = 0

        for (i in shorterString.indices) {
          val shorterStringChar = shorterString[i]
          val longerStringChar = longerString[i + longerOffset]

          if (shorterStringChar != longerStringChar) {
            longerOffset++
            if (longerOffset > 1) return false
          }
        }
        return true
      }
      else -> return false
    }
  }

  /**
   * Returns a [String] representing the compressed form of [string] where the counts of each character is written rather
   * than repeated characters if the compressed length is shorter than the original length. O(n) time, O(n) space.
   *
   * Other approaches:
   * - Pre-compute the length of compressed string before calculating to potentially save space (increases actual time
   * but still O(n)
   * - Don't use a builder and use additional space O(n + k^2) time, k = number of character sequences. This is BAD.
   */
  fun q0106stringCompression(string: String): String {
    val originalLength = string.length

    if (originalLength > 2) {
      var repeatCount = 0
      var prevChar = '\u0000'
      val builder = MyStringBuilder()

      for (c in string) {
        if (c != prevChar) {
          if (prevChar != '\u0000') builder.append((repeatCount + 1).toString())
          builder.append(c)
          repeatCount = 0
        } else if (prevChar != '\u0000') repeatCount++

        prevChar = c
      }
      if (repeatCount != 0) builder.append((repeatCount + 1).toString())

      if (builder.length() < originalLength) return builder.toString()
    }
    return string
  }

  /**
   * Rotates and NxN [matrix] clockwise 90 degrees in O(N^2) time and O(1) space.
   *
   * Other approaches:
   * - Copy to another matrix. Requires O(N^2) space.
   * - Perform other mathematical rotations in O(N^2) time and O(1) space also.
   */
  fun q0107rotateMatrix(matrix: Array<IntArray>) {
    val maxIndex = matrix.size - 1

    for (row in 0 until maxIndex) {
      for (column in 0 until (maxIndex - row)) {
        if (row + column != maxIndex) {
          val mirrorColumn = maxIndex - row
          val mirrorRow = maxIndex - column

          // Swap mirror values
          val val1 = matrix[row][column]
          matrix[row][column] = matrix[mirrorRow][mirrorColumn]
          matrix[mirrorRow][mirrorColumn] = val1
        }
      }
    }

    // Rearrange rows
    val size = matrix.size
    for (col in 0..((matrix.size - 1) /2)) {
      val col1 = matrix[col]
      matrix[col] = matrix[size - 1 - col]
      matrix[size - 1 - col] = col1
    }
  }

  /**
   * If a zero exists in [matrix], the row and column containing it are all zeroed. O(M * N) time, O(M + N) space.
   *
   * Other approaches:
   * - Could reduce space complexity to O(1) if first entry in row is set to zero if any zero exists in the row, and the
   * first entry in the column is set to zero if any exist in the column. Then, rows/columns that start with zero in the
   * matrix are entirely zeroed.
   */
  fun q0108zeroMatrix(matrix: Array<IntArray>) {
    val maxRow = matrix.size - 1
    val maxCol = matrix[0].size - 1
    val rowDelete = BooleanArray(matrix.size)
    val colDelete = BooleanArray(matrix[0].size)

    for (row in 0..maxRow) {
      for (col in 0..maxCol) {
        if (matrix[row][col] == 0) {
          rowDelete[row] = true
          colDelete[col] = true
        }
      }
    }

    for (row in 0..maxRow) {
      for (col in 0..maxCol) {
        if (rowDelete[row]) matrix[row][col] = 0
        if (colDelete[col]) matrix[row][col] = 0
      }
    }
  }

  /**
   * See output.
   */
  fun q0109stringRotation(string1: String, string2: String) {
    println("Needs \"isSubstring()\" method. Basically, call isSubstring(s1s1, s2) after checking they're the same length. This needs O(N) time and space.")
  }
}