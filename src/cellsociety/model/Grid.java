package cellsociety.model;

import cellsociety.model.cell.GameOfLifeCell;
import cellsociety.model.cell.ModelCell;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Grid {

  private int myNumRows;
  private int myNumCols;
  private ModelCell myGrid[][];
  private String myCellType;

  public Grid(int rows, int cols, int[][] startStates, String type) {
    myNumRows = rows;
    myNumCols = cols;
    myCellType = "cellsociety.model.cell." + type + "Cell";
    myGrid = new ModelCell[rows][cols];
    setStartStates(startStates);
  }

  private void setStartStates(int[][] states) {
    for (int row=0; row<myNumRows; row++) {
      for (int col=0; col<myNumCols; col++) {
        try {
          setCell(row, col, states[row][col]);
        }
        catch (ClassNotFoundException e) {
          System.out.println("Class Not Found");
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * implementation with arraylist instead of array[][]
   */
  public Grid (int rows, int cols, ArrayList<ArrayList<Integer>> startStates, String type) {
    myNumRows = rows;
    myNumCols = cols;
    myCellType = "cellsociety.model.cell." + type + "Cell";
    myGrid = new ModelCell[rows][cols];
    setStartStates(startStates);
  }
  private void setStartStates(ArrayList<ArrayList<Integer>> states) {
    for (int row=0; row<myNumRows; row++) {
      for (int col=0; col<myNumCols; col++) {
        try {
          setCell(row, col, states.get(row).get(col));
        }
        catch (ClassNotFoundException e) {
          System.out.println("Class Not Found");
          e.printStackTrace();
        }
      }
    }
  }

  public int getCellStateNumber(int i, int j) {
    return myGrid[i][j].getStateNumber();
  }

  public ModelCell getCell (int i, int j) {
    return myGrid[i][j];
  }

  private void setCell (int i, int j, int state) throws ClassNotFoundException {
    Class<?> clazz = Class.forName(myCellType);
    ModelCell newCell = new GameOfLifeCell(i, j, state);
    try {
      newCell = (ModelCell) clazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(i, j, state);
    }
    catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.out.println("Method Not Found");
      e.printStackTrace();
    }
    if (myGrid[i][j] == null) {
      myGrid[i][j] = newCell;
    }
    else {
      myGrid[i][j].changeState(state);
    }
  }

  public void updateCell(int i, int j, int state){
    myGrid[i][j].changeState(state);
  }


  public int getNumCols(){
    return myNumCols;
  }

  public int getNumRows() {
    return myNumRows;
  }

}
