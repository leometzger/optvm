package optvm.optimization;

import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;
import optvm.optimization.utils.EncodingUtils;
import org.moeaframework.core.Initialization;
import org.moeaframework.core.Solution;

import java.util.ArrayList;
import java.util.List;

public class OptvmInitialization implements Initialization {

    private List<Cloud> clouds;
    private int numberOfVariables;
    private int numberOfObjectives;

    public OptvmInitialization(List<Cloud> clouds, int numberOfObjectives) {
        this.clouds = clouds;
        this.numberOfObjectives = numberOfObjectives;
        this.numberOfVariables = 11;
    }

    @Override
    public Solution[] initialize() {
        ArrayList<Solution> solutions = new ArrayList();

        for (Cloud cloud : this.clouds) {
            for (Datacenter datacenter : cloud.getDatacenters()) {
                for (Host host : datacenter.getHosts()) {
                    Solution solution = EncodingUtils.fromHostToSolution(host);
                    solutions.add(solution);
                }
            }
        }

        return solutions.toArray(new Solution[solutions.size()]);
    }
}
