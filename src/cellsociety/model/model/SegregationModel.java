package cellsociety.model.model;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.Random;

public class SegregationModel extends Model{

  private final int EMPTY = 0;
  private final int RACE1 = 1;
  private final int RACE2 = 2;
  private double threshold = .50;
  private ArrayList<Integer> emptySpots;
  private Random random;
  private int numCols;

  public SegregationModel(Controller controller, Grid grid) {
    super(controller,grid);
    numCols = grid.getNumCols();
    random = new Random();
    emptySpots = new ArrayList<>();
    iterateGrid(row -> col -> {
      if (currGrid.getCellStateNumber(row,col) == EMPTY){
        emptySpots.add(row*numCols+col);
      }
    });
  }

  /**
   * finds 8 neighboring cells and returns them as a linear array: [topLeft,topMid,topRight,midLeft,midRight,botLeft,botMiddle,botRight]
   * <p>
   * if the current point is an edge, it acts as if the edges are DEAD_STATES
   */
  @Override
  protected int[] getNearby(int row, int col) {
    int[] dx = {-1, 0, 1};
    int[] dy = {-1, 0, 1};
    int[] neighbors = new int[8];
    int idx = 0;

    for (int x : dx) {
      for (int y : dy) {
        if (x == 0 && y == 0) {
          continue;
        }
        try {
          neighbors[idx] = currGrid.getCellStateNumber(row + x, col + y);
        } catch (IndexOutOfBoundsException e) {
          //handles edge cases
          neighbors[idx] = EMPTY;
        }
        idx++;
      }
    }
    return neighbors;
  }

  /**
   * current rule for Segregation. returns EMPTY/RACE1/RACE2 state
   */
  protected Integer currRule(int state, int[] nearby) {
    if (state == EMPTY) {
      return EMPTY;
    }

    double allyPercentage = getAllyPercentage(state, nearby);

    if (allyPercentage < threshold){
      relocate(state);
      return EMPTY;
    }
    return state;
  }

  private void relocate(int state) {
    int idx = random.nextInt(emptySpots.size());
    int r = emptySpots.get(idx) / numCols;
    int c = emptySpots.get(idx) % numCols;

    addNewUpdates(r,c,state);
  }

  private double getAllyPercentage(int state, int[] nearby) {
    double totalNeighbors = 0;
    double allies = 0;
    for (int i : nearby) {
      if (i != EMPTY) {
        totalNeighbors += 1;
      }
      if (i == state){
        allies++;
      }
    }
    return allies/totalNeighbors;
  }

}
