package org.soyaga.examples.Tango.ACOTango.UI.Panels;

import lombok.Getter;
import org.soyaga.examples.Tango.ACOTango.UI.Buttons.SquareButton;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Panel where to set the problem, and the solution for/from the optimization.
 */
@Getter
public class OptimizationPanel extends JPanel {
    /**
     * Array composed of Arrays of SquareButton with the buttons
     */
    private SquareButton[][] gridButtons;

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {

        TitledBorder title = BorderFactory.createTitledBorder(
                new EmptyBorder(20, 20, 20, 20), "Optimization");
        title.setTitlePosition(TitledBorder.TOP);
        Border lineBorder =new LineBorder(Color.BLACK, 7, true); // Your existing line border
        this.setBorder(new CompoundBorder(title, lineBorder));
    }

    /**
     * Function that sets the grid of buttons
     * @param colSize int with the col size of the grid
     * @param rowSize int with the row size of the grid
     */
    public void setGridButtons(int colSize, int rowSize){
        this.setLayout(new GridLayout(colSize, rowSize,0,0));
        this.gridButtons = new SquareButton[colSize][rowSize];
        int buttonWidth = this.getWidth()/colSize;
        int buttonHeight = this.getHeight()/rowSize;
        for (int col = 0; col < colSize; col++) {
            for (int row = 0; row < rowSize; row++) {
                SquareButton cell = new SquareButton(new Dimension(buttonWidth, buttonHeight), col, row, this.gridButtons);
                this.gridButtons[col][row] = cell;
                this.add(cell);
            }
        }
    }

    /**
     * Function that sets a solution to the current grid.
     * @param solution ArrayList with the solution.
     */
    public void setSolution(Boolean[][] solution){
        for(SquareButton[] buttonsRow:this.gridButtons){
            for(SquareButton button: buttonsRow){
                if(!button.isManuallySelected()){
                    button.setIconType(solution[button.getRow()][button.getCol()]? "Sun": "Moon");
                } else if (solution[button.getRow()][button.getCol()]) {
                    button.setIconType("Sun");
                }else {
                    button.setIconType("Moon");
                }
            }
        }
    }
}