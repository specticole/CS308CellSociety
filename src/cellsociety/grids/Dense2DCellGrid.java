package cellsociety.grids;

import cellsociety.*;
import java.util.*;
import java.lang.reflect.Constructor;
import java.util.stream.Collectors;

/**
 * Fixed-size, dense 2D grid that implements a map from [0,w)*[0,h] ->
 * Cell. Can be used to implement both wrapping and non-wrapping
 * rectangular and hex grids.
 *
 * @author Franklin Wei
 */
public class Dense2DCellGrid<C extends GridCoordinates> extends CellGrid {
  int width, height;
  Cell cells[][];

  public Dense2DCellGrid(Class<C> coordType, int w, int h) {
    cells = new Cell[h][w];

    try {
      Constructor<C> constructor = coordType.getDeclaredConstructor(Integer.class,
                                                                    Integer.class);

      for(int y = 0; y < h; y++)
        for(int x = 0; x < w; x++)
          cells[y][x] = new Cell(this, constructor.newInstance(x, y));
    } catch(Exception e){
      assert(false);
    }
  }

  @Override
  public Cell getCell(GridCoordinates coords) {
    return cells[coords.getY()][coords.getX()];
  }

  @Override
  public List<Cell> getNeighbors(Cell c) {
    Collection<GridCoordinates> neighborCoordinates = c.getCoordinates().getNeighbors();

    return neighborCoordinates.stream()
        .map(coords -> this.getCell(coords))
        .collect(Collectors.toList());
  }
}
