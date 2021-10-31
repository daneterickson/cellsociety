package cellsociety.model.model.utils.GridIterators;

import cellsociety.model.Grid;
import java.util.List;

public interface GridIteratorInterface {
  List<Integer> getNeighbors(int row, int col, Grid grid);
}
