package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import java.util.List;

public class TriangleVertices extends NeighborFinder implements NeighborFinderInterface {

  public TriangleVertices(EdgePolicies edgePolicy) {
    super(edgePolicy);
  }

  @Override
  public List<Integer> getNeighbors(int row, int col, Grid grid){
    return getTriangleVertices(row,col,grid);
  }

}
