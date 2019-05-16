package optvm.entities;

import java.io.Serializable;

public class OptimizationMetric implements Serializable {

    private String optimizationId;
    private long constraintExecutionTime;
    private long optimizationExecutionTime;
    private int inputHostsLenth;
    private int outputSolutionsLenth;
    private int removedByConstraints;

    public long getConstraintExecutionTime() {
        return constraintExecutionTime;
    }

    public void setConstraintExecutionTime(long constraintExecutionTime) {
        this.constraintExecutionTime = constraintExecutionTime;
    }

    public long getOptimizationExecutionTime() {
        return optimizationExecutionTime;
    }

    public void setOptimizationExecutionTime(long optimizationExecutionTime) {
        this.optimizationExecutionTime = optimizationExecutionTime;
    }

    public int getInputHostsLenth() {
        return inputHostsLenth;
    }

    public void setInputHostsLenth(int inputHostsLenth) {
        this.inputHostsLenth = inputHostsLenth;
    }

    public String getOptimizationId() {
        return optimizationId;
    }

    public void setOptimizationId(String optimizationId) {
        this.optimizationId = optimizationId;
    }

    public int getRemovedByConstraints() {
        return removedByConstraints;
    }

    public void setRemovedByConstraints(int removedByConstraints) {
        this.removedByConstraints = removedByConstraints;
    }

    public int getOutputSolutionsLenth() {
        return outputSolutionsLenth;
    }

    public void setOutputSolutionsLenth(int outputSolutionsLenth) {
        this.outputSolutionsLenth = outputSolutionsLenth;
    }
}
