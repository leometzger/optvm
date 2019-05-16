package optvm.optimization;

import optvm.entities.Host;
import optvm.optimization.utils.EncodingUtils;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.comparator.DominanceComparator;

import java.util.ArrayList;
import java.util.List;

public class OptvmExecutor {

    private final OptvmProblem problem;
    private final List<Host> hosts;

    public OptvmExecutor(OptvmProblem problem, List<Host> hosts) {
        this.problem = problem;
        this.hosts = hosts;
    }

    public List<Host> execute() {
        Executor executor = new Executor().withProblem(this.problem).withAlgorithm("NSGAIII").withMaxEvaluations(10000)
                .distributeOnAllCores();

        NondominatedPopulation result = executor.run();

        NondominatedPopulation hostResult = new NondominatedPopulation();
        hostResult.addAll(result);

        DominanceComparator comparator = result.getComparator();
        List<Host> response = new ArrayList();

        for (Host host : this.hosts) {
            Solution solution = EncodingUtils.fromHostToSolution(host);
            this.problem.evaluate(solution);
            hostResult.add(solution);
        }

        System.out.println(hostResult.size());

        hostResult.sort((solution1, solution2) -> comparator.compare(solution1, solution2));
        return response;
    }
}
