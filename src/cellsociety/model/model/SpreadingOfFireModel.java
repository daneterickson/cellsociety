package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpreadingOfFireModel extends Model {

  private final int EMPTY = 0;
  private final int TREE = 1;
  private final int BURNING = 2;
  private double probCatch;
  private Random random;

  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller, grid);
    random = new Random();
    try {
      probCatch = currGrid.getCell(0, 0).getCellParameter("ProbCatch");
    }catch (Exception e){
      System.out.println("invalid probCatch variable");
      probCatch = 0.5;
    }
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get4Nearby(row, col, currGrid, EMPTY);
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY || state == BURNING) {
      return EMPTY;
    }

    for (int neighborState : nearby) {
      if (neighborState == BURNING) {
        if (random.nextFloat() < probCatch) {
          return BURNING;
        }
        return TREE;
      }
    }
    return TREE;
  }

}
