package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Model{ // implements baseModel{

  protected Grid currGrid;
  protected ArrayList<Integer> newUpdates;
  protected Controller myController;
  protected cellsociety.model.model.gridIterator gridIterator;
  protected int numUpdates = 3;

  public Model(Controller controller, Grid grid) {
    newUpdates = new ArrayList<>();
    myController = controller;
    currGrid = grid;
    gridIterator = new gridIterator();
  }

  public void updateModel(Grid currGrid) {
    this.currGrid = currGrid;
    iterateGrid(row -> col -> {
      updateCell(row, col, currGrid.getCellStateNumber(row, col));
    });
    updateGrid();
    myController.setHasUpdate(true);
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
        currGrid.getCellStateNumber(currRow, currCol);
        gridIterationAction.apply(currRow).accept(currRow);
        currCol += 1;
      } catch (IndexOutOfBoundsException xOutOfBounds) {
        try {
          currCol = 0;
          currRow += 1;
          currGrid.getCellStateNumber(currRow, currCol);
        } catch (IndexOutOfBoundsException yOutOfBounds) {
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
