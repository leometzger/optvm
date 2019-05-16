package optvm.entities;

import java.util.ArrayList;
import java.util.List;

public class Cloud {

    private String id;
    private List<Datacenter> datacenters;

    public Cloud() {
    }

    public Cloud(String id) {
        this.id = id;
        this.datacenters = new ArrayList();
    }

    public List<Host> getHosts() {
        ArrayList<Host> hosts = new ArrayList();

        for (Datacenter datacenter : this.getDatacenters()) {
            hosts.addAll(datacenter.getHosts());
        }

        return hosts;
    }

    public void addDatacenter(Datacenter dc) {
        this.datacenters.add(dc);
    }

    public void setDatacenters(List<Datacenter> datacenters) {
        this.datacenters = datacenters;
    }

    public List<Datacenter> getDatacenters() {
        return datacenters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
