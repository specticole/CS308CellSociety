package cellsociety.model.grids;

import cellsociety.model.GridCoordinates;
import java.util.*;
import java.util.stream.*;

/**
 * Six-neighbor hexagonal CellGrid. Optionally wrapping.
 *
 * The way a hexagonal grid is laid out geometrically is as a dense 2D
 * grid:
 *
 *                                  X COORDINATE  --->
 *               0       1       2       3       4       5       6       7
 *
 * Y             *       *       *       *       *       *       *       *
 *             *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * C         *       *       *       *       *       *       *       *       *
 * O   0     *  0,0  *  1,0  *  2,0  *  3,0  *  4,0  *  5,0  *  6,0  *   .   *
 * O         *       *       *       *       *       *       *       *    .  *
 * R           *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   .   *
 * D             *       *       *       *       *       *       *       *  .    *
 * I   1         *  0,1  *  1,1  *  2,1  *  3,1  *  4,1  *  5,1  *  6,1  *   .   *
 * N             *       *       *       *       *       *       *       *  .    *
 * A           *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   .   *
 * T         *       *       *       *       *       *       *       *    .  *
 * E   2     *  0,2  *  1,2  *  2,2  *  3,2  *  4,2  *  5,2  *  6,2  *   .   *
 *           *       *       *       *       *       *       *       *    .  *
 * |           *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   .   *
 * |             *       *       *       *       *       *       *       *  .    *
 * |   3         *  0,3  *  1,3  *  2,3  *  3,3  *  4,3  *  5,3  *  6,3  *   .   *
 * V             *       *       *       *       *       *       *       *       *
 *                 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *                   *       *       *       *       *       *       *       *
 *
 * (Emacs' rectangle-mode is the best for ASCII art -- try doing
 * _THAT_ in IntelliJ!)
 *
 * Each cell's coordinates are assigned based on a staggered
 * system. This allows us to reuse almost all of the Dense2DCellGrid
 * logic, thus simplifying our architecture.
 *
 * The local direction of the Y basis vector changes depending on the
 * parity (i.e. even or oddness) of the current row. On even-numbered
 * rows, the +Y direction points to the bottom-right neighbor; on
 * odd-number rows, the +Y direction points to the bottom-left
 * neighbor. This staggering is graphically indicated in the diagram
 * above in the X=7 column; the dotted line passes through all cells
 * falling into the X=7 column. The X basis vector remains invariant
 * throughout the grid.
 *
 * @author Franklin Wei
 */
public class HexagonalCellGrid extends Dense2DCellGrid {
  /**
   * Array containing the 2D deltas to neighbor cells on the same row.
   */
  protected static final GridCoordinates sameRowOffsets[] = {
    new GridCoordinates( 1, 0),
    new GridCoordinates(-1, 0)
  };

  /**
   * Array containing the 2D deltas to neighbor cells on adjacent rows
   * (above/below).
   *
   * This is a 2D array: the first index should be the X coordinate of
   * the center cell (modulo 2).
   */
  protected static final GridCoordinates adjacentRowOffsets[][] = {
    // even-numbered rows
    {
      new GridCoordinates(-1, -1),
      new GridCoordinates(0,  -1),
      new GridCoordinates(-1,  1),
      new GridCoordinates(0,   1)
    },
    // odd-numbered rows
    {
      new GridCoordinates(0,  -1),
      new GridCoordinates(1,  -1),
      new GridCoordinates(0,   1),
      new GridCoordinates(1,   1)
    },
  };

  @Override
  public Stream<GridCoordinates> getNeighborOffsets(GridCoordinates center) {
    Stream<GridCoordinates> offsets =
        Stream.concat(Arrays.stream(sameRowOffsets),
                      Arrays.stream(adjacentRowOffsets[center.getY() % 2]));

    // There's no need to filter here -- wrapping and out-of-bounds
    // are handled in Dense2DCellGrid.
    return offsets;
  }

  /**
   * Construct a new HexagonalCellGrid.
   *
   * @param w Width.
   * @param h Height.
   * @param wrapping Whether to torodially wrap.
   */
  public HexagonalCellGrid(int w, int h, boolean wrapping) {
    super(w, h, wrapping);
  }
}
