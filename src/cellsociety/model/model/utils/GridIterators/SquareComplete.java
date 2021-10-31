package cellsociety.model.model.utils.GridIterators;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class SquareComplete extends GridIterator implements GridIteratorInterface{

  public SquareComplete(EdgePolicies edgePolicy) {
    super(edgePolicy);
  }

  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getSquareComplete(row,col,grid);
  }


}
