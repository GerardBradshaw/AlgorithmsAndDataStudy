import linkedlist.MyLinkedList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyLinkedListTest {

  private MyLinkedList linkedList = new MyLinkedList();
  private MyLinkedList nullList = new MyLinkedList();

  @Before
  public void setUp() throws Exception {
    linkedList = new MyLinkedList(6, 7, 0); // [6] -> [7] -> [0] -> null
  }


  // - - - - - - - - - - - - - - - Add tests - - - - - - - - - - - - - - -

  @Test
  public void testAddAtEnd_negativeValue() {
    linkedList.addLast(-4);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7, 0, -4))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexInMiddle() {
    linkedList.addAtIndex(33, 1);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 33, 7, 0))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexAtEnd() {
    linkedList.addAtIndex(99, 3);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7, 0, 99))));
  }

  @Test
  public void testAddAtStart_positiveValue() {
    linkedList.addFirst(5);
    assertThat(linkedList, is(equalTo(new MyLinkedList(5, 6, 7, 0))));
  }

  @Test
  public void testAddAtStart_positiveValueNullList() {
    nullList.addFirst(5);
    assertThat(nullList, is(equalTo(new MyLinkedList(5))));
  }


  // - - - - - - - - - - - - - - - Set tests - - - - - - - - - - - - - - -

  @Test
  public void testSetAtStart_validList() {
    linkedList.setFirst(9);
    assertThat(linkedList, is(equalTo(new MyLinkedList(9, 7, 0))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtStart_nullList() {
    nullList.setFirst(9);
  }

  @Test
  public void testSetAtEnd_validList() {
    linkedList.setLast(9);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7, 9))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtEnd_nullList() {
    nullList.setLast(9);
  }

  @Test
  public void testSetAtIndex_indexStart() {
    linkedList.setAtIndex(9, 0);
    assertThat(linkedList, is(equalTo(new MyLinkedList(9, 7, 0))));
  }

  @Test
  public void testSetAtIndex_indexEnd() {
    linkedList.setAtIndex(9, 2);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7, 9))));
  }

  @Test
  public void testSetAtIndex_indexMiddle() {
    linkedList.setAtIndex(9, 1);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 9, 0))));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetAtIndex_indexOutOfBounds() {
    linkedList.setAtIndex(9, 10);
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtIndex_nullList() {
    nullList.setAtIndex(9, 10);
  }


  // - - - - - - - - - - - - - - - Delete tests - - - - - - - - - - - - - - -

  @Test(expected = NullPointerException.class)
  public void testDeleteItem_nullList() {
    nullList.remove(6);
  }

  @Test
  public void testDeleteItem_firstItem() {
    linkedList.remove(6);
    assertThat(linkedList, is(equalTo(new MyLinkedList(7, 0))));
  }

  @Test
  public void testDeleteItem_middleItem() {
    linkedList.remove(7);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 0))));
  }

  @Test
  public void testDeleteItem_lastItem() {
    linkedList.remove(0);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7))));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteItem_invalidItem() {
    linkedList.remove(5);
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtIndex_indexRandomNullList() {
    nullList.removeAtIndex(3);
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtIndex_indexZeroNullList() {
    nullList.removeAtIndex(0);
  }

  @Test
  public void testDeleteAtIndex_indexZero() {
    linkedList.removeAtIndex(0);
    assertThat(linkedList, is(equalTo(new MyLinkedList(7, 0))));
  }

  @Test
  public void testDeleteAtIndex_indexInMiddle() {
    linkedList.removeAtIndex(1);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 0))));
  }

  @Test
  public void testDeleteAtIndex_indexLast() {
    linkedList.removeAtIndex(2);
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7))));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testDeleteAtIndex_indexOutOfBounds() {
    linkedList.removeAtIndex(3);
  }

  @Test
  public void testDeleteAtEnd_validList() {
    linkedList.removeLast();
    assertThat(linkedList, is(equalTo(new MyLinkedList(6, 7))));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtEnd_nullList() {
    nullList.removeLast();
  }

  @Test
  public void testDeleteAtStart_validList() {
    linkedList.removeFirst();
    assertThat(linkedList, is(equalTo(new MyLinkedList(7, 0))));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtStart_nullList() {
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
  public void testGetAtStart_nullList() {
    int result = nullList.getFirst();
  }

  @Test
  public void testGetAtStart_validList() {
    assertThat(linkedList.getFirst(), is(equalTo(6)));
  }

  @Test(expected = NullPointerException.class)
  public void testGetAtEnd_nullList() {
    int result = nullList.getLast();
  }

  @Test
  public void testGetAtEnd_validList() {
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