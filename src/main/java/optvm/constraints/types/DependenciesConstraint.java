package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;

import optvm.entities.Datacenter;
import optvm.entities.constants.OS;

import java.util.ArrayList;
import java.util.List;

public class DependenciesConstraint implements Constraint<Datacenter> {

    private OS neededOS;

    public DependenciesConstraint() {
        this.neededOS = null;
    }

    public DependenciesConstraint(OS neededOS) {
        this.neededOS = neededOS;
    }

    public List<Datacenter> apply(ConstraintContext context) {
        List<Datacenter> possibleDCs = new ArrayList();

        for (Datacenter dc : context.getPossibleDCs()) {
            if (this.neededOS.equals(dc.getOs()))
                possibleDCs.add(dc);
        }
        return possibleDCs;
    }

    public ConstraintType getType() {
        return ConstraintType.DC;
    }

    public OS getNeededOS() {
        return neededOS;
    }

    public void setNeededOS(OS neededOS) {
        this.neededOS = neededOS;
    }
}
