package cellsociety.model.model.utils.EdgePolicies;

import cellsociety.model.Grid;

/**
 * Interface for edgepolicies. every edgepolicy will have the policy method that will call a
 * specific edgepolicy method
 *
 * @Author Albert Yuan
 */
public interface EdgePolicyInterface {

  int policy(int row, int col, Grid grid);
}
