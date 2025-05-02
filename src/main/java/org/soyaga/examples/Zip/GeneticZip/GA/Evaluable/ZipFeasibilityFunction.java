package org.soyaga.examples.Zip.GeneticZip.GA.Evaluable;

import lombok.Setter;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
@Setter
public class ZipFeasibilityFunction implements FeasibilityFunction {
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
    private ArrayList<SquareButton> priorityCells;

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
        ArrayList<SquareButton> chromosomes = (ArrayList<SquareButton>) genome.getGeneticInformation();
        double feasibility = 0.;
        // Check start and end
        if(chromosomes.get(0) != this.priorityCells.get(0)){
            feasibility++;
        }
        if(chromosomes.get(this.rowSize*this.colSize-1) != this.priorityCells.get(this.priorityCells.size()-1)){
            feasibility++;
        }
        // Check of the path and cell connection.
        ArrayList<SquareButton> priorityActualOrder = new ArrayList<>();
        for(int i=0; i< chromosomes.size()-1; i++){
            SquareButton currentButton = chromosomes.get(i);
            SquareButton nextButton = chromosomes.get(i+1);
            // Store the actual order of the priority cells.
            if (this.priorityCells.contains(currentButton)) priorityActualOrder.add(currentButton);
            if(i==chromosomes.size()-2 && this.priorityCells.contains(nextButton)) priorityActualOrder.add(nextButton);
            //Check continuity and frontiers
            if(!(
                    ((currentButton.getRow() == nextButton.getRow()+1) && (currentButton.getCol() == nextButton.getCol()) && !currentButton.getNorthFrontier()) ||
                    ((currentButton.getRow() == nextButton.getRow()-1) && (currentButton.getCol() == nextButton.getCol()) && !currentButton.getSouthFrontier()) ||
                    ((currentButton.getRow() == nextButton.getRow()) && (currentButton.getCol() == nextButton.getCol()+1) && !currentButton.getWestFrontier()) ||
                    ((currentButton.getRow() == nextButton.getRow()) && (currentButton.getCol() == nextButton.getCol()-1) && !currentButton.getEastFrontier())
            )
            ){
                feasibility++;
            }
        }
        // Count the number of inversions to match the priority order
        feasibility += countInversions(priorityActualOrder);
        //Check that all cells are used
        feasibility += (this.rowSize*this.colSize - new HashSet<>(chromosomes).size());
        return feasibility;
    }

    // Uses merge sort to count inversions efficiently in O(n log n)
    private static int countInversions(ArrayList<SquareButton> list) {
        return mergeSortAndCount(list, 0, list.size() - 1);
    }

    private static int mergeSortAndCount(ArrayList<SquareButton> arr, int left, int right) {
        if (left >= right) return 0;

        int mid = (left + right) / 2;
        int count = mergeSortAndCount(arr, left, mid);
        count += mergeSortAndCount(arr, mid + 1, right);
        count += merge(arr, left, mid, right);

        return count;
    }

    private static int merge(ArrayList<SquareButton> arr, int left, int mid, int right) {
        ArrayList<SquareButton> temp = new ArrayList<>();
        int i = left, j = mid + 1, count = 0;

        while (i <= mid && j <= right) {
            if (arr.get(i).getIconNumber() <= arr.get(j).getIconNumber()) {
                temp.add(arr.get(i++));
            } else {
                temp.add(arr.get(j++));
                count += (mid - i + 1); // Inversion count
            }
        }

        while (i <= mid) temp.add(arr.get(i++));
        while (j <= right) temp.add(arr.get(j++));

        for (int k = left; k <= right; k++) {
            arr.set(k, temp.get(k - left));
        }

        return count;
    }
}