package cellsociety.grids;

import cellsociety.*;
import java.util.*;

/**
 * Non-wrapping rectangular grid coordinates.
 *
 * @author Franklin Wei
 */
public class RectangularCellGrid extends Dense2DCellGrid<RectangularCellGrid.RectangularGridCoordinates> {
  protected class RectangularGridCoordinates extends GridCoordinates {
    public RectangularGridCoordinates(int x, int y) {
      super(x, y);
    }

    @Override
    public Collection<GridCoordinates> getNeighbors() {
      return null;
    }
  }

  public RectangularCellGrid(int w, int h) {
    super(RectangularGridCoordinates.class, w, h);
  }
}
