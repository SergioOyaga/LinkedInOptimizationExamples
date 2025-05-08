package org.soyaga.examples.Zip.ACOZip.ACO.Stats;

import org.soyaga.aco.Colony;
import org.soyaga.aco.world.Graph.Elements.Edge;
import org.soyaga.aco.world.Graph.Elements.Node;
import org.soyaga.aco.world.Graph.Graph;
import org.soyaga.aco.world.PheromoneContainer.PheromoneContainer;
import org.soyaga.aco.world.World;
import org.soyaga.examples.Zip.ACOZip.ACO.ZipACO;
import org.soyaga.aco.StatsRetrievalPolicy.Stat.Stat;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UpdatePheromoneStat implements Stat {
    /**
     * ArrayList{@literal <String>} with the different column headers computed by this Stat.
     */
    private final ArrayList<String> header;
    /**
     * PropertyChangeSupport object to fire events when properties of this class change.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Double with the best found fitness value.
     */
    private Double historicalMinFitness;

    /**
     * Constructor.
     */
    public UpdatePheromoneStat() {
        this.header = new ArrayList<>(){{add("FitnessUpdate");}};
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Function that subscribes to the controller
     * communication point to the outside
     */
    public void subscribeToComponents(ZipACO controller) {
        this.pcs.addPropertyChangeListener(controller);
    }

    /**
     * ArrayList of strings that compose the header for this stat.
     *
     * @return ArrayList{@literal <String>} with the column names.
     */
    @Override
    public ArrayList<String> getHeader() {
        return this.header;
    }

    /**
     * ArrayList of strings that compose the values for this stat.
     *
     * @param world    world used to measure the stats.
     * @param colony   Colony used to measure the stats.
     * @param statArgs VarArgs containing the additional information needed to apply the stat.
     * @return ArrayList{@literal <String>} with the column names.
     */
    @Override
    public ArrayList<String> apply(World world, Colony colony, Object... statArgs) {
        Double fitness = colony.getBestSolution().getFitnessValue();
        this.historicalMinFitness = (this.historicalMinFitness==null || this.historicalMinFitness>fitness)? fitness : this.historicalMinFitness;
        HashMap<Node,HashMap<Node,Double>> pheromoneByNode = new HashMap<>();
        Graph<Node,Edge> graph = world.getGraph();
        PheromoneContainer pheromoneContainer = world.getPheromoneContainer();
        for (Node node: graph.getNodes()){
            pheromoneByNode.put(node, new HashMap<>());
            for(Edge edge: graph.getOutputEdges(node)){
                pheromoneByNode.get(node).put(graph.getNextNode(edge), pheromoneContainer.getNextPheromone(node,edge));
            }
        }
        ArrayList<Object> newValue = new ArrayList<>(){{
            add(statArgs[0]);
            add(historicalMinFitness);
            add(pheromoneByNode);
        }};
        this.pcs.firePropertyChange("updatePheromone", null, newValue);
        return new ArrayList<>(){{add("updated");}};
    }
}