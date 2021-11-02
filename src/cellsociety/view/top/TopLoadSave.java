package cellsociety.view.top;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TopLoadSave {

  private String RESOURCE = "cellsociety.view.top.";
  private String STYLESHEET = String.format("/%sTopLoadSave.css", RESOURCE.replace(".", "/"));

  private HBox myTopLoadSave;
  private Stage myStage;
  private Controller myController;
  private ResourceBundle myResources;

  public TopLoadSave(Stage stage, Controller controller, ResourceBundle resources) {
    myStage = stage;
    myResources = resources;
    myTopLoadSave = new HBox();
    myTopLoadSave.getChildren().add(makeLoadSaveButtons());
    setStyles();
    myController = controller;
  }

  private void setStyles() {
    myTopLoadSave.getStyleClass().add("root");
    myTopLoadSave.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
  }

  private Node makeLoadSaveButtons() {
    HBox topLoadSave = new HBox(15);
    topLoadSave.getChildren().add(makeLoadSIMButton());
    topLoadSave.getChildren().add(makeSaveSIMButton());
    return topLoadSave;
  }

  private Node makeLoadSIMButton() {
    Button loadSIMButton = new Button(myResources.getString("LoadSim"));
    loadSIMButton.setOnAction(e -> loadSIMFile());
    return setID(loadSIMButton, "LoadSIMButton");
  }

  private Node makeSaveSIMButton() {
    Button saveSIMButton = new Button(myResources.getString("SaveSim"));
    saveSIMButton.setOnAction(e -> saveSIM());
    return setID(saveSIMButton, "SaveSIMButton");
  }

  private void loadSIMFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(myResources.getString("LoadSim"));
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SIM", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile == null) {
      return;
    }
    myController.openSIMFile(selectedFile);
  }

  private void saveCSVFile(File saveFile) {
    File CSV = new File(String.format("%s.csv", saveFile.toString()));
    Grid tempGrid = myController.getGrid();
    try {
      PrintWriter csvFile = new PrintWriter(CSV);
      csvFile.write(String.format("%s,%s\n", tempGrid.getNumCols(), tempGrid.getNumRows()));
      for (int i = 0; i < tempGrid.getNumRows(); i++) {
        StringBuilder rowCSV = new StringBuilder();
        for (int j = 0; j < tempGrid.getNumCols(); j++) {
          if (j != tempGrid.getNumCols() - 1) {
            rowCSV.append(tempGrid.getCellStateNumber(i, j) + ",");
          } else {
            rowCSV.append(tempGrid.getCellStateNumber(i, j) + "\n");
          }
        }
        csvFile.write(rowCSV.toString());

      }
      csvFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveSimFile(File saveFile) {
    File SIM = new File(String.format("%s.sim", saveFile.toString()));
    Map simValueMap = myController.getSimPropertiesMap();
    simValueMap.put("InitialStates", String.format("testingSave/%s.csv", saveFile.getName()));
    try {
      PrintWriter simFile = new PrintWriter(SIM);
      for (Object key : simValueMap.keySet()) {
        simFile.write(String.format("%s=%s\n", key.toString(), simValueMap.get(key).toString()));
      }
      simFile.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File openSaveFileDialog() {
    FileChooser fileChooser = new FileChooser();
    File savedCSV = fileChooser.showSaveDialog(myStage);
    return savedCSV;
  }
  private void saveSIM() {
    File saveFiles = openSaveFileDialog();
    saveCSVFile(saveFiles);
    saveSimFile(saveFiles);
  }

  private Node setID(Node disp, String id) {
    disp.setId(id);
    return disp;
  }

  public Node getTopLoadSave() {
    return myTopLoadSave;
  }

  public Node setResource(ResourceBundle bundle) {
    myResources = bundle;
    myTopLoadSave = new HBox();
    myTopLoadSave.getChildren().add(makeLoadSaveButtons());
    setStyles();
    return myTopLoadSave;
  }

}
