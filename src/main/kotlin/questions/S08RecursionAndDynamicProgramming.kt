package questions

import java.awt.Point
import java.lang.Exception
import java.security.InvalidParameterException
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.max
import kotlin.math.pow

class S08RecursionAndDynamicProgramming {

  // - - - - - - - - - - - - - - - - QUESTION 2 - - - - - - - - - - - - - - - -
  fun robotInAGrid(grid: Array<BooleanArray>): String {
    val visitedPoints = HashSet<Point>()
    val routeStack = Stack<String>()
    isValidCell(grid, 0, 0, visitedPoints, routeStack)

    return routeStackToString(routeStack)
  }

  private fun isValidCell(grid: Array<BooleanArray>, row: Int, col: Int,
                          visitedPoints: HashSet<Point>, routeStack: Stack<String>): Boolean {

    if (isInvalidCell(grid, row, col, visitedPoints)) return false
    if (isAtDestination(grid, row, col)) return true

    visitedPoints.add(Point(col, row))

    if (isValidCell(grid, row + 1, col, visitedPoints, routeStack)) {
      routeStack.add("↓")
      return true
    }

    if (isValidCell(grid, row, col + 1, visitedPoints, routeStack)) {
      routeStack.add("→")
      return true
    }

    return false
  }

  private fun isInvalidCell(grid: Array<BooleanArray>, row: Int, col: Int, visitedPoints: HashSet<Point>): Boolean {
    val maxRow = grid.size - 1
    val maxCol = grid[0].size - 1

    return when {
      row > maxRow || col > maxCol -> true
      visitedPoints.contains(Point(col, row)) -> true
      else -> grid[row][col]
    }
  }

  private fun isAtDestination(grid: Array<BooleanArray>, row: Int, col: Int): Boolean {
    return row == grid.size - 1 && col == grid[0].size - 1
  }

  private fun routeStackToString(routeStack: Stack<String>): String {
    if (routeStack.isEmpty()) return "no valid routes"

    val routeString = StringBuilder()
    while (routeStack.isNotEmpty()) {
      routeString.append(routeStack.pop()).append(" ")
    }
    return routeString.substring(0,routeString.length - 1).toString()
  }


  // - - - - - - - - - - - - - - - - QUESTION 3a - - - - - - - - - - - - - - - -
  fun magicIndex(array: IntArray): Int {
    if (array.isEmpty()) return -1
    return getMagicIndex(array, 0, array.size - 1)
  }

  private fun getMagicIndex(array: IntArray, leftIndex: Int, rightIndex: Int): Int {
    if (leftIndex > rightIndex) return -1

    val midIndex = (leftIndex + rightIndex) / 2
    val midValue = array[midIndex]

    return when {
      midValue < midIndex -> getMagicIndex(array, midIndex + 1, rightIndex)
      midValue > midIndex -> getMagicIndex(array, leftIndex, midIndex - 1)
      else -> midIndex
    }
  }

  // - - - - - - - - - - - - - - - - QUESTION 3b - - - - - - - - - - - - - - - -
  fun magicIndex2(array: IntArray): Int {
    if (array.isEmpty()) return -1
    return getMagicIndex2(array, 0, array.size - 1)
  }

  private fun getMagicIndex2(array: IntArray, leftIndex: Int, rightIndex: Int): Int {
    if (leftIndex > rightIndex) return -1

    val midIndex = (leftIndex + rightIndex) / 2
    val midValue = array[midIndex]

    if (midValue == midIndex) return midIndex

    // Left search
    val rightBound = Math.min(midIndex - 1, midValue)
    val leftSearch = getMagicIndex(array, leftIndex, rightBound)
    if (leftSearch != -1) return leftSearch

    // Right search
    val leftBound = Math.max(midIndex + 1, midValue)
    return getMagicIndex2(array, leftBound, rightIndex)
  }

  // - - - - - - - - - - - - - - - - QUESTION 4a - - - - - - - - - - - - - - - -
  fun powerSet(set: ArrayList<Int>): ArrayList<ArrayList<Int>> {
    val results = ArrayList<ArrayList<Int>>()
    for (i in 1 until 2.0.pow(set.size).toInt()) {
      results.add(getSetFromBinary(set, i))
    }
    return results
  }

  private fun getSetFromBinary(set: ArrayList<Int>, num: Int): ArrayList<Int> {
    var numCopy = num
    var index = 0

    val subset = ArrayList<Int>()
    while (numCopy != 0) {
      if (numCopy and 1 == 1) subset.add(set[index])
      numCopy = numCopy.ushr(1)
      index++
    }
    return subset
  }

