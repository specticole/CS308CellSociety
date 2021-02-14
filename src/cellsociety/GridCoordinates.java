package cellsociety;

import java.util.*;

/**
 * Immutable class representing integer coordinates on an arbitrary
 * 2-dimensional "grid". This is generalizable to all 2-dimensional
 * grids, since -- by definition -- a 2-dimensional grid only needs
 * two scalar coordinates to uniquely specify a location.
 *
 * The geometric interpretation mapping GridCoordinates to Cells in a
 * CellGrid is left up to the individual CellGrid type -- Rectangular
 * and Hex grids, for example, interpret the same GridCoordinates
 * slightly differently.
 */
public abstract class GridCoordinates {
  final int x, y;

  /**
   * Create a GridCoordinates object with coordinates (x, y).
   *
   * @param x X
   * @param y Y
   */
  public GridCoordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Retrieve X coordinate.
   *
   * @return X
   */
  public int getX() {
    return x;
  }

  /**
   * Retrieve Y coordinate.
   *
   * @return Y
   */
  public int getY() {
    return y;
  }

  /**
   * Compute the coordinates of cells immediately adjacent to this
   * one.
   *
   * @return Collection of immediately adjacent coordinates.
   */
  public abstract Collection<GridCoordinates> getNeighbors();
}
