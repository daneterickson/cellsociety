package cellsociety.model.parser;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Abstract super class for the Parser hierarchy, which parses the different files needed to run the
 * simulations (CSV and SIM files).
 */
public abstract class Parser {

  /**
   * Abstract method that reads the inputted file and processes the information in the file.
   *
   * @param file is the File to be read
   * @throws CsvValidationException may be thrown from the CSVReader readNext() method
   * @throws IOException            may be thrown from the CSVReader readNext() method or in
   *                                ParserSIM if no file is found
   * @throws NoSuchFieldException   is thrown if the key in the file is not associated with one of
   *                                the simulation properties
   */
  public abstract void readFile(File file)
      throws CsvValidationException, IOException, NoSuchFieldException;

}
