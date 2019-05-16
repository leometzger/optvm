package optvm.constraints.service;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;
import optvm.constraints.types.CohabitationConstraint;
import optvm.constraints.types.ConflictConstraint;
import optvm.constraints.types.CostConstraint;
import optvm.constraints.types.DependenciesConstraint;
import optvm.entities.constants.OS;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstraintRequestBuilder {

    private ConstraintContext context;

    private ConflictConstraint conflictConstraint;
    private CohabitationConstraint cohabitationConstraint;
    private DependenciesConstraint dependenciesConstraint;
    private CostConstraint costConstraint;

    public ConstraintRequestBuilder() {
        this.context = new ConstraintContext();
    }

    public ConstraintRequestBuilder withContext(ConstraintContext context) {
        this.context = context;
        return this;
    }

    public ConstraintRequestBuilder withCost(double maxPerMem, double maxPerStorage, double maxPerBW,
            double maxPerSec) {
        this.costConstraint = new CostConstraint(maxPerMem, maxPerStorage, maxPerBW, maxPerSec);
        return this;
    }

    public ConstraintRequestBuilder withCohabitation(boolean needToBeNeighbor, boolean notInSameHost) {
        this.cohabitationConstraint = new CohabitationConstraint(needToBeNeighbor, notInSameHost);
        return this;
    }

    public ConstraintRequestBuilder withLocalizationConflict(String from, String to) {
        if (this.conflictConstraint == null)
            this.conflictConstraint = new ConflictConstraint();

        this.conflictConstraint.addForbiddenLocalizationMig(from, to);
        return this;
    }

    public ConstraintRequestBuilder withDependencies(String neededOs) {
        this.dependenciesConstraint = new DependenciesConstraint(neededOs);
        return this;
    }

    public ConstraintRequest build() {
        ConstraintRequest request = new ConstraintRequest(this.context);

        request.setCohabitationConstraint(this.cohabitationConstraint);
        request.setCostConstraint(this.costConstraint);
        request.setDependenciesConstraint(this.dependenciesConstraint);
        request.setConflictConstraint(this.conflictConstraint);

        return request;
    }
}
