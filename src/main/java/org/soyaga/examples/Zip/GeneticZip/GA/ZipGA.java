package org.soyaga.examples.Zip.GeneticZip.GA;


import org.soyaga.examples.Zip.GeneticZip.GA.Stats.SolutionFoundStat;
import org.soyaga.examples.Zip.GeneticZip.GA.Stats.UpdateFitnessStat;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;
import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.OnePointCrossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.StatsGeneticAlgorithm;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.MutationPolicy.ByLevelSingleProbMutPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.GenericSwapMutation;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StatsRetrievalPolicy.NIterationsStatsRetrievalPolicy;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.*;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extends StatsGeneticAlgorithm and defines how we gather the results.
 */
public class ZipGA extends StatsGeneticAlgorithm implements PropertyChangeListener, Runnable{
    /**
     * PropertyChangeSupport object to fire events when properties of this class change.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Stat that updates the fitness panel
     */
    private final UpdateFitnessStat updateFitnessStat;
    /**
     * Stat that updates the solution panel
     */
    private final SolutionFoundStat solutionFound;
    /**
     * Long with the start time of the optimization.
     */
    private Long startTime;

    /**
     * Constructor that matches its super constructor.
     *
     * @param ID String with the GA description.
     * @param initializer Initializer with the initializer
     * @param populationSize Integer with the population size;
     */
    public ZipGA(String ID, ZipGAInitializer initializer, int populationSize) throws IOException {
        super(ID,
                populationSize,
                new MaxIterationCriteriaPolicy(                                      // Stopping criteria, max iterations.
                        10000                                                         // Iterations.
                ),
                new FixedCrossoverPolicy(                                            // Crossover Policy, fixed number.
                        populationSize*80/100,                                       // Number of crossovers.
                        new RandomSelection(),                                       // Parent Selection, random selection.
                        new OnePointCrossover()                                      // Crossover type, one-point crossover.
                ),
                new ByLevelSingleProbMutPolicy(                                      // Mutation Policy, single prob.
                    new ArrayList<>(){{                                              // Mutation Levels. Only one level (Genome).
                        add(new ArrayList<>(){{                                      // Array of mutation at Genome level.
                            add(new GenericSwapMutation(6));}});         // Mutation to apply at Genome level.
                    }},
                    0.05,                                                 // Mutation probability.
                    false,                                                          // Hash To shuffle levels.
                    false                                                           // Hash To shuffle Mutations.
                ),
                new FixedElitismPolicy(                                             // Elitism Policy, fixed number.
                        populationSize*10/100                                       // Number of elitists.
                ),
                new FixedNewbornPolicy(                                             // Newborn Policy, fixed number.
                        populationSize*10/100                                       // Number of newborns.
                ),
                initializer,                                                        // Initializer
                new NIterationsStatsRetrievalPolicy(                                // Stat Retrieval Policy, every N iterations.
                        10,                                                       // Number of iterations.
                        new ArrayList<>(){{                                         // Array of stats.
                            add(new CurrentMinFitnessStat(4));         // Min Fitness Stat.
                            add(new CurrentMaxFitnessStat(4));         // Max Fitness Stat.
                            add(new HistoricalMinFitnessStat(4));      // Hist Min Fitness Stat.
                            add(new HistoricalMaxFitnessStat(4));      // Hist Max Fitness Stat.
                            add(new MeanSdStat(4));                    // Fitness Mean and Standard Dev Stat.
                            add(new PercentileStat(4,                  // Interpolated Percentile Fitness Stat.
                                    new ArrayList<>(){{                             // Array of percentiles.
                                        add(0);add(25);add(50);add(75);add(100);    // Percentiles values.
                                    }}));
                            add(new StepGradientStat(4));              // Step Gradient Stat.
                            add(new TimeGradientStat(4));              // Time Gradient Stat.
                            add(new ElapsedTimeStat(4));               // Elapsed Time Stat.
                        }},
                        "",                                                 // Stat retrieval output path.
                        true,                                                       // Print Stats in command line.
                        false                                                       // Save Stats summary in a csv.
                )
        );
        this.pcs = new PropertyChangeSupport(this);
        this.updateFitnessStat = new UpdateFitnessStat();
        this.solutionFound = new SolutionFoundStat();
        this.getStatsRetrievalPolicy().getStats().add(this.updateFitnessStat);
        this.getStatsRetrievalPolicy().getHeader().add(this.updateFitnessStat.getHeader().get(0));
        this.getStatsRetrievalPolicy().getStats().add(this.solutionFound);
        this.getStatsRetrievalPolicy().getHeader().add(this.solutionFound.getHeader().get(0));
        this.subscribeToComponents();
    }

    private void subscribeToComponents() {
        this.updateFitnessStat.subscribeToComponents(this);
        this.solutionFound.subscribeToComponents(this);
    }

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * The same listener object may be added more than once, and will be called
     * as many times as it is added.
     * If {@code listener} is null, no exception is thrown and no action
     * is taken.
     *
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Transform the genome of the best individual into a String representing its value.
     *
     * @return String.
     */
    @Override
    public Object getResult(Object... resultArgs) {
        String indInfo = this.population.getBestIndividual().toString();
        StringBuilder text = new StringBuilder("\n");
        ArrayListGenome<SquareButton> genomeObject = (ArrayListGenome<SquareButton>) this.population.getBestIndividual().getGenome();
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
        return indInfo + text;
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
        else if(evt.getPropertyName().equals("addBestFitness")){
            this.pcs.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
        else{
            System.out.println(evt.getPropertyName()+ " not handled by the ZipGA");
        }
    }
}