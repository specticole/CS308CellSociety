package cellsociety.model;

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
public class GridCoordinates {
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
   * Add a GridCoordinates vector to this vector component-wise.
   *
   * @param rhs Other coordinates.
   * @return Sum of rhs + this.
   */
  public GridCoordinates add(GridCoordinates rhs) {
    return new GridCoordinates(this.getX() + rhs.getX(),
                               this.getY() + rhs.getY());
  }

  /**
   * Subtract a GridCoordinates vector from this vector
   * component-wise.
   *
   * @param rhs Other coordinates.
   * @return Difference rhs - this.
   */
  public GridCoordinates subtract(GridCoordinates rhs) {
    return new GridCoordinates(this.getX() - rhs.getX(),
                               this.getY() - rhs.getY());
  }

  /**
   * Return the (integer) dot product of this vector with rhs.
   *
   * @param rhs Other vector.
   * @return this \cdot rhs
   */
  public int dot(GridCoordinates rhs) {
    return this.getX() * rhs.getX() +
           this.getY() * rhs.getY();
  }
}
