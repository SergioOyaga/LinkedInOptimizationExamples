package org.soyaga.examples.Queens.GeneticQueens.GA.Stats;

import org.soyaga.examples.Queens.GeneticQueens.GA.QueensGA;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;
import org.soyaga.ga.Population;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.Stat;

import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class SolutionFoundStat implements Stat {
    /**
     * ArrayList{@literal <String>} with the different column headers computed by this Stat.
     */
    private final ArrayList<String> header;
    /**
     * long with the time when the solver started.
     */
    private Long startTime;
    /**
     * PropertyChangeSupport object to fire events when properties of this class change.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Flag that controls whether the solution has already been found.
     */
    private boolean found;


    /**
     * Constructor
     */
    public SolutionFoundStat() {
        this.header = new ArrayList<>(){{add("SolutionFound");}};
        this.pcs = new PropertyChangeSupport(this);
        this.found = false;
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
     * Transform the genome of the best individual into Object consumable by the pcs.
     *
     * @return Object [].
     */
    private ArrayList<Object> getNewValue(Individual bestIndividual, Integer iteration) {
        ArrayList<Object> newValues = new ArrayList<>(2);
        String indInfo = bestIndividual.toString();
        StringBuilder text = new StringBuilder("\n");
        ArrayListGenome<Integer> genomeObject = (ArrayListGenome<Integer>) bestIndividual.getGenome();
        ArrayList<Integer> genomeInformation = genomeObject.getGeneticInformation();
        int nQueens = genomeInformation.size();
        for (Integer queenPos : genomeInformation) {
            for (int j = 0; j < nQueens; j++) {
                if (j == queenPos) {
                    text.append(" Q ");
                } else {
                    text.append(" _ ");
                }
            }
            text.append('\n');
        }
        System.out.println(indInfo+text);
        newValues.add(0, iteration);
        newValues.add(1, genomeObject.getGeneticInformation());
        return newValues;
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
        if(this.startTime==null) this.startTime = System.currentTimeMillis();
        if(population.getBestIndividual().getFitnessValue()==0){
            if(!found){
                ArrayList<Object> newValue = this.getNewValue(population.getBestIndividual(), (Integer) statArgs[0]);
                this.pcs.firePropertyChange("solutionFound", null, newValue);
                this.found=true;
            }
            return new ArrayList<>(){{add("True");}};
        }
        return new ArrayList<>(){{add("False");}};
    }
}