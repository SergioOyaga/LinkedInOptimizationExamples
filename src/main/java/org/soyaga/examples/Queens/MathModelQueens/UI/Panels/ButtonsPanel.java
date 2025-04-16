package org.soyaga.examples.Queens.MathModelQueens.UI.Panels;

import org.soyaga.examples.Queens.MathModelQueens.UI.UserInterface;

import javax.swing.*;
import javax.swing.text.*;
import java.text.*;
import java.awt.*;

/**
 * JPanel that contains a grid with the optimized, or to be optimized scenario.
 */
public class ButtonsPanel extends JPanel {
    /**
     * JLabel with the Number of queens
     */
    private final JLabel queensNumberLabel;
    /**
     * JTextField with the number of queens.
     */
    private final JTextField queensNumberTextField;
    /**
     * JButton to trigger the run of the optimization
     */
    private final JButton runButton;

    /**
     * Constructor
     */
    public ButtonsPanel(){
        this.queensNumberLabel = new JLabel("Number Of Queens: ");
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance()) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                return super.stringToValue(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value == null) return "";
                return super.valueToString(value);
            }
        };
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false); // prevents typing non-numeric
        formatter.setMinimum(0);           // optional: set bounds
        formatter.setMaximum(100);
        this.queensNumberTextField = new JFormattedTextField(formatter);
        this.runButton = new JButton("Run");
        this.setBorder(BorderFactory.createBevelBorder(1));
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.queensNumberTextField.setPreferredSize(new Dimension(30,20));
        this.runButton.setEnabled(false);

        this.setLayout(new BorderLayout(7,4));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 7, 4));
        leftPanel.add(this.queensNumberLabel);
        leftPanel.add(this.queensNumberTextField);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(this.runButton, BorderLayout.EAST);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        this.runButton.addActionListener(controller);
        this.queensNumberTextField.addActionListener(controller);
    }

    /**
     * Function that enables the run button and disables the NumberTextField.
     */
    public void enableRunButton(){
        this.runButton.setEnabled(true);
        this.queensNumberTextField.setEnabled(false);
    }
}