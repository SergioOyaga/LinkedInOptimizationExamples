package org.soyaga.examples.Tango.GeneticTango.UI.Panels;

import org.soyaga.examples.Tango.GeneticTango.UI.UserInterface;

import javax.swing.*;

/**
 * Panel composed of two panels, control and stats.
 */
public class ComposedPanel extends JSplitPane {
    /**
     * ControlPanel with the control components
     */
    private final ControlPanel controlPanel;
    /**
     * StatsPanel with the stats info.
     */
    private final StatsPanel statsPanel;

    /**
     * Constructor
     */
    public ComposedPanel() {
        this.controlPanel = new ControlPanel();
        this.statsPanel = new StatsPanel();
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.controlPanel.initComponent();
        this.statsPanel.initComponent();
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(this.controlPanel);
        this.setRightComponent(this.statsPanel);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        this.controlPanel.subscribeToComponents(controller);
    }

    /**
     * Function that adds the new values of the population and the best individual fitness
     * @param iteration double with the iteration
     * @param bestIndividualFitness Double with the best individuals fitness
     * @param populationMeanFitness Double with the population mean fitness.
     * @param populationStandardDeviation Double with the population fitness standard deviation.
     */
    public void addBestFitness(double iteration, double bestIndividualFitness, double populationMeanFitness, double populationStandardDeviation){
        this.statsPanel.addBestFitness(iteration, bestIndividualFitness, populationMeanFitness, populationStandardDeviation);
    }

    /**
     * Function that adds a marker when the solution is found.
     * @param iteration double with the iteration when the solution is found.
     * @param time double with the time that the solution was found.
     */
    public void addSolutionFound(double iteration, double time){
        this.statsPanel.addSolutionFound(iteration, time);
    }

    /**
     * Function that forwards the enablement of the run button and disablement of the NumberTextField.
     */
    public void enableRunButton() {
        this.controlPanel.enableRunButton();
    }

    /**
     * Function that disables the row number selection text field
     */
    public void disableRowTextField(){
        this.controlPanel.disableRowTextField();
    }

    /**
     * Function that disables the column number selection text field
     */
    public void disableColTextField(){
        this.controlPanel.disableColTextField();
    }
}