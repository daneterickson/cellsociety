package cellsociety.model.model;

import cellsociety.model.Grid;
import java.util.function.Consumer;
import java.util.function.Function;

public interface baseModel{

  void updateModel(Grid currGrid);

  private void iterateGrid(Function<Integer, Consumer<Integer>> gridIterationAction){
  }
  private void updateCell(int row, int col, int state) {
  }
  private void addNewUpdates(int row, int col, int newState) {
  }
  private void updateGrid() {
  }
}
