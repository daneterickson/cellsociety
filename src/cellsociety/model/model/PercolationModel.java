package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.Random;

public class PercolationModel extends Model{

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;
  private int probCatch;
  private Random random;

  public PercolationModel(Controller controller, Grid grid) {
    super(controller,grid);
    random = new Random();
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * <p>
   * if the current point is an edge, it acts as if the edges are EMPTY
   */
  @Override
  protected int[] getNearby(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    int[] neighbors = new int[4];
    int idx = 0;

    while(idx<4){
      try {
        neighbors[idx] = currGrid.getCellStateNumber(row + x[idx], col + y[idx]);
      } catch (IndexOutOfBoundsException e) {
        //handles edge cases
        neighbors[idx] = EMPTY;
      }
      idx++;
    }
    return neighbors;
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int row, int col, int state) {
    int[] nearby = getNearby(row, col);
    return 0;
  }

}
