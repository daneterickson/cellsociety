package cellsociety.view.mainView;


import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.GridView;
import cellsociety.view.top.TopLoadSave;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class MainView{
  private GridView myGridView = new GridView();
  private SimControl mySimControl = new SimControl();
  private TopLoadSave myTopLoadSave = new TopLoadSave();

  public MainView(){
  }

  public Scene makeScene(int width, int height){
    BorderPane root = new BorderPane();
    root.setCenter(myGridView.getGridCanvas());
    root.setBottom(mySimControl.getSimControl());
    root.setTop(myTopLoadSave.getTopLoadSave());
    //root.setLeft();
    //root.setRight();
    //root.setTop();
    Scene scene = new Scene(root, width, height);
    return scene;
  }


  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView(){
    myGridView.illustrate();
  }

}
