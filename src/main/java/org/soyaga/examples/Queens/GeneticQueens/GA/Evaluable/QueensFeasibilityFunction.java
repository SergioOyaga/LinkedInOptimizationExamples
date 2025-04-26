package org.soyaga.examples.Queens.GeneticQueens.GA.Evaluable;

import lombok.Setter;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
@Setter
public class QueensFeasibilityFunction implements FeasibilityFunction {
    /**
     * Color board;
     */
    private Color[][] colorBoard;

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
        ArrayList<Integer> chromosomes = (ArrayList<Integer>) genome.getGeneticInformation();
        HashSet<Integer> differentCols = new HashSet<>(chromosomes);
        int numberOfQueens = chromosomes.size();
        // Start with the number of col collisions. Row collisions are impossible due to the Genome structure.
        double feasibility = numberOfQueens- differentCols.size();

        // Evaluate diagonal collisions
        HashSet<Color> colorsUsed= new HashSet<>();
        for(int i = 0; i< numberOfQueens; i++){
            if((i != numberOfQueens-1) && (chromosomes.get(i+1)-chromosomes.get(i)==1 || chromosomes.get(i)-chromosomes.get(i+1)==1)) {
                feasibility+=1;
            }
            colorsUsed.add(this.colorBoard[i][chromosomes.get(i)]);
        }

        // Evaluate number of colors used
        feasibility+= (numberOfQueens - colorsUsed.size());
        return feasibility;
    }
}