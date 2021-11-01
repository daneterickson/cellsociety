package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

public interface NeighborFinderInterface {
  List<Integer> getNeighbors(int row, int col, Grid grid);
}
