package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies;
import cellsociety.model.model.utils.FiniteEdgePolicy;
import cellsociety.model.model.utils.GridIterator;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.utils.HistogramManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Model {

  private Rule myRule;
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private Integer numUpdates;
  private final String stateNumber = "StateNumber";
  private EdgePolicies edgePolicy;
  private HistogramManager histogram;

  public Model(Controller controller, Grid grid) {
    newUpdates = new ArrayList<>();
    myController = controller;
    currGrid = grid;
    edgePolicy = new FiniteEdgePolicy();
    gridIterator = new GridIterator(edgePolicy);
    numUpdates = 3;
    histogram = new HistogramManager(currGrid);
    updateHistogram();
  }

  protected void setRule (Rule rule) {
    myRule = rule;
  }

  protected Grid getCurrGrid() {
    return currGrid;
  }

  protected ArrayList<Integer> getNewUpdates() {
    return newUpdates;
  }

  protected Integer getNumUpdates() {
    return numUpdates;
  }

  protected GridIterator getGridIterator() {
    return gridIterator;
  }

  protected Controller getMyController() {
    return myController;
  }

  public void updateModel(Grid currGrid) {
    newUpdates.clear();
    this.currGrid = currGrid;
    iterateGrid(row -> col -> {
      String currState = null;
      try {
        currState = currGrid.getCell(row, col).getCellProperty(stateNumber);
      } catch (KeyNotFoundException e) {
        //TODO: handle exception
        System.out.println("Invalid Property");
      }
      int stateAsInt = parseInt(currState);
      updateCell(row, col, stateAsInt);
    });
    updateGrid();
    updateHistogram();
    myController.setHasUpdate(true);
  }

  protected void updateHistogram(){
    histogram.clear();
    iterateGrid(row -> col -> {
      String currState = null;
      try {
        currState = currGrid.getCell(row, col).getCellProperty(stateNumber);
      } catch (KeyNotFoundException e) {
        //TODO: handle exception
        System.out.println("Invalid Property");
      }
      int stateAsInt = parseInt(currState);
      histogram.add(stateAsInt,1);
    });

  }

  public HashMap getHistogram(){
    return histogram.getHistogram();
  }

  /**
   * iterates through the grid until an exception, which determine when to go the next row/end. each
   * cell in the grid is then processed and then used to call addToNewGrid
   */
  protected void iterateGrid(Function<Integer, Consumer<Integer>> gridIterationAction) {
    int currRow = 0;
    int currCol = 0;
    while (true) {
      try {
        currGrid.getCell(currRow, currCol).getCellProperty(stateNumber);
        gridIterationAction.apply(currRow).accept(currCol);
        currCol += 1;
      } catch (IndexOutOfBoundsException | KeyNotFoundException xOutOfBounds) {
        try {
          currCol = 0;
          currRow += 1;
          currGrid.getCell(currRow, currCol).getCellProperty(stateNumber);
        } catch (IndexOutOfBoundsException | KeyNotFoundException yOutOfBounds) {
          break;
        }
      }
    }
  }

  /**
   * checks if cell state is changed. row,col, and newState are added to newUpdates if there is a
   * change
   */
  protected void updateCell(int row, int col, int state) {
    List<Integer> nearby = getNearby(row, col);
//    int newState = myRule.determineState(row, col, state, nearby);
    int newState = currRule(row, col, state, nearby);
    if (newState != state) {
      addNewUpdates(row, col, newState);
    }
  }

  protected void addNewUpdates(int row, int col, int newState) {
    newUpdates.add(row);
    newUpdates.add(col);
    newUpdates.add(newState);
  }

  /**
   * updates the current grid by iterating through newUpdates.
   */
  protected void updateGrid() {
    int row;
    int col;
    int newState;
    for (int idx = 0; idx < newUpdates.size(); idx += numUpdates) {
      row = newUpdates.get(idx);
      col = newUpdates.get(idx + 1);
      newState = newUpdates.get(idx + 2);
      currGrid.updateCell(row, col, newState);
    }
  }

  /**
   * Methods that will be overridden based on model type
   *
   * @return
   */

  protected abstract List<Integer> getNearby(int row, int col);

  protected abstract Integer currRule(int currRow, int currCol, int state, List<Integer> nearby);

}
