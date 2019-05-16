package optvm.entities.vos;

import java.io.Serializable;

public class Hardware implements Serializable {

    private long ram;
    private int cores;
    private long mips;
    private long storage;

    public Hardware() {}

    public Hardware(long ram, int cores, long mips, long storage) {
        this.ram = ram;
        this.cores = cores;
        this.mips = mips;
        this.storage = storage;
    }

    public long getStorage() {
        return storage;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public void setStorage(long storage) {
        this.storage = storage;
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
