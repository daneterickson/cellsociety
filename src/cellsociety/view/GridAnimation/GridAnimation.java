package cellsociety.view.GridAnimation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GridAnimation {
  private Timeline myAnimation;
  private boolean isPaused;

  public GridAnimation(){
  }
//TODO This class currently has no use. It may be a good way to setup the animation when refactoring though.


  private void startAnimation () {
    if (myAnimation != null) {
      myAnimation.stop();
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
   // myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECONDS_DELAY), e -> step()));
    myAnimation.play();
    isPaused = false;
  }

}
