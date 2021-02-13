package cellsociety;

/**
 * This class initializes a CellularAutomatonConfiguration object that stores
 * all relevant properties of the simulation, and then initializes an appropriate
 * Model and View object. This class also runs steps of the simulation.
 *
 * @author Patrick Liu
 */
public class CellularAutomatonController {

  public CellularAutomatonController() {

  }

  // uses mkyong.com as reference
  public CellularAutomatonConfiguration loadConfigFile(String configFileName) {
    return new CellularAutomatonConfiguration(configFileName);
  }

  public void playSimulation() {

  }

  public void pauseSimulation() {

  }

  public void changeAnimationRate(double rate) {

  }

  public void step() {

  }

  public void updateViews(int newTime) {
    
  }

}
