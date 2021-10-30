package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegregationRuleTest {

  private Rule rule;
  private ArrayList<Integer> empties = new ArrayList<>();
  private int nearby[];
  private List<Integer> nearbyList;

  @BeforeEach
  void setUp () {
    empties.add(5);
    rule = new SegregationRule(0.5,5,empties);
  }

  @Test
  void testRemainEmpty() {
    nearby = new int[]{1,1,1,1,1,1,1,1};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
  }

  @Test
  void testRemain1() {
    nearby = new int[]{1,1,1,1,1,1,1,1};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
  }

  @Test
  void testMoveSpots() {
    nearby = new int[]{1,1,1,1,1,1,1,1};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,2,nearbyList);
    assertEquals(0,actual);
  }

  private List<Integer> makeNearbyList(int[] nearby) {
    List<Integer> ret = new ArrayList<>();
    for (int i : nearby) {
      ret.add(i);
    }
    return ret;
  }

}
