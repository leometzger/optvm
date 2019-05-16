package optvm.optimization;

import optvm.entities.Host;
import optvm.entities.VM;
import optvm.entities.vos.EnergyInfo;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.List;

public class OptvmProblem extends AbstractProblem {

    private final VM vm;
    private final List<String> objectives;
    private final List<Host> hosts;
    private final Host sourceHost;
    private final int subsetSize;

    public OptvmProblem(
            List<String> objectives,
            VM vm,
            List<Host> hosts,
            Host sourceHost,
            int subsetSize
        ) {
        super(subsetSize, objectives.size());

        this.vm = vm;
        this.objectives = objectives;
        this.hosts = hosts;
        this.sourceHost = sourceHost;
        this.subsetSize = subsetSize;
    }

    private boolean hasObjective(String objective) {
        return this.objectives.stream().anyMatch(obj -> obj.equalsIgnoreCase(objective));
    }

    @Override
    public void evaluate(Solution solution) {
        int i = 0;
        double[] objectives = new double[this.numberOfObjectives];
        List<Host> hosts = new ArrayList();

        for(int k = 0; k < this.subsetSize; ++k)  {
            int index = EncodingUtils.getInt(solution.getVariable(k));
            hosts.add(this.hosts.get(index));
        }

        if (this.hasObjective("MIN_ENERGY_CONSUMPTION")) {
            double energyCost = 0;

            for(Host host : hosts) {
                EnergyInfo energyInfo = host.getEnergyInfo();
                energyCost += (energyInfo.getpMax() - energyInfo.getpMin()) * energyInfo.getCpuUsage() + energyInfo.getpMin();
            }

            objectives[i] = energyCost;
            i++;
        }

        if (this.hasObjective("MIN_INSTALLATION_TIME")) {
            double reconf = 0;

            for(Host host : hosts) {
                List<VM> allVMs = new ArrayList();
                allVMs.addAll(host.getVms());
                allVMs.addAll(host.getMigratingVMs());

                double memory = 0;
                for(VM vm : allVMs) {
                    memory += vm.getRam();
                }

                double tfix = memory / host.getBandwidth();
                reconf += (vm.getDirtyPages() * vm.getIter() + this.vm.getRam()) / this.vm.getBw() + tfix;
            }

            objectives[i] = reconf;
            i++;
        }

        if (this.hasObjective("MIN_MIGRATION_OVERLOAD")) {
            double migrationOverload = 0;

            migrationOverload += (this.sourceHost.getBandwidth() * 0.8)
                    + (this.sourceHost.getHardware().getStorage() * 0.6)
                    + (this.sourceHost.getHardware().getRam() * 0.4)
                    + (this.sourceHost.getHardware().getCores() * 0.1);

            for(Host host : hosts) {

            migrationOverload += (host.getBandwidth() * 0.8)
                    + (host.getHardware().getStorage() * 0.6)
                    + (host.getHardware().getRam() * 0.4)
                    + (host.getHardware().getCores() * 0.1);
            }
            objectives[i] = migrationOverload;
        }

        solution.setObjectives(objectives);
    }

    @Override
    public String getName() {
        return "OptVM";
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(this.subsetSize, this.objectives.size());

        for(int i = 0; i < this.subsetSize; ++i) {
            solution.setVariable(i, EncodingUtils.newInt(0, this.hosts.size() - 1));
        }
        return solution;
    }
}
