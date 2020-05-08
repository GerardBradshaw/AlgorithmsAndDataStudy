package questions

class S05BitManipulation {

  fun insertion(n: Int, m: Int, i: Int, j: Int): Int {
    // Examples in comments are NOT exact (just representative)

    val allOnes = 0.inv() // !0

    val maskLeft = if (j < 31) allOnes.shl(j + 1) else 0 // 1111111 << j+1 = 11110000000
    val maskRight = 1.shl(i) - 1 // (1 << i) - 1 = 0000001000 - 1 = 0000000111

    val mask = maskLeft or maskRight // 1110000000 or 000000111 = 11110000111

    val nCleared = n and mask // 1010011011 && 111110000111 = 1010000011
    val mShifted = m.shl(i) // 1001 << i = 10010000

    return nCleared or mShifted
  }

}