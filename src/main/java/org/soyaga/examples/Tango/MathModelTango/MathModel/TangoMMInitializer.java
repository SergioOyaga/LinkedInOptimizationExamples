package org.soyaga.examples.Tango.MathModelTango.MathModel;


import com.google.ortools.linearsolver.MPConstraintProto;
import com.google.ortools.linearsolver.MPVariableProto;
import lombok.Getter;
import lombok.Setter;
import org.soyaga.Initializer.MMInitializer;
import org.soyaga.examples.Tango.MathModelTango.UI.Buttons.SquareButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class implements MMInitializer and defines the base initializing techniques for a mathematical modeling
 * optimization.
 * It should contain all the information needed to initialize the mathematical problem.
 */
@Setter
@Getter
public class TangoMMInitializer extends MMInitializer {
    /**
     * Integer that contains the number of columns.
     */
    private Integer colSize;
    /**
     * Integer that contains the number of rows.
     */
    private Integer rowSize;
    /**
     * HashSet with the SquareButton that contains fixed values.
     */
    private HashSet<SquareButton> fixedCells;
    /**
     * HashSet with the SquareButton that contains cells with relations.
     */
    private HashSet<SquareButton> fixedRelationCells;

    /**
     * Function that creates and adds all the variables.
     *
     * @param variables HashMap{@literal <String, ArrayList<?>>} where to store the variables.
     * @param mmVarArgs VarArgs to create the variables.
     */
    @Override
    public void createVariables(HashMap<String, ArrayList<?>> variables, Object[] mmVarArgs) {
        variables.put("GCT_(r,c)", createGridCellTypeVar() );
    }

    /**
     * Creates the GridCellType variables.
     * @return ArrayList with the dimensions and the values of the variable.
     */
    private ArrayList<?> createGridCellTypeVar(){
        ArrayList<ArrayList<MPVariableProto.Builder >> GCT = new ArrayList<>(this.rowSize); //board
        for (int row=0; row<this.rowSize; row++){
            GCT.add(row, new ArrayList<>(this.colSize));
            for(int col=0; col< this.colSize; col++) {
                GCT.get(row).add(col, MPVariableProto.newBuilder()
                        .setName("GCT_(" + row + "," + col + ")")
                        .setLowerBound(0)
                        .setUpperBound(1)
                        .setIsInteger(true));
            }
        }
        return GCT;
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
        constraints.put("NotThreeConsecutiveByRow", createNotThreeConsecutiveByRowConstraints(variables, varIndexesByBuilder));
        constraints.put("NotThreeConsecutiveByCol", createNotThreeConsecutiveByColConstraints(variables, varIndexesByBuilder));
        constraints.put("SameZeroesAndOnesByRow", createSameZeroesAndOnesByRowConstraints(variables, varIndexesByBuilder));
        constraints.put("SameZeroesAndOnesByCol", createSameZeroesAndOnesByColConstraints(variables, varIndexesByBuilder));
        constraints.put("FixedCells", createFixedCellsConstraints(variables, varIndexesByBuilder));
        constraints.put("FixedRelations", createFixedRelationsConstraints(variables, varIndexesByBuilder));
    }

