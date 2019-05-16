package optvm.constraints.types;

import optvm.constraints.Constraint;
import optvm.constraints.ConstraintContext;

import optvm.entities.vos.CostInfo;
import optvm.entities.Datacenter;

import java.util.ArrayList;
import java.util.List;

public class CostConstraint implements Constraint<Datacenter> {

    private double maxPerMem;
    private double maxPerStorage;
    private double maxPerBW;
    private double maxPerSec;

    public CostConstraint() {
        this.maxPerBW = 0;
        this.maxPerStorage = 0;
        this.maxPerMem = 0;
        this.maxPerSec = 0;
    }

    public CostConstraint(double maxPerMem, double maxPerStorage, double maxPerBW, double maxPerSec) {
        this.maxPerMem = maxPerMem;
        this.maxPerStorage = maxPerStorage;
        this.maxPerBW = maxPerBW;
        this.maxPerSec = maxPerSec;
    }

    public List<Datacenter> apply(ConstraintContext context) {
        List<Datacenter> possibleDCs = new ArrayList();

        for (Datacenter dc : context.getPossibleDCs()) {
            CostInfo costInfo = dc.getCostInfo();

            if ((this.maxPerBW != 0 && this.maxPerBW < costInfo.getCostPerBw())
                    || (this.maxPerMem != 0 && this.maxPerMem < costInfo.getCostPerMem())
                    || (this.maxPerStorage != 0 && this.maxPerStorage < costInfo.getCostPerStorage())
                    || (this.maxPerSec != 0 && this.maxPerStorage < costInfo.getCostPerStorage()))
                continue;

            possibleDCs.add(dc);
        }
        return possibleDCs;
    }

    public ConstraintType getType() {
        return ConstraintType.DC;
    }

    public double getMaxPerMem() {
        return maxPerMem;
    }

    public void setMaxPerMem(double maxPerMem) {
        this.maxPerMem = maxPerMem;
    }

    public double getMaxPerStorage() {
        return maxPerStorage;
    }

    public void setMaxPerStorage(double maxPerStorage) {
        this.maxPerStorage = maxPerStorage;
    }

    public double getMaxPerBW() {
        return maxPerBW;
    }

    public void setMaxPerBW(double maxPerBW) {
        this.maxPerBW = maxPerBW;
    }

    public double getMaxPerSec() {
        return maxPerSec;
    }

    public void setMaxPerSec(double maxPerSec) {
        this.maxPerSec = maxPerSec;
    }
}
