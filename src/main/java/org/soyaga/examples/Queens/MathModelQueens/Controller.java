package org.soyaga.examples.Queens.MathModelQueens;

import org.soyaga.examples.Queens.MathModelQueens.MathModel.QueensMMInitializer;
import org.soyaga.examples.Queens.MathModelQueens.MathModel.QueensMathModel;
import org.soyaga.examples.Queens.MathModelQueens.UI.UserInterface;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements PropertyChangeListener {
    /**
     * QueensMathModel with the optimization problem
     */
    private final QueensMathModel mathModel;
    /**
     * QueensMMInitializer with the problem MathModel initializer.
     */
    private final QueensMMInitializer queensMMInitializer;
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
        this.queensMMInitializer = new QueensMMInitializer();
        this.mathModel = new QueensMathModel("QueensMathModel", this.queensMMInitializer);
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
     * Function that initializes and starts the Thread for the ACO.
     */
    private void startMathModel() {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.initializeMathModel();
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the math model initializer.
     */
    private void initializeMathModel(){
        this.queensMMInitializer.setBoardColors(this.userInterface.getCellColors());
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
        } else if (evt.getPropertyName().equals("optimizationFinalized")) {
            this.userInterface.setSolution((String) evt.getOldValue(), (ArrayList<Integer>) evt.getNewValue());

        } else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}
