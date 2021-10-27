package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PercolationSettings extends RightPanel{


  public PercolationSettings(ResourceBundle bundle){
    super(bundle);
  }

  @Override
  public void makeSettingsPanel(VBox rightPanel){}

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
