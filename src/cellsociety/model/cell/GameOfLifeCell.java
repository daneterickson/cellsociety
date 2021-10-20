package cellsociety.model.cell;

public class GameOfLifeCell extends ModelCell {

  public static final String ALIVE_BLUE = "0000ff"; // for alive cells in Game of Life
  public static final String DEAD_NAME = "Dead";
  public static final String ALIVE_NAME = "Alive";

  public GameOfLifeCell(int i, int j, int state) {
    super(i, j, state);
  }

  @Override
  protected void assignState(int state) {
    switch (state) {
      case 0 -> {
        setStateColor(DEFAULT_GREY);
        setStateName(DEAD_NAME);
      }
      case 1 -> {
        setStateColor(ALIVE_BLUE);
        setStateName(ALIVE_NAME);
      }
    }
  }
}
