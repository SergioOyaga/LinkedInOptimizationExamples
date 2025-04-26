package org.soyaga.examples.Queens.GeneticQueens.UI.Panels;

import lombok.Getter;
import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.SquareButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.UserInterface;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;

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
     * @param size int with the size of the grid
     * @param controller controller to subscribe the buttons.
     */
    public void setGridButtons(int size, UserInterface controller){
        this.setLayout(new GridLayout(size, size,0,0));
        this.gridButtons = new SquareButton[size][size];
        int buttonSize = this.getWidth()/size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                SquareButton cell = new SquareButton(new Dimension(buttonSize, buttonSize), row, col, this.gridButtons);
                this.gridButtons[row][col] = cell;
                this.add(cell);
            }
        }
        this.subscribeToComponents(controller);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        if(this.gridButtons ==null) return;
        for(JButton[] buttonRow: this.gridButtons){
            for (JButton button: buttonRow){
                button.addMouseListener(controller);
            }
        }
    }

    /**
     * Function that sets a solution to the current grid.
     * @param solution ArrayList with the solution.
     */
    public void setSolution(ArrayList<Integer> solution){
        int row=0;
        for(SquareButton[] buttonsRow:this.gridButtons){
            for(SquareButton button: buttonsRow){
                button.setNoQueen();
            }
        }
        for(Integer col: solution){
            this.gridButtons[row][col].setQueen();
            row++;
        }
    }
}