package alien;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 

public class PrettyGraph
{
    private XYSeries errorData;
    private XYSeriesCollection dataset;
    
    public PrettyGraph(ArrayList<Long> x_time, ArrayList<Double> y_epochError, String animal) {
        
        this.errorData = new XYSeries(animal);
        
        for (int i = 0; i < x_time.size(); i++) {           
            this.errorData.add((x_time.get(i)).doubleValue(), y_epochError.get(i));
        }
        
        this.dataset = new XYSeriesCollection();
        this.dataset.addSeries(this.errorData);
        
        
    }
    
    public void createGraph() {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Epoch error against time", 
                "Time (ms)",
                "Epoch error", 
                dataset,
                PlotOrientation.VERTICAL, 
                true, true, false);
        
        XYPlot plot = (XYPlot) xylineChart.getPlot(); 
        plot.getRenderer().setSeriesPaint(1, new Color(0x00, 0x00, 0xff));
        int width = 1150; /* Width of the image 640*/
        int height = 720; /* Height of the image 480 */ 
        File XYChart = new File( "XYLineChart.jpeg" ); 
        
        try {
            ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
        } catch (IOException e) {
            System.err.println("io exception.");
        }
    }
    
}