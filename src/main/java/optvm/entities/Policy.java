package optvm.entities;

import optvm.constraints.Constraint;
import optvm.entities.constants.Objective;

import java.util.List;

public class Policy {

    private int id;
    private List<Constraint> constraints;
    private List<Objective> objectives;
}
