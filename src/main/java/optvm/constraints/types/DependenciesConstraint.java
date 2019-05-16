package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;

import optvm.entities.Datacenter;

import java.util.ArrayList;
import java.util.List;

public class DependenciesConstraint implements Constraint<Datacenter> {

    private String neededOS;

    public DependenciesConstraint() {
        this.neededOS = null;
    }

    public DependenciesConstraint(String neededOS) {
        this.neededOS = neededOS;
    }

    public List<Datacenter> apply(ConstraintContext context) {
        List<Datacenter> possibleDCs = new ArrayList();

        for (Datacenter dc : context.getPossibleDCs()) {
            if (this.neededOS != null && this.neededOS.equals(dc.getOs()))
                possibleDCs.add(dc);
        }
        return possibleDCs;
    }

    public ConstraintType getType() {
        return ConstraintType.DC;
    }

    public String getNeededOS() {
        return neededOS;
    }

    public void setNeededOS(String neededOS) {
        this.neededOS = neededOS;
    }
}
