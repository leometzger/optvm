package optvm.constraints.service;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;
import optvm.constraints.types.CohabitationConstraint;
import optvm.constraints.types.ConflictConstraint;
import optvm.constraints.types.CostConstraint;
import optvm.constraints.types.DependenciesConstraint;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstraintRequest {

    private ConstraintContext context;

    private ConflictConstraint conflictConstraint;
    private CohabitationConstraint cohabitationConstraint;
    private DependenciesConstraint dependenciesConstraint;
    private CostConstraint costConstraint;

    public ConstraintRequest() {
    }

    public ConstraintRequest(ConstraintContext context) {
        this.context = context;
    }

    public List<Constraint> getConstraints() {
        return Arrays
                .asList(this.conflictConstraint, this.cohabitationConstraint, this.dependenciesConstraint,
                        this.costConstraint)
                .stream().filter(constraint -> constraint != null).collect(Collectors.toList());
    }

    public ConstraintContext getContext() {
        return context;
    }

    public void setContext(ConstraintContext context) {
        this.context = context;
    }

    public ConflictConstraint getConflictConstraint() {
        return conflictConstraint;
    }

    public void setConflictConstraint(ConflictConstraint conflictConstraint) {
        this.conflictConstraint = conflictConstraint;
    }

    public CohabitationConstraint getCohabitationConstraint() {
        return cohabitationConstraint;
    }

    public void setCohabitationConstraint(CohabitationConstraint cohabitationConstraint) {
        this.cohabitationConstraint = cohabitationConstraint;
    }

    public DependenciesConstraint getDependenciesConstraint() {
        return dependenciesConstraint;
    }

    public void setDependenciesConstraint(DependenciesConstraint dependenciesConstraint) {
        this.dependenciesConstraint = dependenciesConstraint;
    }

    public CostConstraint getCostConstraint() {
        return costConstraint;
    }

    public void setCostConstraint(CostConstraint costConstraint) {
        this.costConstraint = costConstraint;
    }

}
