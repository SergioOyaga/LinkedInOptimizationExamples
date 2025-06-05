package org.soyaga.examples.Tango.ACOTango.UI.Panels;

import org.soyaga.examples.Tango.ACOTango.UI.UserInterface;
import org.soyaga.examples.Tango.ACOTango.UI.Buttons.SquareButton;

import javax.swing.*;
import java.util.HashMap;

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
     * Function that adds a new iteration to the pheromone image.
     * @param iteration Integer with the iteration.
     * @param pheromoneByButton hashmap with the pheromone by node
     */
    public void addIteration(Integer iteration, HashMap<SquareButton, HashMap<Boolean, Double>> pheromoneByButton){
        this.statsPanel.addIteration(iteration, pheromoneByButton);
    }

    /**
     * Function that sets the solution found time and iteration
     * @param iteration int with the iteration
     * @param time double with the time
     */
    public void addSolutionFound(int iteration, double time){
        this.statsPanel.setSolutionFound(iteration, time);
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

    /**
     * Function that initializes the grid on the stats panel.
     * @param selectedRowSize Integer with the number of rows
     * @param selectedColSize Integer with the number of columns
     * @param gridButtons SquareButton[][] with the grid.
     */
    public void setGridButtons(Integer selectedRowSize, Integer selectedColSize, SquareButton[][] gridButtons) {
        this.statsPanel.setNumberOfRows(selectedRowSize);
        this.statsPanel.setNumberOfCols(selectedColSize);
        this.statsPanel.setGridButtons(gridButtons);
    }
}