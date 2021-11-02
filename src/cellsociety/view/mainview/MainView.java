package cellsociety.view.mainview;


import cellsociety.controller.Controller;
import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.CenterView;
import cellsociety.view.center.GridView;
import cellsociety.view.center.HistogramView;
import cellsociety.view.center.SquareGridView;
import cellsociety.view.center.TriangleGridView;
import cellsociety.view.left.CellProperties;
import cellsociety.view.right.GameOfLifeSettings;
import cellsociety.view.right.RightPanel;
import cellsociety.view.top.TopLoadSave;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the MainView where all view components are made and stored
 *
 * @author Aaric Han, Nick Strauch, Dane Erickson
 */

public class MainView {

  public static final String VIEW_TYPE_CLASS_NAME = "cellsociety.view.center.%s";

  private Stage myStage;
  private CellProperties myCellProperties;
  private SimControl mySimControl;
  private TopLoadSave myTopLoadSave;
  private RightPanel myRightPanel;
  private Controller myController;
  private ResourceBundle myResources;
  private BorderPane root;
  private CenterView myCenterView;

  /**
   * Constructor that makes a main view
   * @param stage
   * @param controller
   */
  public MainView(Stage stage, Controller controller) {
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    myController = controller;
    myStage = stage;
    myCellProperties = new CellProperties(myController, myResources);
    myTopLoadSave = new TopLoadSave(myStage, myController, myResources);
    myRightPanel = new GameOfLifeSettings(myResources, myController);
    myCenterView = new SquareGridView(myCellProperties, myController);
    mySimControl = new SimControl(myCenterView, myController, myResources, myCellProperties);
  }

  /**
   * Makes a new scene with a new borderpane and all elements of borderpane filled
   * @param width
   * @param height
   * @return scene
   */
  public Scene makeScene(int width, int height) {
    root = new BorderPane();
    root.setCenter(myCenterView.getViewBox());
    root.setBottom(mySimControl.getSimControl());
    root.setLeft(myCellProperties.getCellProperties());
    root.setTop(myTopLoadSave.getTopLoadSave());
    root.setRight(myRightPanel.getTheRightPanel());
    Scene scene = new Scene(root, width, height);
    return scene;
  }

  /**
   * Uses reflection to create the correct CenterView object based on which view type (Histogram,
   * Square Grid, Triangle Grid, etc.) the user inputs. getViewBox() is then called on that Object
   * to get the Node for the view type that is placed in the BorderPane.
   *
   * @param viewType is the name of the CenterView subclass for the view type
   * @throws ClassNotFoundException is thrown if the reflection fails and there's no matching class
   */
  public void assignViewType(String viewType) {
    String className = String.format(VIEW_TYPE_CLASS_NAME, viewType);
    try {
      Class<?> clazz = Class.forName(className);
      myCenterView = (CenterView) clazz.getDeclaredConstructor(CellProperties.class,
              Controller.class)
          .newInstance(myCellProperties, myController);
      root.setCenter(myCenterView.getViewBox());
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      showError(e.getMessage());
      e.printStackTrace();
    }
    myCenterView.updateView();
  }

  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView() {
    myCenterView.updateView();
  }

  /**
   * Toggles grid lines in the center
   */
  public void toggleLines(){
    myCenterView.toggleLines();
  }

  /**
   * Sets the bottom panel to a new bottom panel
   * @param bundle
   */
  public void updateBottomPanel(ResourceBundle bundle) { root.setBottom(mySimControl.setResource(bundle)); }

  /**
   * Sets the right panel to a new right panel
   * @param bundle
   * @param rightPanel
   */
  public void updateRightPanel(ResourceBundle bundle, RightPanel rightPanel) {
    myRightPanel = rightPanel;
    root.setRight(myRightPanel.setResource(bundle));
  }

  /**
   * Updates the right panel with a new language
   * @param bundle
   */
  public void updateRightPanelLang(ResourceBundle bundle) {
    root.setRight(myRightPanel.setResource(bundle));
  }

  /**
   * Updates the top panel with a new language
   * @param bundle
   */
  public void updateTopPanelLang(ResourceBundle bundle) {
    root.setTop(myTopLoadSave.setResource(bundle));
  }

  /**
   * Updates the left panel with a new language
   * @param bundle
   */
  public void updateLeftPanel(ResourceBundle bundle) { root.setLeft(myCellProperties.setResource(bundle)); }

  /**
   * update the left panel with a new sim properties
   * @param bundle
   */
  public void updateLeftView(ResourceBundle bundle) { root.setLeft(myCellProperties.updateLeftView(bundle, myController.getSimPropertiesMap())); }

  /**
   * Updates the canvas (grid) in the view and changes the scaling.
   */
  public void initiateCenterView() {
    myCenterView.initiateView();
  }

  /**
   * Displays the error from reading the SIM File as an Alert in the GUI. Displays the Title of the
   * error as well as the error message to the user.
   *
   * @param message is the String that is displayed on the Alert in the GUI
   */
  public void showError(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("SIM File Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Get left panel
   * @return
   */
  public CellProperties getMyCellProperties() {
    return myCellProperties;
  }
}
