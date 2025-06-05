package org.soyaga.examples.Tango.ACOTango.ACO.Evaluable;

import lombok.AllArgsConstructor;
import org.soyaga.aco.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.aco.Solution;
import org.soyaga.aco.world.Graph.Elements.Edge;
import org.soyaga.aco.world.Graph.Elements.Node;
import org.soyaga.aco.world.World;
import org.soyaga.examples.Tango.ACOTango.UI.Buttons.SquareButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of a solution.
 */
@AllArgsConstructor
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
     * HashMap with the cells with relations.
     */
    private HashSet <SquareButton> cellsWithRelations;
    /**
     * HashMap tha relates Nodes to SquareButtons
     */
    private HashMap<Node, SquareButton> nodeToSquareButton;

    /**
     * This function computes the value of the evaluable object.
     *
     * @param world           world Object containing the "Graph" and "PheromoneContainer" information.
     * @param solution        Solution object to evaluate.
     * @param feasibilityArgs VarArgs containing the additional information needed to evaluate.
     * @return A double containing the value of the evaluation.
     */
    @Override
    public Double evaluate(World world, Solution solution, Object... feasibilityArgs) {
        Integer [][] boardValues = new Integer[this.rowSize][this.colSize];
        ArrayList<Object> solutionNodes = solution.getNodesVisited();
        ArrayList<Object> solutionEdges = solution.getEdgesUtilized();
        double feasibility = 0.;
        // Build arrays
        for(int i=0; i<solutionNodes.size()-1; i++){
            Node origin = (Node) solutionNodes.get(i);
            Edge edge = (Edge) solutionEdges.get(i);
            if ("Sun".equals(edge.getDestination().getID()) || "Moon".equals(edge.getDestination().getID())) {
                SquareButton originButton = this.nodeToSquareButton.get(origin);
                boardValues[originButton.getRow()][originButton.getCol()] = "Sun".equals(edge.getDestination().getID())?1:0;
            }
        }
        // Check fixed values
        for (SquareButton button: this.fixedCells){
            if(boardValues[button.getRow()][button.getCol()]!=("Sun".equals(button.getIconType())?1:0)){
                feasibility++;
            }
        }

        // Check fixed relations
        for (SquareButton button: this.cellsWithRelations){
            //North
            if("=".equals(button.getNorthType())){
                if(!Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow() - 1][button.getCol()])){
                    feasibility++;
                }
            } else if ("x".equals(button.getNorthType())) {
                if(Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow() - 1][button.getCol()])){
                    feasibility++;
                }
            }
            //East
            if("=".equals(button.getEastType())){
                if(!Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow()][button.getCol() + 1])){
                    feasibility++;
                }
            } else if ("x".equals(button.getEastType())) {
                if(Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow()][button.getCol() + 1])){
                    feasibility++;
                }
            }
            //South
            if("=".equals(button.getSouthType())){
                if(!Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow() + 1][button.getCol()])){
                    feasibility++;
                }
            } else if ("x".equals(button.getSouthType())) {
                if(Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow() + 1][button.getCol()])){
                    feasibility++;
                }
            }
            //West
            if("=".equals(button.getWestType())){
                if(!Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow()][button.getCol() - 1])){
                    feasibility++;
                }
            } else if ("x".equals(button.getWestType())) {
                if(Objects.equals(boardValues[button.getRow()][button.getCol()], boardValues[button.getRow()][button.getCol() - 1])){
                    feasibility++;
                }
            }
        }

        // Check equal number of 1/0 by row, by col & 3 consecutive
        HashMap<Integer, Integer> columnCount = new HashMap<>();
        for(int row = 0; row < this.rowSize; row++){
            int rowCount = 0;
            for(int col = 0; col < this.colSize; col++){
                Integer value = boardValues[row][col];
                rowCount+=value;
                columnCount.putIfAbsent(col, 0);
                columnCount.computeIfPresent(col, (k, v) -> v+value);
                //Count 3 True/False row
                if(row + 2 < this.rowSize) {
                    Integer plus1RowValue = boardValues[row+1][col];
                    Integer plus2RowValue = boardValues[row+2][col];
                    int rowSum= value + plus1RowValue + plus2RowValue;
                    if(rowSum==0 || rowSum==3) feasibility++;
                }
                //Count 3 True/False col
                if(col + 2 < this.colSize) {
                    Integer plus1ColValue = boardValues[row][col+1];
                    Integer plus2colValue = boardValues[row][col+2];
                    int colSum= value + plus1ColValue + plus2colValue;
                    if(colSum==0 || colSum==3) feasibility++;
                }
            }
            //count row number of trues/false
            feasibility+= Math.abs(rowCount-this.rowSize/2.);
        }

        //count row number of 1/0
        for(Integer colValue:columnCount.values()){
            feasibility+= Math.abs(colValue -this.colSize/2.);
        }
        return feasibility;
    }
}