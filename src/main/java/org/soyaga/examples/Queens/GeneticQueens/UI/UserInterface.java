package org.soyaga.examples.Queens.GeneticQueens.UI;

import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.RoundedButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.SquareButton;
import org.soyaga.examples.Queens.GeneticQueens.UI.Panels.ComparisonPanel;
import org.soyaga.examples.Queens.GeneticQueens.UI.Panels.ComposedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * JFrame used as UI. Manage events coming from the UI components, and communicate info to the controller that communicates with the backend.
 * Implement Runnable, ActionListener and MouseListener
 */
public class UserInterface extends JFrame implements Runnable, ActionListener, MouseListener {
    /**
     * ComposedPanel with composed info.
     */
    private final ComposedPanel composedPanel;
    /**
     * ComparisonPanel with the comparison between the reference and the optimization.
     */
    private final ComparisonPanel comparisonPanel;
    /**
     * Color that contains the current selected color
     */
    private Color selecetedColor;
    /**
     * Boolean used to track when the mouse is painting
     */
    private boolean isPainting;

    /**
     * Creates a new, initially invisible, <code>Frame</code> with the
     * specified title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public UserInterface() throws HeadlessException {
        super("LinkedIn Queens Problem using Genetic Algorithms");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.composedPanel = new ComposedPanel();
        this.comparisonPanel = new ComparisonPanel();
        this.initComponent();
        this.subscribeToComponents();
        this.isPainting = false;
    }

    /**
     * Function that initializes the JFrame components.
     */
    private void initComponent() {
        this.composedPanel.initComponent();
        this.comparisonPanel.initComponent();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.setLayout(new BorderLayout());
        this.add(splitPane);
        splitPane.setTopComponent(this.composedPanel);
        splitPane.setBottomComponent(this.comparisonPanel);
        System.out.println();
    }

    /**
     * Function that subscribes all GUIWorld, its components and the front components to the UI class to provide one
     * communication point to the outside
     */
    private void subscribeToComponents() {
        this.composedPanel.subscribeToComponents(this);
        this.comparisonPanel.subscribeToComponents(this);
    }

    /**
     * Function that returns the buttons.
     * @return SquareButton[][] with the buttons.
     */
    public SquareButton[][] getCellColors(){
        return this.comparisonPanel.getButtons();
    }

    /**
     * Function that sets the solution to the UI.
     * @param iteration Double with the iteration where the solution gets found.
     * @param time Double with the time that the solution was found.
     * @param solution ArrayList&lt;Integer&gt; with the solution to be plotted in the optimization panel.
     */
    public void setSolution(Double iteration, Double time, ArrayList<Integer> solution){
        this.composedPanel.addSolutionFound(iteration, time);
        this.comparisonPanel.setSolution(solution);
        this.comparisonPanel.setSolutionAsImage();
    }

    /**
     * Function that adds the new values of the population and the best individual fitness.
     * @param bestIndividualFitness Double with the best individuals fitness
     * @param populationMeanFitness Double with the population mean fitness.
     * @param populationStandardDeviation Double with the population fitness standard deviation.
     */
    public void addBestFitness(double bestIndividualFitness, double populationMeanFitness, double populationStandardDeviation){
        this.composedPanel.addBestFitness(bestIndividualFitness, populationMeanFitness, populationStandardDeviation);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        this.setVisible(true);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param evt the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource() instanceof JButton jButton) {
            switch (evt.getActionCommand()) {
                case "Run" -> this.firePropertyChange("runButtonClick",null, null);
                case "SelectColor" ->{
                    this.composedPanel.setSelected((RoundedButton) jButton);
                    this.selecetedColor = ((RoundedButton) jButton).getButtonColor();
                }
                default -> System.out.println("JButton event not handled: " +jButton.getName());
            }
        }
        else if (evt.getSource() instanceof JFormattedTextField jFormatedTextField) {
            this.comparisonPanel.setGridButtons((Integer) jFormatedTextField.getValue(), this);
            this.composedPanel.enableRunButton();
        } else{
            System.out.println("Event not handled: " +((JButton)evt.getSource()).getName());
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if((e.getSource() instanceof SquareButton squareButton)) {
            this.isPainting = true;
            this.comparisonPanel.setCellColor(squareButton, this.selecetedColor);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.isPainting = false;
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(this.isPainting && (e.getSource() instanceof SquareButton squareButton)) {
            this.comparisonPanel.setCellColor(squareButton, this.selecetedColor);
        }
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}