package cellsociety.model.model;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.BURN_STATE;
import static cellsociety.model.cell.SpreadingOfFireCell.TREE_STATE;
import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.rules.SpreadingOfFireRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpreadingOfFireModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private int numUpdates;

  private double probCatch;
  private Random random;

  public SpreadingOfFireModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();

    random = new Random();
    try {
      probCatch = currGrid.getCell(0, 0).getCellParameter("ProbCatch");
    }catch (Exception e){
      System.out.println("invalid probCatch variable");
      probCatch = 0.5;
    }
    setRule(new SpreadingOfFireRule(probCatch));
  }

  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    gridIterator = getGridIterator();
    numUpdates = getNumUpdates();
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.getSquareEdges(row, col, currGrid, EMPTY_STATE);
  }

  /**
   * current rule for Spreading Of Fire. returns EMPTY/TREE/BURNING state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE || state == BURN_STATE) {
      return EMPTY_STATE;
    }

    for (int neighborState : nearby) {
      if (neighborState == BURN_STATE) {
        if (random.nextFloat() < probCatch) {
          return BURN_STATE;
        }
        return TREE_STATE;
      }
    }
    return TREE_STATE;
  }

}
