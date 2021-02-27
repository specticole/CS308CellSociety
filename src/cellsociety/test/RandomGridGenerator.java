package cellsociety.test;

import cellsociety.model.states.Index;
import cellsociety.model.*;
import java.util.*;

public class RandomGridGenerator {
  public static void main(String args[]) {
    int width, height;
    String type = args[0];
    width = Integer.valueOf(args[1]);
    height = Integer.valueOf(args[2]);
    String wrapping = args[3];
    String neighbors = args[4];
    String game = args[5];

    System.out.printf("<grid type=\"%s\" width=\"%d\" height=\"%d\" neighbors=\"%s\" wrapping=\"%s\">\n",
                      type, width, height, neighbors, wrapping);
    Collection<String> states = null;
    try {
      states = ((CellState)(Index.allStates.get(game).newInstance())).getAvailableStates();
    } catch(Exception e) {
    }
    List<String> stateList = new ArrayList<>(states);

    for(int y = 0; y < height; y++) {
      System.out.println("<gridrow>");
      for(int x = 0; x < width; x++) {
        System.out.printf("<gridcell>%s</gridcell>%n", stateList.get((int)(Math.random() * stateList.size())));
      }
      System.out.println("</gridrow>");
    }

    System.out.println("</grid>");
  }
}
