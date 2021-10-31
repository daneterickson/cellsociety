package cellsociety.model.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Subclass of the Parser hierarchy that is used to parse a CSV file of the start states for each
 * cell in the grid.
 */
public class ParserCSV extends Parser {

  private int numRows;
  private int numCols;
  private int startStates[][];

  /**
   * Overridden method that reads the inputted CSV file and processes the information in the file.
   * The grid's number of rows and columns are assigned as well as the start states for each
   * position in the grid. Much of the OpenCSV code is based off http://opencsv.sourceforge.net/#parsing
   *
   * @param file is the File to be read
   * @throws CsvValidationException may be thrown from the CSVReader readNext() method
   * @throws IOException            may be thrown from the CSVReader readNext() method
   */
  @Override
  public void readFile(File file) throws IOException, CsvValidationException {
    CSVReader reader = new CSVReader(new FileReader(file));
    String[] nextLine;
    int row = 1;
    while ((nextLine = reader.readNext()) != null) { // nextLine[] is array of values from line
      if (row == 1) {
        findRowsCols(nextLine);
        row++;
        continue;
      }
      if (nextLine.length != numCols) {
        throw new IOException();
      }
      for (int j = 0; j < numCols; j++) {
        startStates[row - 2][j] = Integer.valueOf(nextLine[j]);
      }
      row++;
    }
    if (row - 2 != numRows && row != 1) {
      throw new IOException();
    }
  }

  private void findRowsCols(String[] nextLine) {
    numCols = Integer.valueOf(nextLine[0]);
    numRows = Integer.valueOf(nextLine[1]);
    startStates = new int[numRows][numCols];
  }

  /**
   * Getter method to return the 2D array of the start states, which is used to create the Grid
   *
   * @return a 2D array of the start states from the CSV file
   */
  public int[][] getStartStates() {
    return startStates;
  }

  /**
   * Getter method to get the number of rows for the simulation
   *
   * @return the number of rows, which is used to create the Grid
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Getter method to get the number of columns for the simulation
   *
   * @return the number of columns, which is used to create the Grid
   */
  public int getNumCols() {
    return numCols;
  }
}
