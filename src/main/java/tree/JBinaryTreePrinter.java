package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JBinaryTreePrinter {

  public static <Integer extends Comparable<?>> void printNode(MyJNode root) {
    int maxLevel = JBinaryTreePrinter.maxLevel(root);

    printNodeInternal(Collections.singletonList(root), 1, maxLevel);
  }

  private static <Integer extends Comparable<?>> void printNodeInternal(List<MyJNode> nodes, int level, int maxLevel) {
    if (nodes.isEmpty() || JBinaryTreePrinter.isAllElementsNull(nodes))
      return;

    int floor = maxLevel - level;
    int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
    int firstSpaces = (int) Math.pow(2, (floor)) - 1;
    int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

    JBinaryTreePrinter.printWhitespaces(firstSpaces);

    List<MyJNode> newNodes = new ArrayList<MyJNode>();
    for (MyJNode node : nodes) {
      if (node != null) {
        System.out.print(node.value);
        newNodes.add(node.left);
        newNodes.add(node.right);
      } else {
        newNodes.add(null);
        newNodes.add(null);
        System.out.print(" ");
      }

      JBinaryTreePrinter.printWhitespaces(betweenSpaces);
    }
    System.out.println("");

    for (int i = 1; i <= endgeLines; i++) {
      for (int j = 0; j < nodes.size(); j++) {
        JBinaryTreePrinter.printWhitespaces(firstSpaces - i);
        if (nodes.get(j) == null) {
          JBinaryTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
          continue;
        }

        if (nodes.get(j).left != null)
          System.out.print("/");
        else
          JBinaryTreePrinter.printWhitespaces(1);

        JBinaryTreePrinter.printWhitespaces(i + i - 1);

        if (nodes.get(j).right != null)
          System.out.print("\\");
        else
          JBinaryTreePrinter.printWhitespaces(1);

        JBinaryTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
      }

      System.out.println("");
    }

    printNodeInternal(newNodes, level + 1, maxLevel);
  }

  private static void printWhitespaces(int count) {
    for (int i = 0; i < count; i++)
      System.out.print(" ");
  }

  private static <T extends Comparable<?>> int maxLevel(MyJNode node) {
    if (node == null)
      return 0;

    return Math.max(JBinaryTreePrinter.maxLevel(node.left), JBinaryTreePrinter.maxLevel(node.right)) + 1;
  }

  private static boolean isAllElementsNull(List list) {
    for (Object object : list) {
      if (object != null)
        return false;
    }

    return true;
  }
}
