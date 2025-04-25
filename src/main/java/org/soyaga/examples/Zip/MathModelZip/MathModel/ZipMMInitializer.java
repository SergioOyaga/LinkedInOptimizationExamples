package org.soyaga.examples.Zip.MathModelZip.MathModel;


import com.google.ortools.linearsolver.MPConstraintProto;
import com.google.ortools.linearsolver.MPVariableProto;
import lombok.Getter;
import lombok.Setter;
import org.soyaga.Initializer.MMInitializer;
import org.soyaga.examples.Zip.MathModelZip.UI.Buttons.SquareButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements MMInitializer and defines the base initializing techniques for a mathematical modeling
 * optimization.
 * It should contain all the information needed to initialize the mathematical problem.
 */
@Setter
@Getter
public class ZipMMInitializer extends MMInitializer {
    /**
     * Integer that contains the number of columns.
     */
    private Integer colSize;
    /**
     * Integer that contains the number of rows.
     */
    private Integer rowSize;
    /**
     * HashSet with the SquareButton that contains the priority cells sorted.
     */
    private ArrayList<SquareButton> priorityCells;
    /**
     * [][]SquareButton that contains cells with relations.
     */
    private SquareButton[][] cells;
    /**
     * Double with a big value for the BigM method.
     */
    private double big;

    /**
     * Function that creates and adds all the variables.
     *
     * @param variables HashMap{@literal <String, ArrayList<?>>} where to store the variables.
     * @param mmVarArgs VarArgs to create the variables.
     */
    @Override
    public void createVariables(HashMap<String, ArrayList<?>> variables, Object[] mmVarArgs) {
        variables.put("GCV_(r,c)", createGridCellValueVar());
        variables.put("AUX_(r,c,i)", createAuxiliaryVar());
    }

    /**
     * Creates the GridCellValue variables.
     * @return ArrayList with the dimensions and the values of the variable.
     */
    private ArrayList<?> createGridCellValueVar(){
        ArrayList<ArrayList<MPVariableProto.Builder >> GCV = new ArrayList<>(this.rowSize); //board
        for (int row=0; row<this.rowSize; row++){
            GCV.add(row, new ArrayList<>(this.colSize));
            for(int col=0; col< this.colSize; col++) {
                GCV.get(row).add(col, MPVariableProto.newBuilder()
                        .setName("GCV_(" + row + "," + col + ")")
                        .setLowerBound(1)
                        .setUpperBound(this.rowSize*this.colSize)
                        .setIsInteger(true));
            }
        }
        return GCV;
    }

    /**
     * Creates the GridCellValue variables.
     * @return ArrayList with the dimensions and the values of the variable.
     */
    private ArrayList<?> createAuxiliaryVar(){
        ArrayList<ArrayList<ArrayList<MPVariableProto.Builder >>> AUX = new ArrayList<>(this.rowSize); //board
        for (int row=0; row<this.rowSize; row++){
            AUX.add(row, new ArrayList<>(this.colSize));
            for(int col=0; col< this.colSize; col++) {
                AUX.get(row).add(col, new ArrayList<>(4));
                AUX.get(row).get(col).add(0, MPVariableProto.newBuilder()
                        .setName("AUX_(" + row + "," + col + ",0)")
                        .setLowerBound(0)
                        .setUpperBound(1)
                        .setIsInteger(true));
                AUX.get(row).get(col).add(1, MPVariableProto.newBuilder()
                        .setName("AUX_(" + row + "," + col + ",1)")
                        .setLowerBound(0)
                        .setUpperBound(1)
                        .setIsInteger(true));
                AUX.get(row).get(col).add(2, MPVariableProto.newBuilder()
                        .setName("AUX_(" + row + "," + col + ",2)")
                        .setLowerBound(0)
                        .setUpperBound(1)
                        .setIsInteger(true));
                AUX.get(row).get(col).add(3, MPVariableProto.newBuilder()
                        .setName("AUX_(" + row + "," + col + ",3)")
                        .setLowerBound(0)
                        .setUpperBound(1)
                        .setIsInteger(true));
            }
        }
        return AUX;
    }

