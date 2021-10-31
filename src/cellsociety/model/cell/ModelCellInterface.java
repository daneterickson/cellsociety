package cellsociety.model.cell;

import cellsociety.model.exceptions.KeyNotFoundException;

public interface ModelCellInterface {

  void setCellParameter(String key, Double value);

  Double getCellParameter(String parameter) throws KeyNotFoundException;

  String getCellProperty(String property) throws KeyNotFoundException;

  void changeState(int newState);
}
