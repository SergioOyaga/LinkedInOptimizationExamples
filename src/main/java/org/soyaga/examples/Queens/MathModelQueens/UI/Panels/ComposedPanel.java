package org.soyaga.examples.Queens.MathModelQueens.UI.Panels;

import org.soyaga.examples.Queens.MathModelQueens.UI.Buttons.RoundedButton;
import org.soyaga.examples.Queens.MathModelQueens.UI.UserInterface;

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
     * Function that forwards the set selected to its components
     * @param button RoundedButton to set as selected.
     */
    public void setSelected(RoundedButton button){
        this.controlPanel.setSelected(button);
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
}