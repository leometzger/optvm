package optvm.optimization.utils;

import optvm.entities.Host;
import optvm.optimization.OptvmSolution;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

public class EncodingUtils {

    public static Solution fromHostToSolution(Host host) {
        OptvmSolution solution = new OptvmSolution(11, 3);

        solution.setAttribute("ID", host.getId());

        RealVariable pmin = new RealVariable(host.getEnergyInfo().getpMin(), 0, 5);
        RealVariable pmax = new RealVariable(host.getEnergyInfo().getpMax(), 0, 5);
        RealVariable uCpu = new RealVariable(host.getEnergyInfo().getCpuUsage(), 0, 100);
        RealVariable distance = new RealVariable(host.getHops(), 0, 100);
        RealVariable memory = new RealVariable(host.getHardware().getRam(), 0, 1000000);
        RealVariable cpu = new RealVariable(host.getHardware().getCpu().getCapacity(), 0, 1000000);
        RealVariable storage = new RealVariable(host.getHardware().getStorage(), 0, 1000000);
        RealVariable bandwidth = new RealVariable(host.getBandwidth(), 0, 1000000);
        RealVariable pe = new RealVariable(host.getHardware().getCpu().getCores(), 0, 1000000);
        RealVariable mips = new RealVariable(host.getHardware().getCpu().getMips(), 0, 1000000);
        RealVariable requiredMemoryByOtherVms = new RealVariable(host.getRequiredMemory(), 0, 1000000);

        solution.setVariable(0, pmin);
        solution.setVariable(1, pmax);
        solution.setVariable(2, uCpu);
        solution.setVariable(3, distance);
        solution.setVariable(4, memory);
        solution.setVariable(5, cpu);
        solution.setVariable(6, storage);
        solution.setVariable(7, bandwidth);
        solution.setVariable(8, pe);
        solution.setVariable(9, mips);
        solution.setVariable(10, requiredMemoryByOtherVms);

        return solution;
    }
}
