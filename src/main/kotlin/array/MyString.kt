package array

import java.lang.NullPointerException
import kotlin.math.pow

class MyString : Comparable<MyString> {

  // ---------------- Fields & Constructors ----------------

  private var chars: CharArray

  private val length: Int
    get() = chars.size

  constructor(charArray: CharArray) {
    chars = charArray
  }

  constructor(charArray: Array<Char>) {
    chars = charArray.toCharArray()
  }

  constructor(charSequence: CharSequence) {
    val charArray = CharArray(charSequence.length)
    for (i in charSequence.indices) charArray[i] = charSequence[i]
    chars = charArray
  }


  // ---------------- Public fun ----------------

  fun get(index: Int): Char {
    if (index > length - 1) throw NullPointerException()
    return chars[index]
  }

  fun subSequence(startIndex: Int, endIndex: Int): MyString {
    if (startIndex > endIndex || endIndex > length - 1) throw NullPointerException()

    return MyString(subSequenceAsCharArray(startIndex, endIndex))
  }

  override fun compareTo(other: MyString): Int {
    return hashCode() - other.hashCode()
  }

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (char in chars) {
      builder.append(char)
    }
    return builder.toString()
  }

  override fun hashCode(): Int {
    var sum = 0.0

    for (i in 0 until length) {
      sum += chars[i].toInt() * 31.0.pow(length - i - 1)
    }
    return sum.toInt()
  }

  override fun equals(other: Any?): Boolean {
    if (other !is MyString || other.length != length) return false
    return (other.chars.contentEquals(chars))
  }


  // ---------------- Helpers ----------------

  private fun subSequenceAsCharArray(startIndex: Int, endIndex: Int): CharArray {
    val subLength = endIndex - startIndex + 1
    val charArray = CharArray(subLength)

    for (i in 0 until subLength) {
      charArray[i] = chars[startIndex + i]
    }
    return charArray
  }

}
