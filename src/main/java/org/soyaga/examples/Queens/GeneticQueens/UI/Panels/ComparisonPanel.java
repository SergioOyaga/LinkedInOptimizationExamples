package org.soyaga.examples.Queens.GeneticQueens.UI.Panels;

import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.SquareButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel composed of two panels, reference and optimization.
 */
public class ComparisonPanel extends JSplitPane {
    /**
     * ReferencePanel to show the LinkedInImages.
     */
    private final ReferencePanel referencePanel;
    /**
     * OptimizationPanel to show the optimization problem/results
     */
    private final OptimizationPanel optimizationPanel;

    /**
     * Constructor
     */
    public ComparisonPanel() {
        super();
        this.referencePanel = new ReferencePanel();
        this.optimizationPanel = new OptimizationPanel();
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.referencePanel.initComponent();
        this.optimizationPanel.initComponent();

        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(this.referencePanel);
        this.setRightComponent(this.optimizationPanel);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        this.optimizationPanel.subscribeToComponents(controller);
    }

    /**
     * Function that forwards the sets of the grid buttons
     * @param size int with the size of the grid
     * @param controller controller to subscribe the buttons.
     */
    public void setGridButtons(int size, UserInterface controller){
        this.optimizationPanel.setGridButtons(size, controller);
        this.optimizationPanel.revalidate();
        this.optimizationPanel.repaint();
    }

    /**
     * Function that sets the color of the selected cell to the selected color.
     * @param jButton SquareButton to change color.
     * @param selectedColor Color selected
     */
    public void setCellColor(SquareButton jButton, Color selectedColor) {
        jButton.setButtonColor(selectedColor);
        this.optimizationPanel.repaint();
    }

    /**
     * Function that forwards the set of a solution to the current grid.
     * @param solution ArrayList with the solution.
     */
    public void setSolution(ArrayList<Integer> solution){
        this.optimizationPanel.setSolution(solution);
        this.optimizationPanel.repaint();
    }

    /**
     * Function that returns the buttons.
     * @return SquareButton[][] with the buttons.
     */
    public SquareButton[][] getButtons() {
        return this.optimizationPanel.getGridButtons();
    }

    /**
     * Function that forwards the set of the image to the solution image.
     */
    public void setSolutionAsImage(){
        this.referencePanel.setSolutionAsImage();
        this.referencePanel.paintComponent(this.referencePanel.getGraphics());
    }
}