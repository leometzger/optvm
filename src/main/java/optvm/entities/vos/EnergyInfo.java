package optvm.entities.vos;

import java.io.Serializable;

public class EnergyInfo implements Serializable {

    private long pMin;
    private long pMax;
    private int cpuUsage;

    public EnergyInfo() {
    }

    public EnergyInfo(long pMin, long pMax, int cpuUsage) {
        this.pMax = pMax;
        this.pMin = pMin;
        this.cpuUsage = cpuUsage;
    }

    public long getpMin() {
        return pMin;
    }

    public long getpMax() {
        return pMax;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(int cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public void setpMin(long pMin) {
        this.pMin = pMin;
    }

    public void setpMax(long pMax) {
        this.pMax = pMax;
    }
}
