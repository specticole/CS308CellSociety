package cellsociety.grids;

import cellsociety.*;
import java.util.*;

/**
 * Fixed-size, non-wrapping 2D array implementation of a rectangular
 * CellGrid.
 *
 * @author Franklin Wei
 */
public class Dense2DCellGrid extends CellGrid {
  int width, height;
  Cell cells[][];

  public RectangularCellGrid(int w, int h) {
    cells = new Cell[h][w];

    for(int y = 0; y < h; y++)
      for(int x = 0; x < w; x++)
        cells[y][x] = new Cell(this, new GridCoordinates(x, y));
  }

  @Override
  public List<Cell> getNeighbors(Cell c) {

  }
}
