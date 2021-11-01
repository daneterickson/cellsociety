package cellsociety.view.mainview;


import cellsociety.controller.Controller;
import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.CenterView;
import cellsociety.view.center.GridView;
import cellsociety.view.center.HistogramView;
import cellsociety.view.center.SquareGridView;
import cellsociety.view.left.CellProperties;
import cellsociety.view.right.GameOfLifeSettings;
import cellsociety.view.right.RightPanel;
import cellsociety.view.top.TopLoadSave;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainView {

  private Stage myStage;
  private CellProperties myCellProperties;
  private SimControl mySimControl;
  private TopLoadSave myTopLoadSave;
  private RightPanel myRightPanel;
  private Controller myController;
  private ResourceBundle myResources;
  private BorderPane root;
  private HistogramView myHistogramView;
  private CenterView myCenterView;

  public MainView(Stage stage, Controller controller){
    myResources = ResourceBundle.getBundle("lang.English", Locale.ENGLISH);
    myController = controller;
    myStage = stage;
    myCellProperties = new CellProperties(myController, myResources);
    myTopLoadSave = new TopLoadSave(myStage, myController, myResources);
    //TODO make button to toggle between these with reflection
//    myGridView = new SquareGridView(myCellProperties, myController);
//    myGridView = new TriangleGridView(myCellProperties, myController);
//    myGridView = new CircleGridView(myCellProperties, myController);
//    myGridView = new HexagonGridView(myCellProperties, myController);
//    myHistogramView = new HistogramView(myController);
    myRightPanel = new GameOfLifeSettings(myResources, myController);
    myCenterView = new SquareGridView(myCellProperties, myController);
    mySimControl = new SimControl(myCenterView, myController, myResources, myCellProperties);
  }

  public Scene makeScene(int width, int height) {
    root = new BorderPane();
    root.setCenter(myCenterView.getViewBox());
//    root.setCenter(myHistogramView.getHistogramBox());
    root.setBottom(mySimControl.getSimControl());
    root.setLeft(myCellProperties.getCellProperties());
    root.setTop(myTopLoadSave.getTopLoadSave());
    root.setRight(myRightPanel.getTheRightPanel());
    Scene scene = new Scene(root, width, height);
    return scene;
  }

  public void assignViewType(String viewType) throws ClassNotFoundException {
    String className = String.format("cellsociety.view.center.%s",viewType);
    Class<?> clazz = Class.forName(className);
    try {
      myCenterView = (CenterView) clazz.getDeclaredConstructor(CellProperties.class, Controller.class)
          .newInstance(myCellProperties, myController);
      root.setCenter(myCenterView.getViewBox());
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      System.out.println("Method Not Found");
      e.printStackTrace();
    }
    myCenterView.updateView();
  }

  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView() {
    myCenterView.updateView();
//    myGridView.updateView();
//    myHistogramView.updateView();
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
  public void initiateCenterView(){
    myCenterView.initiateView();
//    myGridView.initiateView();
//    myHistogramView.initiateView();
  }

  public CellProperties getMyCellProperties() { return myCellProperties; }
  public TopLoadSave getTopLoadSave() {
    return myTopLoadSave;
  }


}
