package org.soyaga.examples.Tango.GeneticTango.GA.Stats;

import org.soyaga.examples.Tango.GeneticTango.GA.TangoGA;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;
import org.soyaga.ga.Population;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.Stat;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class SolutionFoundStat implements Stat {
    /**
     * ArrayList{@literal <String>} with the different column headers computed by this Stat.
     */
    private final ArrayList<String> header;
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
    public void subscribeToComponents(TangoGA controller) {
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
        ArrayListGenome<ArrayListChromosome<Boolean>> genomeObject = (ArrayListGenome<ArrayListChromosome<Boolean>>) bestIndividual.getGenome();
        ArrayList<ArrayListChromosome<Boolean>> genomeInformation = genomeObject.getGeneticInformation();
        Boolean[][] solution = new Boolean[genomeInformation.size()][genomeInformation.get(0).getGeneticInformation().size()];
        int row=0;
        for (ArrayListChromosome<Boolean> chromosome : genomeInformation) {
            int col = 0;
            for (Boolean type: chromosome.getGeneticInformation()) {
                solution[row][col] = type;
                text.append(type?" O ":" ) ");
                col++;
            }
            text.append('\n');
            row++;
        }
        System.out.println(indInfo+text);
        newValues.add(0, iteration);
        newValues.add(1, solution);
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