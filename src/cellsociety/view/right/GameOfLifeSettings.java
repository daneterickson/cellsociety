package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class GameOfLifeSettings extends RightPanel{


  public GameOfLifeSettings(ResourceBundle bundle){
    super(bundle);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){}

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
