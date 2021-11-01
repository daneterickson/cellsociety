package cellsociety.model.model;

import static cellsociety.model.cell.PercolationCell.WATER_STATE;
import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicySetter;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinder;
import cellsociety.model.model.rules.PercolationRule;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.utils.NeighborFinders.NeighborFinderSetter;
import cellsociety.model.model.utils.NeighborFinders.SquareComplete;
import java.util.ArrayList;
import java.util.List;

public class PercolationModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private NeighborFinder neighborFinder;
  private EdgePolicies edgePolicy;
  private int numUpdates;
  private Rule myRule;
  private String endEdge;


  public PercolationModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    getEndEdge();
    myRule = new PercolationRule();
//    setRule(new PercolationRule());
  }
  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    neighborFinder = getNeighborFinder();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareComplete(edgePolicy);
    numUpdates = getNumUpdates();
  }

  @Override
  public void setEdgePolicy(String type){
    EdgePolicySetter eps = new EdgePolicySetter();
    edgePolicy = eps.setEdgePolicy(type);
  }
  @Override
  public String getEdgePolicyType(){
    return edgePolicy.getClass().toString();
  }
  @Override
  public void setNeighborFinder(String type){
    NeighborFinderSetter nfs = new NeighborFinderSetter();
    neighborFinder = nfs.setNeighborFinder(type, edgePolicy);
  }
  @Override
  public String getNeighborFinderType(){
    return neighborFinder.getClass().toString();
  }
  private String getEndEdge() {
    iterateGrid(row-> col -> {
      try {
        if (parseInt(currGrid.getCell(row,col).getCellProperty("StateNumber")) == WATER_STATE){
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
      } catch (KeyNotFoundException e) {
        //TODO: handle exception
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
          try {
            if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER_STATE){
              return true;
            }
          } catch (KeyNotFoundException e) {
            //TODO: handle exception
          }
        }
      }
      case "bottom" -> {
        int r = currGrid.getNumRows() - 1;
        for (int c = 0; c < currGrid.getNumCols();c++){
          try {
            if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER_STATE){
              return true;
            }
          } catch (KeyNotFoundException e) {
            //TODO: handle exception
          }
        }
      }
      case "left" -> {
        int c = 0;
        for (int r = 0; r < currGrid.getNumRows();r++){
          try {
            if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER_STATE){
              return true;
            }
          } catch (KeyNotFoundException e) {
            //TODO: handle exception
          }
        }
      }
      case "right" -> {
        int c = currGrid.getNumCols() - 1;
        for (int r = 0; r < currGrid.getNumRows();r++){
          try {
            if (parseInt(currGrid.getCell(r,c).getCellProperty("StateNumber")) == WATER_STATE){
              return true;
            }
          } catch (KeyNotFoundException e) {
            //TODO: handle exception
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
    return neighborFinder.getNeighbors(row, col, currGrid);
  }

  /**
   * current rule for Percolation. returns EMPTY/WATER/WALL state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    return myRule.determineState(currRow, currCol, state, nearby);
  }

}
