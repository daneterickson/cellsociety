package cellsociety.model.model;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;
import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.rules.Rule;
import cellsociety.model.model.rules.SegregationRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationModel extends Model {
  //base class variables
  private Grid currGrid;
  private ArrayList<Integer> newUpdates;
  private Controller myController;
  private GridIterator gridIterator;
  private int numUpdates;
  private SegregationRule mySegregationRule;

  private double threshold;
  private ArrayList<Integer> emptySpots;
  private Random random;
  private int numCols;

  public SegregationModel(Controller controller, Grid grid) {
    super(controller, grid);
    getBaseInstanceVariables();

    numCols = grid.getNumCols();
    random = new Random();
    emptySpots = new ArrayList<>();
    iterateGrid(row -> col -> {
      int state = 0;
      try {
        state = parseInt(grid.getCell(row, col).getCellProperty("StateNumber"));
      } catch (KeyNotFoundException e) {
        // TODO: handle exception
        System.out.println("Invalid Property");
      }
      if (state == EMPTY_STATE) {
        emptySpots.add(row * numCols + col);
      }
    });
    try{
      threshold = currGrid.getCell(0,0).getCellParameter("Threshold");
    } catch(Exception e){
      System.out.println("invalid threshold variable");
      threshold = 0.5;
    }
    mySegregationRule = new SegregationRule(threshold, numCols, emptySpots);
    setRule(mySegregationRule);
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
    return gridIterator.get8Nearby(row, col, currGrid, EMPTY_STATE);
  }

  @Override
  protected void updateCell(int row, int col, int state) {
    List<Integer> nearby = getNearby(row, col);
    int newState = mySegregationRule.determineState(row, col, state, nearby);
    if (mySegregationRule.relocationStatus()) {
      relocate(state);
      emptySpots.add(row * numCols + col);
    }
    if (newState != state) {
      addNewUpdates(row, col, newState);
    }
  }

  /**
   * current rule for Segregation. returns EMPTY/RACE1/RACE2 state
   */
  @Override
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }

    double allyPercentage = getAllyPercentage(state, nearby);

    if (allyPercentage < threshold) {
      relocate(state);
      emptySpots.add(currRow*numCols + currCol);
      return EMPTY_STATE;
    }
    return state;
  }

  private void relocate(int state) {
    int idx = random.nextInt(emptySpots.size());
    int r = emptySpots.get(idx) / numCols;
    int c = emptySpots.get(idx) % numCols;
    emptySpots.remove(idx);
    addNewUpdates(r, c, state);
  }

  private double getAllyPercentage(int state, List<Integer> nearby) {
    double totalNeighbors = 0;
    double allies = 0;
    for (int i : nearby) {
      if (i != EMPTY_STATE) {
        totalNeighbors += 1;
      }
      if (i == state) {
        allies++;
      }
    }
    return allies / totalNeighbors;
  }

}