    /**
     * Function that computes the indexes of the variable from the indexes in the variable space and the indexing
     * created for the model space.
     * @param name String with the name of the variable in the variables' dict.
     * @param indexes Array with the indexes in the variable space.
     * @param variables HashMap with the arrays of variables by variable name.
     * @param indexByVariable HashMap with the variable and the index in the model space.
     * @return Integer with the index of the variable in the model space.
     */
    private Integer getVariableIndex(String name, Integer[] indexes,HashMap<String, ArrayList<?>> variables,
                                     HashMap<Object ,Integer> indexByVariable ){
        ArrayList<?> var = variables.get(name);
        for(int i = 0; i<indexes.length-1; i++){
            var = (ArrayList<?>) var.get(indexes[i]);
        }
        return indexByVariable.get(var.get(indexes[indexes.length-1]));
    }

    /**
     * Function that creates and adds all the constraints.
     * @param constraints HashMap{@literal <String, ArrayList<?>>} where to store the constraints.
     * @param variables           HashMap{@literal <String, ArrayList<?>>} with the variables.
     * @param varIndexesByBuilder HashMap{@literal <MPVariableProto.Builder, Integer>} with the variables by index.
     * @param mmVarArgs           VarArgs to create the constraints.
     */
    @Override
    public void createConstraints(HashMap<String, ArrayList<?>> constraints,HashMap<String, ArrayList<?>> variables, HashMap<Object, Integer> varIndexesByBuilder, Object[] mmVarArgs) {
        constraints.put("Priority", createPriorityConstraints(variables, varIndexesByBuilder));
        constraints.put("AllCellsUsed", createAllCellsUsedConstraints(variables, varIndexesByBuilder));
        constraints.put("StartNode", createStartNodeConstraints(variables, varIndexesByBuilder));
        constraints.put("EndNode", createEndNodeConstraints(variables, varIndexesByBuilder));
        constraints.put("AuxUnique", createAuxUniqueConstraint(variables,varIndexesByBuilder));
        constraints.put("PathContinuity", createPathContinuityConstraints(variables, varIndexesByBuilder));
    }

