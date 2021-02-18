package cellsociety.model.grids;

import cellsociety.model.GridCoordinates;
import java.util.*;
import java.util.stream.*;

/**
 * Four- or eight- neighbor rectangular CellGrid. Optionally wrapping.
 *
 * @author Franklin Wei
 */
public class RectangularCellGrid extends Dense2DCellGrid {
  private int neighborCount;

  /**
   * Offsets of right-angle neighbors.
   */
  protected static final GridCoordinates directOffsets[] = {
    new GridCoordinates(1,  0),
    new GridCoordinates(-1, 0),
    new GridCoordinates(0,  1),
    new GridCoordinates(0, -1),
  };

  protected static final GridCoordinates diagonalOffsets[] = {
    new GridCoordinates(1,  1),
    new GridCoordinates(1, -1),
    new GridCoordinates(-1, 1),
    new GridCoordinates(-1,-1)
  };

  @Override
  public Collection<GridCoordinates> getNeighborCoordinates(GridCoordinates center) {
    Stream<GridCoordinates> offsets = Arrays.stream(directOffsets);

    if(neighborCount == 8)
      offsets = Stream.concat(offsets, Arrays.stream(diagonalOffsets));

    // There's no need to filter here -- wrapping and out-of-bounds
    // are handleded in Dense2DCellGrid.
    return offsets
        .map(offs -> offs.add(center))
        .collect(Collectors.toList());
  }

  /**
   * Construct a new RectangularCellGrid.
   *
   * @param w Width.
   * @param h Height.
   * @param wrapping Whether to torodially wrap.
   * @param neighbors Number of neighbors in a neighborhood. Must be 4
   * or 8.
   */
  public RectangularCellGrid(int w, int h,
                             boolean wrapping, int neighbors) {
    super(w, h, wrapping);
    assert(neighbors == 4 || neighbors == 8);
    this.neighborCount = neighbors;
  }
}
