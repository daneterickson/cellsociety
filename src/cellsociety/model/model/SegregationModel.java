package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.Random;

public class SegregationModel extends Model{

  private final int EMPTY = 0;
  private final int RACE1 = 1;
  private final int RACE2 = 2;
  private int tolerance;
  private Random random;

  public SegregationModel(Controller controller, Grid grid) {
    super(controller,grid);
    random = new Random();
  }

  /**
   * finds 8 neighboring cells and returns them as a linear array: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   * <p>
   * if the current point is an edge, it acts as if the edges are DEAD_STATES
   */
  @Override
  protected int[] getNearby(int row, int col) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    int[] neighbors = new int[8];
    int idx = 0;

    for (int x : dx) {
      for (int y : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        try {
          neighbors[idx] = currGrid.getCellStateNumber(row + x, col + y);
        } catch (IndexOutOfBoundsException e) {
          //handles edge cases
          neighbors[idx] = EMPTY;
        }
        idx++;
      }
    }
    return neighbors;
  }

  /**
   * current rule for Segregation. returns EMPTY/RACE1/RACE2 state
   */
  @Override
  protected Integer currRule(int state, int[] nearby) {
    return 0;
  }

}
