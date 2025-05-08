package org.soyaga.examples.Zip.MathModelZip.MathModel;

import com.google.ortools.linearsolver.MPModelRequest;
import com.google.ortools.linearsolver.MPVariableProtoOrBuilder;
import lombok.Getter;
import org.soyaga.Initializer.MMInitializer;
import org.soyaga.mm.mathematicalModelAlgorithm.MathematicalProgrammingModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class that extends MathematicalProgrammingModel defines the problem objective function and the solver parameters.
 */
@Getter
public class ZipMathModel extends MathematicalProgrammingModel implements Runnable{
    /**
     * PropertyChangeSupport object to fire events when properties of this class change.
     */
    private final PropertyChangeSupport pcs;

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
     * Constructor.
     *
     * @param ID            String with the ID of the model.
     * @param mmInitializer MMInitializer with the MathModelInfo.
     */
    public ZipMathModel(String ID, MMInitializer mmInitializer) {
        super(ID, mmInitializer);
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Method that returns from an optimized solution the actual result in the
     * form that is convenient for the problem.
     *
     * @param resultArgs VarArgs containing the additional information needed to get the results.
     * @return Object containing the result of the optimization. Ej.:
     * <ul>
     *     <li>Best <b><i>Individual</i></b></li>
     *     <li>Set of best <b><i>Individuals</i></b></li>
     *     <li>Any format suitable for our problem <b><i>Object</i></b></li>
     * </ul>
     */
    @Override
    public Object getResult(Object... resultArgs) {
        Object[] solution = new Object[2];
        String optimizationText = this.getResponse().getStatus()
                + "\nbest objective value:" + this.getResponse().getObjectiveValue()
                + "\nbest objective bound:" + this.getResponse().getBestObjectiveBound()
                + "\n" + this.getResponse().getSolveInfo();
        ZipMMInitializer initializer =  (ZipMMInitializer)this.getMMInitializer();
        Integer[][] result = new Integer[initializer.getRowSize()][initializer.getColSize()];
        int row = 0;
        for(ArrayList<Map.Entry<MPVariableProtoOrBuilder, Double>> rowVariableValues:
                (ArrayList<ArrayList<Map.Entry<MPVariableProtoOrBuilder, Double>>>)this.getVariableValues().get("GCV_(r,c)")){
            int col = 0;
             for(Map.Entry<MPVariableProtoOrBuilder, Double> variableEntry:rowVariableValues){
                 result[row][col]= Math.toIntExact(Math.round(variableEntry.getValue()));
                 col++;
             }
             row++;
        }
        solution[0] = optimizationText+"\n";
        solution[1] = result;
        return solution;
    }

    /**
     * Function that initializes the MPModelRequest.Builder.
     */
    @Override
    public void initializeModelRequest() {
        this.getProtoModelRequest()
                .setEnableInternalSolverOutput(true)
                .setSolverTimeLimitSeconds(600)
                .setSolverType(MPModelRequest.SolverType.SCIP_MIXED_INTEGER_PROGRAMMING);
    }

    /**
     * Function that sets the objective function of the proto model. For linear Objectives in MP problems, we set the
     * objective coefficient directly in the variable Builder, for quadratic objective functions we have to set the indexes
     * in the model builder proto.
     */
    @Override
    public void setObjectiveFunction(Object[] mmVarArgs) {
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            this.optimize();
            Object[] result = (Object[]) this.getResult();
            this.pcs.firePropertyChange("optimizationFinalized", result[0], result[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}