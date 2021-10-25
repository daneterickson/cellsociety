package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredatorPreyModel extends Model{

  private final int EMPTY = 0;
  private final int FISH = 1;
  private final int SHARK = 2;
  private int probCatch;
  private Random random;

  public PredatorPreyModel(Controller controller, Grid grid) {
    super(controller,grid);
    random = new Random();
  }

  /**
   * finds 4 neighboring cells and returns them as a linear array: [north,south,east,west]
   * <p>
   * if the current point is an edge, it acts as if the edges are EMPTY
   * @return
   */
  @Override
  protected List<Integer> getNearby(int row, int col) {
    int[] x = {0, 0, 1, -1};
    int[] y = {1, -1, 0, 0};
    ArrayList<Integer> neighbors = new ArrayList<>();
    int idx = 0;

    while(idx<4){
      try {
        neighbors.add(idx,currGrid.getCellStateNumber(row + x[idx], col + y[idx]));
      } catch (IndexOutOfBoundsException e) {
        //handles edge cases
        neighbors.add(idx,EMPTY);
      }
      idx++;
    }
    return neighbors;
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int state, List<Integer> nearby) {
    if (state == EMPTY){
      return EMPTY;
    }

    if (state == FISH){

    }else {
    }
    return 0;
  }


}
