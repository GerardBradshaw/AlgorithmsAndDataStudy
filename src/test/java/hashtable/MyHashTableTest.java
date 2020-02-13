package hashtable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import hashtable.MyHashTable.KVPair;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyHashTableTest {

  private MyHashTable<String, String> hashTable;
  private MyHashTable<String, String> emptyTable;

  private KVPair<String, String> pair0 = new KVPair<>("key0", "value0");
  private KVPair<String, String> pair1 = new KVPair<>("key1", "value1");
  private KVPair<String, String> pair2 = new KVPair<>("key2", "value2");

  private String newKey = "newKey";
  private String newValue = "newValue";
  private String duplicateKey = pair1.getKey();
  private String duplicateValue = pair1.getValue();
  private String key1 = duplicateKey;
  private String value1 = duplicateValue;
  private String invalidKey = "invalidKey";

  private KVPair<String, String> newKeyNewValue = new KVPair<>(newKey, newValue);
  private KVPair<String, String> newKeyDuplicateValue = new KVPair<>(newKey, duplicateValue);

  private int hashTableSize = 3;
  private int emptyTableSize = 50;



  @Before
  public void setUp() {
    hashTable = new MyHashTable<>(hashTableSize, pair0, pair1, pair2);
    emptyTable = new MyHashTable<>(emptyTableSize);
  }


  // -------- Insert --------

  @Test
  public void testInsert_newKeyNewValue() {
    hashTable.insert(newKey, newValue);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair1, pair2, newKeyNewValue))));
  }

  @Test
  public void testInsert_newKeyDuplicateValue() {
    hashTable.insert(newKey, duplicateValue);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair1, pair2, newKeyDuplicateValue))));
  }

  @Test
  public void testInsert_duplicateKeyDuplicateValue() {
    hashTable.insert(duplicateKey, duplicateValue);
    assertThat(hashTable, is(equalTo(
        new MyHashTable<>(hashTableSize, pair0, pair2, new KVPair<>(duplicateKey, duplicateValue)))));
  }

  @Test
  public void testInsert_duplicateKeyNewValue() {
    hashTable.insert(duplicateKey, newValue);
    assertThat(hashTable, is(equalTo(
        new MyHashTable<>(hashTableSize, pair0, pair2, new KVPair<>(duplicateKey, newValue)))));
  }

  @Test
  public void testInsert_newKeyNewValueEmptyTable() {
    emptyTable.insert(newKey, newValue);
    assertThat(emptyTable, is(equalTo(new MyHashTable<>(emptyTableSize, newKeyNewValue))));
  }

  @Test
  public void testInsert_newKeyNullValue() {
    hashTable.insert(newKey, null);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair1, pair2, new KVPair<>(newKey, null)))));
  }


  // -------- Get --------

  @Test
  public void testGet_validKey() {
    assertThat(hashTable.get(key1), is(equalTo(value1)));
  }

  @Test
  public void testGet_invalidKey() {
    assertThat(hashTable.get(invalidKey), is(equalTo(null)));
  }

  @Test
  public void testGet_emptyTable() {
    assertThat(emptyTable.get(invalidKey), is(equalTo(null)));
  }


  // -------- Remove --------

  @Test
  public void testRemove_validKey() {
    hashTable.remove(key1);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair2))));
  }

  @Test
  public void testRemove_invalidKey() {
    hashTable.remove(invalidKey);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair1, pair2))));
  }

  @Test
  public void testRemove_emptyTable() {
    emptyTable.remove(newKey);
    assertThat(emptyTable, is(equalTo(new MyHashTable<>(emptyTableSize))));
  }


  // -------- Set --------

  @Test
  public void testSet_validKey() {
    hashTable.set(key1, newValue);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, new KVPair<>(key1, newValue), pair2))));
  }

  @Test
  public void testSet_invalidKey() {
    hashTable.set(invalidKey, newValue);
    assertThat(hashTable, is(equalTo(new MyHashTable<>(hashTableSize, pair0, pair1, pair2))));
  }


  // -------- Contains --------

  @Test
  public void testContains_validKey() {
    assertThat(hashTable.contains(key1), is(equalTo(true)));
  }

  @Test
  public void testContains_invalidKey() {
    assertThat(hashTable.contains(invalidKey), is(equalTo(false)));
  }

  @Test
  public void testContains_emptyTable() {
    assertThat(emptyTable.contains(key1), is(equalTo(false)));
  }


  // -------- Equal --------

  @Test
  public void testEquals_tableInDifferentOrder() {
    KVPair<String, String> repeatIndex = new KVPair<>(key1.toUpperCase(), newValue);

    assertThat(new MyHashTable<>(hashTableSize, pair0, pair1, pair2, repeatIndex),
        is(equalTo(new MyHashTable<>(hashTableSize, repeatIndex, pair2, pair1, pair0))));
  }
}
