package cellsociety.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParserSIM implements Parser {
  private File myFile;
  private Map<String, String> myInfoMap;
  private Map<Integer, String> myStatesColorMap;

  public ParserSIM () {
    myInfoMap = new HashMap<>();
    myStatesColorMap = new HashMap<>();
  }

  public void readFile(File file) {
    try {
      Scanner s = new Scanner(file);
      while (s.hasNextLine()) {
        String line = s.nextLine();
        String words[] = line.split("=");
        myInfoMap.put(words[0], words[1]);
      }
      assignStateColors();
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  private void assignStateColors() {
    String colors[] = myInfoMap.get("StateColors").split(",");
    for (int i=0; i<colors.length; i++) {
      myStatesColorMap.put(i, colors[i]);
    }
  }

  public String getStateColor(int stateNum) { return myStatesColorMap.get(stateNum); }

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
