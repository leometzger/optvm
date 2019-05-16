package optvm.optimization;

public class Environment {

    private final long interactions;
    private final long processingFactor;

    public Environment(long processingFactor, long interactions) {
        this.processingFactor = processingFactor;
        this.interactions = interactions;
    }

    public long getProcessingFactor() {
        return processingFactor;
    }

    public long getInteractions() {
        return interactions;
    }
}