    /**
     * Constraint that ensures not 3 similar types are placed consecutively by row.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createNotThreeConsecutiveByRowConstraints(HashMap<String, ArrayList<?>> variables,
                                                              HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<ArrayList<MPConstraintProto.Builder>> notThreeConsecutiveByRow = new ArrayList<>(this.rowSize);
        for (int row=0; row<this.rowSize; row++){
            ArrayList<MPConstraintProto.Builder> notThreeConsecutiveByRowRow = new ArrayList<>(this.colSize-2);
            notThreeConsecutiveByRow.add(row,notThreeConsecutiveByRowRow);
            for(int col=0; col<this.colSize-2; col++){
                MPConstraintProto.Builder notThreeConsecutiveByRowRowCol = MPConstraintProto.newBuilder()
                        .setName("NotThreeConsecutiveByRow_("+row+","+col+")")
                        .setLowerBound(1)
                        .setUpperBound(2);
                notThreeConsecutiveByRowRow.add(col,notThreeConsecutiveByRowRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_c_plus_one_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col+1},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_c_plus_two_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col+2},
                        variables,
                        varIndexesByBuilder);
                notThreeConsecutiveByRowRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_c_plus_one_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_c_plus_two_index).addCoefficient(1.);
            }
        }
        return notThreeConsecutiveByRow;
    }

    /**
     * Constraint that ensures not 3 similar types are placed consecutively by column.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createNotThreeConsecutiveByColConstraints(HashMap<String, ArrayList<?>> variables,
                                                                   HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<ArrayList<MPConstraintProto.Builder>> notThreeConsecutiveByCol = new ArrayList<>(this.colSize);
        for (int col=0; col<this.colSize; col++){
            ArrayList<MPConstraintProto.Builder> notThreeConsecutiveByColCol = new ArrayList<>(this.rowSize-2);
            notThreeConsecutiveByCol.add(col,notThreeConsecutiveByColCol);
            for(int row=0; row<this.rowSize-2; row++){
                MPConstraintProto.Builder notThreeConsecutiveByColColRow = MPConstraintProto.newBuilder()
                        .setName("NotThreeConsecutiveByCol_("+row+","+col+")")
                        .setLowerBound(1)
                        .setUpperBound(2);
                notThreeConsecutiveByColCol.add(row,notThreeConsecutiveByColColRow);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_plus_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row+1, col},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_plus_two_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row+2, col},
                        variables,
                        varIndexesByBuilder);
                notThreeConsecutiveByColColRow
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_plus_one_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_plus_two_c_index).addCoefficient(1.);
            }
        }
        return notThreeConsecutiveByCol;
    }

    /**
     * Constraint that the same number of types is present in each row.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createSameZeroesAndOnesByRowConstraints(HashMap<String, ArrayList<?>> variables,
                                                           HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> sameZeroesAndOnesByRow = new ArrayList<>(this.rowSize);
        for (int row=0; row<this.rowSize; row++){
            MPConstraintProto.Builder sameZeroesAndOnesByRowRow = MPConstraintProto.newBuilder()
                    .setName("SameZeroesAndOnesByRow_("+row+")")
                    .setLowerBound(this.colSize/2)
                    .setUpperBound(this.colSize/2);
            sameZeroesAndOnesByRow.add(row,sameZeroesAndOnesByRowRow);
            for(int col=0; col<this.colSize; col++){
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col},
                        variables,
                        varIndexesByBuilder);
                sameZeroesAndOnesByRowRow
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.);
            }
        }
        return sameZeroesAndOnesByRow;
    }

    /**
     * Constraint that the same number of types is present in each column.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createSameZeroesAndOnesByColConstraints(HashMap<String, ArrayList<?>> variables,
                                                                 HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> sameZeroesAndOnesByCol = new ArrayList<>(this.colSize);
        for (int col=0; col<this.colSize; col++){
            MPConstraintProto.Builder sameZeroesAndOnesByColCol = MPConstraintProto.newBuilder()
                    .setName("SameZeroesAndOnesByCol_("+col+")")
                    .setLowerBound(this.rowSize/2)
                    .setUpperBound(this.rowSize/2);
            sameZeroesAndOnesByCol.add(col,sameZeroesAndOnesByColCol);
            for(int row=0; row<this.rowSize; row++){
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{row, col},
                        variables,
                        varIndexesByBuilder);
                sameZeroesAndOnesByColCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.);
            }
        }
        return sameZeroesAndOnesByCol;
    }

    /**
     * Constraint that ensures fixed cells have the same value as original.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createFixedCellsConstraints(HashMap<String, ArrayList<?>> variables,
                                                          HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> fixedCell = new ArrayList<>();
        for(SquareButton button:this.fixedCells){
            int cellValue;
            if("Sun".equals(button.getIconType())) cellValue=1;
            else if ("Moon".equals(button.getIconType())) cellValue = 0;
            else continue;
            MPConstraintProto.Builder fixedCellRowCol= MPConstraintProto.newBuilder()
                    .setName("fixedCell_(" + button.getRow() + "," + button.getCol() + ")")
                    .setLowerBound(cellValue)
                    .setUpperBound(cellValue);
            fixedCell.add(fixedCellRowCol);
            Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                    new Integer[]{button.getRow(), button.getCol()},
                    variables,
                    varIndexesByBuilder);
            fixedCellRowCol
                    .addVarIndex(GCT_r_c_index).addCoefficient(1.);
        }
        return fixedCell;
    }


    /**
     * Constraint that ensures fixed cell relations are maintained.
     * @param variables HashMap with the variables
     * @param varIndexesByBuilder HashMap with the index by variable.
     * @return ArrayList with the constraints.
     */
    private ArrayList<?> createFixedRelationsConstraints(HashMap<String, ArrayList<?>> variables,
                                                             HashMap<Object, Integer> varIndexesByBuilder) {
        ArrayList<MPConstraintProto.Builder> fixedRelation = new ArrayList<>();
        for(SquareButton button:this.fixedRelationCells){
            if("=".equals(button.getNorthType())){
                MPConstraintProto.Builder fixedNorthRowCol= MPConstraintProto.newBuilder()
                        .setName("fixed=North_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(0)
                        .setUpperBound(0);
                fixedRelation.add(fixedNorthRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow()-1, button.getCol()},
                        variables,
                        varIndexesByBuilder);
                fixedNorthRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(-1.);
            }
            else if ("x".equals(button.getNorthType())) {
                MPConstraintProto.Builder fixedNorthRowCol= MPConstraintProto.newBuilder()
                        .setName("fixedxNorth_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(1)
                        .setUpperBound(1);
                fixedRelation.add(fixedNorthRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow()-1, button.getCol()},
                        variables,
                        varIndexesByBuilder);
                fixedNorthRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(1.);
            }
            if("=".equals(button.getEastType())){
                MPConstraintProto.Builder fixedEastRowCol= MPConstraintProto.newBuilder()
                        .setName("fixed=East_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(0)
                        .setUpperBound(0);
                fixedRelation.add(fixedEastRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()+1},
                        variables,
                        varIndexesByBuilder);
                fixedEastRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(-1.);
            }
            else if ("x".equals(button.getEastType())) {
                MPConstraintProto.Builder fixedEastRowCol= MPConstraintProto.newBuilder()
                        .setName("fixedxEast_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(1)
                        .setUpperBound(1);
                fixedRelation.add(fixedEastRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()+1},
                        variables,
                        varIndexesByBuilder);
                fixedEastRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(1.);
            }
            if("=".equals(button.getSouthType())){
                MPConstraintProto.Builder fixedSouthRowCol= MPConstraintProto.newBuilder()
                        .setName("fixed=South_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(0)
                        .setUpperBound(0);
                fixedRelation.add(fixedSouthRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow()+1, button.getCol()},
                        variables,
                        varIndexesByBuilder);
                fixedSouthRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(-1.);
            }
            else if ("x".equals(button.getSouthType())) {
                MPConstraintProto.Builder fixedSouthRowCol= MPConstraintProto.newBuilder()
                        .setName("fixedxSouth_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(1)
                        .setUpperBound(1);
                fixedRelation.add(fixedSouthRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow()+1, button.getCol()},
                        variables,
                        varIndexesByBuilder);
                fixedSouthRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(1.);
            }
            if("=".equals(button.getWestType())){
                MPConstraintProto.Builder fixedWestRowCol= MPConstraintProto.newBuilder()
                        .setName("fixed=West_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(0)
                        .setUpperBound(0);
                fixedRelation.add(fixedWestRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()-1},
                        variables,
                        varIndexesByBuilder);
                fixedWestRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(-1.);
            }
            else if ("x".equals(button.getWestType())) {
                MPConstraintProto.Builder fixedWestRowCol= MPConstraintProto.newBuilder()
                        .setName("fixedxWest_(" + button.getRow() + "," + button.getCol() + ")")
                        .setLowerBound(1)
                        .setUpperBound(1);
                fixedRelation.add(fixedWestRowCol);
                Integer GCT_r_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()},
                        variables,
                        varIndexesByBuilder);
                Integer GCT_r_min_one_c_index = getVariableIndex("GCT_(r,c)",
                        new Integer[]{button.getRow(), button.getCol()-1},
                        variables,
                        varIndexesByBuilder);
                fixedWestRowCol
                        .addVarIndex(GCT_r_c_index).addCoefficient(1.)
                        .addVarIndex(GCT_r_min_one_c_index).addCoefficient(1.);
            }
        }
        return fixedRelation;
    }
}