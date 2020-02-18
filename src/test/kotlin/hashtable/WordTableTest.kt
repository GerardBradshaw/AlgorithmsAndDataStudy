package hashtable

import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class WordTableTest {

  private lateinit var originalTable: WordTable
  private lateinit var table: WordTable
  private var emptyTable = WordTable()

  private val newWord = Word("new", "a new word!")
  private val existingWord = Word("bad", "not good")

  @Before
  fun setup() {
    table = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep")))

    originalTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep")))  }

  @Test
  fun insertWord_wordWithUniqueKey() {
    table.insertWord(newWord)

    val resultTable = WordTable(arrayOf(
      newWord,
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun insertWord_wordWithExistingKey() {
    table.insertWord(existingWord)
    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(originalTable)))
  }

  @Test
  fun insertWords_validArray() {
    val arrayToInsert = arrayOf(
      Word("car", "a personal motor vehicle"),
      Word("war", "what is it good for?"),
      null,
      Word("oak", "a type of tree"))

    table.insertWords(arrayToInsert)

    val resultTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("car", "a personal motor vehicle"),
      Word("war", "what is it good for?"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep"),
      Word("oak", "a type of tree")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun insertWords_partlyValidArray() {
    val arrayToInsert = arrayOf(
      Word("car", "a personal motor vehicle"),
      existingWord,
      newWord,
      null,
      Word("oak", "a type of tree"))

    table.insertWords(arrayToInsert)

    val resultTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("car", "a personal motor vehicle"),
      newWord,
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep"),
      Word("oak", "a type of tree")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun insertWords_completelyInvalidArray() {
    val arrayToInsert = arrayOf(
      existingWord,
      existingWord,
      null,
      null,
      existingWord,
      null)

    table.insertWords(arrayToInsert)

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(originalTable)))
  }

  @Test
  fun insertWordsWithoutChangingTableSize_validArray() {
    val arrayToInsert = arrayOf(
      Word("car", "a personal motor vehicle"),
      Word("war", "what is it good for?"),
      null,
      Word("oak", "a type of tree"))

    table.insertWordsWithoutChangingTableSize(arrayToInsert)

    val resultTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("car", "a personal motor vehicle"),
      Word("war", "what is it good for?"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep"),
      Word("oak", "a type of tree")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun insertWordsWithoutChangingTableSize_partlyValidArray() {
    val arrayToInsert = arrayOf(
      Word("car", "a personal motor vehicle"),
      existingWord,
      newWord,
      null,
      Word("oak", "a type of tree"))

    table.insertWordsWithoutChangingTableSize(arrayToInsert)

    val resultTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("car", "a personal motor vehicle"),
      newWord,
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep"),
      Word("oak", "a type of tree")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun insertWordsWithoutChangingTableSize_invalidArray() {
    val arrayToInsert = arrayOf(
      existingWord,
      existingWord,
      null,
      null,
      existingWord,
      null)

    table.insertWordsWithoutChangingTableSize(arrayToInsert)

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(originalTable)))
  }

  @Test
  fun get_validString() {
    assertThat(table.get(existingWord.word), CoreMatchers.`is`(CoreMatchers.equalTo(existingWord)))
  }

  @Test
  fun get_invalidString() {
    assertThat(table.get(newWord.word), CoreMatchers.`is`(CoreMatchers.nullValue()))
  }

  @Test(expected = IllegalArgumentException::class)
  fun get_invalidKeyType() {
    val n: Int = 10
    table.get(n)
  }

  @Test
  fun remove_validString() {
    table.remove(existingWord.word)

    val resultTable = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep")))

    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(resultTable)))
  }

  @Test
  fun remove_invalidString() {
    table.remove(newWord.word)
    assertThat(table, CoreMatchers.`is`(CoreMatchers.equalTo(originalTable)))
  }

  @Test(expected = IllegalArgumentException::class)
  fun remove_invalidKeyType() {
    val n: Int = 10
    table.remove(n)
  }

  @Test
  fun contains_validString() {
    assertThat(table.contains(existingWord.word), CoreMatchers.`is`(CoreMatchers.equalTo(true)))
  }

  @Test
  fun contains_invalidString() {
    assertThat(table.contains(newWord.word), CoreMatchers.`is`(CoreMatchers.equalTo(false)))
  }

  @Test(expected = IllegalArgumentException::class)
  fun contains_invalidKeyType() {
    val n: Int = 10
    table.contains(n)
  }

  @Test
  fun size_tableWithSomeWords() {
    assertThat(table.size(), CoreMatchers.`is`(CoreMatchers.equalTo(5)))
  }

  @Test
  fun size_emptyTable() {
    assertThat(emptyTable.size(), CoreMatchers.`is`(CoreMatchers.equalTo(0)))
  }
}