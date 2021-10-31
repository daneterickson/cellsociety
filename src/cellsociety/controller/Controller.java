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
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

  private Model myModel;
  private ParserCSV myParserCSV;
  private ParserSIM myParserSIM;
  private MainView myMainView;
  private Stage myStage;
  private Grid currGrid;
  private boolean hasUpdate;
  private boolean stopAnimation;
  private Map<String, String> simProperties;
  private ResourceBundle myResources;

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;
  public static final int DEFAULT_GRID_WIDTH = 10;
  public static final int DEFAULT_GRID_HEIGHT = 10;
  public static final String DEFAULT_STATE_COLORS= "";
  public static final String DEFAULT_PARAMETERS = "";
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];
  private static final Grid DEFAULT_GRID = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);

  public Controller(Stage stage) {
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    currGrid = DEFAULT_GRID;
    myModel = new GameOfLifeModel(this, currGrid);
    startView(stage);
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
    hasUpdate = true;
    stopAnimation = false;
  }

  private void startView(Stage stage) {
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.initiateGridView();
  }

  public void setStopAnimation(boolean animationState) {
    stopAnimation = animationState;
  }

  public boolean getStopAnimation(){
    return stopAnimation;
  }

  public void setHasUpdate(boolean hasUpdate) {
    this.hasUpdate = hasUpdate;
  }

  public boolean getHasUpdate(){
    return hasUpdate;
  }

  public void updateModel(){
    hasUpdate = false;
    myModel.updateModel(currGrid);
    myMainView.updateView();
  }

  //refactor to remove these.
  public int getCellStateNumber(int i, int j){
    return currGrid.getCellStateNumber(i, j);
  }

  public void setCellState(int i, int j, int state){
    currGrid.updateCell(i, j, state);
  }

  public String getCellColor(int i, int j){
    try {
      return currGrid.getCell(i, j).getCellProperty("StateColor");
    } catch (KeyNotFoundException e) {
      //TODO: handle exception
      System.out.println("Invalid Property");
      return "000000"; // default black
    }
  }

  private void makeNewSimulation() {
    makeNewModel();
    makeNewRightPanel();
    myMainView.initiateGridView();
  }

  private void makeNewRightPanel() {
    try {
      Object rightPanel = Class.forName("cellsociety.view.right." + simProperties.get("Type") + "Settings").getDeclaredConstructor(ResourceBundle.class).newInstance(myResources);
      myMainView.myRightPanel = (RightPanel) rightPanel;
      myMainView.updateRightPanel();
    }
    catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void makeNewModel() {
    try {
      Class<?> clazz = Class.forName("cellsociety.model.model." + simProperties.get("Type") + "Model");
      Object modell = clazz.getDeclaredConstructor(Controller.class, Grid.class).newInstance(this, currGrid);
      myModel = (Model) modell;
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
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(), myParserSIM.getInfo("StateColors"), myParserSIM.getInfo("Parameters"), myParserSIM.getInfo("Type"));
  }

  public void openSIMFile(File simFile) {
    readSIMFile(simFile);
//    readCSVFile();
    makeNewSimulation();
  }

  private void readSIMFile(File simFile) {
    try {
      myParserSIM.readFile(simFile);
      simProperties = myParserSIM.getMap();
      if (simProperties.get("InitialStates").split(",").length == 1) {
        readCSVFile();
      } else {
        RandomStates randomStates = new RandomStates(myParserSIM);
        currGrid = randomStates.makeGrid();
      }
    } catch (FileNotFoundException | NoSuchFieldException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
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
    return currGrid.getNumCols();
  }

  /**
   * Getter method that returns the number of rows in the model grid
   * @return integer number of rows
   */
  public int getNumGridRows(){
    return currGrid.getNumRows();
  }

  /**
   * Method to get the Grid for saving CSV
   */
  public Grid getGrid() {
    return currGrid;
  }

  public Map getSimPropertiesMap() {
    return simProperties;
  }
}