    /**
     * Constraint that ensures the priority is respected.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createPriorityConstraints(HashMap<String, ArrayList<?>> variables,
                                                              HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> priorityConstraints = new ArrayList<>(this.priorityCells.size()-1);
        for (int i=0; i<this.priorityCells.size()-1; i++){
            SquareButton cell = this.priorityCells.get(i);
            SquareButton nextCell = this.priorityCells.get(i+1);
            MPConstraintProto.Builder priorityConstraintCell = MPConstraintProto.newBuilder()
                    .setName("NotThreeConsecutiveByRow_("+cell.getRow()+","+cell.getCol()+")")
                    .setLowerBound(1)
                    .setUpperBound(this.rowSize*this.colSize);
            priorityConstraints.add(i,priorityConstraintCell);
                Integer GCV_r_c_index = getVariableIndex("GCV_(r,c)",
                        new Integer[]{cell.getRow(), cell.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCV_r_c_next_index = getVariableIndex("GCV_(r,c)",
                        new Integer[]{nextCell.getRow(), nextCell.getCol()},
                        variables,
                        varIndexesByBuilder);
            priorityConstraintCell
                        .addVarIndex(GCV_r_c_index).addCoefficient(-1.)
                        .addVarIndex(GCV_r_c_next_index).addCoefficient(1.);
        }
        return priorityConstraints;
    }

    /**
     * Constraint that ensures all cells are used.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createAllCellsUsedConstraints(HashMap<String, ArrayList<?>> variables,
                                                                   HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> allCellUsedArray = new ArrayList<>(1);
        MPConstraintProto.Builder allCellsUsed = MPConstraintProto.newBuilder()
                .setName("AllCellsUsed")
                .setLowerBound((this.rowSize*this.colSize + 1.) * this.rowSize*this.colSize/2.)
                .setUpperBound((this.rowSize*this.colSize + 1.) * this.rowSize*this.colSize/2.);
        allCellUsedArray.add(0,allCellsUsed);
        for (int col=0; col<this.colSize; col++){
            for(int row=0; row<this.rowSize; row++){
                Integer GCV_r_c_index = getVariableIndex("GCV_(r,c)",
                        new Integer[]{row, col},
                        variables,
                        varIndexesByBuilder);
                allCellsUsed.addVarIndex(GCV_r_c_index).addCoefficient(1.);
            }
        }
        return allCellUsedArray;
    }

    /**
     * Constraint that forces the start node.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createStartNodeConstraints(HashMap<String, ArrayList<?>> variables,
                                                           HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> startNodeArray = new ArrayList<>(1);
        MPConstraintProto.Builder startNode = MPConstraintProto.newBuilder()
                .setName("StartNode")
                .setLowerBound(1)
                .setUpperBound(1);
        startNodeArray.add(0,startNode);
        SquareButton startButton = this.priorityCells.get(0);
        Integer GCV_r_c_index = getVariableIndex("GCV_(r,c)",
                new Integer[]{startButton.getRow(), startButton.getCol()},
                variables,
                varIndexesByBuilder);
        startNode.addVarIndex(GCV_r_c_index).addCoefficient(1.);
        return startNodeArray;
    }

    /**
     * Constraint that forces the end node.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createEndNodeConstraints(HashMap<String, ArrayList<?>> variables,
                                                    HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> endNodeArray = new ArrayList<>(1);
        MPConstraintProto.Builder endNode = MPConstraintProto.newBuilder()
                .setName("EndNode")
                .setLowerBound(this.rowSize*this.colSize)
                .setUpperBound(this.rowSize*this.colSize);
        endNodeArray.add(0,endNode);
        SquareButton endButton = this.priorityCells.get(this.priorityCells.size()-1);
        Integer GCV_r_c_index = getVariableIndex("GCV_(r,c)",
                new Integer[]{endButton.getRow(), endButton.getCol()},
                variables,
                varIndexesByBuilder);
        endNode.addVarIndex(GCV_r_c_index).addCoefficient(1.);
        return endNodeArray;
    }

    /**
     * Constraint that forces to only select one of the aux as 1 the rest are 0.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createAuxUniqueConstraint(HashMap<String, ArrayList<?>> variables,
                                                   HashMap<Object, Integer> varIndexesByBuilder){
        ArrayList<MPConstraintProto.Builder> auxUniqueCell = new ArrayList<>(this.rowSize*this.colSize-1);
        for (int row=0; row<this.rowSize; row++){
            for(int col=0; col<this.colSize; col++){
                SquareButton button = this.cells[row][col];
                if (!button.equals(this.priorityCells.get(this.priorityCells.size() - 1))) {
                }
                MPConstraintProto.Builder auxUniqueCellCell = MPConstraintProto.newBuilder()
                        .setName("AuxUnique_("+row+","+col+")")
                        .setLowerBound(1)
                        .setUpperBound(1);
                auxUniqueCell.add(auxUniqueCellCell);

                if (button.getRow() > 0 && !button.getNorthFrontier()) {
                    Integer AUX_r_c_0_index = getVariableIndex("AUX_(r,c,i)",
                            new Integer[]{row, col, 0},
                            variables,
                            varIndexesByBuilder);
                    auxUniqueCellCell.addVarIndex(AUX_r_c_0_index).addCoefficient(1.);
                }
                if (button.getRow() < this.rowSize - 1 && !button.getSouthFrontier()) {
                    Integer AUX_r_c_1_index = getVariableIndex("AUX_(r,c,i)",
                            new Integer[]{row, col, 1},
                            variables,
                            varIndexesByBuilder);
                    auxUniqueCellCell.addVarIndex(AUX_r_c_1_index).addCoefficient(1.);
                }
                if (button.getCol() > 0 && !button.getWestFrontier()) {
                    Integer AUX_r_c_2_index = getVariableIndex("AUX_(r,c,i)",
                            new Integer[]{row, col, 2},
                            variables,
                            varIndexesByBuilder);
                    auxUniqueCellCell.addVarIndex(AUX_r_c_2_index).addCoefficient(1.);
                }
                if (button.getCol() < this.colSize - 1 && !button.getEastFrontier()) {
                    Integer AUX_r_c_3_index = getVariableIndex("AUX_(r,c,i)",
                            new Integer[]{row, col, 3},
                            variables,
                            varIndexesByBuilder);
                    auxUniqueCellCell.addVarIndex(AUX_r_c_3_index).addCoefficient(1.);
                }
            }
        }
        return auxUniqueCell;

    }

    /**
     * Constraint that ensures the path continuity.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createPathContinuityConstraints(HashMap<String, ArrayList<?>> variables,
                                                             HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> fixedRelation = new ArrayList<>();

        for (int row=0; row<this.rowSize; row++){
            for(int col=0; col<this.colSize; col++) {
                SquareButton button = this.cells[row][col];
                if (!button.equals(this.priorityCells.get(this.priorityCells.size() - 1))) {
                    Integer GCV_r_c_index = getVariableIndex("GCV_(r,c)",
                            new Integer[]{button.getRow(), button.getCol()},
                            variables,
                            varIndexesByBuilder);
                    //North Frontier
                    MPConstraintProto.Builder cellContinuityNorthLeft = MPConstraintProto.newBuilder()
                            .setName("cellContinuityLeft_(" + button.getRow() + "," + button.getCol() + ",0)")
                            .setLowerBound(-1 * this.colSize * this.rowSize)
                            .setUpperBound(this.big + 1);
                    MPConstraintProto.Builder cellContinuityNorthRight = MPConstraintProto.newBuilder()
                            .setName("cellContinuityRight_(" + button.getRow() + "," + button.getCol() + ",0)")
                            .setLowerBound(1 - this.big)
                            .setUpperBound(this.colSize * this.rowSize);
                    cellContinuityNorthLeft.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    cellContinuityNorthRight.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    if (button.getRow() > 0 && !button.getNorthFrontier()) {
                        Integer GCV_r_min_one_c_index = getVariableIndex("GCV_(r,c)",
                                new Integer[]{button.getRow() - 1, button.getCol()},
                                variables,
                                varIndexesByBuilder);
                        Integer AUX_r_c_index = getVariableIndex("AUX_(r,c,i)",
                                new Integer[]{button.getRow(), button.getCol(), 0},
                                variables,
                                varIndexesByBuilder);
                        cellContinuityNorthLeft.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(this.big);
                        cellContinuityNorthRight.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(-this.big);
                        fixedRelation.add(cellContinuityNorthLeft);
                        fixedRelation.add(cellContinuityNorthRight);
                    }

                    //South Frontier
                    MPConstraintProto.Builder cellContinuitySouthLeft = MPConstraintProto.newBuilder()
                            .setName("cellContinuityLeft_(" + button.getRow() + "," + button.getCol() + ",1)")
                            .setLowerBound(-1 * this.colSize * this.rowSize)
                            .setUpperBound(this.big + 1);
                    MPConstraintProto.Builder cellContinuitySouthRight = MPConstraintProto.newBuilder()
                            .setName("cellContinuityRight_(" + button.getRow() + "," + button.getCol() + ",1)")
                            .setLowerBound(1 - this.big)
                            .setUpperBound(this.colSize * this.rowSize);
                    cellContinuitySouthLeft.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    cellContinuitySouthRight.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    if (button.getRow() < this.rowSize - 1 && !button.getSouthFrontier()) {
                        Integer GCV_r_min_one_c_index = getVariableIndex("GCV_(r,c)",
                                new Integer[]{button.getRow() + 1, button.getCol()},
                                variables,
                                varIndexesByBuilder);
                        Integer AUX_r_c_index = getVariableIndex("AUX_(r,c,i)",
                                new Integer[]{button.getRow(), button.getCol(), 1},
                                variables,
                                varIndexesByBuilder);
                        cellContinuitySouthLeft.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(this.big);
                        cellContinuitySouthRight.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(-this.big);
                        fixedRelation.add(cellContinuitySouthLeft);
                        fixedRelation.add(cellContinuitySouthRight);
                    }

                    //West Frontier
                    MPConstraintProto.Builder cellContinuityWestLeft = MPConstraintProto.newBuilder()
                            .setName("cellContinuityLeft_(" + button.getRow() + "," + button.getCol() + ",2)")
                            .setLowerBound(-1 * this.colSize * this.rowSize)
                            .setUpperBound(this.big + 1);
                    MPConstraintProto.Builder cellContinuityWestRight = MPConstraintProto.newBuilder()
                            .setName("cellContinuityRight_(" + button.getRow() + "," + button.getCol() + ",2)")
                            .setLowerBound(1 - this.big)
                            .setUpperBound(this.colSize * this.rowSize);
                    cellContinuityWestLeft.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    cellContinuityWestRight.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    if (button.getCol() > 0 && !button.getWestFrontier()) {
                        Integer GCV_r_min_one_c_index = getVariableIndex("GCV_(r,c)",
                                new Integer[]{button.getRow(), button.getCol() - 1},
                                variables,
                                varIndexesByBuilder);
                        Integer AUX_r_c_index = getVariableIndex("AUX_(r,c,i)",
                                new Integer[]{button.getRow(), button.getCol(), 2},
                                variables,
                                varIndexesByBuilder);
                        cellContinuityWestLeft.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(this.big);
                        cellContinuityWestRight.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(-this.big);
                        fixedRelation.add(cellContinuityWestLeft);
                        fixedRelation.add(cellContinuityWestRight);
                    }

                    //East Frontier
                    MPConstraintProto.Builder cellContinuityEastLeft = MPConstraintProto.newBuilder()
                            .setName("cellContinuityLeft_(" + button.getRow() + "," + button.getCol() + ",3)")
                            .setLowerBound(-1 * this.colSize * this.rowSize)
                            .setUpperBound(this.big + 1);
                    MPConstraintProto.Builder cellContinuityEastRight = MPConstraintProto.newBuilder()
                            .setName("cellContinuityRight_(" + button.getRow() + "," + button.getCol() + ",3)")
                            .setLowerBound(1 - this.big)
                            .setUpperBound(this.colSize * this.rowSize);
                    cellContinuityEastLeft.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    cellContinuityEastRight.addVarIndex(GCV_r_c_index).addCoefficient(-1.);
                    if (button.getCol() < this.colSize - 1 && !button.getEastFrontier()) {
                        Integer GCV_r_min_one_c_index = getVariableIndex("GCV_(r,c)",
                                new Integer[]{button.getRow(), button.getCol() + 1},
                                variables,
                                varIndexesByBuilder);
                        Integer AUX_r_c_index = getVariableIndex("AUX_(r,c,i)",
                                new Integer[]{button.getRow(), button.getCol(), 3},
                                variables,
                                varIndexesByBuilder);
                        cellContinuityEastLeft.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(this.big);
                        cellContinuityEastRight.addVarIndex(GCV_r_min_one_c_index).addCoefficient(1.)
                                .addVarIndex(AUX_r_c_index).addCoefficient(-this.big);
                        fixedRelation.add(cellContinuityEastLeft);
                        fixedRelation.add(cellContinuityEastRight);
                    }
                }
            }
        }
        return fixedRelation;
    }
}