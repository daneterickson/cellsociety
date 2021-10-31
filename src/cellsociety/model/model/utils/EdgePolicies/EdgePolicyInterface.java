package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;

public interface EdgePolicyInterface {
  int policy(int row, int col, Grid grid);
}
