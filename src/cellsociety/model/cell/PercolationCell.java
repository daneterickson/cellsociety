package cellsociety.model.cell;

import java.util.Map;

public class PercolationCell extends ModelCell{

  public static final String WATER_NAME = "water";
  public static final String WATER_COLOR = "0000ff";
  public static final String BARRIER_NAME = "barrier";
  public static final String BARRIER_COLOR = "000000";

  private Map<Integer, String> myStartColors;

  public PercolationCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.keySet().size() != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, WATER_NAME, WATER_COLOR, BARRIER_NAME, BARRIER_COLOR);
    }
    else {
      assignThreeCases(state, EMPTY_NAME, myStartColors.get(0), WATER_NAME, myStartColors.get(1), BARRIER_NAME, myStartColors.get(2));
    }
  }
}
