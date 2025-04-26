package org.soyaga.examples.Queens.GeneticQueens.GA;

import org.soyaga.Initializer.GAInitializer;
import org.soyaga.examples.Queens.GeneticQueens.GA.Evaluable.QueensFeasibilityFunction;
import org.soyaga.examples.Queens.GeneticQueens.UI.Buttons.SquareButton;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.random.RandomGenerator;

/**
 * This class enables the initialization of new individuals from scratch.
 */
public class QueensGAInitializer extends GAInitializer {
    /**
     * FeasibilityFunction that evaluates LinkedIn Queens solution.
     */
    private final QueensFeasibilityFunction feasibilityFunction;
    /**
     * HashMap with the cells by color.
     */
    private final HashMap<Color,ArrayList<SquareButton>> colorCells;
    /**
     * ArrayList with the colors sorted from less to more options.
     */
    private final ArrayList<Color> colorsSorted;
    /**
     * Integer with the number of queens in the problem
     */
    private Integer numberOfQueens;

    public QueensGAInitializer() {
        this.feasibilityFunction = new QueensFeasibilityFunction();
        this.colorCells = new HashMap<>();
        this.colorsSorted = new ArrayList<>();
    }

    /**
     * Function that sets the colors, the number pf queens and sorts the colors from fewer options to more options.
     * @param board Color[][] with the board
     */
    public void setColorCells(SquareButton[][] board){
        this.numberOfQueens = board.length;
        HashSet<Color> colors = new HashSet<>();
        Color[][] colorBoard = new Color[this.numberOfQueens][this.numberOfQueens];
        for (SquareButton[] boardRow:board){
            for(SquareButton button: boardRow){
                Color color = button.getButtonColor();
                this.colorCells.computeIfAbsent(color, c ->new ArrayList<>()).add(button);
                colors.add(color);
                colorBoard[button.getRow()][button.getCol()] = color;
            }
        }
        this.colorsSorted.addAll(colors);
        this.colorsSorted.sort(Comparator.comparingInt(c-> this.colorCells.get(c).size()));
        this.feasibilityFunction.setColorBoard(colorBoard);
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
        ArrayListGenome<Integer> genome = new ArrayListGenome<>();
        HashMap<Integer, Integer> genomeReference = new HashMap<>();
        ArrayList<Integer> remainingRows = new ArrayList<>();
        ArrayList<Integer> remainingCols = new ArrayList<>();
        for(int i=0; i<this.numberOfQueens; i++){
            remainingRows.add(i);
            remainingCols.add(i);
        }
        for(Color color:this.colorsSorted){
            ArrayList<SquareButton> buttons = new ArrayList<>(this.colorCells.get(color));
            buttons.removeIf(b -> !remainingRows.contains(b.getRow()));
            buttons.removeIf(b -> !remainingCols.contains(b.getCol()));
            Integer selectedRow, selectedCol;
            if(buttons.isEmpty()) {
                selectedRow = remainingRows.get(RandomGenerator.getDefault().nextInt(remainingRows.size()));
                selectedCol = remainingCols.get(RandomGenerator.getDefault().nextInt(remainingCols.size()));
            }
            else {
                SquareButton selectedCell = buttons.get(RandomGenerator.getDefault().nextInt(buttons.size()));
                selectedRow = selectedCell.getRow();
                selectedCol = selectedCell.getCol();
            }
            remainingRows.remove(selectedRow);
            remainingCols.remove(selectedCol);
            genomeReference.put(selectedRow, selectedCol);
        }
        for (int i = 0; i< this.numberOfQueens;i++){
            genome.add(genomeReference.get(i));
        }
        return new Individual(genome,this.feasibilityFunction,null, 1.);
    }
}