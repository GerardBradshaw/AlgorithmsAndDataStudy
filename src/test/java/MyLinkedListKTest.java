import linkedlist.MyLinkedListK;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MyLinkedListKTest {

  private MyLinkedListK linkedList = new MyLinkedListK();
  private MyLinkedListK nullList = new MyLinkedListK();

  @Before
  public void setUp() throws Exception {
    linkedList = new MyLinkedListK(6, 7, 0);
  }


  // - - - - - - - - - - - - - - - Add tests - - - - - - - - - - - - - - -

  @Test
  public void testAddAtEnd_negativeValue() {
    linkedList.addAtEnd(-4);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 7, 0, -4))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexInMiddle() {
    linkedList.addAtIndex(33, 1);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 33, 7, 0))));
  }

  @Test
  public void testAddAtIndex_positiveValueIndexAtEnd() {
    linkedList.addAtIndex(99, 3);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 7, 0, 99))));
  }

  @Test
  public void testAddAtStart_positiveValue() {
    linkedList.addAtStart(5);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(5, 6, 7, 0))));
  }

  @Test
  public void testAddAtStart_positiveValueNullList() {
    nullList.addAtStart(5);
    assertThat(nullList, is(equalTo(new MyLinkedListK(5))));
  }


  // - - - - - - - - - - - - - - - Set tests - - - - - - - - - - - - - - -

  @Test
  public void testSetAtStart_validList() {
    linkedList.setAtStart(9);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(9, 7, 0))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtStart_nullList() {
    nullList.setAtStart(9);
  }

  @Test
  public void testSetAtEnd_validList() {
    linkedList.setAtEnd(9);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 7, 9))));
  }

  @Test(expected = NullPointerException.class)
  public void testSetAtEnd_nullList() {
    nullList.setAtEnd(9);
  }

  @Test
  public void testSetAtIndex_indexStart() {
    linkedList.setAtIndex(9, 0);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(9, 7, 0))));
  }

  @Test
  public void testSetAtIndex_indexEnd() {
    linkedList.setAtIndex(9, 2);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 7, 9))));
  }

  @Test
  public void testSetAtIndex_indexMiddle() {
    linkedList.setAtIndex(9, 1);
    assertThat(linkedList, is(equalTo(new MyLinkedListK(6, 9, 0))));
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
    nullList.deleteItem(6);
  }

  @Test
  public void testDeleteItem_firstItem() {
    linkedList.deleteItem(6);

    MyLinkedListK expectedOutput = new MyLinkedListK(7, 0);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test
  public void testDeleteItem_middleItem() {
    linkedList.deleteItem(7);

    MyLinkedListK expectedOutput = new MyLinkedListK(6, 0);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test
  public void testDeleteItem_lastItem() {
    linkedList.deleteItem(0);

    MyLinkedListK expectedOutput = new MyLinkedListK(6, 7);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteItem_invalidItem() {
    linkedList.deleteItem(5);
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtIndex_indexRandomNullList() {
    nullList.deleteAtIndex(3);
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtIndex_indexZeroNullList() {
    nullList.deleteAtIndex(0);
  }

  @Test
  public void testDeleteAtIndex_indexZero() {
    linkedList.deleteAtIndex(0);

    MyLinkedListK expectedOutput = new MyLinkedListK(7, 0);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test
  public void testDeleteAtIndex_indexInMiddle() {
    linkedList.deleteAtIndex(1);

    MyLinkedListK expectedOutput = new MyLinkedListK(6, 0);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test
  public void testDeleteAtIndex_indexLast() {
    linkedList.deleteAtIndex(2);

    MyLinkedListK expectedOutput = new MyLinkedListK(6, 7);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testDeleteAtIndex_indexOutOfBounds() {
    linkedList.deleteAtIndex(3);
  }

  @Test
  public void testDeleteAtEnd_validList() {
    linkedList.deleteAtEnd();

    MyLinkedListK expectedOutput = new MyLinkedListK(6, 7);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtEnd_nullList() {
    nullList.deleteAtEnd();
  }

  @Test
  public void testDeleteAtStart_validList() {
    linkedList.deleteAtStart();

    MyLinkedListK expectedOutput = new MyLinkedListK(7, 0);

    assertThat(linkedList, is(equalTo(expectedOutput)));
  }

  @Test(expected = NullPointerException.class)
  public void testDeleteAtStart_nullList() {
    nullList.deleteAtStart();
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
    int result = nullList.getAtStart();
  }

  @Test
  public void testGetAtStart_validList() {
    assertThat(linkedList.getAtStart(), is(equalTo(6)));
  }

  @Test(expected = NullPointerException.class)
  public void testGetAtEnd_nullList() {
    int result = nullList.getAtEnd();
  }

  @Test
  public void testGetAtEnd_validList() {
    assertThat(linkedList.getAtEnd(), is(equalTo(0)));
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