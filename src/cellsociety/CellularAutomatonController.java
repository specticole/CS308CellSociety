package cellsociety;

import cellsociety.view.CellularAutomatonView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class initializes a CellularAutomatonConfiguration object that stores all relevant
 * properties of the simulation, and then initializes an appropriate Model and View object. This
 * class also runs steps of the simulation.
 *
 * @author Patrick Liu
 */
public class CellularAutomatonController {
  public static final double[] STEP_SIZES = {1.0, 1.0 / 5, 1.0 / 10, 1.0 / 20, 1.0 / 60};

  private Timeline animation;
  private CellularAutomatonView myView;

  public CellularAutomatonController() {

  }

  // uses mkyong.com as reference
  public CellularAutomatonConfiguration loadConfigFile(String configFileName) {
    return new CellularAutomatonConfiguration(configFileName);
  }

  public void startSimulation() {
    KeyFrame frame = new KeyFrame(Duration.seconds(STEP_SIZES[2]), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  public void playSimulation() {

  }

  public void pauseSimulation() {

  }

  public void changeAnimationRate(double rate) {

  }

  private void step() {

  }

  public void updateViews(int newTime) {

  }

}
