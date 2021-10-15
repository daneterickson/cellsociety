package cellsociety.model;

public class Grid {

  private int myNumRows;
  private int myNumCols;
  private ModelCell myGrid[][];

  public Grid (int rows, int cols, int[][] startStates) {
    myNumRows = rows;
    myNumCols = cols;
    myGrid = new ModelCell[rows][cols];
    setStartStates(startStates);
  }

  private void setStartStates(int[][] states) {
    for (int row=0; row<myNumRows; row++) {
      for (int col=0; col<myNumCols; col++) {
        setCell(row, col, states[row][col]);
      }
    }
  }

  public int getCellState (int i, int j) {
    return myGrid[i][j].getState();
  }

  private void setCell (int i, int j, int state) {
    if (myGrid[i][j] == null) {
      myGrid[i][j] = new ModelCell(i, j, state);
    }
    else {
      myGrid[i][j].changeState(state);
    }
  }
}
