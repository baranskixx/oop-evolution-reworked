package oop.evolution.GUI;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Representation of single graph in the GUI.
 */
public class Graph {
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
    public static int RECORDS_ON_CHART = 10;

    /**
     * Default constructor.
     * @param xName Name of x-axis.
     * @param yName Name of y-axis.
     * @param width Width of the chart.
     * @param height Height of the chart.
     */
    public Graph(String xName, String yName, int width, int height){
        xAxis.setLabel(xName);
        yAxis.setLabel(yName);
        xAxis.setForceZeroInRange(false);
        yAxis.setForceZeroInRange(false);


        chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setMaxSize(width, height);
        chart.setMinSize(width, height);
        chart.setPrefSize(width, height);
    }

    /**
     * Add new (x, y) data to chart's DataSeries. If number of data exceeds RECORDS_ON_CHART value
     * the oldest data is removed.
     * @param xVal New xAxis value.
     * @param yVal New yAxis value.
     */
    public void updateData(int xVal, int yVal){
        dataSeries.getData().add(new XYChart.Data<>(xVal, yVal));
        dataSeries.getData().remove(0, dataSeries.getData().size()-RECORDS_ON_CHART);
        chart.getData().clear();
        chart.getData().add(dataSeries);
    }
    /**
     * Chart getter.
     */
    public LineChart<Number, Number> getLineChart(){
        return chart;
    }
}
