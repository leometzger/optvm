package optvm.constraints;

import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;

import java.util.List;
import java.util.stream.Collectors;

public class ConstraintApplier {

    private final List<Constraint> constraints;
    private final ConstraintContext context;

    public ConstraintApplier(ConstraintContext context, List<Constraint> constraints) {
        this.constraints = constraints;
        this.context = context;
    }

    public List<Cloud> applyConstraints() {
        for (Constraint constraint : this.getConstraints()) {
            switch (constraint.getType()) {
            case CLOUD:
                List<Cloud> possibleClouds = constraint.apply(context);
                this.context.setPossibleTargets(possibleClouds);
                break;

            case DC:
                List<Datacenter> possibleDatacenters = constraint.apply(context);
                List<Datacenter> dcsToRemove = this.context.getPossibleDCs().stream()
                        .filter(dc -> !possibleDatacenters.contains(dc)).collect(Collectors.toList());
                this.context.removeDCs(dcsToRemove);
                break;

            case HOST:
                List<Host> possibleHosts = constraint.apply(context);
                List<Host> hostsToRemove = this.context.getPossibleHosts().stream()
                        .filter(host -> !possibleHosts.contains(host)).collect(Collectors.toList());
                this.context.removeHosts(hostsToRemove);
                break;
            default:
                break;
            }

            this.context.removeUnreachableDCs();
            this.context.removeUnreachableClouds();
        }

        return this.context.getPossibleTargets();
    }

    public List<Constraint> getConstraints() {
        return this.constraints;
    }

    public ConstraintContext getContext() {
        return context;
    }
}
