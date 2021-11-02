package cellsociety.model;

import cellsociety.model.cell.GameOfLifeCell;
import cellsociety.model.cell.ModelCell;
import cellsociety.model.cell.ModelCellInterface;
import cellsociety.model.cell.ViewCellInterface;
import cellsociety.model.exceptions.KeyNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * Class to create a Grid object, which stores all the ModelCells at their correct location.
 */
public class Grid {

  private int myNumRows;
  private int myNumCols;
  private ModelCell myGrid[][];
  private String myCellType;
  private String myStateColors;
  private String myParameters;

  /**
   * Constructor to create a Grid object using information provided from the SIM and CSV files.
   *
   * @param rows        is the number of rows for the Grid
   * @param cols        is the number of columns for the Grid
   * @param startStates is a 2D array of the start states for the simulation
   * @param stateColors is a comma separated String of the state colors for each state
   * @param parameters  is a comma separated String of the parameters for the simulation
   * @param type        is a String for the simulation type ("GameOfLife", "Percolation", etc.)
   */
  public Grid(int rows, int cols, int[][] startStates, String stateColors, String parameters,
      String type) {
    myNumRows = rows;
    myNumCols = cols;
    myStateColors = stateColors;
    myParameters = parameters;
    myCellType = String.format("cellsociety.model.cell.%sCell", type);
    myGrid = new ModelCell[rows][cols];
    setStartStates(startStates);
  }

  private void setStartStates(int[][] states) {
    for (int row = 0; row < myNumRows; row++) {
      for (int col = 0; col < myNumCols; col++) {
        setCell(row, col, states[row][col]);
      }
    }
  }

  /**
   * Getter method to get the state of a specific cell in the Grid.
   *
   * @param i is the row position of the cell
   * @param j is the column position of the cell
   * @return the state (int) of the cell
   */
  public int getCellStateNumber(int i, int j) {
    try {
      return Integer.valueOf(myGrid[i][j].getCellProperty("StateNumber"));
    } catch (KeyNotFoundException e) {
      System.out.println("Invalid Property");
      return 0;
    }
  }

  /**
   * Getter method to get a Model Cell Interface object at a specific location. Model Cell Interface
   * only has access to the public methods needed in the Model classes.
   *
   * @param i is the row position of the cell
   * @param j is the column position of the cell
   * @return the ModelCell at the given location
   */
  public ModelCellInterface getModelCell(int i, int j) {
    return myGrid[i][j];
  }

  /**
   * Getter method to get a View Cell Interface object at a specific location. View Cell Interface
   * only has access to the public methods needed in the View classes.
   *
   * @param i is the row position of the cell
   * @param j is the column position of the cell
   * @return the ModelCell at the given location
   */
  public ViewCellInterface getViewCell(int i, int j) {
    return myGrid[i][j];
  }

  private void setCell(int i, int j, int state) {
    try {
      Class<?> clazz = Class.forName(myCellType);
      ModelCell newCell;
      newCell = (ModelCell) clazz.getDeclaredConstructor(int.class, int.class, String.class,
              String.class, int.class)
          .newInstance(i, j, myStateColors, myParameters, state);
      myGrid[i][j] = newCell;
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      myGrid[i][j] = new GameOfLifeCell(i, j, myStateColors, myParameters, state);
    }
  }

  /**
   * Updates the state of a cell at a given position
   *
   * @param i     is the row position of the cell
   * @param j     is the column position of the cell
   * @param state is the new state for the cell
   */
  public void updateCell(int i, int j, int state) {
    myGrid[i][j].changeState(state);
  }

  /**
   * Getter method to get the number of columns of the Grid
   *
   * @return the number of columns in the Grid
   */
  public int getNumCols() {
    return myNumCols;
  }

  /**
   * Getter method to get the number of rows of the Grid
   *
   * @return the number of rows in the Grid
   */
  public int getNumRows() {
    return myNumRows;
  }

  //  /**
//   * implementation with arraylist instead of array[][]
//   */
//  public Grid (int rows, int cols, ArrayList<ArrayList<Integer>> startStates, String type) {
//    myNumRows = rows;
//    myNumCols = cols;
//    myCellType = "cellsociety.model.cell." + type + "Cell";
//    myGrid = new ModelCell[rows][cols];
//    setStartStates(startStates);
//  }
//  private void setStartStates(ArrayList<ArrayList<Integer>> states) {
//    for (int row=0; row<myNumRows; row++) {
//      for (int col=0; col<myNumCols; col++) {
//        try {
//          setCell(row, col, states.get(row).get(col));
//        }
//        catch (ClassNotFoundException e) {
//          System.out.println("Class Not Found");
//          e.printStackTrace();
//        }
//      }
//    }
//  }
}
