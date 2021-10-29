package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PredatorPreySettings extends RightPanel{
  private static final int MIN_FISH_REP = 1;
  private static final int MAX_FISH_REP = 21;
  private static final int START_FISH_REP = 5;
  private static final int MIN_SHARK_REP = 1;
  private static final int MAX_SHARK_REP = 21;
  private static final int START_SHARK_REP = 5;
  private static final int MIN_SHARK_E = 1;
  private static final int MAX_SHARK_E = 21;
  private static final int START_SHARK_E = 5;
  private static final int MIN_FISH_E_VAL = 1;
  private static final int MAX_FISH_E_VAL = 21;
  private static final int START_FISH_E_VAL = 5;
  //TODO set these Strings from the properties file
  private static final String FISH_REP_LABEL = "Fish Repopulation Rate";
  private static final String SHARK_REP_LABEL = "Shark Repopulation Rate";
  private static final String SHARK_E_LABEL = "Shark Energy Level";
  private static final String FISH_E_VAL_LABEL = "Fish Energy Level";





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
    Label fishRepLabel = makeALabel(FISH_REP_LABEL, "fishRepLabel");
    Slider fishRepSlider = makeASlider(MIN_FISH_REP, MAX_FISH_REP, START_FISH_REP, "fishRepSlider", true);
    Label sharkRepLabel = makeALabel(SHARK_REP_LABEL, "sharkRepLabel");
    Slider sharkRepSlider = makeASlider(MIN_SHARK_REP, MAX_SHARK_REP, START_SHARK_REP, "sharkRepSlider", true);
    Label sharkELabel = makeALabel(SHARK_E_LABEL, "sharkELabel");
    Slider sharkEnergySlider = makeASlider(MIN_SHARK_E, MAX_SHARK_E, START_SHARK_E, "sharkEnergySlider", true);
    Label fishEValLabel = makeALabel(FISH_E_VAL_LABEL, "fishEValLabel");
    Slider fishEnergyValueSlider = makeASlider(MIN_FISH_E_VAL, MAX_FISH_E_VAL, START_FISH_E_VAL, "fishEValSlider", true);
    sliderGroup.getChildren().addAll(fishRepLabel ,fishRepSlider, sharkRepLabel, sharkRepSlider, sharkELabel, sharkEnergySlider, fishEValLabel, fishEnergyValueSlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }

}
