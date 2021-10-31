package cellsociety.model.model.utils;

import cellsociety.model.Grid;

public interface EdgePolicyInterface {
  int policy(int row, int col, Grid grid);
}
