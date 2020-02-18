package hashtable

import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class WordTableTest {

  private lateinit var table: WordTable
  private var emptyTable = WordTable()

  private val newWord = Word("new", "a new word!")
  private val existingWord = Word("bad", "a song by Michael Jackson")

  @Before
  fun setup() {
    table = WordTable(arrayOf(
      Word("pin", "a sharp thing"),
      Word("use", "to use"),
      Word("bad", "not good"),
      Word("eat", "to consume"),
      Word("nap", "a mini-sleep")))
  }

  @Test
  fun insertWord_wordWithUniqueKey() {
    fail()
  }

  @Test
  fun insertWord_wordWithExistingKey() {
    fail()
  }

  @Test
  fun insertWords_validArray() {
    fail()
  }

  @Test
  fun insertWords_partlyValidArray() {
    fail()
  }

  @Test
  fun insertWords_completelyInvalidArray() {
    fail()
  }

  @Test
  fun insertWordsWithoutChangingTableSize_validArray() {
    fail()
  }

  @Test
  fun insertWordsWithoutChangingTableSize_partlyValidArray() {
    fail()
  }

  @Test
  fun insertWordsWithoutChangingTableSize_invalidArray() {
    fail()
  }

  @Test
  fun get_validString() {
    fail()
  }

  @Test
  fun get_invalidString() {
    fail()
  }

  @Test
  fun get_invalidKeyType() {
    fail()
  }

  @Test
  fun remove_validString() {
    fail()
  }

  @Test
  fun remove_invalidString() {
    fail()
  }

  @Test
  fun remove_invalidKeyType() {
    fail()
  }

  @Test
  fun contains_validString() {
    fail()
  }

  @Test
  fun contains_invalidString() {
    fail()
  }

  @Test
  fun contains_invalidKeyType() {
    fail()
  }

  @Test
  fun size_tableWithSomeWords() {
    fail()
  }

  @Test
  fun size_emptyTable() {
    fail()
  }

  @Test
  fun memorySize_tableWithSomeWords() {
    fail()
  }

  @Test
  fun memorySize_emptyTable() {
    fail()
  }

  @Test
  fun memorySize_resizedTableWithSomeWords() {
    fail()
  }

}