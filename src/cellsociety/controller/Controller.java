package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import cellsociety.model.model.GameOfLifeModel;
import cellsociety.model.model.Model;
import cellsociety.model.parser.ParserCSV;
import cellsociety.model.parser.ParserSIM;
import cellsociety.model.parser.RandomStates;
import cellsociety.view.mainview.MainView;
import cellsociety.view.right.RightPanel;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

  private List<Model> myModelsList;
  private ParserCSV myParserCSV;
  private ParserSIM myParserSIM;
  private MainView myMainView;
  private Stage myStage;
  private List<Grid> myGridsList;
  private boolean hasUpdate;
  private boolean stopAnimation;
  private List<Map<String, String>> simPropertiesList;
  private ResourceBundle myResources;
  private int currentGridNumber;

  private static final int SCENE_WIDTH = 600;
  private static final int SCENE_HEIGHT = 600;
  private static final int DEFAULT_GRID_WIDTH = 10;
  private static final int DEFAULT_GRID_HEIGHT = 10;
  public static final String DEFAULT_STATE_COLORS= "";
  public static final String DEFAULT_PARAMETERS = "";
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];


  public Controller(Stage stage) {
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    currentGridNumber = 0;
    Grid defaultGrid = makeDefaultGrid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);
    myGridsList = new ArrayList<>();
    myGridsList.add(defaultGrid);
    myModelsList = new ArrayList<>();
    myModelsList.add(new GameOfLifeModel(this, defaultGrid));
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.initiateGridView();
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
    simPropertiesList = new ArrayList<>();
    hasUpdate = true;
    stopAnimation = false;
  }

  public void setStopAnimation(boolean animationState) {
    stopAnimation = animationState;
  }

  public void setHasUpdate(boolean hasUpdate) {
    this.hasUpdate = hasUpdate;
  }

  public boolean getHasUpdate(){
    return hasUpdate;
  }

  public void setCurrentGridNumber(int newGridNumber){
    currentGridNumber = newGridNumber;
  }

  public int getCurrentGridNumber(){
    return currentGridNumber;
  }

  public void updateModels(){
    hasUpdate = false;
    for (int i = 0; i < myModelsList.size(); i++) {
      myModelsList.get(i).updateModel(myGridsList.get(i));
      myMainView.updateView();
    }
  }

  public int getCellStateNumber(int i, int j){
    return myGridsList.get(currentGridNumber).getCellStateNumber(i, j);
  }

  public void setCellState(int i, int j, int state){
    myGridsList.get(currentGridNumber).updateCell(i, j, state);
  }

  public String getCellColor(int i, int j){
    try {
      return myGridsList.get(currentGridNumber).getCell(i, j).getCellProperty("StateColor");
    } catch (KeyNotFoundException e) {
      //TODO: handle exception
      System.out.println("Invalid Property");
      return "000000"; // default black
    }
  }

  public void makeNewDefaultSimulation(){
    Grid defaultGrid = makeDefaultGrid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);
    myGridsList.add(defaultGrid);
    myModelsList.add(new GameOfLifeModel(this, defaultGrid));
    //myMainView.initiateGridView();
  }

  public void openSIMFile(File simFile) {
    readSIMFile(simFile);
//    readCSVFile();
    makeNewSimulation();
  }

  private void makeNewSimulation() {
    makeNewModel();

    makeNewRightPanel();
    myMainView.initiateGridView();
  }

  private void makeNewRightPanel() {
    try {
      Object rightPanel = Class.forName("cellsociety.view.right." + simPropertiesList.get(currentGridNumber).get("Type") + "Settings").getDeclaredConstructor(ResourceBundle.class).newInstance(myResources);
      myMainView.myRightPanel = (RightPanel) rightPanel;
      myMainView.updateRightPanel();
    }
    catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void makeNewModel() {
    try {
      Class<?> clazz = Class.forName("cellsociety.model.model." + simPropertiesList.get(currentGridNumber).get("Type") + "Model");
      Object modell = clazz.getDeclaredConstructor(Controller.class, Grid.class).newInstance(this, myGridsList.get(currentGridNumber));
      myModelsList.remove(currentGridNumber);
      myModelsList.add(currentGridNumber, (Model) modell);
    }
    catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void readCSVFile() {
    File csvFile = new File(String.format("data/%s", myParserSIM.getInfo("InitialStates")));
    try {
      myParserCSV.readFile(csvFile);
    } catch (CsvValidationException | IOException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }

    myGridsList.remove(currentGridNumber);
    myGridsList.add(currentGridNumber, new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(), myParserSIM.getInfo("StateColors"), myParserSIM.getInfo("Parameters"), myParserSIM.getInfo("Type")));
  }

//  private void makeProbStates(int rows, int cols, int numFilled, String type) {
//
//  }
//
//  private void makeRandomStates(int rows, int cols, int numFilled, String type) {
//    int states[][] = new int[rows][cols];
//    int fill = 0;
//    int numCases = 3;
//    Random rand = new Random();
//    if (type.equals("GameOfLife")) numCases = 2;
//    while (fill < numFilled) {
//      int state = rand.nextInt(numCases);
//      int r = rand.nextInt(rows);
//      int c = rand.nextInt(cols);
//      if (states[r][c] == 0) {
//        states[r][c] = state;
//        fill++;
//      }
//    }
//  }

  private void readSIMFile(File simFile) {
    try {
      myParserSIM.readFile(simFile);
      if(simPropertiesList.size()==currentGridNumber){simPropertiesList.add(null);}
      simPropertiesList.add(currentGridNumber, myParserSIM.getMap());
      simPropertiesList.remove(currentGridNumber+1);
      if (simPropertiesList.get(currentGridNumber).get("InitialStates").split(",").length == 1) {
        readCSVFile();
      } else {
        RandomStates randomStates = new RandomStates(myParserSIM);
        myGridsList.remove(currentGridNumber);
        myGridsList.add(currentGridNumber, randomStates.makeGrid());
      }
    } catch (FileNotFoundException | NoSuchFieldException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
  }

  public void addDefaultSimPropMap(){
    simPropertiesList.add(new HashMap<>() );
  }

  public void setLang(String langString) {
    ResourceBundle bundle;
    if (langString.equals("EN")) {
      bundle = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    }
    else {
      bundle = ResourceBundle.getBundle("lang.Spanish", new Locale("es", "ES"));
    }
    myMainView.updateLeftPanel(bundle);
  }

  /**
   * Getter method that returns the number of columns in the model grid
   * @return integer number of columns
   */
  public int getNumGridCols(){
    return myGridsList.get(currentGridNumber).getNumCols();
  }

  /**
   * Getter method that returns the number of rows in the model grid
   * @return integer number of rows
   */
  public int getNumGridRows(){
    return myGridsList.get(currentGridNumber).getNumRows();
  }

  /**
   * Method to get the Grid for saving CSV
   */
  public Grid getGrid() {
    return myGridsList.get(currentGridNumber);
  }


  public Map getSimPropertiesMap() {return simPropertiesList.get(currentGridNumber);}

  private Grid makeDefaultGrid(int height, int width, int[][] cellStates, String stateColors, String parameters, String type){
    return new Grid(height, width, cellStates, stateColors, parameters, type);

  }

}
