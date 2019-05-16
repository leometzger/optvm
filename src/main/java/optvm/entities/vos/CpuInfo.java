package optvm.entities.vos;

public class CpuInfo {

    private int cores;
    private long mips;

    public CpuInfo() {
    }

    public CpuInfo(int cores, long mips) {
        this.cores = cores;
        this.mips = mips;
    }

    public long getCapacity() {
        return this.cores * this.mips;
    }

    public int getCores() {
        return cores;
    }

    public long getMips() {
        return mips;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public void setMips(long mips) {
        this.mips = mips;
    }
}
