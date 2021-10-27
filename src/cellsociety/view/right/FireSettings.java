package cellsociety.view.right;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class FireSettings extends RightPanel{
  private VBox myRightPanel;

  public FireSettings(){
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
