package org.soyaga.examples.Zip.MathModelZip.UI.Panels;

import org.soyaga.examples.Zip.MathModelZip.UI.UserInterface;

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
     * Function that forwards the set of the solution Text.
     * @param solutionText String with the solution text.
     */
    public void setSolutionText(String solutionText){
        this.statsPanel.setSolutionText(solutionText);
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