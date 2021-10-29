package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationSettings extends RightPanel{
  private final double MIN_SIMILARITY = 0;
  private final double MAX_SIMILARITY = 1;
  private final double STARTING_SIMILARITY = .5;

  private static final double TICK_SPACING = .1;
  //TODO get string for properties
  private static final String SIMILARITY_LABEL = "Preferred Similarity";


  public SegregationSettings(ResourceBundle bundle){
    super(bundle);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){
    rightPanel.getChildren().addAll(makeSliders());
  }

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){
    VBox sliderGroup = new VBox();
    Label similarityLabel = makeALabel(SIMILARITY_LABEL, "similarityLabel");
    Slider similaritySlider = makeASlider(MIN_SIMILARITY,MAX_SIMILARITY,STARTING_SIMILARITY, "similaritySlider", true, TICK_SPACING);
    sliderGroup.getChildren().addAll(similarityLabel, similaritySlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }



}
