package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class TriangleComplete extends NeighborFinder implements NeighborFinderInterface {

  public TriangleComplete(EdgePolicies edgePolicy) {
    super(edgePolicy);
  }

  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getTriangleComplete(row,col,grid);
  }

}
