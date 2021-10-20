package cellsociety.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParserSIM implements Parser {
  private File myFile;
  private Map<String, String> myInfoMap;

  public ParserSIM (File file) {
    myInfoMap = new HashMap<>();
    myFile = file;
  }

  public void readFile() {
    try {
      Scanner s = new Scanner(myFile);
      while (s.hasNextLine()) {
        String line = s.nextLine();
        String words[] = line.split("=");
        myInfoMap.put(words[0], words[1]);
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  public String getType() {
    return myInfoMap.get("Type");
  }

  public String getTitle() {
    return myInfoMap.get("Title");
  }

  public String getInitialStates() {
    return myInfoMap.get("InitialStates");
  }




}
