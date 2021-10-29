package cellsociety.view.right;

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
  //TODO get from properties file
  private static final String PROB_LABEL = "Probability of Spreading";

  public SpreadingOfFireSettings(ResourceBundle bundle){
    super(bundle);
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
    Label probLabel = makeALabel(PROB_LABEL, "probLabel");
    Slider probSlider = makeASlider(MIN_PROB,MAX_PROB,STARTING_PROB,"probSlider", true, TICK_SPACING);
    sliderGroup.getChildren().addAll(probLabel, probSlider);
    return sliderGroup;}

  @Override
  protected Node makeTextBox(){
    return null;
  }


}
