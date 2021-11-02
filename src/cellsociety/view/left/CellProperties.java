package cellsociety.view.left;

import cellsociety.controller.Controller;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CellProperties {

  private String RESOURCE = "cellsociety.view.left.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"CellProp.css";
  private static final int SPACING = 40;

  private String stateColorTitle;
  private int myCurrentX;
  private int myCurrentY;
  private String myCurrentState;
  private Label myCellCoordinatesLabel;
  private Label myCellStateLabel;
  private Label mySimTypeLabel;
  private VBox myCellProperties;
  private ResourceBundle myResource;
  private Controller myController;


  public CellProperties(Controller controller,ResourceBundle resource){
    myController = controller;
    myResource = resource;
    stateColorTitle = myResource.getString("ColorKey");
    myCellProperties = new VBox();
    myCellProperties.setSpacing(SPACING);
    myCellProperties.getChildren().add(makeCellPropLabels());
    setStyles();
  }

  /**
   * Getter method that returns the CellProperties VBox. This contains all the displayed cell info.
   * @return Node that contains the displayed cell properties.
   */
  public Node getCellProperties(){
    return myCellProperties;
  }

  /**
   * Updates the label that displays the current cell that is hovered over.
   * @param currentX X coordinate of current cell.
   * @param currentY Y coordinate of current cell.
   */
  public void updateCellCordLabel(int currentX, int currentY){
    myCurrentX = currentX;
    myCurrentY = currentY;
    myCellCoordinatesLabel.setText(String.format(myResource.getString("CoordLabel"), myCurrentX, myCurrentY));
  }

  /**
   * Updates the label that displays the current simulation type
   * @param simType The String representing the simulation type
   */
  public void updateSimTypeLabel(String simType){
    mySimTypeLabel.setText(simType);
  }

  private Node makeCellPropLabels(){
    VBox labelBox = new VBox();
    labelBox.getChildren().add(initializeSimTypeLabel());
    labelBox.getChildren().add(makeCellCordTitle());
    labelBox.getChildren().add(initializeCellCordLabel());
    //labelBox.getChildren().add(makeStateColorTitle());

    return labelBox;
  }

  private Node initializeCellCordLabel(){
    myCellCoordinatesLabel = new Label(String.format(myResource.getString("CoordLabel"), myCurrentX, myCurrentY));
    return myCellCoordinatesLabel;
  }

  private Node makeCellCordTitle(){
    Label title = new Label(myResource.getString("CoordinateTitle"));
    title.getStyleClass().add("coordinateTitle");
    return title;
  }

  private Node initializeSimTypeLabel(){
    mySimTypeLabel = new Label(myResource.getString("DefaultSim"));
    return mySimTypeLabel;
  }

  private Node makeStateColorTitle(){
    Label title = new Label(stateColorTitle);
    title.getStyleClass().add("stateColorsTitle");
    return title;
  }
  /*private Node initializeCellStateLabel(myCurrentState){
    myCellStateLabel = new Label();
    return myCellStateLabel;
  }*/

  private void setStyles() {
    myCellProperties.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myCellProperties.getStyleClass().add("root");
    myCellCoordinatesLabel.getStyleClass().add("coordinateLabel");
    mySimTypeLabel.getStyleClass().add("simTypeLabel");
  }

  public Node setResource(ResourceBundle bundle) {
    myResource = bundle;
    myCellProperties = new VBox();
    myCellProperties.getChildren().add(makeCellPropLabels());
    setStyles();
    return myCellProperties;
  }

  public Node updateLeftView(ResourceBundle bundle, Map simProps) {
    myResource = bundle;
    myCellProperties = new VBox();

    mySimTypeLabel = new Label((String) simProps.get("Type"));

    Label title = new Label(myResource.getString("CoordinateTitle"));
    title.getStyleClass().add("coordinateTitle");

    myCellCoordinatesLabel = new Label("("+myCurrentX+", "+myCurrentY+")");

    Label color = new Label(myResource.getString("StateColorTitle"));
    title.getStyleClass().add("stateColorsTitle");

    myCellProperties.getChildren().add(mySimTypeLabel);
    myCellProperties.getChildren().add(title);
    myCellProperties.getChildren().add(myCellCoordinatesLabel);
    myCellProperties.getChildren().add(color);
    setStyles();


    return myCellProperties;
  }


}
