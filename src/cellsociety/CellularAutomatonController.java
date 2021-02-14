package cellsociety;

import cellsociety.view.CellularAutomatonView;
import java.util.List;
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
  private KeyFrame frame;
  private CellularAutomatonView myView;
  private List<List<String>> myStates;
  private int currentTime;

  public CellularAutomatonController() {
    currentTime = 0;
    frame = new KeyFrame(Duration.seconds(STEP_SIZES[2]), e -> step(currentTime));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
  }

  public CellularAutomatonController(CellularAutomatonView myView) {
    this();
    this.myView = myView;
  }

  // uses mkyong.com as reference
  public CellularAutomatonConfiguration loadConfigFile(String configFileName) {
    CellularAutomatonConfiguration simulationConfig = new CellularAutomatonConfiguration(configFileName);

    return new CellularAutomatonConfiguration(configFileName);
  }

  public void startSimulation() {
    animation.play();
  }

  public void playSimulation() {
    animation.play();
  }

  public void pauseSimulation() {
    animation.pause();
  }

  private void changeAnimationRate(double rate) {
    frame = new KeyFrame(Duration.seconds(rate), e -> step(currentTime));
  }

  public void changeRateSlider(int sliderPos) {
    changeAnimationRate(STEP_SIZES[sliderPos - 1]);
  }

  private void step(int time) {
    // call Model method to get updated states (or at time t)
    // for now, just choose a random cell and change its state
    int changeRow = (int) (Math.random() * myStates.size());
    int changeCol = (int) (Math.random() * myStates.get(0).size());
    String targetState = myStates.get(changeRow).get(changeCol);
    if (targetState.equals("A")) {
      myStates.get(changeRow).set(changeCol, "D");
    }
    else {
      myStates.get(changeRow).set(changeCol, "A");
    }
    // send updated states to View
    updateViews();
    currentTime++;
  }

  private void updateViews() {

  }

}
