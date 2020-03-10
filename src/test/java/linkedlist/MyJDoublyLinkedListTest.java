package linkedlist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyJDoublyLinkedListTest {

  private MyJDoublyLinkedList<Integer> linkedList = new MyJDoublyLinkedList<>();
  private MyJDoublyLinkedList<Integer> nullList = new MyJDoublyLinkedList<>();

  @Before
  public void setUp() throws Exception {
    linkedList = new MyJDoublyLinkedList<>(6, 7, 0); // [6] -> [7] -> [0] -> null
  }


  // - - - - - - - - - - - - - - - Add tests - - - - - - - - - - - - - - -

  @Test
  public void testAddLast_negativeValue() {
    linkedList.addLast(-4);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7, 0, -4))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexInMiddle() {
    linkedList.addAtIndex(33, 1);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 33, 7, 0))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexLast() {
    linkedList.addAtIndex(99, 3);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7, 0, 99))));
  }

  @Test
  public void testAddFirst_positiveValue() {
    linkedList.addFirst(5);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(5, 6, 7, 0))));
  }

  @Test
  public void testAddFirst_positiveValueNullList() {
    nullList.addFirst(5);
    assertThat(nullList, is(equalTo(new MyJDoublyLinkedList<>(5))));
  }


  // - - - - - - - - - - - - - - - Set tests - - - - - - - - - - - - - - -

  @Test
  public void testSetFirst_validList() {
    linkedList.setFirst(9);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(9, 7, 0))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetFirst_nullList() {
    nullList.setFirst(9);
  }

  @Test
  public void testSetLast_validList() {
    linkedList.setLast(9);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7, 9))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetLast_nullList() {
    nullList.setLast(9);
  }

  @Test
  public void testSetAtIndex_indexFirst() {
    linkedList.setAtIndex(9, 0);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(9, 7, 0))));
  }

  @Test
  public void testSetAtIndex_indexLast() {
    linkedList.setAtIndex(9, 2);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7, 9))));
  }

  @Test
  public void testSetAtIndex_indexMiddle() {
    linkedList.setAtIndex(9, 1);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 9, 0))));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetAtIndex_indexOutOfBounds() {
    linkedList.setAtIndex(9, 10);
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtIndex_nullList() {
    nullList.setAtIndex(9, 10);
  }


  // - - - - - - - - - - - - - - - Remove tests - - - - - - - - - - - - - - -

  @Test(expected = NullPointerException.class)
  public void testRemoveItem_nullList() {
    nullList.remove(6);
  }

  @Test
  public void testRemoveItem_firstItem() {
    linkedList.remove(6);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(7, 0))));
  }

  @Test
  public void testRemoveItem_middleItem() {
    linkedList.remove(7);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 0))));
  }

  @Test
  public void testRemoveItem_lastItem() {
    linkedList.remove(0);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7))));
  }

  @Test(expected = NullPointerException.class)
  public void testRemoveItem_invalidItem() {
    linkedList.remove(5);
  }

  @Test(expected = NullPointerException.class)
  public void testRemoveAtIndex_indexRandomNullList() {
    nullList.removeAtIndex(3);
  }

  @Test(expected = NullPointerException.class)
  public void testRemoveAtIndex_indexZeroNullList() {
    nullList.removeAtIndex(0);
  }

  @Test
  public void testRemoveAtIndex_indexZero() {
    linkedList.removeAtIndex(0);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(7, 0))));
  }

  @Test
  public void testRemoveAtIndex_indexInMiddle() {
    linkedList.removeAtIndex(1);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 0))));
  }

  @Test
  public void testRemoveAtIndex_indexLast() {
    linkedList.removeAtIndex(2);
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7))));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testRemoveAtIndex_indexOutOfBounds() {
    linkedList.removeAtIndex(3);
  }

  @Test
  public void testRemoveLast_validList() {
    linkedList.removeLast();
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(6, 7))));
  }

  @Test(expected = NullPointerException.class)
  public void testRemoveLast_nullList() {
    nullList.removeLast();
  }

  @Test
  public void testRemoveFirst_validList() {
    linkedList.removeFirst();
    assertThat(linkedList, is(equalTo(new MyJDoublyLinkedList<>(7, 0))));
  }

  @Test(expected = NullPointerException.class)
  public void testRemoveFirst_nullList() {
    nullList.removeFirst();
  }


  // - - - - - - - - - - - - - - - Get tests - - - - - - - - - - - - - - -

  @Test(expected = NullPointerException.class)
  public void testGetAtIndex_indexZeroNullList() {
    nullList.getAtIndex(0);
  }

  @Test
  public void testGetAtIndex_indexZero() {
    assertThat(linkedList.getAtIndex(0), is(equalTo(6)));
  }

  @Test(expected = NullPointerException.class)
  public void testGetAtIndex_indexRandomNullList() {
    nullList.getAtIndex(4);
  }

  @Test
  public void testGetAtIndex_indexMiddle() {
    assertThat(linkedList.getAtIndex(1), is(equalTo(7)));
  }

  @Test
  public void testGetAtIndex_indexLast() {
    assertThat(linkedList.getAtIndex(2), is(equalTo(0)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetAtIndex_indexOutOfBounds() {
    int result = linkedList.getAtIndex(3);
  }

  @Test(expected = NullPointerException.class)
  public void testGetFirst_nullList() {
    int result = nullList.getFirst();
  }

  @Test
  public void testGetFirst_validList() {
    assertThat(linkedList.getFirst(), is(equalTo(6)));
  }

  @Test(expected = NullPointerException.class)
  public void testGetLast_nullList() {
    int result = nullList.getLast();
  }

  @Test
  public void testGetLast_validList() {
    assertThat(linkedList.getLast(), is(equalTo(0)));
  }

  // - - - - - - - - - - - - - - - Contains tests - - - - - - - - - - - - - - -

  @Test
  public void testContains_firstItem() {
    assertThat(linkedList.contains(6), is(equalTo(true)));
  }

  @Test
  public void testContains_middleItem() {
    assertThat(linkedList.contains(7), is(equalTo(true)));
  }

  @Test
  public void testContains_lastItem() {
    assertThat(linkedList.contains(0), is(equalTo(true)));
  }

  @Test
  public void testContains_invalidItem() {
    assertThat(linkedList.contains(123), is(equalTo(false)));
  }

  @Test(expected = NullPointerException.class)
  public void testContains_nullList() {
    boolean result = nullList.contains(123);
  }
}