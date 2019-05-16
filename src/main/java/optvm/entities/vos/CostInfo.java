package optvm.entities.vos;

import java.io.Serializable;

public class CostInfo implements Serializable {

    private double costPerSec;
    private double costPerMem;
    private double costPerStorage;
    private double costPerBw;

    public CostInfo() {
        this.costPerSec = -1;
        this.costPerBw = -1;
        this.costPerMem = -1;
        this.costPerStorage = -1;
    }

    public double getCostPerSec() {
        return costPerSec;
    }

    public void setCostPerSec(double costPerSec) {
        this.costPerSec = costPerSec;
    }

    public double getCostPerMem() {
        return costPerMem;
    }

    public void setCostPerMem(double costPerMem) {
        this.costPerMem = costPerMem;
    }

    public double getCostPerStorage() {
        return costPerStorage;
    }

    public void setCostPerStorage(double costPerStorage) {
        this.costPerStorage = costPerStorage;
    }

    public double getCostPerBw() {
        return costPerBw;
    }

    public void setCostPerBw(double costPerBw) {
        this.costPerBw = costPerBw;
    }

}
