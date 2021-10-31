package cellsociety.model.model.utils.GridIterators;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class SquareEdges extends GridIterator implements GridIteratorInterface{

  public SquareEdges(EdgePolicies edgePolicy) {
    super(edgePolicy);
  }

  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getSquareEdges(row,col,grid);
  }
}
