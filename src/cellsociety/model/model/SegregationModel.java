package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationModel extends Model {

  private final int EMPTY = 0;
  private final int RACE1 = 1;
  private final int RACE2 = 2;
  private double threshold;
  private ArrayList<Integer> emptySpots;
  private Random random;
  private int numCols;

  public SegregationModel(Controller controller, Grid grid) {
    super(controller, grid);
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
      if (state == EMPTY) {
        emptySpots.add(row * numCols + col);
      }
    });
    try{
      threshold = currGrid.getCell(0,0).getCellParameter("Threshold");
    } catch(Exception e){
      System.out.println("invalid threshold variable");
      threshold = 0.5;
    }
  }

  @Override
  protected List<Integer> getNearby(int row, int col) {
    return gridIterator.get8Nearby(row, col, currGrid, EMPTY);
  }

  /**
   * current rule for Segregation. returns EMPTY/RACE1/RACE2 state
   */
  protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY) {
      return EMPTY;
    }

    double allyPercentage = getAllyPercentage(state, nearby);

    if (allyPercentage < threshold) {
      relocate(state);
      emptySpots.add(currRow*numCols + currCol);
      return EMPTY;
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
      if (i != EMPTY) {
        totalNeighbors += 1;
      }
      if (i == state) {
        allies++;
      }
    }
    return allies / totalNeighbors;
  }

}
