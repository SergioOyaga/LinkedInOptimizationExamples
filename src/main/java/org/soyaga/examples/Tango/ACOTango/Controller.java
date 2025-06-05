package org.soyaga.examples.Tango.ACOTango;

import org.soyaga.Initializer.ACOInitializer;
import org.soyaga.aco.Ant.EdgeSelector.RandomProportionalEdgeSelector;
import org.soyaga.aco.Ant.SimpleMemoryAnt;
import org.soyaga.aco.BuilderEvaluator.AllNodesCircleBuilderEvaluator;
import org.soyaga.aco.Solution;
import org.soyaga.aco.world.GenericWorld;
import org.soyaga.aco.world.Graph.Elements.Edge;
import org.soyaga.aco.world.Graph.Elements.Node;
import org.soyaga.aco.world.Graph.GenericGraph;
import org.soyaga.examples.Tango.ACOTango.ACO.Evaluable.TangoFeasibilityFunction;
import org.soyaga.examples.Tango.ACOTango.ACO.TangoACO;
import org.soyaga.examples.Tango.ACOTango.UI.UserInterface;
import org.soyaga.examples.Tango.ACOTango.UI.Buttons.SquareButton;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller implements PropertyChangeListener {
    /**
     * TangoACO with the optimization problem
     */
    private TangoACO aco;
    /**
     * ACOInitializer to use in the ACO
     */
    private ACOInitializer acoInitializer;
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
     * HashMap tha relates SquareButtons to Nodes
     */
    private HashMap<SquareButton, Node> squareButtonToNode;
    /**
     * HashMap tha relates Nodes to SquareButtons
     */
    private HashMap<Node, SquareButton> nodeToSquareButton;
    /**
     * World with the problem topology
     */
    private GenericWorld world;

    /**
     * Class that controls the interactions between frontend and backend.
     */
    public Controller() throws IOException {
        this.userInterface = new UserInterface();
        this.frontendThread = new Thread(this.userInterface);// Frontend Thread.
        this.subscribeToComponents();
    }

    /**
     * Function that subscribes the ui and the ga-model to the Controller
     */
    private void subscribeToComponents() {
        this.userInterface.addPropertyChangeListener(this);
    }

    /**
     * Function that starts the Thread for the frontend, the app.
     */
    public void startApp(){
        this.frontendThread.start();
    }

    /**
     * Function that initializes and starts the Thread for the GaModel.
     */
    private void startACO() throws IOException {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.initializeACOModel();
        this.aco = new TangoACO("TangoACO",this.world,this.acoInitializer);
        this.aco.addPropertyChangeListener(this);
        this.backendThread = new Thread(this.aco); // Backend Thread.
        this.backendThread.start();
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// Set the cursor to default.
    }

    /**
     * Function that sets the ga model initializer.
     */
    private void initializeACOModel(){
        this.computeWorld();
        ArrayList<Node> nodesToVisit = new ArrayList<>(this.nodeToSquareButton.keySet());
        this.acoInitializer = new ACOInitializer(
                new SimpleMemoryAnt(
                        new Solution(
                                null,                        //No objective
                                new TangoFeasibilityFunction(                 //Tango Feasibility function
                                        this.userInterface.getSelectedRowSize(),        //RowSize
                                        this.userInterface.getSelectedColSize(),        //ColSize
                                        this.userInterface.getFixedCells(),             //Fixed cells (SquareButtons)
                                        this.userInterface.getCellsWithRelations(),     // Cells with relations (SquareButtons)
                                        this.nodeToSquareButton                         // Relates the Node with the SquareButton.
                                ),
                                100.,                                         //Double with the infeasibility penalization.
                                80,                                        //Integer with the maximum number of edges an ant's solution can have.
                                new AllNodesCircleBuilderEvaluator(         //Solution evaluator
                                        nodesToVisit                        //Nodes to visit
                                )
                        ),
                        new RandomProportionalEdgeSelector(                //Edge selector How the ant selects the edge to take.
                                2.,                                        //Double with the ants' Alpha (>0) parameter (importance of the edges pheromones against the edges "distances").
                                0.                                         //Double with the ants' Beta (>0) parameter (importance of the edges "distances" against the edges pheromones).
                        ),                                                 //
                        100.                                               //Double with the amount of pheromone each ant can deposit in its track (same order of the problem optimal fitness).
                ),
                100                                                        //Integer with the initial number of ants.
        );
    }

    /**
     * Function that sets the world and button/Node maps.
     */
    private void computeWorld() {
        GenericGraph graph=new GenericGraph(1.);
        this.world = new GenericWorld(graph,graph);
        this.squareButtonToNode = new HashMap<>();
        this.nodeToSquareButton = new HashMap<>();
        for(SquareButton[] cellRow:this.userInterface.getCells()){
            for(SquareButton cell:cellRow){
                Node node = new Node(cell.getRow()+","+cell.getCol());
                this.squareButtonToNode.put(cell, node);
                this.nodeToSquareButton.put(node, cell);
                graph.addNode(node);
            }
        }
        Node sun = new Node("Sun");
        graph.addNode(sun);
        Node moon = new Node("Moon");
        graph.addNode(moon);
        SquareButton[][] cells = this.userInterface.getCells();
        for (SquareButton[] cellRow : cells) {
            for (SquareButton cell : cellRow) {
                Node node = this.squareButtonToNode.get(cell);
                if (cell.isManuallySelected()) {
                    if ("Sun".equals(cell.getIconType())) {
                        node.addEdge(sun, 1., 1.);
                    } else if ("Moon".equals(cell.getIconType())) {
                        node.addEdge(moon, 1., 1.);
                    }
                }
                else {
                    node.addEdge(sun, 1., 1.);
                    node.addEdge(moon, 1., 1.);
                }
                sun.addEdge(node,1., 1.);
                moon.addEdge(node,1., 1.);
            }
        }
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
            System.out.println("Started");
            try {
                this.startACO();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (evt.getPropertyName().equals("updatePheromone")) {
            ArrayList<Object> newValueList = (ArrayList<Object>) evt.getNewValue();
            HashMap<Node, HashMap<Node, Double>> pheromone = (HashMap<Node, HashMap<Node, Double>>) newValueList.get(2);
            HashMap<SquareButton, HashMap<Boolean, Double>> newValue = new HashMap<>();
            for(Map.Entry<Node, HashMap<Node, Double>> entryNode:pheromone.entrySet()){
                SquareButton buttonOrig = this.nodeToSquareButton.get(entryNode.getKey());
                if(buttonOrig != null) {
                    newValue.put(buttonOrig, new HashMap<>());
                    for (Map.Entry<Node, Double> entryValue : entryNode.getValue().entrySet()) {
                        Boolean nodeType = "Sun".equals(entryValue.getKey().getID());
                        newValue.get(buttonOrig).put(nodeType, entryValue.getValue());
                    }
                }
            }
            this.userInterface.addIteration((Integer) newValueList.get(0), newValue);
        }
        else if (evt.getPropertyName().equals("solutionFound")) {
            ArrayList<Object> solution = (ArrayList<Object>) evt.getNewValue(); // newValue = [iteration number, solution time, solution Grid]
            Boolean [][] boardValues = new Boolean[this.userInterface.getSelectedRowSize()][this.userInterface.getSelectedColSize()];
            ArrayList<Node> path = (ArrayList<Node>)solution.get(1);
            // Build arrays
            for(int i=0; i<path.size()-1; i+=2){
                Node origin = path.get(i);
                Node dest = path.get(i+1);
                if ("Sun".equals(dest.getID()) || "Moon".equals(dest.getID())) {
                    SquareButton originButton = this.nodeToSquareButton.get(origin);
                    boardValues[originButton.getRow()][originButton.getCol()] = "Sun".equals(dest.getID());
                }
            }
            this.userInterface.setSolution((Integer) solution.get(0), (Double) solution.get(2), boardValues);
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}