  // - - - - - - - - - - - - - - - - QUESTION 4b - - - - - - - - - - - - - - - -
  fun powerSet2(set: ArrayList<Int>): ArrayList<ArrayList<Int>> {
    val results = ArrayList<ArrayList<Int>>()

    for (i in set.indices) {
      val currentVal = set[i]

      val additionalResults = ArrayList<ArrayList<Int>>(2.0.pow(i-1).toInt())

      for (list in results) {
        val listWithCurrentVal = ArrayList(list).also { it.add(currentVal) }
        additionalResults.add(listWithCurrentVal)
      }

      results.addAll(additionalResults)
      results.add(ArrayList<Int>(intArrayOf(currentVal).toList()))
    }
    return results
  }

  // - - - - - - - - - - - - - - - - QUESTION 5a - - - - - - - - - - - - - - - -
  fun multiplyWithAddition(n: Int, m: Int): Int {
    val larger = if (n > m) n else m
    val smaller = if (larger == n) m else n
    return addLarger(larger, smaller)
  }

  private fun addLarger(larger: Int, smaller: Int): Int {
    if (smaller == 0) return 0
    return larger + addLarger(larger, smaller - 1)
  }

  // - - - - - - - - - - - - - - - - QUESTION 5b - - - - - - - - - - - - - - - -
  fun multiplyWithGrid(n: Int, m: Int): Int {
    val larger = if (n > m) n else m
    val smaller = if (larger == n) m else n
    return multiply(larger, smaller)
  }

  private fun multiply(larger: Int, smaller: Int): Int {
    if (smaller == 0) return 0
    else if (smaller == 1) return larger

    val s = smaller / 2 // could also use smaller.ushr(1)

    val firstHalf = multiply(larger, s)
    val secondHalf = firstHalf + if (smaller % 2 != 0) larger else 0

    // less efficient call that causes duplicate work and brings time and space to O(k), k = smaller word length:
    // val secondHalf = if (smaller % 2 != 0) multiply(larger, smaller - s) else firstHalf

    return firstHalf + secondHalf
  }

  // - - - - - - - - - - - - - - - - QUESTION 6a - - - - - - - - - - - - - - - -
  fun towersOfHanoi(source: Stack<Int>, destination: Stack<Int>, buffer: Stack<Int>) {
    if (buffer.isNotEmpty() || destination.isNotEmpty()) throw InvalidParameterException()
    moveDisks(source.size, source, destination, buffer)
  }

  private fun moveDisks(quantity: Int, source: Stack<Int>, destination: Stack<Int>, buffer: Stack<Int>) {
    if (quantity <= 0) return

    moveDisks(quantity - 1, source, buffer, destination)
    moveTop(source, destination)
    moveDisks(quantity - 1, buffer, destination, source)
  }

  private fun moveTop(source: Stack<Int>, destination: Stack<Int>) {
    if (source.isNotEmpty()) destination.add(source.pop())
  }

  // - - - - - - - - - - - - - - - - QUESTION 6b - - - - - - - - - - - - - - - -
  class Tower {
    private val disks = Stack<Int>()

    fun add(disk: Int) {
      if (disks.isNotEmpty() && disk >= disks.peek()) throw Exception()
      disks.push(disk)
    }

    fun moveAllToDestination(buffer: Tower, destination: Tower) {
      moveDisks(disks.size, destination, buffer)
    }

    private fun moveDisks(quantity: Int, destination: Tower, buffer: Tower) {
      if (quantity <= 0) return

      moveDisks(quantity - 1, buffer, destination)
      moveTopTo(destination)
      buffer.moveDisks(quantity - 1, destination, this)
    }

    private fun moveTopTo(destination: Tower) {
      if (disks.isNotEmpty()) destination.add(disks.pop())
    }

    fun printAndClear() {
      val copy = disks.clone() as Stack<*>
      while (copy.isNotEmpty()) println(copy.pop())
    }
  }

  // - - - - - - - - - - - - - - - - QUESTION 7 - - - - - - - - - - - - - - - -
  fun getPermsWithoutDups(str: String): ArrayList<String> {
    if (str.isEmpty()) {
      val singlePerm = ArrayList<String>()
      singlePerm.add(str)
      return singlePerm
    }

    val perms = ArrayList<String>()

    for (i in str.indices) {
      val start = str.substring(0,i)
      val end = str.substring(i+1)
      val permsWithoutI = getPermsWithoutDups(start + end)

      for (permWithoutI in permsWithoutI) {
        perms.add(str[i] + permWithoutI)
      }
    }
    return perms
  }


