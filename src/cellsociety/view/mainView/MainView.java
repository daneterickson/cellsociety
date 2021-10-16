package cellsociety.view.mainView;


import cellsociety.view.center.GridView;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class MainView extends BorderPane {
  GridView myGridView = new GridView();

  public MainView(){
    this.setCenter(myGridView.getGridCanvas());
    this.setBottom(new Button("testButton"));
  }


  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView(){
    myGridView.illustrate();
  }







}
