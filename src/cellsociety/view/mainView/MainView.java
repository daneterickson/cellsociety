package cellsociety.view.mainView;


import cellsociety.view.center.GridView;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class MainView {
  GridView myGridView = new GridView();
  private static final String DEFAULT_STYLESHEET = "/cellsociety/view/resources/Styling.css";


  public MainView(){

  }


  public Scene makeScene(int width, int height){
    BorderPane root = new BorderPane();
    root.setCenter(myGridView.getGridCanvas());
    //root.setLeft();
    //root.setRight();
    //root.setTop();
    //root.setBottom();
    Scene scene = new Scene(root, width, height);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    return scene;
  }
  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView(){
    myGridView.illustrate();
  }







}
