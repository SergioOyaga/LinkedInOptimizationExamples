package org.soyaga.examples.Queens.GeneticQueens.UI.Panels;

import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.RoundedButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.UserInterface;

import javax.swing.*;

/**
 * Panel with the controls panels, buttons and palette.
 */
public class ControlPanel extends JSplitPane {
    /**
     * ButtonsPanel with the buttons.
     */
    private final ButtonsPanel buttonsPanel;
    /**
     * ColorsPanel with the palette
     */
    private final ColorsPanel colorsPanel;

    /**
     * Constructor
     */
    public ControlPanel(){
        this.buttonsPanel = new ButtonsPanel();
        this.colorsPanel = new ColorsPanel();
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.buttonsPanel.initComponent();
        this.colorsPanel.initComponent();
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setLeftComponent(this.buttonsPanel);
        this.setRightComponent(this.colorsPanel);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        this.buttonsPanel.subscribeToComponents(controller);
        this.colorsPanel.subscribeToComponents(controller);
    }

    /**
     * Function that forwards the set selected to its components
     * @param button RoundedButton to set as selected.
     */
    public void setSelected(RoundedButton button){
        this.colorsPanel.setSelected(button);
    }

    /**
     * Function that forwards the enablement of the run button and disablement of the NumberTextField.
     */
    public void enableRunButton() {
        this.buttonsPanel.enableRunButton();
    }
}