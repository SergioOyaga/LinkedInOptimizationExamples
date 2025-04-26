package org.soyaga.examples.Tango.GeneticTango.GA;

import lombok.Setter;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.examples.Tango.GeneticTango.UI.Buttons.SquareButton;
import org.soyaga.examples.Tango.GeneticTango.GA.Evaluable.TangoFeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.HashMap;
import java.util.HashSet;
import java.util.random.RandomGenerator;

/**
 * This class enables the initialization of new individuals from scratch.
 */
public class TangoGAInitializer extends GAInitializer {
    /**
     * FeasibilityFunction that evaluates LinkedIn Queens solution.
     */
    private final TangoFeasibilityFunction feasibilityFunction;
    /**
     * Integer with the number of rows in the problem
     */
    private Integer rowSize;
    /**
     * Integer with the number of cols in the problem
     */
    private Integer colSize;
    /**
     * HashSet with the fixed cells
     */
    private HashSet<SquareButton> fixedCells;
    /**
     * HashMap with the fixed relations cells by row and column
     */
    private HashMap<Integer, HashMap<Integer, SquareButton>> fixedRelationCells;

    public TangoGAInitializer() {
        this.feasibilityFunction = new TangoFeasibilityFunction();
    }

    /**
     * Setts the row size
     * @param rowSize integer with the row size.
     */
    public void setRowSize(Integer rowSize){
        this.rowSize = rowSize;
        this.feasibilityFunction.setRowSize(rowSize);
    }

    /**
     * Setts the col size
     * @param colSize integer with the row size.
     */
    public void setColSize(Integer colSize){
        this.colSize = colSize;
        this.feasibilityFunction.setColSize(colSize);
    }

    /**
     * Setts the fixed cells.
     * @param fixedCells HashSet with the fixed cells.
     */
    public void setFixedCells(HashSet<SquareButton> fixedCells){
        this.fixedCells = fixedCells;
        this.feasibilityFunction.setFixedCells(this.fixedCells);
    }

    /**
     * Setts the fixed cells.
     * @param fixedRelationCells HashMap with the fixed relations by row and column
     */
    public void setFixedRelationCells(HashMap<Integer, HashMap<Integer, SquareButton>> fixedRelationCells){
        this.fixedRelationCells = fixedRelationCells;
        HashSet<SquareButton> fixedRelationCellsSet = new HashSet<>();
        for(HashMap<Integer, SquareButton> value:this.fixedRelationCells.values()){
            fixedRelationCellsSet.addAll(value.values());
        }
        this.feasibilityFunction.setFixedRelationCells(fixedRelationCellsSet);
    }

    /**
     * This function initializes a new individual from scratch.
     * In this case, each individual has one ArrayListGenome, composed of N Integers
     * (where positions in the array represent rows and the integer value the column).
     *
     * @return A randomly initialized Individual.
     */
    @Override
    public Individual initializeIndividual() {
        HashMap<Integer, HashMap<Integer, Boolean>> initialization = new HashMap<>(); // Map<row, {Map<cols,{bool}}>>

        // Set fixed cells and relations
        for(SquareButton fixed: this.fixedCells){
            initialization.computeIfAbsent(fixed.getRow(), r->new HashMap<>()).computeIfAbsent(
                    fixed.getCol(), c -> "Sun".equals(fixed.getIconType()));
            this.setFixedRelations(fixed.getRow(),fixed.getCol(), initialization);
        }

        // Set remaining cells and relations
        for(int row = 0; row < this.rowSize; row++){
            for(int col = 0; col < this.colSize; col++){
                if(!initialization.computeIfAbsent(row, r-> new HashMap<>()).containsKey(col)){
                    initialization.get(row).put(col, RandomGenerator.getDefault().nextBoolean());
                    this.setFixedRelations(row, col, initialization);
                }
            }
        }

        //Convert to Genome
        ArrayListGenome<ArrayListChromosome<Boolean>> genome = new ArrayListGenome<>();
        for(int row = 0; row < this.rowSize; row++){
            ArrayListChromosome<Boolean> chromosome = new ArrayListChromosome<>();
            genome.add(chromosome);
            for(int col = 0; col < this.colSize; col++){
                chromosome.add(initialization.get(row).get(col));
            }
        }
        return new Individual(genome,this.feasibilityFunction,null, 1.);
    }

    /**
     * Function that checks whether this row, col has any fixed relation.
     * @param row int with the row
     * @param col int witt the col
     * @param initialization Hashmap to check.
     */
    private void setFixedRelations(int row, int col, HashMap<Integer, HashMap<Integer, Boolean>> initialization) {
        SquareButton button = this.fixedRelationCells.getOrDefault(row, new HashMap<>()).get(col);
        if(button != null){
            Boolean value = initialization.get(row).get(col);

            // north relation
            Boolean northValue = initialization.getOrDefault(row-1, new HashMap<>()).getOrDefault(col, null);
            if(northValue == null && "=".equals(button.getNorthType())){
                initialization.computeIfAbsent(row-1, r-> new HashMap<>()).computeIfAbsent(
                        col, c -> value);
                this.setFixedRelations(row-1, col, initialization);
            }
            else if (northValue == null && "x".equals(button.getNorthType())) {
                initialization.computeIfAbsent(row-1, r-> new HashMap<>()).computeIfAbsent(
                        col, c -> !value);
                this.setFixedRelations(row-1, col, initialization);
            }

            // south relation
            Boolean southValue = initialization.getOrDefault(row+1, new HashMap<>()).getOrDefault(col, null);
            if(southValue == null && "=".equals(button.getSouthType())){
                initialization.computeIfAbsent(row+1, r-> new HashMap<>()).computeIfAbsent(
                        col, c -> value);
                this.setFixedRelations(row+1, col, initialization);
            }
            else if (southValue == null && "x".equals(button.getSouthType())) {
                initialization.computeIfAbsent(row+1, r-> new HashMap<>()).computeIfAbsent(
                        col, c -> !value);
                this.setFixedRelations(row+1, col, initialization);
            }

            // west relation
            Boolean westValue = initialization.getOrDefault(row, new HashMap<>()).getOrDefault(col-1, null);
            if(westValue == null && "=".equals(button.getWestType())){
                initialization.computeIfAbsent(row, r-> new HashMap<>()).computeIfAbsent(
                        col-1, c -> value);
                this.setFixedRelations(row, col-1, initialization);
            }
            else if (westValue == null && "x".equals(button.getWestType())) {
                initialization.computeIfAbsent(row, r-> new HashMap<>()).computeIfAbsent(
                        col-1, c -> !value);
                this.setFixedRelations(row, col-1, initialization);
            }

            // east relation
            Boolean eastValue = initialization.getOrDefault(row, new HashMap<>()).getOrDefault(col+1, null);
            if(eastValue == null && "=".equals(button.getEastType())){
                initialization.computeIfAbsent(row, r-> new HashMap<>()).computeIfAbsent(
                        col+1, c -> value);
                this.setFixedRelations(row, col+1, initialization);
            }
            else if (eastValue == null && "x".equals(button.getEastType())) {
                initialization.computeIfAbsent(row, r-> new HashMap<>()).computeIfAbsent(
                        col+1, c -> !value);
                this.setFixedRelations(row, col+1, initialization);
            }
        }

    }
}