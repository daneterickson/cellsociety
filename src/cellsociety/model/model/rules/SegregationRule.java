package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;

import java.util.ArrayList;
import java.util.List;

public class SegregationRule extends Rule {

  private int myThreshold;
  private int myNumCols;
  private ArrayList<Integer> myEmptySpots;

  public SegregationRule (int threshold, int numCols, ArrayList<Integer> emptySpots) {
    super();
    myThreshold = threshold;
    myNumCols = numCols;
    myEmptySpots = emptySpots;
  }
  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }

    double allyPercentage = getAllyPercentage(state, nearby);

    if (allyPercentage < myThreshold) {
//      relocate(state); // TODO: This will be called in SegregationModel right after determineState() is called (outside this class)
      myEmptySpots.add(currRow*myNumCols + currCol);
      return EMPTY_STATE;
    }
    return state;
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
