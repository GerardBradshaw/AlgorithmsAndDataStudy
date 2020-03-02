package array

import java.lang.NullPointerException
import kotlin.math.pow

class MyString : Comparable<MyString> {

  // ---------------- Member variables ----------------

  private lateinit var chars: CharArray
  private val length: Int
    get() = chars.size


  // ---------------- Constructors ----------------

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


  // ---------------- String methods ----------------

  fun get(index: Int): Char {
    if (index > length - 1) throw NullPointerException()
    return chars[index]
  }

  fun subSequence(startIndex: Int, endIndex: Int): MyString {
    if (startIndex > endIndex || endIndex > length - 1)
      throw NullPointerException()

    val subLength = endIndex - startIndex + 1

    val charSeq = CharArray(subLength)

    for (i in 0 until subLength) {
      charSeq[i] = chars[startIndex + i]
    }

    return MyString(charSeq)
  }

  override fun compareTo(other: MyString): Int {
    // -'ve this less than, 0 equal, 1 this more than
    return hashCode() - other.hashCode()
  }


  // ---------------- Any callbacks ----------------

  override fun toString(): String {
    val builder = MyStringBuilder()

    for (char in chars) builder.append(char)

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
    if (other !is MyString) return false

    if (other.length != length) return false

    if (other.chars.contentEquals(chars)) return true
    return false
  }
}
