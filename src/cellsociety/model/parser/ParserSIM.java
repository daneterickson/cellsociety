package cellsociety.model.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParserSIM extends Parser {

  private static final String keys[] = {"Type","Title","Author","Description","InitialStates","Parameters","StateColors"};
  private File myFile;
  private Map<String, String> mySimulationProperties;
//  private Map<Integer, String> myStatesColorMap;

  public ParserSIM() {
    mySimulationProperties = new HashMap<>();
//    myStatesColorMap = new HashMap<>();
  }

  public void readFile(File file) throws FileNotFoundException, NoSuchFieldException {
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
      mySimulationProperties.put(words[0], words[1]);
      if (!Arrays.asList(keys).contains(words[0])) throw new NoSuchFieldException("Invalid Key");
    }
//    assignColorMap();
  }

//  public void assignColorMap() {
//    if (mySimulationProperties.get("StateColors") == null) return;
//    String colors[] = mySimulationProperties.get("StateColors").split(",");
//    for (int i = 0; i < colors.length; i++) {
//      myStatesColorMap.put(i, colors[i]);
//    }
//  }

  public String getInfo (String key) { return mySimulationProperties.get(key); }

  public Map getMap() { return mySimulationProperties; }

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
//  public Double getParameters(int index) {
//    if (mySimulationProperties.get("Parameters") == null) return 0.0;
////    List<Double> parameters = new ArrayList<>();
////    for (String num : myInfoMap.get("Parameters").split(",")) {
////      parameters.add(Double.valueOf(num));
////    }
////    return parameters.get(index);
//    return Double.valueOf(mySimulationProperties.get("Parameters").split(",")[index]);
//  }


}
