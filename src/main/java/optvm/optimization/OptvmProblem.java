package optvm.optimization;

import optvm.entities.VM;
import optvm.entities.constants.Objective;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;
import java.util.List;

public class OptvmProblem extends AbstractProblem {

    private final VM vm;
    private final Environment environment;
    private final ContingencyThreshholds contThreshHolds;
    private final List<Objective> objectives;

    public OptvmProblem(List<Objective> objectives, VM vm, Environment environment,
            ContingencyThreshholds threshHolds) {
        super(11, objectives.size());

        this.vm = vm;
        this.objectives = objectives;
        this.environment = environment;
        this.contThreshHolds = threshHolds;
    }

    private boolean hasObjective(Objective objective) {
        return this.objectives.stream().anyMatch(obj -> obj.equals(objective));
    }

    @Override
    public void evaluate(Solution solution) {
        double[] x = EncodingUtils.getReal(solution);
        int i = 0;

        if (this.hasObjective(Objective.MIN_ENERGY_CONSUMPTION)) {
            double pmin = x[0];
            double pmax = x[1];
            double cpuUsage = x[2];

            double energ = (pmax - pmin) * cpuUsage * pmin;
            solution.setObjective(i, energ);
            i++;
        }

        if (this.hasObjective(Objective.MIN_INSTALLATION_TIME)) {
            double distance = x[3];
            double data = this.vm.getDirtyPages() + this.vm.getSize();

            double traf = (data / distance) + ((distance - 1) * this.environment.getProcessingFactor());
            double cont = 0;

            double dmig = traf + cont;
            solution.setObjective(i, dmig);
            i++;
        }

        if (this.hasObjective(Objective.MIN_MIGRATION_OVERLOAD)) {
            double memoryRequiredByOtherVMs = x[10];
            double bandwidth = x[7];

            double tfix = memoryRequiredByOtherVMs / bandwidth;
            double iter = this.environment.getInteractions();
            double dirtyPages = this.vm.getDirtyPages();
            double vmMemory = this.vm.getCurrentAllocatedMemory();

            double treconf = (((dirtyPages * iter) + vmMemory) / bandwidth) + tfix;
            solution.setObjective(i, treconf);
        }
    }

    @Override
    public OptvmSolution newSolution() {
        OptvmSolution solution = new OptvmSolution(this.numberOfVariables, this.numberOfObjectives);

        solution.setAttribute("ID", new RealVariable(0, 1000000));

        solution.setVariable(0, EncodingUtils.newReal(0, 5)); // pmin
        solution.setVariable(1, EncodingUtils.newReal(0, 5)); // pmax
        solution.setVariable(2, EncodingUtils.newReal(0, 100)); // Ucpui
        solution.setVariable(3, EncodingUtils.newInt(0, 100)); // distance
        solution.setVariable(4, EncodingUtils.newReal(0, 1000000)); // memory
        solution.setVariable(5, EncodingUtils.newReal(0, 1000000)); // cpu
        solution.setVariable(6, EncodingUtils.newReal(0, 1000000)); // storage
        solution.setVariable(7, EncodingUtils.newReal(0, 1000000)); // bw
        solution.setVariable(8, EncodingUtils.newReal(0, 10)); // pe
        solution.setVariable(9, EncodingUtils.newReal(0, 1000000)); // mips
        solution.setVariable(10, EncodingUtils.newReal(0, 1000000)); // memoria requerida por outras vms

        return solution;
    }
}
