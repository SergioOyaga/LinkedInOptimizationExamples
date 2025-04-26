package org.soyaga.examples.Tango.GeneticTango.UI.Panels;

import org.soyaga.examples.Tango.GeneticTango.UI.UserInterface;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Panel with the controls panels, buttons and palette.
 */
public class ControlPanel extends JPanel {
    /**
     * JLabel with the Number of rows
     */
    private final JLabel rowNumberLabel;
    /**
     * JTextField with the number of rows.
     */
    private final JTextField rowNumberTextField;
    /**
     * JLabel with the Number of columns
     */
    private final JLabel colNumberLabel;
    /**
     * JTextField with the number of columns.
     */
    private final JTextField colNumberTextField;
    /**
     * JButton to trigger the run of the optimization
     */
    private final JButton runButton;

    /**
     * Constructor
     */
    public ControlPanel(){
        this.rowNumberLabel = new JLabel("Number Of rows: ");
        this.colNumberLabel = new JLabel("Number Of columns: ");
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
        this.rowNumberTextField = new JFormattedTextField(formatter);
        this.colNumberTextField = new JFormattedTextField(formatter);
        this.runButton = new JButton("Run");
        this.setBorder(BorderFactory.createBevelBorder(1));
    }

    /**
     * Function that initializes the JPanel components.
     */
    public void initComponent() {
        this.rowNumberTextField.setPreferredSize(new Dimension(30,20));
        this.rowNumberTextField.setName("RowTextField");
        this.colNumberTextField.setPreferredSize(new Dimension(30,20));
        this.colNumberTextField.setName("ColTextField");
        this.runButton.setEnabled(false);

        this.setLayout(new BorderLayout(7,4));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 7, 4));
        leftPanel.add(this.rowNumberLabel);
        leftPanel.add(this.rowNumberTextField);
        leftPanel.add(this.colNumberLabel);
        leftPanel.add(this.colNumberTextField);
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 7, 4));
        rightPanel.add(this.runButton);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Function that subscribes all its components and the front components to the UserInterface class to provide one
     * communication point to the outside
     */
    public void subscribeToComponents(UserInterface controller) {
        this.runButton.addActionListener(controller);
        this.rowNumberTextField.addActionListener(controller);
        this.colNumberTextField.addActionListener(controller);
    }

    /**
     * Function that enables the run button and disables the NumberTextField.
     */
    public void enableRunButton(){
        this.runButton.setEnabled(true);
    }

    /**
     * Function that disables the row number selection text field
     */
    public void disableRowTextField(){
        this.rowNumberTextField.setEnabled(false);
    }

    /**
     * Function that disables the column number selection text field
     */
    public void disableColTextField(){
        this.colNumberTextField.setEnabled(false);
    }
}