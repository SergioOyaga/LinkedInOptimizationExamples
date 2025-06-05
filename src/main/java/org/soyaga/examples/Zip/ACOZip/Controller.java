package org.soyaga.examples.Zip.ACOZip;

import org.soyaga.Initializer.ACOInitializer;
import org.soyaga.aco.Ant.EdgeSelector.RandomProportionalEdgeSelector;
import org.soyaga.aco.Ant.PheromoneEaterMemoryAnt;
import org.soyaga.aco.Ant.SimpleAnt;
import org.soyaga.aco.Ant.SimpleMemoryAnt;
import org.soyaga.aco.BuilderEvaluator.AllNodesLineBuilderEvaluator;
import org.soyaga.aco.Solution;
import org.soyaga.aco.world.GenericWorld;
import org.soyaga.aco.world.Graph.Elements.Node;
import org.soyaga.aco.world.Graph.GenericGraph;
import org.soyaga.examples.Zip.ACOZip.ACO.Evaluable.ZipFeasibilityFunction;
import org.soyaga.examples.Zip.ACOZip.ACO.ZipACO;
import org.soyaga.examples.Zip.ACOZip.UI.Buttons.SquareButton;
import org.soyaga.examples.Zip.ACOZip.UI.UserInterface;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller implements PropertyChangeListener {
    /**
     * ZipACO with the optimization problem
     */
    private ZipACO aco;
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
     * Function that initializes and starts the Thread for the ACOModel.
     */
    private void startACO() throws IOException {
        this.userInterface.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //Set the cursor to wait as the update of the world might take a while.
        this.initializeACOModel();
        this.aco = new ZipACO("ZipACO",this.world,this.acoInitializer);
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
        Node startNode = this.squareButtonToNode.get(this.userInterface.getPriorityCells().get(0));
        Node endNode = this.squareButtonToNode.get(this.userInterface.getPriorityCells().get(this.userInterface.getPriorityCells().size()-1));
        ArrayList<Node> priorityNodes = new ArrayList<>();
        HashMap<Node, Integer> priorityByNode = new HashMap<>();
        int i=0;
        for(SquareButton button:this.userInterface.getPriorityCells()){
            Node node = this.squareButtonToNode.get(button);
            priorityNodes.add(node);
            priorityByNode.put(node, i);
            i++;
        }
        ArrayList<Node> nodesToVisit = new ArrayList<>(this.nodeToSquareButton.keySet());
        this.acoInitializer = new ACOInitializer(
                new SimpleMemoryAnt(
                        new Solution(
                                null,                        //No objective
                                new ZipFeasibilityFunction(                 //Zip Feasibility function
                                        this.userInterface.getSelectedRowSize(),        //RowSize
                                        this.userInterface.getSelectedColSize(),        //ColSize
                                        priorityNodes,                                  //PriorityNodes
                                        priorityByNode                                  //PriorityByNode
                                ),
                                10.,                                         //Double with the infeasibility penalization.
                                200,                                        //Integer with the maximum number of edges an ant's solution can have.
                                new AllNodesLineBuilderEvaluator(           //Solution evaluator
                                        startNode,                          //Start Node
                                        endNode,                            //End Node
                                        nodesToVisit                        //Nodes to visit
                                )
                        ),
                        new RandomProportionalEdgeSelector(                //Edge selector How the ant selects the edge to take.
                                2.,                                        //Double with the ants' Alpha (>0) parameter (importance of the edges pheromones against the edges "distances").
                                0.                                         //Double with the ants' Beta (>0) parameter (importance of the edges "distances" against the edges pheromones).
                        ),                                                 //
                        100.                                               //Double with the amount of pheromone each ant can deposit in its track (same order of the problem optimal fitness).
                ),
                50                                                         //Integer with the initial number of ants.
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
        SquareButton[][] cells = this.userInterface.getCells();
        for(int i = 0; i < cells.length;i++){
            SquareButton[] cellRow = cells[i];
            for(int j = 0; j < cells.length; j++){
                SquareButton cell = cellRow[j];
                Node node = this.squareButtonToNode.get(cell);
                if (i > 0 && !cell.getNorthFrontier()){
                    node.addEdge(this.squareButtonToNode.get(cells[i-1][j]), 1., 1.);
                }
                if (i < cells.length-1 && !cell.getSouthFrontier()){
                    node.addEdge(this.squareButtonToNode.get(cells[i+1][j]), 1., 1.);
                }
                if (j > 0 && !cell.getWestFrontier()){
                    node.addEdge(this.squareButtonToNode.get(cells[i][j-1]), 1., 1.);
                }
                if (j < cellRow.length-1 && !cell.getEastFrontier()){
                    node.addEdge(this.squareButtonToNode.get(cells[i][j+1]), 1., 1.);
                }
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
            HashMap<SquareButton, HashMap<SquareButton, Double>> newValue = new HashMap<>();
            for(Map.Entry<Node, HashMap<Node, Double>> entryNode:pheromone.entrySet()){
                SquareButton buttonOrig = this.nodeToSquareButton.get(entryNode.getKey());
                newValue.put(buttonOrig, new HashMap<>());
                for(Map.Entry<Node, Double> entryValue: entryNode.getValue().entrySet()){
                    SquareButton buttonDest = this.nodeToSquareButton.get(entryValue.getKey());
                    newValue.get(buttonOrig).put(buttonDest, entryValue.getValue());
                }
            }
            this.userInterface.addIteration((Integer) newValueList.get(0), newValue);
        }
        else if (evt.getPropertyName().equals("solutionFound")) {
            ArrayList<Object> newValue = (ArrayList<Object>) evt.getNewValue();
            ArrayList<Node> solution = (ArrayList<Node>) newValue.get(1); // newValue = [iteration number, solution Array of Node, solution time]
            ArrayList<SquareButton> solutionList = new ArrayList<>();
            for(Node node:solution){
                solutionList.add(this.nodeToSquareButton.get(node));
            }
            this.userInterface.setSolution(Integer.valueOf((Integer) newValue.get(0)), (Double) newValue.get(2),  solutionList);
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the Controller");
        }
    }
}