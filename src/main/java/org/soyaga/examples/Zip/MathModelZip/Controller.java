package org.soyaga.examples.Zip.MathModelZip;

import org.soyaga.examples.Zip.MathModelZip.MathModel.ZipMMInitializer;
import org.soyaga.examples.Zip.MathModelZip.MathModel.ZipMathModel;
import org.soyaga.examples.Zip.MathModelZip.UI.UserInterface;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controller implements PropertyChangeListener {
    /**
     * ZipMathModel with the optimization problem
     */
    private final ZipMathModel mathModel;
    /**
     * ZipMMInitializer with the problem MathModel initializer.
     */
    private final ZipMMInitializer tangoMMInitializer;
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
    public Controller() {
        this.userInterface = new UserInterface();
        this.tangoMMInitializer = new ZipMMInitializer();
        this.mathModel = new ZipMathModel("ZipMathModel", this.tangoMMInitializer);
        this.frontendThread = new Thread(this.userInterface);// Frontend Thread.
        this.backendThread = new Thread(this.mathModel); // Backend Thread.
        this.subscribeToComponents();
    }

    /**
     * Function that subscribes the ui and the math-model to the Controller
     */
    private void subscribeToComponents() {
        this.userInterface.addPropertyChangeListener(this);
        this.mathModel.addPropertyChangeListener(this);
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
    private void startMathModel() {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.mathModel.clear();
        this.initializeMathModel();
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the math model initializer.
     */
    private void initializeMathModel(){
        this.tangoMMInitializer.setRowSize(this.userInterface.getSelectedRowSize());
        this.tangoMMInitializer.setColSize(this.userInterface.getSelectedColSize());
        this.tangoMMInitializer.setBig(this.userInterface.getSelectedRowSize()*this.userInterface.getSelectedColSize());
        this.tangoMMInitializer.setPriorityCells(this.userInterface.getPriorityCells());
        this.tangoMMInitializer.setCells(this.userInterface.getCells());
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
                this.backendThread = new Thread(this.mathModel);
            }
            if(this.backendThread.getState() == Thread.State.NEW){
                System.out.println("Started");
                this.startMathModel();
            }
        }
        else if (evt.getPropertyName().equals("optimizationFinalized")) {
            this.userInterface.setSolution((String) evt.getOldValue(), (Integer[][]) evt.getNewValue());
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}