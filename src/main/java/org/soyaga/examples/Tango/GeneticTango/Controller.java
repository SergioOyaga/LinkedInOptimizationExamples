package org.soyaga.examples.Tango.GeneticTango;

import org.soyaga.examples.Tango.GeneticTango.UI.UserInterface;
import org.soyaga.examples.Tango.GeneticTango.GA.TangoGA;
import org.soyaga.examples.Tango.GeneticTango.GA.TangoGAInitializer;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {
    /**
     * TangoGA with the optimization problem
     */
    private final TangoGA geneticAlgorithm;
    /**
     * TangoGAInitializer with the problem MathModel initializer.
     */
    private final TangoGAInitializer tangoGAInitializer;
    /**
     * UI with the user interface.
     */
    private final UserInterface userInterface;
    /**
     * Frontend thread.
     */
    private final Thread frontendThread;
    /**
     * Backend thread.
     */
    private Thread backendThread;

    /**
     * Class that controls the interactions between frontend and backend.
     */
    public Controller() throws IOException {
        this.userInterface = new UserInterface();
        this.tangoGAInitializer = new TangoGAInitializer();
        this.geneticAlgorithm = new TangoGA("TangoGA", this.tangoGAInitializer, 1000);
        this.frontendThread = new Thread(this.userInterface);// Frontend Thread.
        this.backendThread = new Thread(this.geneticAlgorithm); // Backend Thread.
        this.subscribeToComponents();
    }

    /**
     * Function that subscribes the ui and the math-model to the Controller
     */
    private void subscribeToComponents() {
        this.userInterface.addPropertyChangeListener(this);
        this.geneticAlgorithm.addPropertyChangeListener(this);
    }

    /**
     * Function that starts the Thread for the frontend, the app.
     */
    public void startApp(){
        this.frontendThread.start();
    }

    /**
     * Function that initializes and starts the Thread for the MathModel.
     */
    private void startGA() {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.initializeMathModel();
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the math model initializer.
     */
    private void initializeMathModel(){
        this.tangoGAInitializer.setRowSize(this.userInterface.getSelectedRowSize());
        this.tangoGAInitializer.setColSize(this.userInterface.getSelectedColSize());
        this.tangoGAInitializer.setFixedCells(this.userInterface.getFixedCells());
        this.tangoGAInitializer.setFixedRelationCells(this.userInterface.getCellsWithRelations());
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("runButtonClick")){
            if(this.backendThread.getState() == Thread.State.TERMINATED) {
                System.out.println("terminated");
                this.backendThread = new Thread(this.geneticAlgorithm);
            }
            if(this.backendThread.getState() == Thread.State.NEW){
                System.out.println("Started");
                this.startGA();
            }
        }
        else if (evt.getPropertyName().equals("addBestFitness")) {
            ArrayList<Double> fitness = (ArrayList<Double>) evt.getNewValue();
            this.userInterface.addBestFitness(fitness.get(0), fitness.get(1), fitness.get(2), fitness.get(3));
        }
        else if (evt.getPropertyName().equals("solutionFound")) {
            ArrayList<Object> solution = (ArrayList<Object>) evt.getNewValue(); // newValue = [iteration number, solution time, solution Grid]
            this.userInterface.setSolution(Double.valueOf((Integer) solution.get(0)), (Double) solution.get(2), (Boolean[][]) solution.get(1));
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}