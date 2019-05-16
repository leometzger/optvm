package optvm.entities;

import optvm.entities.Restriction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Policy implements Serializable {

    private String id;
    private String name;
    private String strategy;
    private int nHostsToOptimize;
    private List<Restriction> constraints;
    private List<String> objectives;

    public Policy() {
        this.constraints = new ArrayList();
        this.objectives = new ArrayList();
        this.nHostsToOptimize = 100;
        this.strategy = "NSGAII";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Restriction> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Restriction> constraints) {
        this.constraints = constraints;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getnHostsToOptimize() {
        return nHostsToOptimize;
    }

    public void setnHostsToOptimize(int nHostsToOptimize) {
        this.nHostsToOptimize = nHostsToOptimize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.name);

        for(String objective : this.objectives) {
            sb.append("\n" + objective);
        }
        for(Restriction restriction : this.constraints) {
            sb.append("\n" + restriction.toString());
        }

        return sb.toString();
    }
}
