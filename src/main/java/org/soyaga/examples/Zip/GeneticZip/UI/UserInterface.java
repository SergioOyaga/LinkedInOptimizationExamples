package org.soyaga.examples.Zip.GeneticZip.UI;

import lombok.Getter;
import org.soyaga.examples.Zip.GeneticZip.UI.Panels.ComparisonPanel;
import org.soyaga.examples.Zip.GeneticZip.UI.Panels.ComposedPanel;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * JFrame used as UI. Manage events coming from the UI components, and communicate info to the controller that communicates with the backend.
 * Implement Runnable, ActionListener and MouseListener
 */
public class UserInterface extends JFrame implements Runnable, ActionListener {
    /**
     * ComposedPanel with composed info.
     */
    private final ComposedPanel composedPanel;
    /**
     * ComparisonPanel with the comparison between the reference and the optimization.
     */
    private final ComparisonPanel comparisonPanel;
    /**
     * Integer that contains the selected number of columns.
     */
    @Getter
    private Integer selectedColSize;
    /**
     * Integer that contains the selected number of rows.
     */
    @Getter
    private Integer selectedRowSize;

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
        super("LinkedIn Zip Problem using Genetic Algorithm");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.composedPanel = new ComposedPanel();
        this.comparisonPanel = new ComparisonPanel();
        this.initComponent();
        this.subscribeToComponents();
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
    }

    /**
     * Function that returns the priority cells sorted.
     * @return HashSet&lt;SquareButton&gt; with the priority cells sorted.
     */
    public ArrayList<SquareButton> getPriorityCells(){
        ArrayList<SquareButton> fixedButtons = new ArrayList<>();
        for(SquareButton button:this.comparisonPanel.getButtons()){
            if(button.getIconNumber() !=null){
                fixedButtons.add(button);
            }
        }
        fixedButtons.sort(Comparator.comparing(SquareButton::getIconNumber));
        return fixedButtons;
    }

    /**
     * Function that returns the buttons that contains relations with other cells.
     * @return HashSet&lt;SquareButton&gt; with the cells with relations.
     */
    public SquareButton[][] getCells(){
        return comparisonPanel.getGrid();
    }

    /**
     * Function that sets the solution to the UI.
     * @param iteration Double with the iteration where the solution gets found.
     * @param time Double with the time that the solution was found.
     * @param solution Boolean[][]; with the solution to be plotted in the optimization panel.
     */
    public void setSolution(Double iteration, Double time, ArrayList<SquareButton> solution){
        this.composedPanel.addSolutionFound(iteration, time);
        this.comparisonPanel.setSolution(solution);
        this.comparisonPanel.setSolutionAsImage();
    }

    /**
     * Function that adds the new values of the population and the best individual fitness.
     * @param iteration double with the iteration
     * @param bestIndividualFitness Double with the best individuals fitness
     * @param populationMeanFitness Double with the population mean fitness.
     * @param populationStandardDeviation Double with the population fitness standard deviation.
     */
    public void addBestFitness(double iteration, double bestIndividualFitness, double populationMeanFitness, double populationStandardDeviation){
        this.composedPanel.addBestFitness(iteration, bestIndividualFitness, populationMeanFitness, populationStandardDeviation);
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
            if (evt.getActionCommand().equals("Run")) {
                this.firePropertyChange("runButtonClick", null, null);
            } else {
                System.out.println("JButton event not handled: " + jButton.getName());
            }
        }
        else if (evt.getSource() instanceof JFormattedTextField jFormatedTextField) {
            if("RowTextField".equals(jFormatedTextField.getName())){//
                this.selectedRowSize = (Integer) jFormatedTextField.getValue();
                this.composedPanel.disableRowTextField();
            }
            if ("ColTextField".equals(jFormatedTextField.getName())) {
                this.selectedColSize = (Integer) jFormatedTextField.getValue();
                this.composedPanel.disableColTextField();
            }
            if(this.selectedColSize != null && this.selectedRowSize != null) {
                this.comparisonPanel.setGridButtons(this.selectedRowSize, this.selectedColSize);
                this.composedPanel.enableRunButton();
            }
        } else{
            System.out.println("Event not handled: " +((JButton)evt.getSource()).getName());
        }
    }

    /**
     * Function that clears stats panel
     */
    public void clearStats() {
        this.composedPanel.clearStats();
    }
}