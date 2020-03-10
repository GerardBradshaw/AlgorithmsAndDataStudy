package trie

import array.MyStringBuilder
import java.lang.Exception

class MyTrie {

  // ---------------- Properties ----------------

  private val head = Node()
  var wordCount = 0


  // ---------------- Public methods ----------------

  fun insert(word: String) {
    var current = head

    for (char in word) {
      if (!current.children.contains(char)) {
        current.children[char] = Node(char)
      }
      current = current.children[char] ?: throw Exception()
    }
    current.isCompleteWord = true
    wordCount++
  }

  fun delete(word: String) {
    var current = head
    var keepIndex = 0
    var keepNode: Node? = null

    for (index in 0..word.length - 2) {
    //for (index in word.indices) {
      current = current.children[word[index]] ?: return
      if (current.isCompleteWord || current.children.size > 1) {
        keepNode = current
        keepIndex = index
      }
    }
    current = current.children[word[word.length - 1]] ?: return
    current.isCompleteWord = false
    wordCount--
    if (current.children.size > 0) return

    if (keepNode == null) safeDelete(word, 0, head)
    else safeDelete(word, keepIndex, keepNode)
  }

  fun contains(word: String): Boolean {
    var current = head

    for (char in word) {
      current = current.children[char] ?: return false
    }
    return current.isCompleteWord
  }

  fun isEmpty(): Boolean {
    return head.children.isEmpty()
  }

  fun printWordList() {
    if (head.children.isEmpty()) println("(empty)")
    else printHelper(head, MyStringBuilder())
  }

  // ---------------- Helpers ----------------

  private fun safeDelete(word: String, lastIndexToKeep: Int, lastNodeToKeep: Node) {
    val indexLast = word.length - 1

    if (indexLast <= lastIndexToKeep) return

    var indexDelete = lastIndexToKeep + 1
    var charDelete = word[indexDelete]
    var nodeDelete = lastNodeToKeep.children[charDelete]

    var indexNext = indexDelete + 1

    if (indexNext > indexLast) {
      lastNodeToKeep.children.remove(charDelete)

      return
    }

    var charNext = word[indexNext]
    var nodeNext = nodeDelete?.children?.get(charNext)

    while (nodeNext != null && !nodeNext.isCompleteWord) {
      nodeDelete?.children?.remove(charDelete)
      indexDelete++
      charDelete = word[indexDelete]
      nodeDelete = nodeNext
      indexNext++
      charNext = word[indexNext]
      nodeNext = nodeDelete.children.get(charNext)
    }
    if (nodeNext != null) nodeNext.isCompleteWord = false
  }

  private fun printHelper(node: Node, builder: MyStringBuilder) {
    val children = node.children
    val char = node.char

    builder.append(char)

    if (node.isCompleteWord) println(builder.toString())

    if (children.isNotEmpty()) {
      for (child in children.values) {
        printHelper(child, MyStringBuilder(builder.toString()))
      }
    }
  }

  private fun toStringHelper(node: Node, builder: MyStringBuilder, wordList: MyStringBuilder): MyStringBuilder {
    val children = node.children
    val char = node.char

    builder.append(char)

    if (node.isCompleteWord) return wordList.append(builder.toString())

    if (children.isNotEmpty()) {
      for (child in children.values) {
        printHelper(child, MyStringBuilder(builder.toString()))
      }
    }
    return wordList
  }


  // ---------------- Any methods ----------------

  override fun equals(other: Any?): Boolean {
    if (other !is MyTrie) return false
    return other.toString() == toString()
  }

  override fun hashCode(): Int {
    return toString().hashCode()
  }

  override fun toString(): String {
    return toStringHelper(head, MyStringBuilder(), MyStringBuilder()).toString()
  }

  // ---------------- Node class ----------------

  data class Node(val char: Char? = null) {
    val children = HashMap<Char, Node>()
    var isCompleteWord = false

    override fun equals(other: Any?): Boolean {
      if (other !is Node) return false
      if (other.char == char) return true
      return false
    }

    override fun toString(): String {
      val builder = MyStringBuilder().append(char ?: '*').append(" -> [").append(if (isCompleteWord) "*, " else "-, ")

      for (key in children.keys) {
        builder.append(key).append(", ")
      }
      builder.removeEnd(2)
      return builder.append("]").toString()
    }

    override fun hashCode(): Int {
      return toString().hashCode()
    }
  }
}