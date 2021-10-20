package cellsociety.view.mainView;


import cellsociety.controller.Controller;
import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.GridView;
import cellsociety.view.left.CellProperties;
import cellsociety.view.top.TopLoadSave;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainView {

  private Stage myStage;
  private CellProperties myCellProperties = new CellProperties();
  private GridView myGridView;
  private SimControl mySimControl;
  private TopLoadSave myTopLoadSave;
  private Controller myController;

  public MainView(Stage stage, Controller controller){
    myController = controller;
    myStage = stage;
    myTopLoadSave = new TopLoadSave(myStage, myController);
    myGridView = new GridView(myCellProperties, myController);
    mySimControl = new SimControl(myGridView, myController);
  }

  public Scene makeScene(int width, int height) {
    BorderPane root = new BorderPane();
    root.setCenter(myGridView.getGridBox());
    root.setBottom(mySimControl.getSimControl());
    root.setLeft(myCellProperties.getCellProperties());
    root.setTop(myTopLoadSave.getTopLoadSave());
    //root.setRight();
    //root.setTop();
    Scene scene = new Scene(root, width, height);
    return scene;
  }


  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView() {
    myGridView.updateGrid();
  }

  /**
   * Updates the canvas (grid) in the view and changes the scaling.
   */
  public void initiateGridView(){
    myGridView.initiateGrid();
  }


  public TopLoadSave getTopLoadSave() {
    return myTopLoadSave;
  }

}
