package cellsociety.model.parser;

import static cellsociety.model.cell.GameOfLifeCell.GAME_OF_LIFE_CASES;

import cellsociety.model.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class to generate the initial states for random configurations where a CSV file is not given
 */
public class RandomStates {

  private ParserSIM myParser;
  private int myNumRows;
  private int myNumCols;
  private int myNumFilled;
  private double myProb0;
  private double myProb1;
  private double myProb2;
  private int myNumCases;
  private int states[][];
  private Random rand = new Random();

  /**
   * Constructor to make a RandomStates object based on a ParserSIM object. The RandomStates object
   * is used to generate the initial states for random configurations.
   *
   * @param parser is the ParserSIM object used for the current simulation
   */
  public RandomStates(ParserSIM parser) {
    myParser = parser;
    String parameters[] = parser.getInfo("InitialStates").split(",");
    myNumRows = Integer.parseInt(parameters[0]);
    myNumCols = Integer.parseInt(parameters[1]);
    states = new int[myNumRows][myNumCols];
    myNumCases = 3;
    if (parser.getInfo("Type").equals("GameOfLife")) {
      myNumCases = GAME_OF_LIFE_CASES;
    }
    assignVariables(parameters);
  }

  private void assignVariables(String[] parameters) {
    switch (parameters.length) {
      case 3 -> {
        myNumFilled = Integer.parseInt(parameters[2]);
        numFilledStates();
      }
      case 4 -> {
        myProb0 = Double.valueOf(parameters[2]);
        myProb1 = Double.valueOf(parameters[3]);
        probDensity2States();
      }
      case 5 -> {
        myProb0 = Double.valueOf(parameters[2]);
        myProb1 = Double.valueOf(parameters[3]);
        myProb2 = Double.valueOf(parameters[4]);
        probDensity3States();
      }
    }
  }

  private void numFilledStates() {
    int fill = 0;
    while (fill < myNumFilled) {
      int state = rand.nextInt(myNumCases - 1) + 1;
      int r = rand.nextInt(myNumRows);
      int c = rand.nextInt(myNumCols);
      if (states[r][c] == 0) {
        states[r][c] = state;
        fill++;
      }
    }
  }

  private void probDensity2States() {
    for (int r = 0; r < myNumRows; r++) {
      for (int c = 0; c < myNumCols; c++) {
        double tmp = rand.nextDouble();
        if (tmp < myProb1) {
          states[r][c] = 1;
        }
      }
    }
  }

  private void probDensity3States() {
    List<Integer> probs = makeProbsList();
    for (int r = 0; r < myNumRows; r++) {
      for (int c = 0; c < myNumCols; c++) {
        int tmp = rand.nextInt(100);
        states[r][c] = probs.get(tmp);
      }
    }
  }

  private List<Integer> makeProbsList() {
    int state0[] = new int[(int) Math.round(myProb0 * 100)];
    int state1[] = new int[(int) Math.round(myProb1 * 100)];
    Arrays.fill(state1, 1);
    int state2[] = new int[100 - state0.length - state1.length];
    Arrays.fill(state2, 2);
    List<Integer> probs = new ArrayList<>();
    for (int i : state0) {
      probs.add(i);
    }
    for (int i : state1) {
      probs.add(i);
    }
    for (int i : state2) {
      probs.add(i);
    }
    return probs;
  }

  /**
   * Makes and returns the Grid to be used in the simulation based on the generated random
   * configuration of initial states.
   *
   * @return the Grid object to be used in the simulation
   */
  public Grid makeGrid() {
    return new Grid(myNumRows, myNumCols, states, myParser.getInfo("StateColors"),
        myParser.getInfo("Parameters"), myParser.getInfo("Type"));
  }

  protected RandomStates(ParserSIM parser, long seed) {
    rand = new Random(seed);
    myParser = parser;
    String parameters[] = parser.getInfo("InitialStates").split(",");
    myNumRows = Integer.parseInt(parameters[0]);
    myNumCols = Integer.parseInt(parameters[1]);
    states = new int[myNumRows][myNumCols];
    myNumCases = 3;
    if (parser.getInfo("Type").equals("GameOfLife")) {
      myNumCases = GAME_OF_LIFE_CASES;
    }
    assignVariables(parameters);
  }
}
