package org.soyaga.examples.Zip.GeneticZip.GA.Stats;

import lombok.Setter;
import org.soyaga.examples.Zip.GeneticZip.GA.ZipGA;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;
import org.soyaga.ga.Population;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.Stat;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

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
    @Setter
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
    public void subscribeToComponents(ZipGA controller) {
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
        ArrayListGenome<SquareButton> genomeObject = (ArrayListGenome<SquareButton>) bestIndividual.getGenome();
        ArrayList<SquareButton> genomeInformation = genomeObject.getGeneticInformation();
        int size = genomeInformation.size();
        HashMap<Integer, HashMap<Integer, Integer>> orderByRowByCol = new HashMap<>();
        for(int pos=0;pos<size;pos++){
            SquareButton button = genomeInformation.get(pos);
            orderByRowByCol.computeIfAbsent(button.getRow(), r -> new HashMap<>()).put(button.getCol(), pos);
        }
        ArrayList<Integer> rows = new ArrayList<>(orderByRowByCol.keySet());
        rows.sort(Integer::compareTo);
        for(Integer row:rows){
            ArrayList<Integer> cols = new ArrayList<>(orderByRowByCol.get(row).keySet());
            cols.sort(Integer::compareTo);
            for(Integer col:cols){
                text.append(" "+orderByRowByCol.get(row).get(col) +" ");
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