package org.soyaga.examples.Zip.GeneticZip;

import org.soyaga.examples.Zip.GeneticZip.GA.ZipGA;
import org.soyaga.examples.Zip.GeneticZip.GA.ZipGAInitializer;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;
import org.soyaga.examples.Zip.GeneticZip.UI.UserInterface;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {
    /**
     * ZipGA with the optimization problem
     */
    private final ZipGA geneticAlgorithm;
    /**
     * ZipGAInitializer with the problem GAModel initializer.
     */
    private final ZipGAInitializer zipGAInitializer;
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
        this.zipGAInitializer = new ZipGAInitializer();
        this.geneticAlgorithm = new ZipGA("ZipGA", this.zipGAInitializer, 100);
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
     * Function that initializes and starts the Thread for the GAModel.
     */
    private void startGA() {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.userInterface.clearStats();
        this.initializeGAModel();
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the ga model initializer.
     */
    private void initializeGAModel(){
        this.zipGAInitializer.setRowSize(this.userInterface.getSelectedRowSize());
        this.zipGAInitializer.setColSize(this.userInterface.getSelectedColSize());
        this.zipGAInitializer.setPriorityCells(this.userInterface.getPriorityCells());
        this.zipGAInitializer.setConnectedCellsByCell(this.userInterface.getCells());
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
            ArrayList<Object> solution = (ArrayList<Object>) evt.getNewValue(); // newValue = [iteration number, solution time, solution Array of SquareButtons]
            this.userInterface.setSolution(Double.valueOf((Integer) solution.get(0)), (Double) solution.get(2), (ArrayList<SquareButton>) solution.get(1));
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}