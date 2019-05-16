package optvm.constraints;

import optvm.constraints.types.ConstraintType;

import java.util.List;

public interface Constraint<T> {

     List<T> apply(ConstraintContext context);

     ConstraintType getType();
}
