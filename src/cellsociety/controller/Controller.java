package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.exceptions.InvalidFileException;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Controller class makes view and model and serves as the connection between them
 * <p>
 * The controller is passed into the view components and model components, so they all have access
 * to public methods
 * <p>
 * The Controller also stores the grid for frontend display
 *
 * @author Aaric Han, Nick Strauch, Dane Erickson, Albert Yuan
 */
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

  private static final int SCENE_WIDTH = 1000;
  private static final int SCENE_HEIGHT = 600;
  public static final int DEFAULT_GRID_WIDTH = 10;
  public static final int DEFAULT_GRID_HEIGHT = 10;
  public static final String DEFAULT_STATE_COLORS = "";
  public static final String DEFAULT_PARAMETERS = "";
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];

  /**
   * Constructor to make a controller
   *
   * @param stage
   */
  public Controller(Stage stage) {
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    currentGridNumber = 0;
    Grid defaultGrid = makeDefaultGrid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES,
        DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);
    myGridsList = new ArrayList<>();
    myGridsList.add(defaultGrid);
    myModelsList = new ArrayList<>();
    myModelsList.add(new GameOfLifeModel(this, defaultGrid));
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.initiateCenterView();
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
    simPropertiesList = new ArrayList<>();
    hasUpdate = true;
    stopAnimation = false;
  }

  /**
   * stop animation
   *
   * @param animationState
   */
  public void setStopAnimation(boolean animationState) {
    stopAnimation = animationState;
  }

  /**
   * Gets whether the animation is stopped
   *
   * @return
   */
  public boolean getStopAnimation() {
    return stopAnimation;
  }

  /**
   * Sets status of update in the grid
   *
   * @param hasUpdate
   */
  public void setHasUpdate(boolean hasUpdate) {
    this.hasUpdate = hasUpdate;
  }

  /**
   * Checks status of update in the grid
   *
   * @return hasUpdate
   */
  public boolean getHasUpdate() {
    return hasUpdate;
  }

  /**
   * Sets the curr Grid number if there are multiple grids
   *
   * @param newGridNumber
   */
  public void setCurrentGridNumber(int newGridNumber) {
    currentGridNumber = newGridNumber;
  }

  /**
   * Gets the current clicked grid
   *
   * @return currentGridNumber
   */
  public int getCurrentGridNumber() {
    return currentGridNumber;
  }

  /**
   * Update models in the grid with the back end and updates view
   */
  public void updateModels() {
    hasUpdate = false;
    for (int i = 0; i < myModelsList.size(); i++) {
      myModelsList.get(i).updateModel(myGridsList.get(i));
      myMainView.updateView();
    }
  }

  /**
   * Update model settings like probability
   *
   * @param prob
   */
  public void updateModelSettings(ArrayList prob) {
    for (int i = 0; i < myModelsList.size(); i++) {
      myModelsList.get(i).changeSettings(prob);
      myMainView.updateView();
    }
  }

  /**
   * Get a particular cell's state number
   *
   * @param i
   * @param j
   * @return cellStateNumber
   */
  public int getCellStateNumber(int i, int j) {
    return myGridsList.get(currentGridNumber).getCellStateNumber(i, j);
  }

  /**
   * Set a particular cell's state number
   *
   * @param i
   * @param j
   * @param state
   */
  public void setCellState(int i, int j, int state) {
    myGridsList.get(currentGridNumber).updateCell(i, j, state);
  }

  /**
   * Get a particular cell's state color
   *
   * @param i
   * @param j
   * @return
   */
  public String getCellColor(int i, int j) {
    try {
      return myGridsList.get(currentGridNumber).getViewCell(i, j).getCellProperty("StateColor");
    } catch (KeyNotFoundException e) {
      myMainView.showError("State Color Not Available");
      return "000000"; // default black
    }
  }

  /**
   * Get a histogram map for the first model
   *
   * @return
   */
  public Map<Integer, Integer> getHistogramMap() {
    return myModelsList.get(0).getHistogramMap();
  }

  /**
   * Get names and colors for the first model
   *
   * @return
   */
  public Map getNamesAndColors() {
    return myGridsList.get(0).getViewCell(0, 0).getNameColorMap();
  }

  /**
   * Make a default simulation for a cold boot
   */
  public void makeNewDefaultSimulation() {
    Grid defaultGrid = makeDefaultGrid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES,
        DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);
    myGridsList.add(defaultGrid);
    myModelsList.add(new GameOfLifeModel(this, defaultGrid));
  }

  /**
   * Toggle the view lines
   */
  public void toggleCenterLines() {
    myMainView.toggleLines();
  }

  /**
   * Open a the file given
   *
   * @param simFile
   */
  public void openSIMFile(File simFile) {
    try {
      readSIMFile(simFile);
      makeNewSimulation();
    } catch (InvalidFileException | NumberFormatException | IOException | CsvValidationException e) {
      myMainView.showError(e.getMessage());
    }
  }

  /**
   * Change the center view type
   *
   * @param viewType
   */
  public void updateCenterViewType(String viewType) {
    myMainView.assignViewType(viewType);
  }

  private void makeNewSimulation() {
    makeNewModel();
    makeNewRightPanel();
    myMainView.initiateCenterView();
    makeNewLeftPanel();
  }

  private void makeNewLeftPanel() {
    myMainView.updateLeftView(myResources);
  }

  private void makeNewRightPanel() {
    try {
      Object rightPanel = Class.forName(
              "cellsociety.view.right." + simPropertiesList.get(currentGridNumber).get("Type")
                  + "Settings").getDeclaredConstructor(ResourceBundle.class, Controller.class)
          .newInstance(myResources, this);
      myMainView.updateRightPanel(myResources, (RightPanel) rightPanel);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      myMainView.showError("RightPanel Class Not Found");
    }
  }

  private void makeNewModel() {
    try {
      Class<?> clazz = Class.forName(
          "cellsociety.model.model." + simPropertiesList.get(currentGridNumber).get("Type")
              + "Model");
      Object modell = clazz.getDeclaredConstructor(Controller.class, Grid.class)
          .newInstance(this, myGridsList.get(currentGridNumber));
      myModelsList.remove(currentGridNumber);
      myModelsList.add(currentGridNumber, (Model) modell);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      myMainView.showError("Model Class Not Found");
    }
  }

  private void readCSVFile() throws CsvValidationException, IOException, NumberFormatException {
    File csvFile = new File(String.format("data/%s", myParserSIM.getInfo("InitialStates")));
    myParserCSV.readFile(csvFile);
    myGridsList.remove(currentGridNumber);
    myGridsList.add(currentGridNumber,
        new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(),
            myParserSIM.getInfo("StateColors"), myParserSIM.getInfo("Parameters"),
            myParserSIM.getInfo("Type")));
  }


  private void readSIMFile(File simFile)
      throws InvalidFileException, IOException, CsvValidationException, NumberFormatException {
    myParserSIM.readFile(simFile);
    if (simPropertiesList.size() == currentGridNumber) {
      simPropertiesList.add(null);
    }
    simPropertiesList.add(currentGridNumber, myParserSIM.getMap());
    simPropertiesList.remove(currentGridNumber + 1);
    if (simPropertiesList.get(currentGridNumber).get("InitialStates").split(",").length == 1) {
      readCSVFile();
    } else {
      RandomStates randomStates = new RandomStates(myParserSIM);
      myGridsList.remove(currentGridNumber);
      myGridsList.add(currentGridNumber, randomStates.makeGrid());
    }
  }

  /**
   * Make a new simulation properties list
   */
  public void addDefaultSimPropMap() {
    simPropertiesList.add(new HashMap<>());
  }

  /**
   * Toggle which language depending on the language passed in and updates all view elements
   *
   * @param langString
   */
  public void setLang(String langString) {
    ResourceBundle bundle;
    if (langString.equals("EN")) {
      bundle = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    } else {
      bundle = ResourceBundle.getBundle("lang.Spanish", new Locale("es", "ES"));
    }
    myMainView.updateLeftPanel(bundle);
    myMainView.updateRightPanelLang(bundle);
    myMainView.updateTopPanelLang(bundle);
    myMainView.updateBottomPanel(bundle);
  }

  /**
   * Getter method that returns the number of columns in the model grid
   *
   * @return integer number of columns
   */
  public int getNumGridCols() {
    return myGridsList.get(currentGridNumber).getNumCols();
  }

  /**
   * Getter method that returns the number of rows in the model grid
   *
   * @return integer number of rows
   */
  public int getNumGridRows() {
    return myGridsList.get(currentGridNumber).getNumRows();
  }

  /**
   * Method to get the Grid for saving CSV
   */
  public Grid getGrid() {
    return myGridsList.get(currentGridNumber);
  }

  /**
   * Get current grid simulation properties list
   *
   * @return simProperty
   */
  public Map getSimPropertiesMap() {
    if (simPropertiesList == null) {
      return null;
    }
    return simPropertiesList.get(currentGridNumber);
  }

  private Grid makeDefaultGrid(int height, int width, int[][] cellStates, String stateColors,
      String parameters, String type) {
    return new Grid(height, width, cellStates, stateColors, parameters, type);
  }

  /**
   * Set edge policy for the current model
   *
   * @param policy
   */
  public void setEdgePolicy(String policy) {
    myModelsList.get(currentGridNumber).setEdgePolicy(policy);
  }

  /**
   * Set NeighborFinder for current Grid number
   *
   * @param type
   */
  public void setNeighborFinder(String type) {
    myModelsList.get(currentGridNumber).setNeighborFinder(type);
  }

}
