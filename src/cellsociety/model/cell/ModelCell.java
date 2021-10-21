package cellsociety.model.cell;

public abstract class ModelCell {

  public static final String DEFAULT_GREY = "c0c0c0"; // light grey for blank cells

  private int myRow;
  private int myCol;
  private int myStateNumber;
  private String myStateColor;
  private String myStateName;

  public ModelCell(int i, int j, int state) {
    myRow = i;
    myCol = j;
    myStateNumber = state;
    assignState(state);
  }

  protected abstract void assignState(int state);

  public void changeState(int newState) {
    myStateNumber = newState;
    assignState(newState);
  }

  public int getStateNumber() {
    return myStateNumber;
  }

  public String getStateName() {
    return myStateName;
  }

  public String getStateColor() {
    return myStateColor;
  }

  protected void setStateColor(String color) {
    myStateColor = color;
  }

  protected void setStateName(String name) {
    myStateName = name;
  }

}
