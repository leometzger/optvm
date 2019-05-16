package optvm.entities;

import optvm.entities.constants.OS;
import optvm.entities.vos.CostInfo;
import optvm.entities.vos.Localization;

import java.util.ArrayList;
import java.util.List;

public class Datacenter {

    private String id;
    private String architecture;
    private String hypervisor;
    private OS os;
    private Localization localization;
    private CostInfo costInfo;
    private List<Host> hosts;

    public Datacenter() {
        this.localization = new Localization();
        this.costInfo = new CostInfo();
        this.hosts = new ArrayList();
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public String getHypervisor() {
        return hypervisor;
    }

    public void setHypervisor(String hypervisor) {
        this.hypervisor = hypervisor;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public CostInfo getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(CostInfo costInfo) {
        this.costInfo = costInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
