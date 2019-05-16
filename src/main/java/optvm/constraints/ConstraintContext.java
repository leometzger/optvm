package optvm.constraints;

import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;
import optvm.entities.VM;

import java.util.ArrayList;
import java.util.List;

public class ConstraintContext {

    private List<Cloud> possibleTargets;

    private VM vm;
    private Datacenter fromDC;
    private Host fromHost;
    private Cloud fromCloud;

    public List<Datacenter> getPossibleDCs() {
        ArrayList<Datacenter> possibleDCs = new ArrayList();

        for (Cloud cloud : this.possibleTargets) {
            possibleDCs.addAll(cloud.getDatacenters());
        }

        return possibleDCs;
    }

    public List<Host> getPossibleHosts() {
        List<Host> hosts = new ArrayList();

        for (Datacenter dc : this.getPossibleDCs()) {
            hosts.addAll(dc.getHosts());
        }

        return hosts;
    }

    public void removeHosts(List<Host> hosts) {
        for (Host host : hosts) {
            this.removeHost(host);
        }
    }

    private void removeHost(Host hostToRemove) {
        List<Host> hostList = null;

        for (Cloud cloud : this.possibleTargets) {
            for (Datacenter dc : cloud.getDatacenters()) {
                for (Host host : dc.getHosts()) {
                    if (host == hostToRemove) {
                        hostList = dc.getHosts();
                    }
                }
            }
        }
        if (hostList != null) {
            hostList.remove(hostToRemove);
        }
    }

    public void removeDCs(List<Datacenter> datacenters) {
        for (Datacenter dc : datacenters) {
            this.removeDC(dc);
        }
    }

    private void removeDC(Datacenter dcToRemove) {
        List<Datacenter> dcList = null;

        for (Cloud cloud : this.possibleTargets) {
            for (Datacenter dc : cloud.getDatacenters()) {
                if (dc == dcToRemove) {
                    dcList = cloud.getDatacenters();
                }
            }
        }

        dcList.remove(dcToRemove);
    }

    public void removeUnreachableClouds() {
        List<Cloud> unreachableClouds = new ArrayList();

        for (Cloud cloud : this.possibleTargets) {
            if (cloud.getDatacenters().size() == 0) {
                unreachableClouds.add(cloud);
            }
        }

        this.possibleTargets.removeAll(unreachableClouds);
    }

    public void removeUnreachableDCs() {
        for (Cloud cloud : this.possibleTargets) {
            List<Datacenter> dcsToRemove = new ArrayList();

            for (Datacenter dc : cloud.getDatacenters()) {
                if (dc.getHosts().size() == 0) {
                    dcsToRemove.add(dc);
                }
            }
            cloud.getDatacenters().removeAll(dcsToRemove);
        }
    }

    public void setPossibleTargets(List<Cloud> possibleTargets) {
        this.possibleTargets = possibleTargets;
    }

    public List<Cloud> getPossibleTargets() {
        return possibleTargets;
    }

    public Datacenter getFromDC() {
        return this.fromDC;
    }

    public Host getFromHost() {
        return this.fromHost;
    }

    public VM getVm() {
        return vm;
    }

    public Cloud getFromCloud() {
        return fromCloud;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    public void setFromDC(Datacenter fromDC) {
        this.fromDC = fromDC;
    }

    public void setFromHost(Host fromHost) {
        this.fromHost = fromHost;
    }

    public void setFromCloud(Cloud fromCloud) {
        this.fromCloud = fromCloud;
    }

}