  // - - - - - - - - - - - - - - - - QUESTION 8a - - - - - - - - - - - - - - - -
  fun getPermsWithDups(str: String): ArrayList<String> {
    if (str.isEmpty()) {
      val singlePerm = ArrayList<String>()
      singlePerm.add(str)
      return singlePerm
    }

    val perms = ArrayList<String>()
    val visitedCharSet = HashSet<Char>()

    for (i in str.indices) {
      val start = str.substring(0,i)
      val end = str.substring(i+1)
      val permsWithoutI = getPermsWithDups(start + end)

      val char = str[i]

      if (!visitedCharSet.contains(char)) {
          for (permWithoutI in permsWithoutI) {
            perms.add(char + permWithoutI)
          }
          visitedCharSet.add(char)
        }
    }
    return perms
  }

  // - - - - - - - - - - - - - - - - QUESTION 8b - - - - - - - - - - - - - - - -
  fun getPermsWithDups2(str: String): ArrayList<String> {
    val charToCountMap = stringToCharMap(str)
    return getPermsFromMap(charToCountMap)
  }

  private fun getPermsFromMap(map: HashMap<Char, Int>): ArrayList<String> {
    if (map.size == 1) {
      val onlyPerm = getOnlyPerm(map)
      val result = ArrayList<String>()
      result.add(onlyPerm)
      return result
    }

    val perms = ArrayList<String>()

    @Suppress("UNCHECKED_CAST")
    for (char in map.keys) {
      val tempMap = map.clone() as HashMap<Char, Int>
      val count = tempMap.getOrDefault(char,1)

      if (count > 1) tempMap.put(char, count - 1)
      else tempMap.remove(char)

      val permsWithoutChar = getPermsFromMap(tempMap)

      for (permWithoutChar in permsWithoutChar) {
        perms.add(char + permWithoutChar)
      }
    }
    return perms
  }

  private fun getOnlyPerm(map: HashMap<Char, Int>): String {
    val entry = map.entries.iterator().next()
    val char = entry.key
    val count = entry.value

    val resultBuilder = StringBuilder()
    for (i in 0 until count) {
      resultBuilder.append(char)
    }
    return resultBuilder.toString()
  }

  private fun stringToCharMap(str: String): HashMap<Char, Int> {
    val map = HashMap<Char, Int>()
    for (char in str) {
      val count = map.getOrDefault(char, 1)
      map.put(char, count)
    }
    return map
  }

  // - - - - - - - - - - - - - - - - QUESTION 8c - - - - - - - - - - - - - - - -
  fun getPermsWithDups3(str: String): ArrayList<String> {
    val result = ArrayList<String>()
    val charToCountMap = getCharMapFromString(str)
    getPerms(charToCountMap, "", str.length, result)
    return result
  }

  private fun getPerms(map: HashMap<Char, Int>, prefix: String,
                       remaining: Int, result: ArrayList<String>) {
    if (remaining == 0) {
      result.add(prefix)
      return
    }

    for (char in map.keys) {
      val count = map[char]!!
      if (count > 0) {
        map.put(char, count - 1)
        getPerms(map, prefix + char, remaining - 1, result)
        map.put(char, count)
      }
    }
  }

  private fun getCharMapFromString(str: String): HashMap<Char, Int> {
    val map = HashMap<Char, Int>()
    for (char in str) {
      val count = map.getOrDefault(char, 0)
      map.put(char, count + 1)
    }
    return map
  }


  // - - - - - - - - - - - - - - - - QUESTION 9 - - - - - - - - - - - - - - - -
  fun parens(n: Int): ArrayList<String> {
    val results = ArrayList<String>()
    getSolution(results, n, n, CharArray(2*n), 0)
    return results
  }

  private fun getSolution(results: ArrayList<String>, openRem: Int, closeRem: Int,
                          chars: CharArray, index: Int) {
    if (openRem > closeRem) return

    if (openRem == 0 && closeRem == 0) results.add(String(chars))
    else {
      if (openRem > 0) {
        chars[index] = '('
        getSolution(results, openRem - 1, closeRem, chars, index + 1)
      }
      if (closeRem > 0) {
        chars[index] = ')'
        getSolution(results, openRem, closeRem - 1, chars, index + 1)
      }
    }
  }


