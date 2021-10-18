package cellsociety.view.mainView;


import cellsociety.view.bottom.SimControl;
import cellsociety.view.center.GridView;
import cellsociety.view.top.TopLoadSave;
import javafx.scene.layout.BorderPane;


public class MainView extends BorderPane {
  private TopLoadSave myTopLoadSave = new TopLoadSave();
  private GridView myGridView = new GridView();
  private SimControl mySimControl = new SimControl();

  public MainView(){
    this.setTop(myTopLoadSave.getTopLoadSave());
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
