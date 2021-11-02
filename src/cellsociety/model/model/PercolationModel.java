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

  /**
   * Constructor to make a new PercolationModel, which is extended from the Model super class
   *
   * @param controller
   * @param grid
   */
  public PercolationModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();
    getEndEdge();
    myRule = new PercolationRule();
  }

  private void getBaseInstanceVariables() {
    currGrid = getCurrGrid();
    newUpdates = getNewUpdates();
    myController = getMyController();
    neighborFinder = getNeighborFinder();
    edgePolicy = getEdgePolicy();
    neighborFinder = new SquareComplete();
    numUpdates = getNumUpdates();
  }

  /**
   * sets the edgepolicy to a new policy type by using reflection and edgepolicysetter class
   *
   * @param type - the type of edgepolicy as a string
   */
  @Override
  public void setEdgePolicy(String type) {
    EdgePolicySetter eps = new EdgePolicySetter();
    edgePolicy = eps.setEdgePolicy(type);
  }

  /**
   * @return the current edgepolicy type as a string
   */
  @Override
  public String getEdgePolicyType() {
    return edgePolicy.getClass().toString();
  }

  /**
   * sets the neighborfinder to a new neighborfinder type by using reflection and
   * neighborfindersetter class
   *
   * @param type - the type of neighborfinder as a string
   */
  @Override
  public void setNeighborFinder(String type) {
    NeighborFinderSetter nfs = new NeighborFinderSetter();
    neighborFinder = nfs.setNeighborFinder(type);
  }

  /**
   * @return the current neighborfinder type as a string
   */
  @Override
  public String getNeighborFinderType() {
    return neighborFinder.getClass().toString();
  }

  /**
   * iterates through the grid to determine where the starting edge is to calculate the ending
   * edge.
   */
  private String getEndEdge() {
    iterateGrid(row -> col -> {
      try {
        if (parseInt(currGrid.getModelCell(row, col).getCellProperty("StateNumber"))
            == WATER_STATE) {
          if (row == 0) {
            endEdge = "bottom";
          }
          if (row == currGrid.getNumRows() - 1) {
            endEdge = "top";
          }
          if (col == 0) {
            endEdge = "right";
          }
          if (col == currGrid.getNumCols() - 1) {
            endEdge = "left";
          }
        }
      } catch (KeyNotFoundException e) {

      }
    });
    return endEdge;
  }

  /**
   * Overrides updateModel from the superclass. Also checks for if the simulation has percolates,
   * which will tell the controller to stop the animation in view
   *
   * @param currGrid
   */
  @Override
  public void updateModel(Grid currGrid) {
    super.updateModel(currGrid);
    if (checkEnd()) {
      myController.setStopAnimation(true);
    }
  }

  /**
   * returns true if the game is ended by checking if endEdge is water
   */
  private boolean checkEnd() {
    try {
      switch (endEdge) {
        case "top" -> {
          int r = 0;
          for (int c = 0; c < currGrid.getNumCols(); c++) {
            if (parseInt(currGrid.getModelCell(r, c).getCellProperty("StateNumber"))
                == WATER_STATE) {
              return true;
            }
          }
        }
        case "bottom" -> {
          int r = currGrid.getNumRows() - 1;
          for (int c = 0; c < currGrid.getNumCols(); c++) {

            if (parseInt(currGrid.getModelCell(r, c).getCellProperty("StateNumber"))
                == WATER_STATE) {
              return true;
            }
          }
        }
        case "left" -> {
          int c = 0;
          for (int r = 0; r < currGrid.getNumRows(); r++) {
            if (parseInt(currGrid.getModelCell(r, c).getCellProperty("StateNumber"))
                == WATER_STATE) {
              return true;
            }
          }
        }
        case "right" -> {
          int c = currGrid.getNumCols() - 1;
          for (int r = 0; r < currGrid.getNumRows(); r++) {

            if (parseInt(currGrid.getModelCell(r, c).getCellProperty("StateNumber"))
                == WATER_STATE) {
              return true;
            }
          }
        }
        default -> {
          return false;
        }
      }
    } catch (KeyNotFoundException e) {

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
    return myRule.determineState(currRow, currCol, state, nearby, currGrid, edgePolicy);
  }

}
