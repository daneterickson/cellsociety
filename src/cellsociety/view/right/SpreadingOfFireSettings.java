package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SpreadingOfFireSettings extends RightPanel{
  private double MIN_PROB = 0;
  private double MAX_PROB = 1;
  private double STARTING_PROB = .5;
  private static final double TICK_SPACING = .1;

  public SpreadingOfFireSettings(ResourceBundle bundle, Controller controller){
    super(bundle, controller);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){rightPanel.getChildren().add(makeSliders());}

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){
    VBox sliderGroup = new VBox();
    Label probLabel = makeALabel(super.getMyResource().getString("SpreadingOfFireProbLabel"), "probLabel");
    Slider probSlider = makeASlider(MIN_PROB,MAX_PROB,STARTING_PROB,"probSlider", true, TICK_SPACING);
    probSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      ArrayList settingsPkg = new ArrayList();
      settingsPkg.add(probSlider.getValue());
      setProbSettings(settingsPkg);
    });
    sliderGroup.getChildren().addAll(probLabel, probSlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }

  @Override
  protected void setProbSettings(ArrayList probability) {
    super.getMyController().updateModelSettings(probability);
  }

}
