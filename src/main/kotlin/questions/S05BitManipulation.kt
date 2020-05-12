package questions

import kotlin.math.pow

class S05BitManipulation {

  fun insertion(n: Int, m: Int, i: Int, j: Int): Int {
    val allOnes = 0.inv()

    val maskLeft = if (j < 31) allOnes.shl(j + 1) else 0
    val maskRight = 1.shl(i) - 1

    val mask = maskLeft or maskRight

    val nCleared = n and mask
    val mShifted = m.shl(i)

    return nCleared or mShifted
  }

  fun toBinaryString(n: Int, bits: Int): String {
    val stringBuilder = StringBuilder()
    var remainder = n

    for (i in bits-1 downTo 0) {
      val bitValue = 2.0.pow(i).toInt()

      if (remainder == 0 || remainder / bitValue == 0) stringBuilder.append(0)
      else {
        remainder -= bitValue
        stringBuilder.append(1)
      }
    }

    return if (remainder != 0) "ERROR"
    else stringBuilder.toString()
  }

  fun smallDoubleToBinaryString(d: Double): String {
    if (d < 0 || d > 1) return "must be between 0 and 1"

    val stringBuilder = StringBuilder().append(".")
    var remainder = d

    for (i in 1..32) {
      if (remainder == 0.0) break

      val bitValue = 2.0.pow(-i)

      if (remainder - bitValue < 0) stringBuilder.append(0)
      else {
        stringBuilder.append(1)
        remainder -= bitValue
      }
    }

    return if (remainder != 0.0) return "ERROR"
    else stringBuilder.toString()
  }

  fun flipBitToWin(n: Int): Int {
    if (n.inv() == 0) return 32

    var nCopy = n
    var currentLength = 0
    var prevLength = 0

    var maxLength = 1

    while (nCopy != 0) {
      if ((nCopy and 1) == 1) currentLength++
      else {
        prevLength = if (nCopy and 2 == 0) 0 else currentLength
        currentLength = 0
      }
      maxLength = Math.max(prevLength + currentLength + 1, maxLength)
      nCopy = nCopy.ushr(1)
    }
    return maxLength
  }

  fun nextNumber(n: Int): IntArray {
    var nextLargest = -1
    var nextSmallest = -1
    var bitMask = 1
    var zeroCount = 0
    var oneCount = 0

    while (bitMask != 0) {
      val currentBit = if (bitMask and n != 0) 1 else 0

      if (currentBit == 1) {
        if (zeroCount > 0 && nextSmallest == -1) nextSmallest = getNextSmallest(zeroCount, oneCount, n)
        oneCount++

      } else {
        if (oneCount > 0 && nextLargest == -1) nextLargest = getNextLargest(zeroCount, oneCount, n)
        zeroCount++
      }

      if (nextLargest != -1 && nextSmallest != -1) break

      bitMask = bitMask shl 1
    }

    if (nextSmallest == -1) nextSmallest = n
    if (nextLargest == -1) nextLargest = n

    return intArrayOf(nextSmallest, nextLargest)
  }

  private fun getNextLargest(zeroCount: Int, oneCount: Int, n: Int): Int {
    val ones = 0.inv()

    val nFlippedAtCurrentPosition = n or 1.shl(zeroCount + oneCount)
    val leftSide = nFlippedAtCurrentPosition and ones.shl(zeroCount + oneCount)
    val rightSide = ones.shl(oneCount - 1).inv()

    return leftSide or rightSide
  }

  private fun getNextSmallest(zeroCount: Int, oneCount: Int, n: Int): Int {
    val ones = 0.inv()

    val rightSideLeft = ones.shl(zeroCount + oneCount)
    val rightSideRight = ones.shl(zeroCount - 1).inv()
    val rightSide = (rightSideLeft or rightSideRight).inv()

    val leftSide = n and ones.shl(zeroCount + oneCount + 1)

    return leftSide or rightSide
  }

  fun oneCount(n: Int): Int {
    var mask = 1
    var count = 0

    while (mask < n) {
      if (n and mask != 0) count++
      mask = mask.shl(1)
    }
    return count
  }

  fun debugger(){}

  fun pairwiseSwap(n: Int): Int {
    var workingResult = 0
    for (i in 0..30 step 2) {
      val firstBit = if (n and 2.0.pow(i.toDouble()).toInt() != 0) 1 else 0
      var secondBit = if (n and 2.0.pow(i.toDouble() + 1).toInt() != 0) 1 else 0
      if (i == 30) secondBit = 0
      workingResult = workingResult or firstBit.shl(i + 1)
      workingResult = workingResult or secondBit.shl(i)
    }
    return workingResult
  }

  fun pairwiseSwap2(n: Int): Int {
    val evenShiftedRight = (n and 0b0101010_10101010_10101010_10101010).ushr(1)
    val oddShiftedLeft = (n and 0b1010101_01010101_01010101_01010101).shl(1)
    return evenShiftedRight or oddShiftedLeft
  }



}