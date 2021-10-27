package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.List;

public class PercolationModel extends Model {

  private final int EMPTY = 0;
  private final int WATER = 1;
  private final int BARRIER = 2;
  private String endEdge;


  public PercolationModel(Controller controller, Grid grid) {
    super(controller, grid);
    getEndEdge();
  }

  private String getEndEdge() {
    iterateGrid(row-> col -> {
      if (parseInt(currGrid.getCell(row,col).getCellProperty("StateNumber")) == WATER){
        if (row == 0){
          endEdge = "bottom";
        }
        if (row == currGrid.getNumRows()-1){
          endEdge = "top";
        }
        if (col == 0){
          endEdge = "right";
        }
        if (col == currGrid.getNumCols()-1){
          endEdge = "left";
        }
      }
    });
    return endEdge;
  }

  @Override
  public void updateModel(Grid currGrid) {
    super.updateModel(currGrid);
    if (checkEnd()) {
      myController.setStopAnimation(true);
    }
  }

  private boolean checkEnd() {
    switch (endEdge){
      case "top" -> {
        int r = 0;
        for (int c = 0; c < currGrid.getNumCols();c++){
          if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER){
            return true;
          }
        }
      }
      case "bottom" -> {
        int r = currGrid.getNumRows();
        for (int c = 0; c < currGrid.getNumCols();c++){
          if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER){
            return true;
          }
        }
      }
      case "left" -> {
        int c = 0;
        for (int r = 0; r < currGrid.getNumRows();r++){
          if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER){
            return true;
          }
        }
      }
      case "right" -> {
        int c = currGrid.getNumCols();
        for (int r = 0; r < currGrid.getNumRows();r++){
          if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER){
            return true;
          }
        }
      }
      default -> {
        return false;
      }
    }
    return false;
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get8Nearby(row, col, currGrid, EMPTY);
  }

  /**
   * current rule for Percolation. returns EMPTY/WATER/WALL state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY) {
      if (findNearbyWater(nearby)) {
        return WATER;
      }
      return EMPTY;
    }
    return state;
  }

  private boolean findNearbyWater(List<Integer> nearby) {
    for (int i : nearby) {
      if (i == WATER) {
        return true;
      }
    }
    return false;
  }

}
