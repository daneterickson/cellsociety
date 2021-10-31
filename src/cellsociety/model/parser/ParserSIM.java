package cellsociety.model.parser;

import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Subclass of the Parser hierarchy that is used to parse a SIM file with the information for the
 * simulation, such as Type, Name, and InitialStates info.
 */
public class ParserSIM extends Parser {

  public static final String SIM_FILE_KEYS[] = {"Type", "Title", "Author", "Description",
      "InitialStates", "Parameters", "StateColors"};
  private Map<String, String> mySimulationProperties;

  public ParserSIM() {
    mySimulationProperties = new HashMap<>();
  }

  /**
   * Overridden method that reads the inputted SIM file and processes the information in the file.
   * Each key in the file has a corresponding Key in the Simulation Properties map, which stores all
   * the information for the simulation.
   *
   * @param file is the File to be read
   * @throws IOException          is thrown if no file is found
   * @throws NoSuchFieldException is thrown if the key in the file is not associated with one of the
   *                              simulation properties
   */
  @Override
  public void readFile(File file) throws FileNotFoundException, NoSuchFieldException {
    Scanner s = new Scanner(file);
    while (s.hasNextLine()) {
      String line = s.nextLine();
      if (line.charAt(0) == '#') continue;
      String words[] = line.split("=");
      mySimulationProperties.put(words[0], words[1]);
      if (!Arrays.asList(SIM_FILE_KEYS).contains(words[0])) {
        throw new NoSuchFieldException("Invalid Key");
      }
    }
  }

  /**
   * Getter method to get the String value of a particular property from the SIM file
   *
   * @param key is the name of the desired property
   * @return the value of the property from the SIM file
   */
  public String getInfo(String key) {
    return mySimulationProperties.get(key);
  }

  /**
   * Getter method to get a Map of all the simulation properties
   *
   * @return the Map of all the simulation properties from the SIM file
   */
  public Map getMap() {
    return mySimulationProperties;
  }


}
