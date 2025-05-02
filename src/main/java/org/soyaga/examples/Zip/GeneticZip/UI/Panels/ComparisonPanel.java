package org.soyaga.examples.Zip.GeneticZip.UI.Panels;

import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;

import javax.swing.*;
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
     * Function that forwards the sets of the grid buttons
     * @param colSize int with the col size of the grid
     * @param rowSize int with the row size of the grid
     */
    public void setGridButtons(int rowSize, int colSize){
        this.optimizationPanel.setGridButtons(rowSize, colSize);
        this.optimizationPanel.revalidate();
        this.optimizationPanel.repaint();
    }

    /**
     * Function that forwards the set of a solution to the current grid.
     * @param solution Boolean[][] with the solution.
     */
    public void setSolution(ArrayList<SquareButton> solution){
        this.optimizationPanel.setSolution(solution);
        this.optimizationPanel.repaint();
    }

    /**
     * Function that returns the buttons.
     * @return ArrayList<SquareButton> with the buttons.
     */
    public ArrayList<SquareButton> getButtons() {
        return this.optimizationPanel.getSolutionArray();
    }

    /**
     * Function that returns the buttons.
     * @return ArrayList<SquareButton> with the buttons.
     */
    public SquareButton[][] getGrid() {
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