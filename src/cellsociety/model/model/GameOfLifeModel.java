package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.GridIterator;
import cellsociety.model.model.rules.GameOfLifeRule;
import cellsociety.model.model.rules.Rule;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private int numUpdates;
  private Rule myRule;

  public GameOfLifeModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    myRule = new GameOfLifeRule();
//    setRule(new GameOfLifeRule());
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
    return gridIterator.getSquareComplete(row, col, currGrid);
  }

  /**
   * current rule for Game of life. returns dead/live state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    return myRule.determineState(currRow, currCol, state, nearby);
  }

}
