/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficas;

import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author depot
 */
public class Graficador{
    
    private JFreeChart xyChart;
    private ChartPanel panel;
    private final XYSeries[] series_rgb;
    private final XYSeriesCollection dataSet;
    private final ArrayList<int[]> valores;
    private static final String[] series = new String[]{"RED","GREEN","BLUE"};

    public Graficador(int[] red, int[] green, int[] blue) {
        this.xyChart = null;
        this.panel = null;
        this.valores = new ArrayList<>();
        this.dataSet = new XYSeriesCollection();
        this.valores.add(red);
        this.valores.add(green);
        this.valores.add(blue);
        this.series_rgb = new XYSeries[3];
        for(int numSerie = 0; numSerie < 3; numSerie++){
            this.series_rgb[numSerie] = new XYSeries(Graficador.series[numSerie]);
        }
        initGraph();
    }
    
    public ChartPanel getChartPanel(){
        return this.panel;
    }
    
    private void initGraph(){
        addPoints();//Agregamos los valores de r, g y b, y creamos los XYSeries
        addToDataSet();//Agregamos las series al dataset
        createJFreeChart();//Creamos el JFreeChart
    }
    
    private void addToDataSet(){
        for(XYSeries serie : this.series_rgb){
            this.dataSet.addSeries(serie);
        }
    }
    
    private void addPoints(){
        for(int i=0;i<3;i++){
            int[] val = this.valores.get(i);
            XYSeries serieAux = this.series_rgb[i];
            for(int punto = 0; punto <= 255;punto++){
                serieAux.add(punto,val[punto]);
            }
        }
    }
    
    private void createJFreeChart(){
        this.xyChart = ChartFactory.createXYLineChart("Histograma",
                                                      "Colores",
                                                      "Cantidad",
                                                      this.dataSet,
                                                      PlotOrientation.VERTICAL,
                                                      true,true,false);
        createPlot();
        this.panel = new ChartPanel(this.xyChart);
    }
    
    private void createPlot(){
        XYPlot plot = this.xyChart.getXYPlot();
        XYLineAndShapeRenderer render = new XYLineAndShapeRenderer();
        render.setSeriesPaint(0,Color.RED);
        render.setSeriesPaint(1,Color.GREEN);
        render.setSeriesPaint(2,Color.BLUE);
        plot.setRenderer(render);
        
    }
    
    
}
