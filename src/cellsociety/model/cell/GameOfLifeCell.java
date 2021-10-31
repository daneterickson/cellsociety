package cellsociety.model.cell;

import java.util.ResourceBundle;

/**
 * Subclass of ModelCell that makes a cell for each spot in Game of Life simulation
 */
public class GameOfLifeCell extends ModelCell {

  public static final int DEAD_STATE = 0;
  public static final int ALIVE_STATE = 1;
  public static final int GAME_OF_LIFE_CASES = 2;

  private String myStartColors;
  private ResourceBundle myResources;
  private String aliveBlue;
  private String deadName;
  private String aliveName;

  /**
   * Constructor for a GameOfLifeCell object, which is extended from the ModelCell super class
   *
   * @param i is the row position of the cell being created
   * @param j is the column position of the cell being created
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters is a comma separated String of the parameters (may be "" for no parameters)
   * @param state is the state of the cell being created
   */
  public GameOfLifeCell(int i, int j, String stateColors, String parameters, int state) {
    super(i, j, stateColors, parameters, state);
    myResources = getMyResources();
    assignConstants();
    myStartColors = stateColors;
    assignState(state);
  }

  @Override
  protected void assignConstants() {
    aliveBlue = myResources.getString("AliveBlue");
    deadName = myResources.getString("DeadName");
    aliveName = myResources.getString("AliveName");
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.split(PARAMETER_DELIMINATOR).length != 2) {
      assignTwoCases(state, deadName, DEFAULT_GREY, aliveName, aliveBlue);
    } else {
      String stateColors[] = myStartColors.split(PARAMETER_DELIMINATOR);
      assignTwoCases(state, deadName, stateColors[DEAD_STATE], aliveName, stateColors[ALIVE_STATE]);
    }
  }

  @Override
  protected void setParameters(String parameters) {
  }
}
