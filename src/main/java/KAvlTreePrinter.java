import tree.MyKAvlTree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KAvlTreePrinter {

  public static <Integer extends Comparable<?>> void printNode(Node root) {
    int maxLevel = KAvlTreePrinter.maxLevel(root);

    printNodeInternal(Collections.singletonList(root), 1, maxLevel);
  }

  private static <Integer extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
    if (nodes.isEmpty() || KAvlTreePrinter.isAllElementsNull(nodes))
      return;

    int floor = maxLevel - level;
    int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
    int firstSpaces = (int) Math.pow(2, (floor)) - 1;
    int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

    KAvlTreePrinter.printWhitespaces(firstSpaces);

    List<Node> newNodes = new ArrayList<Node>();
    for (Node node : nodes) {
      if (node != null) {
        System.out.print(node.getValue());
        newNodes.add(node.getLeft());
        newNodes.add(node.getRight());
      } else {
        newNodes.add(null);
        newNodes.add(null);
        System.out.print(" ");
      }

      KAvlTreePrinter.printWhitespaces(betweenSpaces);
    }
    System.out.println("");

    for (int i = 1; i <= endgeLines; i++) {
      for (int j = 0; j < nodes.size(); j++) {
        KAvlTreePrinter.printWhitespaces(firstSpaces - i);
        if (nodes.get(j) == null) {
          KAvlTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
          continue;
        }

        if (nodes.get(j).getLeft() != null)
          System.out.print("/");
        else
          KAvlTreePrinter.printWhitespaces(1);

        KAvlTreePrinter.printWhitespaces(i + i - 1);

        if (nodes.get(j).getRight() != null)
          System.out.print("\\");
        else
          KAvlTreePrinter.printWhitespaces(1);

        KAvlTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
      }

      System.out.println("");
    }

    printNodeInternal(newNodes, level + 1, maxLevel);
  }

  private static void printWhitespaces(int count) {
    for (int i = 0; i < count; i++)
      System.out.print(" ");
  }

  private static <T extends Comparable<?>> int maxLevel(Node node) {
    if (node == null)
      return 0;

    return Math.max(KAvlTreePrinter.maxLevel(node.getLeft()), KAvlTreePrinter.maxLevel(node.getRight())) + 1;
  }

  private static boolean isAllElementsNull(List list) {
    for (Object object : list) {
      if (object != null)
        return false;
    }

    return true;
  }
}
