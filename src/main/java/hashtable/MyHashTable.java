package hashtable;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyHashTable<K,V> implements GHashTable<K,V>, Iterable<MyHashTable.KVPair<K,V>> {

  // -------- Member variables --------

  private LinkedList<KVPair<K,V>>[] data;
  private int keyCount = 0;


  // -------- Constructor --------

  public MyHashTable(int size) {
    data = new LinkedList[size];
  }

  @SafeVarargs
  public MyHashTable(int size, KVPair<K,V>... pairs) {
    data = new LinkedList[size];
    for (KVPair<K,V> pair : pairs) {
      insert(pair.key, pair.value);
    }
  }


  // -------- Helpers --------

  private int convertKeyToIndex(Object key) {
    return Math.abs(key.hashCode() % data.length);
  }


  // -------- GHashTable callbacks --------

  @Override
  public void insert(K key, V value) {
    int index = convertKeyToIndex(key);

    // If it's the first entry, just add and move on
    if (data[index] == null) {
      data[index] = new LinkedList<>();

    } else {
      LinkedList<KVPair<K,V>> pairList = data[index];
      for (KVPair<K,V> pair : pairList) {
        if (pair.getKey().equals(key)) {
          throw new IllegalArgumentException();
        }
      }
    }
    data[index].add(new KVPair<>(key, value));
    keyCount++;
  }

  @Override
  public V get(Object key) {
    int index = convertKeyToIndex(key);
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
  public void remove(Object key) {
    if (key == null) return;

    int index = convertKeyToIndex(key);
    LinkedList<KVPair<K,V>> pairList = data[index];

    // If pairList is null or empty, return null (invalid key)
    if (pairList == null || pairList.size() == 0) return;

    int initialSize = pairList.size();

    // Go through pairList and remove if the key matches
    pairList.removeIf(p -> p.getKey().equals(key));
    keyCount += pairList.size() - initialSize;
  }

  @Override
  public void set(K key, V value) {
    int index = convertKeyToIndex(key);
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
  public boolean contains(Object key) {
    // First confirm that the object is type K
    int index = convertKeyToIndex(key);
    LinkedList<KVPair<K,V>> pairList = data[index];

    // Return false if pairList has no pairs
    if (pairList == null || pairList.size() == 0) return false;

    for (KVPair<K,V> pair : pairList) {
      if (pair.getKey().equals(key)) return true;
    }
    return false;
  }

  @Override
  public int size() {
    return keyCount;
  }

  // -------- Object callbacks --------

  @Override
  public boolean equals(Object obj) {
    // First confirm that the object is a MyHashTable
    if (obj instanceof MyHashTable) {

      // Cast the object to MyHashTable to help compiler
      MyHashTable<?,?> other = (MyHashTable<?,?>) obj;

      // First check they're the same size
      if (other.size() != keyCount) return false;

      // If keyCount == 0, they're both empty and therefore the same
      if (keyCount == 0) return true;

      // For each LinkedList of KVPairs in this...
      for (LinkedList<KVPair<K,V>> pairList : data) {

        if (pairList == null) continue;

        // For each KVPair in the LinkedLists...
        for (KVPair<K,V> pair : pairList) {

          // If the other list does not return the correct value for the key, return false
          if (pair.getValue() == null && other.get(pair.getKey()) == null) continue;

          if (!other.get(pair.getKey()).equals(pair.getValue())) return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();

    int currentIndex = 0;
    LinkedList<KVPair<K,V>> currentList = null;

    while (currentIndex < data.length) {
      currentList = data[currentIndex];
      if (currentList != null) {
        for (KVPair<K,V> pair : currentList) {
          output.append("\n").append(pair.getKey().toString()).append(", ").append(pair.getValue().toString());
        }
      }
      currentIndex++;
    }

    return output.toString();
  }

  // -------- Iterable callbacks --------

  @NotNull
  @Override
  public Iterator<KVPair<K,V>> iterator() {
    return new Iterator<KVPair<K,V>>() {

      int currentIndex = 0;
      int currentListIndex = 0;

      @Override
      public boolean hasNext() {
        // Iterate through data until out of bounds
        while (currentIndex < data.length) {

          // If there is a LL at the current index, iterate on that LL
          LinkedList<KVPair<K,V>> currentList = data[currentIndex];
          if (currentList != null) {
            if (currentListIndex < currentList.size()) {
              if (currentList.get(currentListIndex) != null) {
                return true;
              }
            }
            currentListIndex = 0;
          }
          currentIndex++;
        }
        return false;
      }

      @Override
      public KVPair<K,V> next() {
        currentListIndex++;
        return data[currentIndex].get(currentListIndex - 1);
      }
    };
  }


  // -------- KV Pair class ---------

  public static class KVPair<K,V> {
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

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof KVPair) {
        KVPair<?,?> other = (KVPair<?,?>) obj;

        if (other.getValue() == null) {
          return value == null && key.equals(other.getKey());
        }

        return key.equals(other.getKey()) && value.equals(other.getValue());
      }
      return false;
    }
  }
}
