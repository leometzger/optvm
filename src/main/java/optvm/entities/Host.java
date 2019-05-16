package optvm.entities;

import optvm.entities.vos.EnergyInfo;
import optvm.entities.vos.Hardware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Host implements Serializable {

    private String id;
    private boolean active;
    private long bandwidth;
    private int hops;
    private long requiredMemory;
    private String hipervisor;
    private EnergyInfo energyInfo;
    private Hardware hardware;
    private List<VM> vms;
    private List<VM> migratingVMs;

    public Host() {
        this.vms = new ArrayList();
        this.migratingVMs = new ArrayList();
    }

    public Host(boolean active, long bandwidth, int hops, long requiredMemory, EnergyInfo energyInfo, String hipervisor,
            Hardware hardware) {
        this.active = active;
        this.hops = hops;
        this.hipervisor = hipervisor;
        this.hardware = hardware;
        this.bandwidth = bandwidth;
        this.energyInfo = energyInfo;
        this.requiredMemory = requiredMemory;
    }

    public long getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(long requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public EnergyInfo getEnergyInfo() {
        return energyInfo;
    }

    public void setEnergyInfo(EnergyInfo energyInfo) {
        this.energyInfo = energyInfo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<VM> getVms() {
        return vms;
    }

    public void setVms(List<VM> vms) {
        this.vms = vms;
    }

    public String getHipervisor() {
        return hipervisor;
    }

    public void setHipervisor(String hipervisor) {
        this.hipervisor = hipervisor;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public List<VM> getMigratingVMs() {
        return migratingVMs;
    }

    public void setMigratingVMs(List<VM> migratingVMs) {
        this.migratingVMs = migratingVMs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
