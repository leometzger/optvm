package optvm.optimization;

import optvm.entities.Host;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

import java.util.List;

public class OptvmExecutor {

    private final OptvmProblem problem;
    private final String strategy;

    public OptvmExecutor(OptvmProblem problem, String strategy) {
        this.strategy = strategy;
        this.problem = problem;
    }

    public OptvmExecutor(OptvmProblem problem) {
       this.problem = problem;
       this.strategy = "NSGAII";
    }

    public NondominatedPopulation execute() {
        Executor executor = new Executor()
                .withProblem(this.problem)
                .withAlgorithm(this.strategy)
                .withMaxEvaluations(10000)
                .distributeOnAllCores();

        NondominatedPopulation result = executor.run();
        return result;
    }
}
