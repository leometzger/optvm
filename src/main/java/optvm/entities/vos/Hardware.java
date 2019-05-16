package optvm.entities.vos;

public class Hardware {

    private long ram;
    private long storage;
    private CpuInfo cpu;

    public Hardware() {
    }

    public Hardware(long ram, long storage, CpuInfo cpuInfo) {
        this.ram = ram;
        this.cpu = cpuInfo;
        this.storage = storage;
    }

    public long getStorage() {
        return storage;
    }

    public long getRam() {
        return ram;
    }

    public CpuInfo getCpu() {
        return cpu;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }

    public void setCpu(CpuInfo cpu) {
        this.cpu = cpu;
    }
}
