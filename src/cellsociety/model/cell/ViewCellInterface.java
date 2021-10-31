package cellsociety.model.cell;

import cellsociety.model.exceptions.KeyNotFoundException;

public interface ViewCellInterface {

  String getCellProperty(String property) throws KeyNotFoundException;
}
