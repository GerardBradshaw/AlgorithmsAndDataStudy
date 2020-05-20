package questions

import java.awt.Point
import java.lang.Exception
import java.lang.NullPointerException
import java.security.InvalidParameterException
import java.util.*
import kotlin.collections.HashSet
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





}