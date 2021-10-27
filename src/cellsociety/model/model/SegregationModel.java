package cellsociety.model.model;

import static java.lang.Integer.parseInt;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationModel extends Model {

  private final int EMPTY = 0;
  private final int RACE1 = 1;
  private final int RACE2 = 2;
  private double threshold = .50;
  private ArrayList<Integer> emptySpots;
  private Random random;
  private int numCols;

  public SegregationModel(Controller controller, Grid grid) {
    super(controller, grid);
    numCols = grid.getNumCols();
    random = new Random();
    emptySpots = new ArrayList<>();
    iterateGrid(row -> col -> {
      int state = parseInt(grid.getCell(row, col).getCellProperty("StateNumber"));
      if (state == EMPTY) {
        emptySpots.add(row * numCols + col);
      }
    });
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
      return EMPTY;
    }
    return state;
  }

  private void relocate(int state) {
    int idx = random.nextInt(emptySpots.size());
    int r = emptySpots.get(idx) / numCols;
    int c = emptySpots.get(idx) % numCols;

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
