package org.soyaga.examples.Queens.GeneticQueens.GA.Stats;

import org.soyaga.examples.Queens.GeneticQueens.GA.QueensGA;
import org.soyaga.ga.Population;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.Stat;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class UpdateFitnessStat implements Stat {
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
    public UpdateFitnessStat() {
        this.header = new ArrayList<>(){{add("FitnessUpdate");}};
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Function that subscribes to the controller
     * communication point to the outside
     */
    public void subscribeToComponents(QueensGA controller) {
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
     * @param population Population used to compute the stat.
     * @param statArgs   VarArgs containing the additional information needed to apply the stat.
     * @return ArrayList{@literal <String>} with the column names.
     */
    @Override
    public ArrayList<String> apply(Population population, Object... statArgs) {
        Double fitness = population.getBestIndividual().getFitnessValue();
        this.historicalMinFitness = (this.historicalMinFitness==null || this.historicalMinFitness>fitness)? fitness : this.historicalMinFitness;
        Double mean = population.getPopulation().stream().reduce(0.,
                (acc,val) -> acc+val.getFitnessValue(),Double::sum)/population.getPopulation().size();
        Double sd = Math.sqrt(population.getPopulation().stream().
                reduce(0.,(acc,val) -> acc+(val.getFitnessValue()-mean)*(val.getFitnessValue()-mean),Double::sum)/
                population.getPopulation().size());
        ArrayList<Double> oldValue = new ArrayList<>(){{
            add(historicalMinFitness);
            add(mean);
            add(sd);
        }};
        this.pcs.firePropertyChange("addBestFitness", null, oldValue);
        return new ArrayList<>(){{add("updated");}};
    }
}