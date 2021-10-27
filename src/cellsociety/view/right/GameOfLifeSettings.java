package cellsociety.view.right;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class GameOfLifeSettings extends RightPanel{
  private VBox myRightPanel;

  public GameOfLifeSettings(){
    super();
    myRightPanel = getTheRightPanel();
  }

  @Override
  public void makeSettingsPanel(){}

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){return null;}

  @Override
  protected Node makeTextBox(){
    return null;
  }

}
