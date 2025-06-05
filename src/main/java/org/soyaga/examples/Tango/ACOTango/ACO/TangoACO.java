package org.soyaga.examples.Tango.ACOTango.ACO;

import org.soyaga.Initializer.ACOInitializer;
import org.soyaga.aco.AntColonyAlgorithm.StatsAntColonyAlgorithm;
import org.soyaga.aco.Colony;
import org.soyaga.aco.SolutionConstructorPolicy.ParallelConstructorPolicy;
import org.soyaga.aco.StatsRetrievalPolicy.NIterationsStatsRetrievalPolicy;
import org.soyaga.aco.StatsRetrievalPolicy.Stat.*;
import org.soyaga.aco.StoppingPolicy.MaxIterationCriteriaPolicy;
import org.soyaga.aco.UpdatePheromonePolicy.AddPheromonePolicy.MaxGlobalBestFitnessProportionalAddPheromonePolicy;
import org.soyaga.aco.UpdatePheromonePolicy.EvaporatePheromonePolicy.MinPercentageEvaporatePheromonePolicy;
import org.soyaga.aco.UpdatePheromonePolicy.SimpleUpdatePheromonePolicy;
import org.soyaga.aco.world.World;
import org.soyaga.examples.Tango.ACOTango.ACO.Stats.SolutionFoundStat;
import org.soyaga.examples.Tango.ACOTango.ACO.Stats.UpdatePheromoneStat;
import org.soyaga.examples.Tango.ACOTango.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class TangoACO extends StatsAntColonyAlgorithm implements PropertyChangeListener, Runnable{
    /**
     * PropertyChangeSupport object to fire events when properties of this class change.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Stat that updates the fitness panel
     */
    private final UpdatePheromoneStat updatePheromoneStat;
    /**
     * Stat that updates the solution panel
     */
    private final SolutionFoundStat solutionFound;
    /**
     * Long with the start time of the optimization.
     */
    private Long startTime;

    public TangoACO(String ID, World world, ACOInitializer acoInitializer ) throws IOException {
        super(ID,
                world,                                                              //World
                new Colony(),                                                       //Colony
                new MaxIterationCriteriaPolicy(10000),                 //Stopping criteria policy
                acoInitializer,                                                     //Initializer
                new ParallelConstructorPolicy(),                                    //Constructor
                new SimpleUpdatePheromonePolicy(                                    //UpdatePheromonePolicy, first evaporate then add:
                        new MaxGlobalBestFitnessProportionalAddPheromonePolicy(     //AddPheromonePolicy, prop to sol fitness:
                                1.                                                  //Double, max pheromones.
                        ),
                        new MinPercentageEvaporatePheromonePolicy(                  //EvaporatePheromonePolicy, percentage persistence:
                                0.999,                                                 //Double, persistence.
                                0.2                                                 // Double, min pheromone.
                        )
                ),
                new NIterationsStatsRetrievalPolicy(                                //Stats retrieval policy, every n iterations:
                        1,                                                         //Integer, steps between measures.
                        new ArrayList<>(){{                                         //Array Of Stats
                            add(new CurrentMinFitnessStat(2));        //Min Fitness Stat.
                            add(new CurrentMaxFitnessStat(2));        //Max Fitness Stat.
                            add(new HistoricalMinFitnessStat(2));     //Hist Min Fitness Stat.
                            add(new MeanSdFitnessStat(2));            //Fitness Mean and Standard Dev Stat.
                            add(new MeanSdPheromoneStat(2));          //Fitness Mean and Standard Dev Stat.
                        }},
                        null,                                               //String, with the output path.
                        true,                                                       //Boolean, print in console.
                        false                                                       //Boolean, store in csv file.
                )
        );
        this.updatePheromoneStat = new UpdatePheromoneStat();                       //Stat that triggers events to this
        this.solutionFound = new SolutionFoundStat();                               //Stat that triggers events to this
        this.getStatsRetrievalPolicy().getStats().add(this.updatePheromoneStat);
        this.getStatsRetrievalPolicy().getHeader().add(this.updatePheromoneStat.getHeader().get(0));
        this.getStatsRetrievalPolicy().getStats().add(this.solutionFound);
        this.getStatsRetrievalPolicy().getHeader().add(this.solutionFound.getHeader().get(0));
        this.pcs = new PropertyChangeSupport(this);                      //PCS that events to controller
        this.subscribeToComponents();

    }

    private void subscribeToComponents() {
        this.updatePheromoneStat.subscribeToComponents(this);
        this.solutionFound.subscribeToComponents(this);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("solutionFound")){
            Double timeElapsed = (System.currentTimeMillis()- this.startTime)/1000.;
            ArrayList<Object> newValue = new ArrayList<>((ArrayList<Object>)evt.getNewValue());
            newValue.add(timeElapsed);
            this.pcs.firePropertyChange(evt.getPropertyName(),evt.getOldValue(),newValue );
        }
        else if(evt.getPropertyName().equals("updatePheromone")){
            this.pcs.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the ZipGA");
        }

    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            this.startTime =System.currentTimeMillis();
            this.optimize();
            this.solutionFound.setFound(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that returns from an optimized solution the actual result in the
     * form that is convenient for the problem.
     *
     * @param resultArgs VarArgs containing the additional information needed to get the results.
     * @return Object containing the result of the optimization. Ej.:
     * <ul>
     *     <li>Best <b><i>Individual</i></b></li>
     *     <li>Set of best <b><i>Individuals</i></b></li>
     *     <li>Any format suitable for our problem <b><i>Object</i></b></li>
     * </ul>
     */
    @Override
    public Object getResult(Object... resultArgs) {
        return null;
    }

    public void addPropertyChangeListener(Controller controller) {
        this.pcs.addPropertyChangeListener(controller);
    }
}
