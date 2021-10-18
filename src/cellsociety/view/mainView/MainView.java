package cellsociety.view.mainView;


import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.GridView;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class MainView extends BorderPane{
  GridView myGridView = new GridView();
  SimControl mySimControl = new SimControl();

  public MainView(){
    this.setCenter(myGridView.getGridCanvas());
    this.setBottom(mySimControl.getSimControl());
  }

  /**
   * Updates the canvas (grid) in the view.
   */
  public void updateView(){
    myGridView.illustrate();
  }







}
