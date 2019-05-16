package optvm.optimization;

import org.moeaframework.core.Solution;

import java.io.Serializable;

public class OptvmSolution extends Solution {
    public OptvmSolution(int numberOfVariables, int numberOfObjectives) {
        super(numberOfVariables, numberOfObjectives);
    }

    public OptvmSolution(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
    }

    public OptvmSolution(double[] objectives) {
        super(objectives);
    }

    protected OptvmSolution(Solution solution) {
        super(solution);
    }

    @Override
    public OptvmSolution copy() {
        OptvmSolution solution = new OptvmSolution(this);

        for (String key : this.getAttributes().keySet()) {
            solution.setAttribute(key, (Serializable) this.getAttribute(key));
        }

        return solution;
    }
}
