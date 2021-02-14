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

  /**
   * Creates a Controller object that stores a myView object, will pass updated states
   * from the Model to the View
   * @param myView - the View object for this simulation
   */
  public CellularAutomatonController(CellularAutomatonView myView) {
    this();
    this.myView = myView;
  }

  /**
   * Takes in a filename and returns an object that stores all relevant information for
   * the simulation in convenient data structures
   * @param configFileName - the name of the configuration file
   * @return - object that stores data relevant to the Model and the View
   */
  public CellularAutomatonConfiguration loadConfigFile(String configFileName) {
    CellularAutomatonConfiguration simulationConfig = new CellularAutomatonConfiguration(configFileName);
    myStates = simulationConfig.getInitialStates();
    return simulationConfig;
  }

  /**
   * Starts or resumes the simulation by resuming the Timeline object
   */
  public void playSimulation() {
    animation.play();
  }

  /**
   * Pauses the simulation by pausing the Timeline object
   */
  public void pauseSimulation() {
    animation.pause();
  }

  // replaces the current KeyFrame object in order to change the animation rate
  private void changeAnimationRate(double rate) {
    frame = new KeyFrame(Duration.seconds(rate), e -> step(currentTime));
    animation.stop();
    animation.getKeyFrames().clear();
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Changes the animation rate based on a five-position slider
   * @param sliderPos - the new position of the slider
   */
  public void changeRateSlider(int sliderPos) {
    changeAnimationRate(STEP_SIZES[sliderPos - 1]);
  }

  /**
   * Pauses the simulation and increments the time by one
   */
  public void stepOnce() {
    animation.pause();
    currentTime++;
    step(currentTime);
  }

  /**
   * Resets the simulation by changing the instance time variable to 0
   */
  public void resetSimulation() {
    animation.pause();
    currentTime = 0;
    // step method will get states at time t = 0
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
    myView.updateView(myStates);
    currentTime++;
  }

}
