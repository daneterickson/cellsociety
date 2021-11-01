package cellsociety.view.mainview;


import cellsociety.controller.Controller;
import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.CircleGridView;
import cellsociety.view.center.GridView;
import cellsociety.view.center.HexagonGridView;
import cellsociety.view.center.SquareGridView;
import cellsociety.view.center.TriangleGridView;
import cellsociety.view.left.CellProperties;
import cellsociety.view.right.GameOfLifeSettings;
import cellsociety.view.right.RightPanel;
import cellsociety.view.top.TopLoadSave;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainView {

  private Stage myStage;
  private CellProperties myCellProperties;
  private GridView myGridView;
  private SimControl mySimControl;
  private TopLoadSave myTopLoadSave;
  private RightPanel myRightPanel;
  private Controller myController;
  private ResourceBundle myResources;
  private BorderPane root;

  public MainView(Stage stage, Controller controller){
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    myController = controller;
    myStage = stage;
    myCellProperties = new CellProperties(myController, myResources);
    myTopLoadSave = new TopLoadSave(myStage, myController, myResources);
    //myGridView = new SquareGridView(myCellProperties, myController);
    //myGridView = new TriangleGridView(myCellProperties, myController);
    //myGridView = new CircleGridView(myCellProperties, myController);
    myGridView = new HexagonGridView(myCellProperties, myController);
    myRightPanel = new GameOfLifeSettings(myResources, myController);
    mySimControl = new SimControl(myGridView, myController);
  }

  public Scene makeScene(int width, int height) {
    root = new BorderPane();
    root.setCenter(myGridView.getGridBox());
    root.setBottom(mySimControl.getSimControl());
    root.setLeft(myCellProperties.getCellProperties());
    root.setTop(myTopLoadSave.getTopLoadSave());
    root.setRight(myRightPanel.getTheRightPanel());
    Scene scene = new Scene(root, width, height);
    return scene;
  }

  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView() {
    myGridView.updateGrids();
  }

  public void updateRightPanel(ResourceBundle bundle, RightPanel rightPanel) {
    myRightPanel = rightPanel;
    root.setRight(myRightPanel.setResource(bundle));
  }
  public void updateRightPanelLang(ResourceBundle bundle) {
    root.setRight(myRightPanel.setResource(bundle));
  }
  public void updateLeftPanel(ResourceBundle bundle) { root.setLeft(myCellProperties.setResource(bundle)); }
  public void updateLeftView(ResourceBundle bundle) { root.setLeft(myCellProperties.updateLeftView(bundle, myController.getSimPropertiesMap())); }

  /**
   * Updates the canvas (grid) in the view and changes the scaling.
   */
  public void initiateGridView(){
    myGridView.initiateGrid();
  }

  public CellProperties getMyCellProperties() { return myCellProperties; }
  public TopLoadSave getTopLoadSave() {
    return myTopLoadSave;
  }


}
