package cellsociety.view;

import cellsociety.CellularAutomatonConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Graph {

  public static final int MAX_HEIGHT = 250;

  private LineChart lineChart;
  private CellularAutomatonConfiguration config;
  private int time;
  private Map<String, XYChart.Series> dataMap;

  public Graph(CellularAutomatonConfiguration config){
    this.config = config;
  }

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
