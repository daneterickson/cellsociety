package cellsociety.model.model;

import static cellsociety.model.cell.GameOfLifeCell.ALIVE_STATE;
import static cellsociety.model.cell.GameOfLifeCell.DEAD_STATE;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.GridIterator;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private int numUpdates;

  public GameOfLifeModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();

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
    return gridIterator.getSquareComplete(row, col, currGrid, DEAD_STATE);
  }

  /**
   * current rule for Game of life. returns dead/live state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    int population = 0;
    for (int i : nearby) {
      if (i == ALIVE_STATE) {
        population += 1;
      }
    }
    if (state == DEAD_STATE && population == 3) {
      return ALIVE_STATE;
    }
    if (state == ALIVE_STATE && (population == 2 || population == 3)) {
      return ALIVE_STATE;
    }
    return DEAD_STATE;
  }

}
