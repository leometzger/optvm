package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;

import optvm.entities.Datacenter;

import java.util.ArrayList;
import java.util.List;

public class ConflictConstraint implements Constraint {

    private final List<LocationMigration> forbiddenLocalizationMig;

    public ConflictConstraint() {
        this.forbiddenLocalizationMig = new ArrayList();
    }

    public List<Datacenter> apply(ConstraintContext context) {
        List<Datacenter> possibleDCs = new ArrayList();
        String vmCountry = context.getFromDC().getLocalization().getCountry();

        for (Datacenter dc : context.getPossibleDCs()) {
            for (LocationMigration lm : this.forbiddenLocalizationMig) {
                String dcCountry = dc.getLocalization().getCountry();

                if (lm.getOrigin() != vmCountry || lm.getDest() != dcCountry)
                    possibleDCs.add(dc);
            }
        }

        return possibleDCs;
    }

    public void addForbiddenLocalizationMig(String from, String to) {
        this.forbiddenLocalizationMig.add(new LocationMigration(from, to));
    }

    public ConstraintType getType() {
        return ConstraintType.DC;
    }

    public List<LocationMigration> getForbiddenLocalizationMig() {
        return forbiddenLocalizationMig;
    }
}
