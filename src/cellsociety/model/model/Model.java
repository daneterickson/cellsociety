package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Model {

  protected Grid currGrid;
  protected ArrayList<Integer> newUpdates;
  protected Controller myController;

  public Model(Controller controller, Grid grid) {
    newUpdates = new ArrayList<>();
    myController = controller;
    currGrid = grid;
  }

  public void updateModel(Grid currGrid){
    this.currGrid = currGrid;
    iterateGrid(row -> col -> {
      updateCell(row,col,currGrid.getCellStateNumber(row, col));
    });
    updateGrid();
    myController.setHasUpdate(true);
  }

  /**
   * iterates through the grid until an exception, which determine when to go the next row/end. each
   * cell in the grid is then processed and then used to call addToNewGrid
   */
  protected void iterateGrid(Function<Integer, Consumer<Integer>> gridIterationAction){
    int row = 0;
    int col = 0;
    while (true) {
      try {
        currGrid.getCellStateNumber(row, col);
        gridIterationAction.apply(row).accept(row);
        col += 1;
      } catch (IndexOutOfBoundsException xOutOfBounds) {
        try {
          col = 0;
          row += 1;
          currGrid.getCellStateNumber(row, col);
        } catch (IndexOutOfBoundsException yOutOfBounds) {
          break;
        }
      }
    }
  }

  /**
   * checks if cell state is changed. row,col, and newState are added to newUpdates if there is a change
   */
  protected void updateCell(int row, int col, int state) {
    int newState = currRule(row,col,state);
    if (newState != state){
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
    for(int idx = 0; idx < newUpdates.size();idx+=3){
      row = newUpdates.get(idx);
      col = newUpdates.get(idx+1);
      newState = newUpdates.get(idx+2);
      currGrid.updateCell(row,col,newState);
    }
  }

  /**
   * Methods that will be overridden based on model type
   */

  protected abstract int[] getNearby(int row, int col);

  protected abstract Integer currRule(int row, int col, int state);

}
