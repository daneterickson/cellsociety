package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PercolationSettings extends RightPanel{


  public PercolationSettings(ResourceBundle bundle, Controller controller){
    super(bundle, controller);
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

  @Override
  protected void setProbSettings(ArrayList probability) { }

}
