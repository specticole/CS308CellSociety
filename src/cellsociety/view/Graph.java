package cellsociety.view;

import javafx.scene.chart.LineChart;

public abstract class Graph {

  LineChart lineChart;

  public Graph(LineChart chart){
    lineChart = chart;
  }

}
