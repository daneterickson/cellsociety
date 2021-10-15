package cellsociety.model;

public class ModelCell {

  private int myRow;
  private int myCol;
  private int myState;

  public ModelCell (int i, int j, int state) {
    myRow = i;
    myCol = j;
    myState = state;
  }

  public void changeState (int newState) {
    myState = newState;
  }

  public int getState () {
    return myState;
  }


}
