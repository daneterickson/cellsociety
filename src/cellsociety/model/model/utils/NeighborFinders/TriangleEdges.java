package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class TriangleEdges extends NeighborFinder implements NeighborFinderInterface {

  public TriangleEdges(EdgePolicies edgePolicy) {
    super(edgePolicy);
  }

  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getTriangleEdges(row,col,grid);
  }

}
