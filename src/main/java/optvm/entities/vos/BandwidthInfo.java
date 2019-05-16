package optvm.entities.vos;

public class BandwidthInfo {

    private long speed;

    public BandwidthInfo() {
    }

    public BandwidthInfo(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }
}
