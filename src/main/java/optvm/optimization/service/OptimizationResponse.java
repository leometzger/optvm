package optvm.optimization.service;

import optvm.entities.Host;

import java.util.List;

public class OptimizationResponse {

    private final List<Host> hosts;

    public OptimizationResponse(List<Host> hosts) {
        this.hosts = hosts;
    }

    public List<Host> getHosts() {
        return hosts;
    }
}
