package org.soyaga.examples.Queens.GeneticQueens.UI.Panels;

import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.RoundedButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel containing the Colors palette to select from.
 */
public class ColorsPanel extends JPanel {
    /**
     * ArrayList with the RoundedButton palette.
     */
    private ArrayList<RoundedButton> listOfColors;

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.listOfColors = new ArrayList<>(10);
        this.listOfColors.add(new RoundedButton(new Color(254,123,95)));
        this.listOfColors.add(new RoundedButton(new Color(179, 223, 160)));
        this.listOfColors.add(new RoundedButton(new Color(254, 202, 145)));
        this.listOfColors.add(new RoundedButton(new Color(150, 189, 254)));
        this.listOfColors.add(new RoundedButton(new Color(187,163,225)));
        this.listOfColors.add(new RoundedButton(new Color(233,160,191)));
        this.listOfColors.add(new RoundedButton(new Color(230, 243,137)));
        this.listOfColors.add(new RoundedButton(new Color(185, 178, 159)));
        this.listOfColors.add(new RoundedButton(new Color(223,223,223)));
        this.listOfColors.add(new RoundedButton(new Color(163, 210, 216)));
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 4, 5));
        for(RoundedButton button: this.listOfColors){
            this.add(button);
        }
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        for(RoundedButton button: this.listOfColors){
            button.addActionListener(controller);
        }
    }

    /**
     * Function that forwards the set selected to its components
     * @param button RoundedButton to set as selected.
     */
    public void setSelected(RoundedButton button){
        for(RoundedButton button1:this.listOfColors){
            button1.setSelected(false);
        }
        button.setSelected(true);
    }
}