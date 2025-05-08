package org.soyaga.examples.Zip.ACOZip.ACO.Stats;

import lombok.Setter;
import org.soyaga.aco.Colony;
import org.soyaga.aco.world.World;
import org.soyaga.examples.Zip.ACOZip.ACO.ZipACO;
import org.soyaga.aco.StatsRetrievalPolicy.Stat.Stat;

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
        if(colony.getBestSolution().getFitnessValue()==0){
            if(!found){
                ArrayList<Object> newValue = new ArrayList<>(){{
                    add(statArgs[0]);
                    add(colony.getBestSolution().getNodesVisited());
                }};
                this.pcs.firePropertyChange("solutionFound", null, newValue);
                this.found=true;
            }
            return new ArrayList<>(){{add("True");}};
        }
        return new ArrayList<>(){{add("False");}};
    }
}