package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;

public class GameOfLifeModel extends Model{

  private final int DEAD_STATE = 0;
  private final int LIVE_STATE = 1;

  public GameOfLifeModel(Controller controller, Grid grid) {
    super(controller,grid);
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
          neighbors[idx] = DEAD_STATE;
        }
        idx++;
      }
    }
    return neighbors;
  }

  /**
   * current rule for Game of life. returns dead/live state
   */
  @Override
  protected Integer currRule(int state, int[] nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == LIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return LIVE_STATE;
    }
    if (state == LIVE_STATE && (population == 2 || population == 3)) {
      return LIVE_STATE;
    }
    return DEAD_STATE;
  }

}
