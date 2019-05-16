package optvm.api.requests;

import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;
import optvm.entities.VM;

import java.util.ArrayList;
import java.util.List;

public class OptimizationRequest {

    private int subsetSize;
    private String policyId;
    private VM vm;
    private Host sourceHost;
    private Datacenter sourceDC;
    private Cloud sourceCloud;
    private List<Cloud> clouds;

    public OptimizationRequest() {
        this.subsetSize = 5;
        this.clouds = new ArrayList();
    }

    public int getSubsetSize() {
        return subsetSize;
    }

    public void setSubsetSize(int subsetSize) {
        this.subsetSize = subsetSize;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    public Host getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(Host sourceHost) {
        this.sourceHost = sourceHost;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public Datacenter getSourceDC() {
        return sourceDC;
    }

    public void setSourceDC(Datacenter sourceDC) {
        this.sourceDC = sourceDC;
    }

    public Cloud getSourceCloud() {
        return sourceCloud;
    }

    public void setSourceCloud(Cloud sourceCloud) {
        this.sourceCloud = sourceCloud;
    }
}
