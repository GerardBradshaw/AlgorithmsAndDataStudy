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


}