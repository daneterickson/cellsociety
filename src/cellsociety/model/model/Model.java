package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;

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
    iterateGrid();
    updateGrid();
    myController.setHasUpdate(true);
  }

  /**
   * iterates through the grid until an exception, which determine when to go the next row/end. each
   * cell in the grid is then processed and then used to call addToNewGrid
   */
  protected void iterateGrid(){
    int row = 0;
    int col = 0;
    while (true) {
      try {
        updateCell(row, col, currGrid.getCellStateNumber(row, col));
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
   * checks if cell state is changed. row,col, and newState are added to newUpdates if there is a change
   */
  protected void updateCell(int row, int col, int state) {
    //nearby: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
    int[] nearby = getNearby(row, col);
    int newState = currRule(state, nearby);
    if (newState != state){
      newUpdates.add(row);
      newUpdates.add(col);
      newUpdates.add(newState);
    }
  }

  /**
   * Methods that will be overridden based on model type
   */

  protected int[] getNearby(int row, int col) {
    return new int[0];
  }

  protected Integer currRule(int state, int[] nearby) {
    return 0;
  }

}
