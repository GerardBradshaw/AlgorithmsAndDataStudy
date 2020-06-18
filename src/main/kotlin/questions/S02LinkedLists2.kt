package questions

class S02LinkedLists2 {
  fun sumListRecur(listA: Node, listB: Node): Node {
    var aPointer: Node? = listA
    var bPointer: Node? = listB

    var paddedListA = listA
    var paddedListB = listB
    while (aPointer != null || bPointer != null) {
      if (aPointer == null) paddedListA = Node(0, paddedListA)
      if (bPointer == null) paddedListB = Node(0, paddedListB)
      aPointer = aPointer?.next
      bPointer = bPointer?.next
    }

    // Sum the lists
    val sumResult = sumValues(paddedListA, paddedListB)

    // Add additional first digit if there's a carry
    if (sumResult.carry != 0) {
      val head = Node(sumResult.carry)
      head.next = sumResult.head
      return head
    }
    return sumResult.head!!
  }

  private fun sumValues(listA: Node?, listB: Node?): Result {
    if (listA == null && listB == null) return Result()

    val prevResult = sumValues(listA?.next, listB?.next)

    val aValue = listA?.value ?: 0
    val bValue = listB?.value ?: 0

    val sum = aValue + bValue + prevResult.carry
    val thisSum = sum % 10
    val carryUp = (sum - thisSum) / 10

    val newHead = Node(thisSum)
    newHead.next = prevResult.head

    return Result(carryUp, newHead)
  }

  private data class Result(var carry: Int = 0, var head: Node? = null)

  data class Node(var value: Int, var next: Node? = null)

  fun strStr(haystack: String, needle: String): Int {
    if (needle.isEmpty()) return 0
    else if (needle.length > haystack.length) return -1

    val haystackMaxIndex = haystack.length - 1
    val needleMaxIndex = needle.length - 1
    var h = 0

    mainLoop@
    while (h <= haystackMaxIndex - needleMaxIndex) {
      if (haystack[h + needleMaxIndex] == needle[needleMaxIndex]) {
        for (n in needleMaxIndex downTo 0) {
          if (haystack[h + n] != needle[n]) {
            // Bad char rule
            val failedChar = haystack[h + n]
            val nextIndexOfFailedChar = getNextIndex(failedChar, needle, n)
            h += n - nextIndexOfFailedChar
            continue@mainLoop
          }
        }
        return h
      }
      h++
    }
    return -1
  }

  private fun getNextIndex(char: Char, string: String, startIndex: Int): Int {
    for (i in startIndex downTo 0) {
      if (string[i] == char) return i
    }
    return 0
  }
}