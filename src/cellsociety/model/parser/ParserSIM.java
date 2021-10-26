package cellsociety.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.print.DocFlavor.READER;

public class ParserSIM implements Parser {

  private File myFile;
  private Map<String, String> myInfoMap;
  private Map<Integer, String> myStatesColorMap;

  public ParserSIM() {
    myInfoMap = new HashMap<>();
    myStatesColorMap = new HashMap<>();
  }

  public void readFile(File file) throws FileNotFoundException {
//    try {
//      Scanner s = new Scanner(file);
//      while (s.hasNextLine()) {
//        String line = s.nextLine();
//        String words[] = line.split("=");
//        myInfoMap.put(words[0], words[1]);
//      }
//      assignStateColors();
//    }
//    catch (FileNotFoundException e) {
//      System.out.println("File not found");
//    }
    Scanner s = new Scanner(file);
    while (s.hasNextLine()) {
      String line = s.nextLine();
      String words[] = line.split("=");
      myInfoMap.put(words[0], words[1]);
    }
//    assignColorMap();
  }

  public void assignColorMap() {
    if (myInfoMap.get("StateColors") == null) return;
    String colors[] = myInfoMap.get("StateColors").split(",");
    for (int i = 0; i < colors.length; i++) {
      myStatesColorMap.put(i, colors[i]);
    }
  }

  public String getInfo (String key) { return myInfoMap.get(key); }

//  public String getType() {
//    return myInfoMap.get("Type");
//  }
//
//  public String getTitle() {
//    return myInfoMap.get("Title");
//  }
//
//  public String getInitialStates() {
//    return myInfoMap.get("InitialStates");
//  }

  // Assume parameters for PredatorPrey go fish reproduction time, shark reproduction time, shark energy level
  public double getParameters(int index) {
    if (myInfoMap.get("Parameters") == null) return 0.0;
//    List<Double> parameters = new ArrayList<>();
//    for (String num : myInfoMap.get("Parameters").split(",")) {
//      parameters.add(Double.valueOf(num));
//    }
//    return parameters.get(index);
    return Double.valueOf(myInfoMap.get("Parameters").split(",")[index]);
  }


}
