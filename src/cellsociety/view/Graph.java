package cellsociety.view;

import cellsociety.controller.CellularAutomatonConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Creates the Graph view that can be shown alongside the grid simulation.
 * Graphs take in the CellularAutomatonConfiguration object that pertains to the current
 * simulation in order to determine what the states are so they can be displayed.
 * The number of each state are shown on the graph as the simulation progresses through time.
 *
 * @author Patrick Liu
 */

public class Graph {

  public static final int MAX_HEIGHT = 250;

  private LineChart lineChart;
  private CellularAutomatonConfiguration config;
  private int time;
  private Map<String, XYChart.Series> dataMap;

  /**
   * Constructor of a Graph object.
   * Takes in a configuration to determine what to graph.
   *
   * @param config configuration that determines states and amount of initial states
   */
  public Graph(CellularAutomatonConfiguration config){
    this.config = config;
  }

  /**
   * Initializes the LineChart to be shown as a part of a SimulationView.
   *
   * @return the LineChart that represents the graph of the simulation
   */
  public LineChart initialize(){
    time = 0;
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle(config.getSimulationMetadata().get("title"));
    lineChart.getStyleClass().add("graph");
    dataMap = new HashMap<>();
    for (String cellState : config.getCellStyles().keySet()) {
      XYChart.Series tempSeries = new XYChart.Series();
      tempSeries.setName(cellState);
      dataMap.put(cellState, tempSeries);
      lineChart.getData().add(tempSeries);
    }
    updateGraph(config.getInitialStates());
    return lineChart;
  }

  /**
   * Updates the Graph to show the next counts of each states.
   * Used by SimulationView to send the information from the controller to the Graph.
   *
   * @param currentStates 2D list of the new states from the controller
   */
  public void updateGraph(List<List<String>> currentStates) {
    Map<String, Integer> stateCounts = getStateCounts(currentStates);
    for (String state : stateCounts.keySet()) {
      dataMap.get(state).getData().add(new XYChart.Data(time, stateCounts.get(state)));
    }
    time++;
  }

  private Map<String, Integer> getStateCounts(List<List<String>> currentStates) {
    Map<String, Integer> stateCountMap = new HashMap<>();
    for (String cellState : config.getCellStyles().keySet()) {
      stateCountMap.put(cellState, 0);
    }
    for (List<String> row : currentStates) {
      for (String state : row) {
        stateCountMap.put(state, stateCountMap.get(state) + 1);
      }
    }
    return stateCountMap;
  }

}
