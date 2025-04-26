package org.soyaga.examples.Tango.GeneticTango.GA.Evaluable;

import lombok.Setter;
import org.soyaga.examples.Tango.GeneticTango.UI.Buttons.SquareButton;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
@Setter
public class TangoFeasibilityFunction implements FeasibilityFunction {
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
     * HashSet with the fixed relations cells
     */
    private HashSet<SquareButton> fixedRelationCells;

    /**
     * Computes the feasibility of an individual's genome.
     * In this case, we consider the solution feasible if the genome's positions contain all numbers between 0
     * and N-1.
     * This ensures that all Queens are placed in different
     * columns (Genome values) and different rows (Genome's values positions).
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs object that allows retention of information from the evaluation for use in decision-making.
     * @return A Double representing the feasibility value.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        ArrayList<ArrayListChromosome<Boolean>> chromosomes = (ArrayList<ArrayListChromosome<Boolean>>) genome.getGeneticInformation();
        double feasibility = 0.;
        // Check equal number of True/False by row, by col & 3 consecutive
        HashMap<Integer, Integer> columnCount = new HashMap<>();
        for(int row = 0; row < this.rowSize; row++){
            int rowCount = 0;
            for(int col = 0; col < this.colSize; col++){
                Boolean gen = chromosomes.get(row).getGeneticInformation().get(col);
                Integer genValue = gen?1:0;
                rowCount+=genValue;
                columnCount.putIfAbsent(col, 0);
                columnCount.computeIfPresent(col, (k, v) -> v+genValue);
                //Count 3 True/False row
                if(row + 2 < this.rowSize) {
                    Integer genPlus1RowValue = chromosomes.get(row+1).getGeneticInformation().get(col)?1:0;
                    Integer genPlus2RowValue = chromosomes.get(row+2).getGeneticInformation().get(col)?1:0;
                    int rowSum= genValue + genPlus1RowValue + genPlus2RowValue;
                    if(rowSum==0 || rowSum==3) feasibility++;
                }
                //Count 3 True/False col
                if(col + 2 < this.colSize) {
                    Integer genPlus1ColValue = chromosomes.get(row).getGeneticInformation().get(col+1)?1:0;
                    Integer genPlus2colValue = chromosomes.get(row).getGeneticInformation().get(col+2)?1:0;
                    int colSum= genValue + genPlus1ColValue + genPlus2colValue;
                    if(colSum==0 || colSum==3) feasibility++;
                }
            }
            //count row number of trues/false
            feasibility+= Math.abs(rowCount-this.rowSize/2.);
        }

        //count row number of trues/false
        for(Integer colValue:columnCount.values()){
            feasibility+= Math.abs(colValue -this.colSize/2.);
        }

        // Check fixed Cells
        for(SquareButton fixedCell:this.fixedCells){
            Boolean sun = chromosomes.get(fixedCell.getRow()).getGeneticInformation().get(fixedCell.getCol());
            if(("Sun".equals(fixedCell.getIconType()) && !sun) ||
               ("x".equals(fixedCell.getIconType()) && sun)){
                feasibility++;
            }
        }

        // Check relations
        for(SquareButton fixedRelation:this.fixedRelationCells){
            Boolean sun = chromosomes.get(fixedRelation.getRow()).getGeneticInformation().get(fixedRelation.getCol());
            // north relation
            if(fixedRelation.getRow() > 0) {
                Boolean northValue = chromosomes.get(fixedRelation.getRow() - 1).getGeneticInformation().get(fixedRelation.getCol());
                if (("=".equals(fixedRelation.getNorthType()) && (northValue != sun)) ||
                        ("x".equals(fixedRelation.getNorthType()) && (northValue == sun))) {
                    feasibility++;
                }
            }

            // south relation
            if(fixedRelation.getRow() < this.rowSize-1) {
                Boolean southValue = chromosomes.get(fixedRelation.getRow() + 1).getGeneticInformation().get(fixedRelation.getCol());
                if (("=".equals(fixedRelation.getSouthType()) && (southValue != sun)) ||
                        ("x".equals(fixedRelation.getSouthType()) && (southValue == sun))) {
                    feasibility++;
                }
            }

            // west relation
            if(fixedRelation.getCol() > 0) {
                Boolean westValue = chromosomes.get(fixedRelation.getRow()).getGeneticInformation().get(fixedRelation.getCol() - 1);
                if (("=".equals(fixedRelation.getWestType()) && (westValue != sun)) ||
                        ("x".equals(fixedRelation.getWestType()) && (westValue == sun))) {
                    feasibility++;
                }
            }

            // east relation
            if(fixedRelation.getCol() < this.colSize-1) {
                Boolean eastValue = chromosomes.get(fixedRelation.getRow()).getGeneticInformation().get(fixedRelation.getCol() + 1);
                if (("=".equals(fixedRelation.getEastType()) && (eastValue != sun)) ||
                        ("x".equals(fixedRelation.getEastType()) && (eastValue == sun))) {
                    feasibility++;
                }
            }
        }

        return feasibility;
    }
}