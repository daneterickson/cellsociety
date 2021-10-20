package cellsociety.model;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;

public class Parser {

  private int numRows;
  private int numCols;
  private int startStates[][];

  public Parser() {
  }

  // method to read a csv file. Mostly taken from:
  // http://opencsv.sourceforge.net/#parsing
  public void readCSV(File file) {
    try {
      CSVReader reader = new CSVReader(new FileReader(file));
      String[] nextLine;
      int row = 1;
      while ((nextLine = reader.readNext()) != null) { // nextLine[] is array of values from line
        if (row == 1) {
          findRowsCols(nextLine);
          row++;
          continue;
        }
        for (int j=0; j<numCols; j++) {
          startStates[row-2][j] = Integer.valueOf(nextLine[j]);
        }
        row++;
      }
      System.out.println("Got here");
    }
    catch (Exception e) {
      System.out.println("Invalid File");
    }
  }

  private void findRowsCols(String[] nextLine) {
    numRows = Integer.valueOf(nextLine[0]);
    numCols = Integer.valueOf(nextLine[1]);
    startStates = new int[numRows][numCols];
  }

  public int[][] getStartStates () {
    return startStates;
  }

  public int getNumRows () {
    return numRows;
  }

  public int getNumCols () {
    return numCols;
  }
}
