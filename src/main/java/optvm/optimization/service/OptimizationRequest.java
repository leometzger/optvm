package optvm.optimization.service;

import optvm.entities.Host;
import optvm.entities.VM;
import optvm.optimization.ContingencyThreshholds;
import optvm.optimization.Environment;
import optvm.optimization.Objective;

import java.util.List;

public class OptimizationRequest {

    private List<Objective> objectives;
    private List<Host> hosts;
    private VM vm;
    private Environment environment;
    private ContingencyThreshholds threshholds;

    public OptimizationRequest(List<Host> hosts, List<Objective> objectives, VM vm, Environment environment,
            ContingencyThreshholds threshholds) {
        this.hosts = hosts;
        this.objectives = objectives;
        this.vm = vm;
        this.environment = environment;
        this.threshholds = threshholds;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public VM getVm() {
        return vm;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public ContingencyThreshholds getThreshholds() {
        return threshholds;
    }

    /** Remover */

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setThreshholds(ContingencyThreshholds threshholds) {
        this.threshholds = threshholds;
    }
}
