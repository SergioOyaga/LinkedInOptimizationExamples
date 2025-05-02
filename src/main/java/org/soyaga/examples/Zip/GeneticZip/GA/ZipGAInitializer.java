package org.soyaga.examples.Zip.GeneticZip.GA;

import org.soyaga.Initializer.GAInitializer;
import org.soyaga.examples.Zip.GeneticZip.UI.Buttons.SquareButton;
import org.soyaga.examples.Zip.GeneticZip.GA.Evaluable.ZipFeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.random.RandomGenerator;

/**
 * This class enables the initialization of new individuals from scratch.
 */
public class ZipGAInitializer extends GAInitializer {
    /**
     * FeasibilityFunction that evaluates LinkedIn Queens solution.
     */
    private final ZipFeasibilityFunction feasibilityFunction;
    /**
     * Integer with the number of rows in the problem
     */
    private Integer rowSize;
    /**
     * Integer with the number of cols in the problem
     */
    private Integer colSize;
    /**
     * ArrayList with the priority cells
     */
    private ArrayList<SquareButton> priorityCells;
    /**
     * HashMap with the connections between cells by cells.
     */
    private HashMap<SquareButton, HashSet<SquareButton>> connectedCellsByCell;
    /**
     * HashSet with all the cells that are not fixed (start and end)
     */
    private HashSet<SquareButton> notFixedCells;

    public ZipGAInitializer() {
        this.feasibilityFunction = new ZipFeasibilityFunction();
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
     * Setts the priority cells.
     * @param priorityCells ArrayList with the priority cells.
     */
    public void setPriorityCells(ArrayList<SquareButton> priorityCells){
        this.priorityCells = priorityCells;
        this.feasibilityFunction.setPriorityCells(this.priorityCells);
    }

    /**
     * Setts the cell relations.
     * @param cells SquareButton[][] with the cells
     */
    public void setConnectedCellsByCell(SquareButton[][] cells){
        this.connectedCellsByCell = new HashMap<>();
        this.notFixedCells = new HashSet<>();
        for(int i=0; i< this.rowSize; i++){
            SquareButton[] squareButtonRow = cells[i];
            for(int j = 0; j< this.colSize;j++){
                SquareButton button = squareButtonRow[j];
                this.notFixedCells.add(button);
                this.connectedCellsByCell.put(button, new HashSet<>());
                if(i > 0 && !button.getNorthFrontier()){
                    this.connectedCellsByCell.get(button).add(cells[i-1][j]);
                }
                if(i < this.rowSize-1 && !button.getSouthFrontier()){
                    this.connectedCellsByCell.get(button).add(cells[i+1][j]);
                }
                if(j > 0 && !button.getWestFrontier()){
                    this.connectedCellsByCell.get(button).add(cells[i][j-1]);
                }
                if(j < this.colSize-1 && !button.getEastFrontier()){
                    this.connectedCellsByCell.get(button).add(cells[i][j+1]);
                }
            }
        }
        this.notFixedCells.remove(this.priorityCells.get(0));
        this.notFixedCells.remove(this.priorityCells.get(this.priorityCells.size()-1));
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
        //Create used and remaining sets to add remaining buttons;
        HashSet<SquareButton> usedCells = new HashSet<>();
        HashSet<SquareButton> remainingCells = new HashSet<>(this.notFixedCells);
        //Genome
        ArrayList<SquareButton> initialization = new ArrayList<>(this.rowSize*this.colSize); // Array<SquareButtons>
        SquareButton currentCell = this.priorityCells.get(0);
        SquareButton lastCell = this.priorityCells.get(this.priorityCells.size()-1);
        //Add first and last item;
        initialization.add(currentCell);
        initialization.add(lastCell);
        usedCells.add(currentCell);
        usedCells.add(lastCell);

        //Add remaining cells following the path.
        int i = 1;
        while(!remainingCells.isEmpty()){
            HashSet<SquareButton> cellConnections = new HashSet<>(this.connectedCellsByCell.get(currentCell));
            cellConnections.removeAll(usedCells);
            if (cellConnections.isEmpty()){ //if the path is closed, the select a random cell.
                cellConnections=remainingCells;
            }
            currentCell = new ArrayList<>(cellConnections).get(RandomGenerator.getDefault().nextInt(cellConnections.size()));
            initialization.add(i, currentCell);//Insert next cell
            usedCells.add(currentCell);
            remainingCells.remove(currentCell);
            i++;
        }
        ArrayListGenome<SquareButton> genome = new ArrayListGenome<>();
        genome.addAll(initialization);
        return new Individual(genome,this.feasibilityFunction,null, 1.);
    }
}