package cellsociety.model.model.utils.NeighborFinders;

import cellsociety.model.Grid;
import java.util.List;

/**
 * Interface for NeighborFinder. every NeighborFinder will have the policy method that will call a specific
 * NeighborFinder method
 *
 * @Author Albert Yuan
 */
public interface NeighborFinderInterface {
  List<Integer> getNeighbors(int row, int col, Grid grid);
}
