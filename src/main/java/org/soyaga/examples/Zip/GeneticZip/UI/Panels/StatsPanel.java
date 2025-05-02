package org.soyaga.examples.Zip.GeneticZip.UI.Panels;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * JPanel with the stats from the optimization.
 */
public class StatsPanel extends JPanel {
    /**
     * BufferedImage that with the default image.
     */
    private BufferedImage defaultImage;
    /**
     * YIntervalSeries that stores the population data.
     */
    private final YIntervalSeries populationSeries;
    /**
     * YIntervalSeries that stores the individual data.
     */
    private final YIntervalSeries individualSeries;
    /**
     * YIntervalDataset containing both series.
     */
    private final YIntervalSeriesCollection fitnessDataset;
    /**
     * JFreeChart to plot
     */
    private final JFreeChart bestIndividualFitnessChart;
    /**
     * Renderer of the plot.
     */
    private final DeviationRenderer fitnessRenderer;
    /**
     * ChartPanel instance that plots the chart.
     */
    private final ChartPanel chartPanel;
    /**
     * XYTextAnnotation with the solution time.
     */
    private XYTextAnnotation annotation;
    /**
     * Double with the max fitness
     */
    private Double maxFitness;

    /**
     * Constructor
     */
    public StatsPanel() {
        super();
        try {
            this.defaultImage = ImageIO.read(Objects.requireNonNull(org.soyaga.examples.Queens.GeneticQueens.UI.Panels.StatsPanel.class.getResource("/DefaultImages/Default_GA_Image.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.setPreferredSize(new Dimension(200,200));
        this.populationSeries = new YIntervalSeries("Population");
        this.individualSeries = new YIntervalSeries("BestIndividual");
        this.fitnessDataset = new YIntervalSeriesCollection();
        this.bestIndividualFitnessChart = ChartFactory.createXYLineChart(
                null,                  // chart title
                null,                       // x axis label
                "Fitness",                  // y axis label
                this.fitnessDataset,        // data
                PlotOrientation.VERTICAL,   // orientation
                true,                       // include legend
                false,                      // tooltips
                false                       // urls
        );
        this.fitnessRenderer = getDeviationRenderer();
        this.chartPanel = new ChartPanel(this.bestIndividualFitnessChart);
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        //initialize components
        customizeXYPlot();
        customizeChart();

        //Set layout and add components
        this.setLayout(new BorderLayout());
        this.chartPanel.setVisible(false);
        this.add(this.chartPanel, BorderLayout.CENTER);
        this.fitnessDataset.addSeries(this.populationSeries);
        this.fitnessDataset.addSeries(this.individualSeries);
        ((XYPlot)this.bestIndividualFitnessChart.getPlot()).setRenderer(this.fitnessRenderer);

        //JPanel properties
    }

    /**
     * Function that customizes the XYPlot
     */
    private void customizeXYPlot(){
        XYPlot plot = (XYPlot) this.bestIndividualFitnessChart.getPlot();
        plot.setBackgroundPaint(new Color(50,50,50));
        plot.setDomainGridlinePaint(new Color(200,200,200,100));
        plot.setRangeGridlinePaint(new Color(200,200,200, 100));
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setLowerMargin(0);
        xAxis.setUpperMargin(0.02);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    /**
     * Function that customizes the JFreeChart
     */
    private void customizeChart(){
        this.bestIndividualFitnessChart.setBackgroundPaint(new Color(0,0,0,0));
        this.bestIndividualFitnessChart.getLegend().setBackgroundPaint(new Color(0,0,0,0));
    }

    /**
     * Function that builds the Deviation renderer.
     * @return DeviationRenderer customized.
     */
    private DeviationRenderer getDeviationRenderer() {
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        //Properties for series 1 (Population series)
        renderer.setSeriesStroke(0, new BasicStroke(3.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND), false);
        renderer.setSeriesPaint(0, new Color(255,255,255), false);
        renderer.setSeriesFillPaint(0, new Color(255, 255, 255,100), false);
        //Properties for series 2 (Individual series)
        renderer.setSeriesStroke(1, new BasicStroke(3.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND), false);
        renderer.setSeriesPaint(1, new Color(0,255,0), false);
        renderer.setSeriesFillPaint(1, new Color(0, 0, 0,0), false);
        return renderer;
    }

    /**
     * Function that adds the new values of the population and the best individual fitness
     * @param iteration double with the iteration
     * @param bestIndividualFitness Double with the best individuals fitness
     * @param populationMeanFitness Double with the population mean fitness.
     * @param populationStandardDeviation Double with the population fitness standard deviation.
     */
    public void addBestFitness(double iteration, double bestIndividualFitness, double populationMeanFitness, double populationStandardDeviation){
        this.populationSeries.add(iteration, populationMeanFitness,
                populationMeanFitness-populationStandardDeviation,
                populationMeanFitness+populationStandardDeviation);
        this.individualSeries.add(iteration, bestIndividualFitness,
                bestIndividualFitness,bestIndividualFitness);
        if (this.maxFitness == null) {
            this.maxFitness = bestIndividualFitness;
        } else {
            this.maxFitness = Math.max(this.maxFitness, bestIndividualFitness);
            this.maxFitness = Math.max(this.maxFitness, populationMeanFitness);
            this.maxFitness = Math.max(this.maxFitness, populationMeanFitness+populationStandardDeviation);
        }
        if(this.annotation !=null) {
            this.annotation.setX(iteration*0.8);
            this.annotation.setY(this.maxFitness);
        }
        this.repaint();
    }

    /**
     * Function that adds a marker when the solution is found.
     * @param iteration double with the iteration when the solution is found.
     * @param time double with the time that the solution was found.
     */
    public void addSolutionFound(double iteration, double time){
        time = ((int)(time*100.))/100.;
        ValueMarker marker = new ValueMarker(iteration);
        marker.setStroke(new BasicStroke(4));
        marker.setPaint(Color.RED);
        marker.setLabelFont(this.getFont().deriveFont(16.F));
        marker.setLabelOffset(new RectangleInsets(16., 0., 0., 0.));
        marker.setLabelPaint(Color.WHITE);
        marker.setLabel("Solution found");
        ((XYPlot) this.bestIndividualFitnessChart.getPlot()).addDomainMarker(marker);
        this.annotation = new XYTextAnnotation("Solution found in: " + time + " s", 0, 0);
        this.annotation.setPaint(Color.GREEN);
        this.annotation.setFont(this.getFont().deriveFont(16.F));
        ((XYPlot) this.bestIndividualFitnessChart.getPlot()).addAnnotation(annotation);
    }

    /**
     * Function call in the graphical context to display the component.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        if(this.populationSeries.getItemCount()>0 | this.individualSeries.getItemCount()>0) {
        this.chartPanel.setVisible(true);
            String text = "Solution found in: " + " s";
            Font bigFont = g2.getFont().deriveFont((float) (this.getWidth() / 100.));
            g2.setFont(bigFont);
            FontMetrics metrics = g2.getFontMetrics(bigFont);
            int textHeight = metrics.getAscent() / 2;
            int textWidth = metrics.stringWidth(text);
            g2.drawString(text,this.getWidth()- textWidth - textHeight, textHeight*2);
        }else{
            this.chartPanel.setVisible(false);
            g2.drawImage(this.defaultImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, this); // Draw the image
        }
    }
}