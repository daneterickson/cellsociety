package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PredatorPreySettings extends RightPanel{
  private static final int MIN_FISH_REP = 1;
  private static final int MAX_FISH_REP = 20;
  private static final int START_FISH_REP = 5;
  private static final int MIN_SHARK_REP = 1;
  private static final int MAX_SHARK_REP = 20;
  private static final int START_SHARK_REP = 5;
  private static final int MIN_SHARK_E = 1;
  private static final int MAX_SHARK_E = 20;
  private static final int START_SHARK_E = 5;
  private static final int MIN_FISH_E_VAL = 1;
  private static final int MAX_FISH_E_VAL = 20;
  private static final int START_FISH_E_VAL = 5;




  public PredatorPreySettings(ResourceBundle bundle){
    super(bundle);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){rightPanel.getChildren().addAll(makeSliders());}

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){
    VBox sliderGroup = new VBox();
    Slider fishRepSlider = makeASlider(MIN_FISH_REP, MAX_FISH_REP, START_FISH_REP, "fishRepSlider", true);
    Slider sharkRepSlider = makeASlider(MIN_SHARK_REP, MAX_SHARK_REP, START_SHARK_REP, "sharkRepSlider", true);
    Slider sharkEnergySlider = makeASlider(MIN_SHARK_E, MAX_SHARK_E, START_SHARK_E, "sharkEnergySlider", true);
    Slider fishEnergyValueSlider = makeASlider(MIN_FISH_E_VAL, MAX_FISH_E_VAL, START_FISH_E_VAL, "fishEValSlider", true);
    sliderGroup.getChildren().addAll(fishRepSlider, sharkRepSlider, sharkEnergySlider, fishEnergyValueSlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }

}
