package org.soyaga.examples.Queens.GeneticQueens;

import org.soyaga.examples.Queens.GeneticQueens.UI.UserInterface;
import org.soyaga.examples.Queens.GeneticQueens.GA.QueensGAInitializer;
import org.soyaga.examples.Queens.GeneticQueens.GA.QueensGA;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {
    /**
     * QueensGA with the optimization problem
     */
    private final QueensGA geneticAlgorithm;
    /**
     * QueensGAInitializer with the problem GA initializer.
     */
    private final QueensGAInitializer queensGAInitializer;
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
     * @throws IOException Exception.
     */
    public Controller() throws IOException {
        this.userInterface = new UserInterface();
        this.queensGAInitializer = new QueensGAInitializer();
        this.geneticAlgorithm = new QueensGA("QueensGA", this.queensGAInitializer, 100);
        this.frontendThread = new Thread(this.userInterface);// Frontend Thread.
        this.backendThread = new Thread(this.geneticAlgorithm); // Backend Thread.
        this.subscribeToComponents();
    }

    /**
     * Function that subscribes the ui and the ga-model to the Controller
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
     * Function that initializes and starts the Thread for the ACO.
     */
    private void startGA() {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.initializeGA();
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the ga model initializer.
     */
    private void initializeGA(){
        this.queensGAInitializer.setColorCells(this.userInterface.getCellColors());
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
            this.userInterface.addBestFitness(fitness.get(0), fitness.get(1), fitness.get(2));
        }
        else if (evt.getPropertyName().equals("solutionFound")) {
            ArrayList<Object> solution = (ArrayList<Object>) evt.getNewValue(); // newValue = [iteration number, solution time, solution Array]
            this.userInterface.setSolution(Double.valueOf((Integer) solution.get(0)), (Double) solution.get(2), (ArrayList<Integer>) solution.get(1));
        } else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}