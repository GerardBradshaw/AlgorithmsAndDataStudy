package hashtable;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyHashTable<K,V> implements GHashTable<K,V> {

  // -------- Member variables --------

  LinkedList<KVPair<K,V>>[] data;
  int keyCount;


  // -------- Constructor --------

  public MyHashTable(int size) {
    data = new LinkedList[size];
  }


  // -------- Helpers --------

  private int convertKeyHashToIndex(int hashCode) {
    return hashCode % data.length;
  }


  // -------- GHashTable callbacks --------
  @Override
  public void insert(K key, V value) {
    int index = convertKeyHashToIndex(key.hashCode());
    if (data[index] == null) data[index] = new LinkedList<>();
    data[index].add(new KVPair<>(key, value));
  }

  @Override
  public V get(K key) {
    int index = convertKeyHashToIndex(key.hashCode());
    LinkedList<KVPair<K, V>> pairList = data[index];

    // If pairList is null or empty, return null (invalid key)
    if (pairList == null || pairList.size() == 0) return null;

    // Go through pairList and return the value corresponding to the key
    for (KVPair<K,V> pair : pairList) {
      if (pair.getKey().equals(key)) {
        return pair.getValue();
      }
    }

    // Return null if no value found for given key
    return null;
  }

  @Override
  public void remove(K key) {
    int index = convertKeyHashToIndex(key.hashCode());
    LinkedList<KVPair<K,V>> pairList = data[index];

    // If pairList is null or empty, return null (invalid key)
    if (pairList == null || pairList.size() == 0) return;

    // Go through pairList and remove if the key matches
    pairList.removeIf(p -> p.getKey().equals(key));
  }

  @Override
  public void set(K key, V value) {
    int index = convertKeyHashToIndex(key.hashCode());
    LinkedList<KVPair<K,V>> pairList = data[index];

    // If pairList is null or empty, just add a new value
    if (pairList == null) {
      pairList = new LinkedList<>();
      pairList.add(new KVPair<>(key, value));

    } else if (pairList.size() == 0) {
      pairList.add(new KVPair<>(key, value));

    } else {
      for (KVPair<K,V> pair : pairList) {
        if (pair.getKey().equals(key)) {
          pair.setValue(value);
          return;
        }
      }
    }
  }

  @Override
  public boolean contains(K key) {
    int index = convertKeyHashToIndex(key.hashCode());
    LinkedList<KVPair<K,V>> pairList = data[index];

    // Check to see if pairList has any data. If there isn't catch the exception and return false.
    try {
      pairList.getFirst();
    } catch (NoSuchElementException e) {
      return false;
    }

    for (KVPair<K,V> pair : pairList) {
      if (pair.getKey().equals(key)) return true;
    }
    return false;
  }


  // -------- KV Pair class ---------

  private static class KVPair<K,V> {
    private K key;
    private V value;

    KVPair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public void setKey(K key) {
      this.key = key;
    }

    public V getValue() {
      return value;
    }

    public void setValue(V value) {
      this.value = value;
    }
  }
}
