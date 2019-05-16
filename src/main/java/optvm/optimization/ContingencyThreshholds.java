package optvm.optimization;

public class ContingencyThreshholds {

    private final long memory;
    private final long cpu;
    private final long storage;
    private final long bandwidth;

    public ContingencyThreshholds(long memory, long cpu, long storage, long bandwidth) {
        this.memory = memory;
        this.cpu = cpu;
        this.storage = storage;
        this.bandwidth = bandwidth;
    }

    public long getMemory() {
        return memory;
    }

    public long getCpu() {
        return cpu;
    }

    public long getStorage() {
        return storage;
    }

    public long getBandwidth() {
        return bandwidth;
    }
}
