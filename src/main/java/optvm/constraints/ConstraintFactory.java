package optvm.constraints;

import optvm.constraints.types.*;

import java.util.Map;

public class ConstraintFactory {

    public static Constraint makeConstraint(String type, Map<String, String> args) {
        switch (type) {
            case "CloudName":
                CloudNameConstraint cloudName = new CloudNameConstraint();
                cloudName.setForbiddenCloudName(args.get("name"));
                return cloudName;
            case "Cost":
                CostConstraint cost = new CostConstraint();

                if(args.containsKey("maxPerMem"))
                    cost.setMaxPerMem(Double.parseDouble(args.get("maxPerMem")));

                if(args.containsKey("maxPerStorage"))
                    cost.setMaxPerStorage(Double.parseDouble(args.get("maxPerStorage")));

                if(args.containsKey("maxPerBW"))
                    cost.setMaxPerBW(Double.parseDouble(args.get("maxPerBW")));

                if(args.containsKey("maxPerSec"))
                    cost.setMaxPerSec(Double.parseDouble(args.get("maxPerSec")));

                return cost;

            case "Conflict":
                ConflictConstraint conflict = new ConflictConstraint();

                for(String key : args.keySet())
                    conflict.addForbiddenLocalizationMig(key, args.get(key));

                return conflict;

            case "Cohabitation":
                CohabitationConstraint cohabitation = new CohabitationConstraint();

                if(args.containsKey("needToBeNeighbor"))
                    cohabitation.setNeedToBeNeighbor(true);

                if(args.containsKey("notInSameHost"))
                    cohabitation.setNotInSameHost(true);

                return cohabitation;

            case "Dependencies":
                DependenciesConstraint dependencies = new DependenciesConstraint();

                if(args.containsKey("neededOs")) {
                    dependencies.setNeededOS(args.get("neededOS"));
                }

            default:
                return null;
        }
    }
}
