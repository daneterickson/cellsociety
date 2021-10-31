package cellsociety.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.utils.EdgePolicies.EdgePolicies;
import cellsociety.model.model.utils.EdgePolicies.FiniteEdgePolicy;
import cellsociety.model.model.utils.EdgePolicies.SphericalEdgePolicy;
import cellsociety.model.model.utils.EdgePolicies.ToroidalEdgePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class setEdgePolicyAndNeighborFinderTest {

  private Grid myGrid;
  private int myStates[][];
  private int numRows;
  private int numCols;
  private String myStartColors;
  private String myParameters;
  private EdgePolicies myEdgePolicies;
  private int row;
  private int col;
  private Model myModel;
  private Controller myController;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{1, 1, 1, 1, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 0, 0, 0, 1},
        {2, 2, 2, 2, 2}};
    numRows = 5;
    numCols = 5;
    String type = "PredatorPrey";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    row = 2;
    col = 2;

  }

  @Test
  void testPredPrey() throws NoSuchFieldException {

    myModel = new PredatorPreyModel(myController, myGrid);
    assertEquals(true, myModel.getEdgePolicyType().contains("FiniteEdgePolicy"),"Pred/prey should have finite edge policy. got: "+myModel.getEdgePolicyType());
    assertEquals(true, myModel.getNeighborFinderType().contains("SquareEdges"),"Pred/prey should have square edges neighbor finder. got: "+myModel.getNeighborFinderType());
    myModel.setEdgePolicy("Spherical");
    myModel.setNeighborFinder("SquareCorners");
    assertEquals(true, myModel.getEdgePolicyType().contains("SphericalEdgePolicy"), "edge policy should be spherical. got: " + myModel.getEdgePolicyType());
    assertEquals(true, myModel.getNeighborFinderType().contains("SquareCorners"), "neighbor finder should be square corners. got: "+myModel.getNeighborFinderType());

  }

  @Test
  void testGameOfLife() throws NoSuchFieldException {

    myModel = new GameOfLifeModel(myController, myGrid);
    assertEquals(true, myModel.getEdgePolicyType().contains("FiniteEdgePolicy"),"Game of Life should have finite edge policy. got: "+myModel.getEdgePolicyType());
    assertEquals(true, myModel.getNeighborFinderType().contains("SquareComplete"),"Game of Life should have square complete neighbor finder. got: "+myModel.getNeighborFinderType());
    myModel.setEdgePolicy("Spherical");
    myModel.setNeighborFinder("SquareCorners");
    assertEquals(true, myModel.getEdgePolicyType().contains("SphericalEdgePolicy"), "edge policy should be spherical. got: " + myModel.getEdgePolicyType());
    assertEquals(true, myModel.getNeighborFinderType().contains("SquareCorners"), "neighbor finder should be square corners. got: "+myModel.getNeighborFinderType());

  }
}
