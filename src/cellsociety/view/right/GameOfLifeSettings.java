package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class GameOfLifeSettings extends RightPanel{


  public GameOfLifeSettings(ResourceBundle bundle, Controller controller){
    super(bundle, controller);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){rightPanel.getChildren().addAll(makeButtons());}

  @Override
  protected Node makeButtons(){
    return makeGridLinesToggleButton();
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
