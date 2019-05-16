package optvm.entities;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Optimization implements Serializable {

    private String id;
    private String policyId;
    private int subsetSize;
    private Map<String, List<HostRef>> results;

    private Map<String, String> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public int getSubsetSize() {
        return subsetSize;
    }

    public void setSubsetSize(int subsetSize) {
        this.subsetSize = subsetSize;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public Map<String, List<HostRef>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<HostRef>> results) {
        this.results = results;
    }
}