  // - - - - - - - - - - - - - - - - QUESTION 10 - - - - - - - - - - - - - - - -
  fun paintFill(screen: Array<IntArray>, row: Int, col: Int, newColour: Int) {
    val maxRow = screen.size - 1
    val maxCol = screen[0].size - 1
    if (row > maxRow || col > maxCol) throw InvalidParameterException()

    if (screen[row][col] == newColour) return

    val visitedCells = HashSet<Point>()
    dfsPaint(screen, row, col, screen[row][col], newColour, visitedCells)
  }

  private fun dfsPaint(screen: Array<IntArray>, row: Int, col: Int,
                       oldColour: Int, newColour: Int, visitedCells: HashSet<Point>) {
    val maxRow = screen.size - 1
    val maxCol = screen[0].size - 1
    if (row > maxRow || col > maxCol  || row < 0 || col < 0) return

    if (visitedCells.contains(Point(col, row))) return
    visitedCells.add(Point(col, row))

    val currentColour = screen[row][col]
    if (currentColour == oldColour || currentColour == newColour) {
      screen[row][col] = newColour
      dfsPaint(screen, row - 1, col, oldColour, newColour, visitedCells) //above
      dfsPaint(screen, row + 1, col, oldColour, newColour, visitedCells) //below
      dfsPaint(screen, row, col - 1, oldColour, newColour, visitedCells) //left
      dfsPaint(screen, row, col + 1, oldColour, newColour, visitedCells) //right
    }
  }


  // - - - - - - - - - - - - - - - - QUESTION 11 - - - - - - - - - - - - - - - -
  fun coins(change: Int): Int {
    val changeToWays = HashMap<Int, Int>()
    return countWays(change, intArrayOf(1, 5, 10, 25), 0, changeToWays)
  }

  private fun countWays(total: Int, denoms: IntArray, index: Int, map: HashMap<Int, Int>): Int {
    if (map.contains(total)) return map[total]!!

    val coin = denoms[index]
    if (index == denoms.size - 1) {
      val remaining = total % coin
      return if (remaining == 0) 1 else 0
    }

    var ways = 0
    for (amount in 0..total step coin) {
      ways += countWays(total - amount, denoms, index + 1, map)
    }

    map[total] = ways
    return ways
  }


  // - - - - - - - - - - - - - - - - QUESTION 12 - - - - - - - - - - - - - - - -
  fun eightQueens() {
    for (i in 0..7) placeValueAtPos(IntArray(8), i, 0)
  }

  private fun placeValueAtPos(board: IntArray, value: Int, pos: Int) {
    board[pos] = value
    if (isPosConflictWithPrev(board, pos)) return

    if (pos == 7) {
      println(board.contentToString())
      return
    }

    for (i in 0..7) {
      if (i == value) continue
      placeValueAtPos(board, i, pos + 1)
    }
  }

  private fun isPosConflictWithPrev(board: IntArray, pos: Int): Boolean {
    if (pos == 0) return false

    for (i in (pos - 1) downTo 0) {
      val colDiff = board[pos] - board[i]
      if (colDiff == 0) return true

      val rowDiff = pos - i
      val isDiagConflict = colDiff == rowDiff || colDiff + rowDiff == 0
      if (isDiagConflict) return true
    }
    return false
  }


  // - - - - - - - - - - - - - - - - QUESTION 13 - - - - - - - - - - - - - - - -
  fun stackOfBoxes(boxes: Array<Box>): Int {
    if (boxes.isEmpty()) return 0
    return putRemainingOnStack(null, boxes, BooleanArray(boxes.size))
  }

  private fun putRemainingOnStack(base: Box?, boxes: Array<Box>, used: BooleanArray): Int {
    val baseHeight = base?.h ?: 0
    var maxHeightSoFar = baseHeight

    for (index in boxes.indices) {
      if (used[index]) continue
      else {
        val box = boxes[index]

        if (box.canSitOn(base)) {
          used[index] = true
          val thisHeight = putRemainingOnStack(box, boxes, used)
          maxHeightSoFar = max(thisHeight + baseHeight, maxHeightSoFar)
          used[index] = false
        }
      }
    }
    return maxHeightSoFar
  }

  data class Box(val w: Int, val d: Int, val h: Int) {
    fun canSitOn(box: Box?): Boolean {
      if (box == null) return true
      val isSmallerInAllDimens = w < box.w && d < box.d && h < box.h
      return isSmallerInAllDimens
    }
  }


}