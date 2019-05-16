package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;
import optvm.entities.Cloud;

import java.util.ArrayList;
import java.util.List;

public class CloudNameConstraint implements Constraint<Cloud> {

    private String forbiddenCloudName;

    public CloudNameConstraint(){}

    public CloudNameConstraint(String forbiddenCloudName) {
        this.forbiddenCloudName = forbiddenCloudName;
    }

    @Override
    public List<Cloud> apply(ConstraintContext context) {
        List<Cloud> possibleClouds = new ArrayList();

        for(Cloud cloud : context.getPossibleTargets()) {
            if(cloud.getId() != this.forbiddenCloudName) {
                possibleClouds.add(cloud);
            }
        }

        return possibleClouds;
    }

    public String getForbiddenCloudName() {
        return forbiddenCloudName;
    }

    public void setForbiddenCloudName(String forbiddenCloudName) {
        this.forbiddenCloudName = forbiddenCloudName;
    }

    @Override
    public ConstraintType getType() {
        return null;
    }
}
