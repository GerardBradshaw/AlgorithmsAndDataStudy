package hashtable

import java.lang.StringBuilder
import java.util.*

class WordTable {

  // -------- Properties --------

  private var wordLists: Array<LinkedList<Word>?>
  private var wordCount = 0


  // -------- Secondary Constructor --------

  constructor(words: Array<Word?>) {
    // Create a table of null values
    wordLists = arrayOfNulls(nextPrime(words.size * 2))

    // Insert each word into the table
    insertWordsWithoutChangingTableSize(words)
  }


  // -------- Public methods --------

  fun insertWord(word: Word) {
    // If the table will be at 80% capacity when the word is added, increase its size
    if ((wordCount + 1) > (4 * wordLists.size / 5))
      resizeTable()

    // Get the index
    val index = convertWordToIndex(word)

    // Get the list at the index or create a new one. Also add the new word to the list
    wordLists[index] = (wordLists[index] ?: LinkedList()).also { it.add(word) }

    /*
    The above statement can also be written as:

    val list = wordListTable[index] ?: MyDoubleLinkedList()
    wordListTable[index] = list
    list.addFirst(word)

    OR

    val listAtIndex = wordListTable[index]
    if (listAtIndex == null)
      wordListTable[index] = MyDoubleLinkedList(word)
    else
      listAtIndex.addFirst(word)
     */

    // Increase the word count
    wordCount++
  }

  fun insertWords(words: Array<Word?>) {
    for (word in words)
      if (word != null)
        insertWord(word)
  }

  fun insertWordsWithoutChangingTableSize(words: Array<Word?>) {
    for (word in words)
      if (word != null) {
        // Get the index
        val index = convertWordToIndex(word)

        // Get the list at the index or create a new one. Also add the new word to the list
        wordLists[index] = (wordLists[index] ?: LinkedList()).also { it.add(word) }

        // Increase the word count
        wordCount++
      }
  }

  fun get(key: Any): Word? {
    // If the key is not a String, throw an exception
    if (key !is String)
      throw java.lang.IllegalArgumentException()

    // Get the index from the key and the corresponding list. If the list is null, return null
    val index = convertStringToIndex(key)
    val list = wordLists[index] ?: return null

    // Go through the words in the list. Return the word if it's found
    for (word in list) {
      if (word.word.equals(key))
        return word
    }
    return null
  }

  fun remove(key: Any) {
    // If the key is not a String, throw an exception
    if (key !is String)
      throw IllegalArgumentException()

    // Get the index from the key and the corresponding list. If the list is null, return
    val index = convertStringToIndex(key)
    val list = wordLists[index] ?: return

    // Go through the words in the list. Remove it if the key matches and stop looking
    for (word in list) {
      if (word.word.equals(key)) {
        list.remove(word)
        wordCount--;

        // Resize the table if the list is now less than half the number of words
        if (wordCount < (wordLists.size / 2))
          resizeTable()

        // Return because the unique word has been removed
        return
      }
    }

    // If we reach here, $key was not in the table so we do nothing
  }

  fun contains(key: Any): Boolean {
    // If the key is not a String, throw an exception
    if (key !is String)
      throw IllegalArgumentException()

    // Get the index from the key and the corresponding list. If the list is null, return false
    val index = convertStringToIndex(key)
    val list = wordLists[index] ?: return false

    // Go through the words in the list. If a key matches $key, return true
    for (word in list) {
      if (word.word.equals(key))
        return true
    }

    // If we reach here, $key was not found
    return false
  }

  fun size(): Int {
    return wordCount
  }

  fun memorySize(): Int {
    return wordLists.size
  }


  // -------- Helper methods --------

  private fun convertWordToIndex(word: Word): Int {
    return convertStringToIndex(word.word)
  }

  private fun convertStringToIndex(string: String): Int {
    return string.hashCode() % wordLists.size;
  }

  private fun resizeTable() {
    // Get an array of all existing words
    val words = getWordArray()

    // Recreate wordListTable as a table of nulls
    wordLists = arrayOfNulls(nextPrime(wordCount * 2))

    // Reset word count and add each word that was previously in the array
    wordCount = 0
    insertWordsWithoutChangingTableSize(words)
  }

  private fun getWordArray(): Array<Word?> {
    // Preallocate word array
    val words = arrayOfNulls<Word>(wordCount)

    // Create an index counter
    var currentIndex = 0

    // Iterate through each word in each list of the table and add each Word to words
    for (list in wordLists) {
      if (list != null) {
        for (word in list) {
          words[currentIndex] = word
          currentIndex++
        }
      }
    }
    return words
  }

  private fun isPrime(n: Int): Boolean {
    if (n % 2 == 0)
      return false;

    for (i in 3..(n / 2) step 2)
      if (n % i == 0)
        return false

    return true
  }

  private fun nextPrime(n: Int): Int {
    var i = n;

    while (true) {
      if (isPrime(i)) return i
      i++
    }
  }


  // -------- Any methods --------

  override fun toString(): String {
    val builder = StringBuilder()

    for (list in wordLists) {
      // Add a new line for each position
      builder.append("\n")

      // If the list contains words, print the words at that position, otherwise add a dash
      if (list != null)
        for (word in list)
          builder.append("${word.word} ")
      else
        builder.append("-")
    }
    // Build the string and return it
    return builder.toString()
  }

  override fun equals(other: Any?): Boolean {
    // Not equal if other is not a WordTable
    if (other !is WordTable)
      return false

    // Not equal if other has a different size (wordCount)
    if (other.size() != size())
      return false

    // Get an array of all the words in this
    val words = getWordArray()

    // Not equal if there's a word in this that isn't in other
    for (word in words) {
      if (word != null) {
        val otherWord = other.get(word.word)
        if (otherWord == null || !otherWord.equals(word))
          return false
      }
    }

    // Return true because other is a WordTable, contains the same number of words, and all the words are the same
    return true
  }

}