package cellsociety.view.right;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class FireSettings extends RightPanel{
  private VBox myRightPanel;
  private double MIN_PROB = 0;
  private double MAX_PROB = 1;
  private double STARTING_PROB = .5;

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
  protected Node makeSliders(){
    Group sliderGroup = new Group();
    Slider probSlider = makeASlider(MIN_PROB,MAX_PROB,STARTING_PROB,"probSlider");
    sliderGroup.getChildren().add(probSlider);
    return sliderGroup;}

  @Override
  protected Node makeTextBox(){
    return null;
  }


}
