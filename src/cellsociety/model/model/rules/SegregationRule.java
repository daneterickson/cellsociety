package cellsociety.model.model.rules;

import static cellsociety.model.cell.ModelCell.EMPTY_STATE;

import java.util.ArrayList;
import java.util.List;

public class SegregationRule extends Rule {

  private double myThreshold;
  private int myNumCols;
  private ArrayList<Integer> myEmptySpots;
  private boolean relocateCheck = false;

  public SegregationRule(double threshold, int numCols, ArrayList<Integer> emptySpots) {
//    super();
    myThreshold = threshold;
    myNumCols = numCols;
    myEmptySpots = emptySpots;
  }

  @Override
  public int determineState(int currRow, int currCol, int state, List<Integer> nearby) {
    relocateCheck = false;
    if (state == EMPTY_STATE) {
      return EMPTY_STATE;
    }
    double allyPercentage = getAllyPercentage(state, nearby);
    if (allyPercentage < myThreshold) {
      relocateCheck = true;
      return EMPTY_STATE;
    }
    return state;
  }

//  private void relocate(int state) {
//    int idx = random.nextInt(myEmptySpots.size());
//    int r = myEmptySpots.get(idx) / myNumCols;
//    int c = myEmptySpots.get(idx) % myNumCols;
//    myEmptySpots.remove(idx);
//    addNewUpdates(r, c, state);
//  }

  /**
   * Getter method that gets the relocation status to know when to relocate a cell.
   * Relocation Status determined in determineState() method.
   *
   * @return relocateCheck is the boolean of whether to relocate
   */
  public boolean relocationStatus () {
    return relocateCheck;
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
