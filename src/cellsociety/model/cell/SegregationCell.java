package cellsociety.model.cell;

import java.util.Map;

public class SegregationCell extends ModelCell{

  public static final String RACE1_RED = "ff0000";
  public static final String RACE1_NAME = "race1";
  public static final String RACE2_BLUE = "0000ff";
  public static final String RACE2_NAME = "race2";

  private Map<Integer, String> myStartColors;

  public SegregationCell(int i, int j, Map<Integer, String> startColors, int state) {
    super(i, j, startColors, state);
    myStartColors = startColors;
  }

  @Override
  protected void assignState(int state) {
    if (myStartColors == null || myStartColors.keySet().size() != 3) {
      assignThreeCases(state, EMPTY_NAME, DEFAULT_GREY, RACE1_NAME, RACE1_RED, RACE2_NAME, RACE2_BLUE);
    }
    else {
      assignThreeCases(state, EMPTY_NAME, myStartColors.get(0), RACE1_NAME, myStartColors.get(1), RACE2_NAME, myStartColors.get(2));
    }
  }
}
