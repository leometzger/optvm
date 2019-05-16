package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;

import optvm.entities.Host;

import java.util.ArrayList;
import java.util.List;

public class CohabitationConstraint implements Constraint<Host> {

    private boolean needToBeNeighbor;
    private boolean notInSameHost;

    public CohabitationConstraint(boolean needToBeNeighbor, boolean notInSameHost) {
        this.needToBeNeighbor = needToBeNeighbor;
        this.notInSameHost = notInSameHost;
    }

    public List<Host> apply(ConstraintContext context) {
        List<Host> possibleHosts = new ArrayList();
        int vmDistance = context.getFromHost().getHops();

        if (!needToBeNeighbor && !notInSameHost)
            return possibleHosts;

        for (Host host : context.getPossibleHosts()) {
            int distance = Math.abs(host.getHops() - vmDistance);
            boolean sameHost = context.getFromHost() == host;

            if ((this.needToBeNeighbor && distance == 1) || (this.notInSameHost && !sameHost))
                possibleHosts.add(host);
        }

        return possibleHosts;
    }

    public ConstraintType getType() {
        return ConstraintType.HOST;
    }

    public boolean isNeedToBeNeighbor() {
        return needToBeNeighbor;
    }

    public void setNeedToBeNeighbor(boolean needToBeNeighbor) {
        this.needToBeNeighbor = needToBeNeighbor;
    }

    public boolean isNotInSameHost() {
        return notInSameHost;
    }

    public void setNotInSameHost(boolean notInSameHost) {
        this.notInSameHost = notInSameHost;
    }
}
