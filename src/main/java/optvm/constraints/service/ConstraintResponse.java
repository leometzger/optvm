package optvm.constraints.service;

import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;

import java.util.ArrayList;
import java.util.List;

public class ConstraintResponse {

    private List<Cloud> clouds;

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public List<Datacenter> getDatacenters() {
        ArrayList<Datacenter> datacenters = new ArrayList();

        for (Cloud cloud : this.clouds)
            datacenters.addAll(cloud.getDatacenters());

        return datacenters;
    }

    public List<Host> getHosts() {
        ArrayList<Host> hosts = new ArrayList();

        for (Cloud cloud : this.clouds)
            for (Datacenter dc : cloud.getDatacenters())
                hosts.addAll(dc.getHosts());

        return hosts;
    }
}
